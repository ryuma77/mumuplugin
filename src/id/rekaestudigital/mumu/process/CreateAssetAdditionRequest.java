package id.rekaestudigital.mumu.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAsset;
import org.compiere.model.MAssetAddition;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import id.rekaestudigital.mumu.model.MREDAssetAdditionRequest;

public class CreateAssetAdditionRequest extends SvrProcess {

    private int p_RED_AssetAdditionRequest_ID = 0;

    private static final String REGISTER_TYPE_INVOICE = "INV";
    private static final String REGISTER_TYPE_MANUAL = "MAN";

    @Override
    protected void prepare() {
        p_RED_AssetAdditionRequest_ID = getRecord_ID();
    }

    @Override
    protected String doIt() throws Exception {

        if (p_RED_AssetAdditionRequest_ID <= 0) {
            throw new AdempiereException("Record RED Asset Addition Request tidak ditemukan.");
        }

        MREDAssetAdditionRequest request =
                new MREDAssetAdditionRequest(getCtx(), p_RED_AssetAdditionRequest_ID, get_TrxName());

        if (request.get_ID() <= 0) {
            throw new AdempiereException("Request tidak valid.");
        }

        if (request.isProcessed()) {
            throw new AdempiereException("Request ini sudah diproses.");
        }

        try {
            String registerType = getRegisterType(request);

            validateBasicRequest(request, registerType);

            MInvoice invoice = null;
            MInvoiceLine invoiceLine = null;
            SerialInfo serialInfo = null;

            if (REGISTER_TYPE_INVOICE.equals(registerType)) {

                invoice = new MInvoice(getCtx(), request.getC_Invoice_ID(), get_TrxName());
                validateInvoice(invoice);

                int C_InvoiceLine_ID = getInt(request, "C_InvoiceLine_ID");

                invoiceLine = new MInvoiceLine(getCtx(), C_InvoiceLine_ID, get_TrxName());
                validateInvoiceLine(request, invoice, invoiceLine);

                int serialId = getInt(request, "RED_InOutLineSerial_ID");

                validateSerialRequirement(invoiceLine.getC_InvoiceLine_ID(), serialId);

                if (serialId > 0) {
                    serialInfo = validateAndGetSerialInfo(serialId, invoiceLine.getC_InvoiceLine_ID());
                }

            } else if (REGISTER_TYPE_MANUAL.equals(registerType)) {

                int manualSerialId = getInt(request, "RED_InOutLineSerial_ID");

                if (manualSerialId > 0) {
                    serialInfo = validateAndGetSerialInfoManual(manualSerialId);
                }
            }

            BigDecimal amount = getAssetAmount(request, invoiceLine);

            syncRequestFromSource(request, registerType, invoiceLine, serialInfo, amount);

            MAsset asset = createAsset(request, invoice, invoiceLine, serialInfo);

            validateAssetWorkfileCreated(asset.getA_Asset_ID());

            MAssetAddition addition = createAssetAddition(
                    request,
                    invoice,
                    invoiceLine,
                    asset,
                    amount,
                    serialInfo
            );

            if (serialInfo != null) {
                updateSerialRegistered(request, asset, addition, serialInfo);
            }

            setIfColumnExists(request, "A_Asset_ID", asset.getA_Asset_ID());
            setIfColumnExists(request, "A_Asset_Addition_ID", addition.getA_Asset_Addition_ID());
            setIfColumnExists(request, "Processed", "Y");
            setIfColumnExists(request, "AssetAdditionPosted", addition.isPosted() ? "Y" : "N");
            setIfColumnExists(request, "PostingStatus",
                    addition.isPosted()
                            ? "Asset Addition completed and posted."
                            : "Asset Addition completed. Posting belum terbentuk atau belum dijalankan.");
            setIfColumnExists(request, "ErrorMsg", null);

            request.saveEx();

            return "Berhasil membuat Asset " + asset.getValue()
                    + " dan Asset Addition " + addition.getDocumentNo();

        } catch (Exception e) {

            try {
                setIfColumnExists(request, "ErrorMsg", cut(e.getMessage(), 1900));
                request.saveEx();
            } catch (Exception ignored) {
                // Jangan menutup error utama
            }

            throw e;
        }
    }

    private String getRegisterType(MREDAssetAdditionRequest request) {

        String registerType = getString(request, "RegisterType");

        if (registerType == null || registerType.trim().isEmpty()) {
            if (request.getC_Invoice_ID() > 0) {
                return REGISTER_TYPE_INVOICE;
            }

            return REGISTER_TYPE_MANUAL;
        }

        registerType = registerType.trim().toUpperCase();

        if ("INVOICE".equals(registerType)) {
            return REGISTER_TYPE_INVOICE;
        }

        if ("MANUAL".equals(registerType)) {
            return REGISTER_TYPE_MANUAL;
        }

        return registerType;
    }

    private void validateBasicRequest(MREDAssetAdditionRequest request, String registerType) {

        if (!REGISTER_TYPE_INVOICE.equals(registerType)
                && !REGISTER_TYPE_MANUAL.equals(registerType)) {
            throw new AdempiereException("Register Type tidak valid. Gunakan INV atau MAN.");
        }

        if (REGISTER_TYPE_INVOICE.equals(registerType)) {

            if (request.getC_Invoice_ID() <= 0) {
                throw new AdempiereException("Purchase Invoice wajib diisi untuk mode Invoice.");
            }

            if (getInt(request, "C_InvoiceLine_ID") <= 0) {
                throw new AdempiereException("Invoice Line wajib dipilih untuk mode Invoice.");
            }
        }

        if (getSourceProductId(request, null) <= 0) {
            throw new AdempiereException("Product wajib diisi.");
        }

        if (request.getA_Asset_Group_ID() <= 0) {
            throw new AdempiereException("Asset Group wajib diisi.");
        }

        String assetName = getString(request, "AssetName");

        if (assetName == null || assetName.trim().isEmpty()) {
            throw new AdempiereException("Asset Name wajib diisi.");
        }

        if (request.getDateAcct() == null) {
            throw new AdempiereException("Date Acct wajib diisi.");
        }

        BigDecimal amount = getBigDecimal(request, "AssetUnitAmount");

        if (amount == null || amount.signum() <= 0) {
            throw new AdempiereException("Asset Amount harus lebih besar dari 0.");
        }
    }

    private void validateInvoice(MInvoice invoice) {

        if (invoice == null || invoice.get_ID() <= 0) {
            throw new AdempiereException("Invoice tidak ditemukan.");
        }

        if (invoice.isSOTrx()) {
            throw new AdempiereException("Invoice harus Purchase Invoice, bukan Sales Invoice.");
        }

        if (!MInvoice.DOCSTATUS_Completed.equals(invoice.getDocStatus())
                && !MInvoice.DOCSTATUS_Closed.equals(invoice.getDocStatus())) {
            throw new AdempiereException("Invoice harus Completed atau Closed.");
        }
    }

    private void validateInvoiceLine(
            MREDAssetAdditionRequest request,
            MInvoice invoice,
            MInvoiceLine invoiceLine) {

        if (invoiceLine == null || invoiceLine.get_ID() <= 0) {
            throw new AdempiereException("Invoice Line tidak ditemukan.");
        }

        if (invoiceLine.getC_Invoice_ID() != invoice.getC_Invoice_ID()) {
            throw new AdempiereException("Invoice Line tidak berasal dari Invoice yang dipilih.");
        }

        if (invoiceLine.getM_Product_ID() <= 0) {
            throw new AdempiereException("Invoice Line harus memiliki Product.");
        }

        if (invoiceLine.getQtyInvoiced() == null || invoiceLine.getQtyInvoiced().signum() <= 0) {
            throw new AdempiereException("Qty Invoice Line harus lebih besar dari 0.");
        }

        if (invoiceLine.getLineNetAmt() == null || invoiceLine.getLineNetAmt().signum() <= 0) {
            throw new AdempiereException("Line Net Amount harus lebih besar dari 0.");
        }
    }

    private void validateSerialRequirement(int C_InvoiceLine_ID, int RED_InOutLineSerial_ID) {

        int serialCount = DB.getSQLValueEx(
                get_TrxName(),
                "SELECT COUNT(*) "
              + "FROM RED_InOutLineSerial s "
              + "JOIN M_MatchInv mi ON mi.M_InOutLine_ID = s.M_InOutLine_ID "
              + "WHERE mi.C_InvoiceLine_ID=?",
                C_InvoiceLine_ID
        );

        if (serialCount > 0 && RED_InOutLineSerial_ID <= 0) {
            throw new AdempiereException(
                    "Invoice Line ini memiliki serial number dari receipt. Pilih serial number terlebih dahulu."
            );
        }
    }

    private SerialInfo validateAndGetSerialInfo(int RED_InOutLineSerial_ID, int C_InvoiceLine_ID) {

        String sql =
                "SELECT "
              + "    s.RED_InOutLineSerial_ID, "
              + "    s.SerNo, "
              + "    s.M_InOutLine_ID, "
              + "    s.M_Product_ID, "
              + "    COALESCE(s.IsAssetRegistered,'N') AS IsAssetRegistered "
              + "FROM RED_InOutLineSerial s "
              + "JOIN M_MatchInv mi ON mi.M_InOutLine_ID = s.M_InOutLine_ID "
              + "WHERE s.RED_InOutLineSerial_ID=? "
              + "AND mi.C_InvoiceLine_ID=?";

        SerialInfo info = null;

        try (PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName())) {

            pstmt.setInt(1, RED_InOutLineSerial_ID);
            pstmt.setInt(2, C_InvoiceLine_ID);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    info = new SerialInfo();

                    info.RED_InOutLineSerial_ID = rs.getInt("RED_InOutLineSerial_ID");
                    info.SerialNo = rs.getString("SerNo");
                    info.M_InOutLine_ID = rs.getInt("M_InOutLine_ID");
                    info.M_Product_ID = rs.getInt("M_Product_ID");
                    info.IsAssetRegistered = rs.getString("IsAssetRegistered");
                }
            }

        } catch (Exception e) {
            throw new AdempiereException("Gagal membaca Serial Number: " + e.getMessage(), e);
        }

        validateSerialInfo(info);

        return info;
    }

    private SerialInfo validateAndGetSerialInfoManual(int RED_InOutLineSerial_ID) {

        String sql =
                "SELECT "
              + "    s.RED_InOutLineSerial_ID, "
              + "    s.SerNo, "
              + "    s.M_InOutLine_ID, "
              + "    s.M_Product_ID, "
              + "    COALESCE(s.IsAssetRegistered,'N') AS IsAssetRegistered "
              + "FROM RED_InOutLineSerial s "
              + "WHERE s.RED_InOutLineSerial_ID=?";

        SerialInfo info = null;

        try (PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName())) {

            pstmt.setInt(1, RED_InOutLineSerial_ID);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    info = new SerialInfo();

                    info.RED_InOutLineSerial_ID = rs.getInt("RED_InOutLineSerial_ID");
                    info.SerialNo = rs.getString("SerNo");
                    info.M_InOutLine_ID = rs.getInt("M_InOutLine_ID");
                    info.M_Product_ID = rs.getInt("M_Product_ID");
                    info.IsAssetRegistered = rs.getString("IsAssetRegistered");
                }
            }

        } catch (Exception e) {
            throw new AdempiereException("Gagal membaca Serial Number Manual: " + e.getMessage(), e);
        }

        validateSerialInfo(info);

        return info;
    }

    private void validateSerialInfo(SerialInfo info) {

        if (info == null) {
            throw new AdempiereException("Serial number tidak valid.");
        }

        if (info.SerialNo == null || info.SerialNo.trim().isEmpty()) {
            throw new AdempiereException("Serial No kosong.");
        }

        if ("Y".equals(info.IsAssetRegistered)) {
            throw new AdempiereException("Serial number ini sudah pernah dibuatkan asset: " + info.SerialNo);
        }
    }

    private BigDecimal getAssetAmount(MREDAssetAdditionRequest request, MInvoiceLine invoiceLine) {

        BigDecimal assetUnitAmount = getBigDecimal(request, "AssetUnitAmount");

        if (assetUnitAmount != null && assetUnitAmount.signum() > 0) {
            return assetUnitAmount;
        }

        if (invoiceLine != null) {

            BigDecimal qty = invoiceLine.getQtyInvoiced();
            BigDecimal lineNetAmt = invoiceLine.getLineNetAmt();

            if (qty != null
                    && qty.signum() > 0
                    && lineNetAmt != null
                    && lineNetAmt.signum() > 0) {
                return lineNetAmt.divide(qty, 2, RoundingMode.HALF_UP);
            }
        }

        throw new AdempiereException("Asset Amount harus lebih besar dari 0.");
    }

    private void syncRequestFromSource(
            MREDAssetAdditionRequest request,
            String registerType,
            MInvoiceLine invoiceLine,
            SerialInfo serialInfo,
            BigDecimal amount) {

        setIfColumnExists(request, "RegisterType", registerType);

        if (invoiceLine != null) {
            setIfColumnExists(request, "M_Product_ID", invoiceLine.getM_Product_ID());
            setIfColumnExists(request, "Qty", invoiceLine.getQtyInvoiced());
            setIfColumnExists(request, "PriceActual", invoiceLine.getPriceActual());
            setIfColumnExists(request, "LineNetAmt", invoiceLine.getLineNetAmt());
        }

        setIfColumnExists(request, "AssetUnitAmount", amount);

        if (serialInfo != null) {
            setIfColumnExists(request, "RED_InOutLineSerial_ID", serialInfo.RED_InOutLineSerial_ID);
            setIfColumnExists(request, "M_InOutLine_ID", serialInfo.M_InOutLine_ID);
            setIfColumnExists(request, "SerNo", serialInfo.SerialNo);

            if (getInt(request, "M_Product_ID") <= 0 && serialInfo.M_Product_ID > 0) {
                setIfColumnExists(request, "M_Product_ID", serialInfo.M_Product_ID);
            }
        }
    }

    private MAsset createAsset(
            MREDAssetAdditionRequest request,
            MInvoice invoice,
            MInvoiceLine invoiceLine,
            SerialInfo serialInfo) {

        MAsset asset = new MAsset(getCtx(), 0, get_TrxName());

        asset.setAD_Org_ID(request.getAD_Org_ID());
        asset.setA_Asset_Group_ID(request.getA_Asset_Group_ID());

        int M_Product_ID = getSourceProductId(request, invoiceLine);

        String assetNo = getString(request, "AssetNo");

        if (assetNo == null || assetNo.trim().isEmpty()) {
            assetNo = generateAssetNo(
                    M_Product_ID,
                    request.getA_Asset_Group_ID(),
                    request.getDateAcct()
            );
        }

        asset.setValue(assetNo);
        asset.setName(getString(request, "AssetName"));
        asset.setM_Product_ID(M_Product_ID);

        if (invoice != null && invoice.getC_BPartner_ID() > 0) {
            asset.setC_BPartner_ID(invoice.getC_BPartner_ID());
        }

        if (request.getAssetServiceDate() != null) {
            asset.setAssetServiceDate(request.getAssetServiceDate());
        } else if (invoice != null && invoice.getDateInvoiced() != null) {
            asset.setAssetServiceDate(invoice.getDateInvoiced());
        } else {
            asset.setAssetServiceDate(request.getDateAcct());
        }

        if (request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
            asset.setDescription(request.getDescription());
        }

        if (serialInfo != null && serialInfo.SerialNo != null && !serialInfo.SerialNo.trim().isEmpty()) {
            asset.setSerNo(serialInfo.SerialNo);
        } else {
            String serNo = getString(request, "SerNo");

            if (serNo != null && !serNo.trim().isEmpty()) {
                asset.setSerNo(serNo);
            }
        }

        if (serialInfo != null && serialInfo.M_InOutLine_ID > 0 && columnExists(asset, "M_InOutLine_ID")) {
            asset.set_ValueOfColumn("M_InOutLine_ID", serialInfo.M_InOutLine_ID);
        } else if (getInt(request, "M_InOutLine_ID") > 0 && columnExists(asset, "M_InOutLine_ID")) {
            asset.set_ValueOfColumn("M_InOutLine_ID", getInt(request, "M_InOutLine_ID"));
        }

        asset.saveEx();

        return asset;
    }

    private void validateAssetWorkfileCreated(int A_Asset_ID) {

        int count = DB.getSQLValueEx(
                get_TrxName(),
                "SELECT COUNT(*) "
              + "FROM A_Depreciation_Workfile "
              + "WHERE A_Asset_ID=?",
                A_Asset_ID
        );

        if (count <= 0) {
            throw new AdempiereException(
                    "Asset berhasil dibuat, tetapi Depreciation Workfile tidak terbentuk. "
                  + "Cek setup Asset Group Accounting: A_Asset_Group_Acct harus ada, "
                  + "AD_Org_ID harus 0 atau sama dengan org asset, dan accounting schema harus valid."
            );
        }
    }

    private MAssetAddition createAssetAddition(
            MREDAssetAdditionRequest request,
            MInvoice invoice,
            MInvoiceLine invoiceLine,
            MAsset asset,
            BigDecimal amount,
            SerialInfo serialInfo) {

        MAssetAddition addition = new MAssetAddition(getCtx(), 0, get_TrxName());

        addition.setAD_Org_ID(request.getAD_Org_ID());
        addition.setA_Asset_ID(asset.getA_Asset_ID());

        int M_Product_ID = getSourceProductId(request, invoiceLine);
        addition.setM_Product_ID(M_Product_ID);

        if (invoice != null && invoice.getC_Invoice_ID() > 0) {
            addition.setC_Invoice_ID(invoice.getC_Invoice_ID());
        }

        if (invoiceLine != null && columnExists(addition, "C_InvoiceLine_ID")) {
            addition.set_ValueOfColumn("C_InvoiceLine_ID", invoiceLine.getC_InvoiceLine_ID());
        }

        if (serialInfo != null && serialInfo.M_InOutLine_ID > 0 && columnExists(addition, "M_InOutLine_ID")) {
            addition.set_ValueOfColumn("M_InOutLine_ID", serialInfo.M_InOutLine_ID);
        } else if (getInt(request, "M_InOutLine_ID") > 0 && columnExists(addition, "M_InOutLine_ID")) {
            addition.set_ValueOfColumn("M_InOutLine_ID", getInt(request, "M_InOutLine_ID"));
        }

        if (request.getDateDoc() != null) {
            addition.setDateDoc(request.getDateDoc());
        } else if (invoice != null && invoice.getDateInvoiced() != null) {
            addition.setDateDoc(invoice.getDateInvoiced());
        } else {
            addition.setDateDoc(request.getDateAcct());
        }

        addition.setDateAcct(request.getDateAcct());

        addition.setAssetAmtEntered(amount);
        addition.setAssetSourceAmt(amount);
        addition.setAssetValueAmt(amount);

        addition.setA_QTY_Current(Env.ONE);

        addition.setA_SourceType("MAN");
        addition.setPostingType(getAssetPostingType(asset.getA_Asset_ID()));

        addition.saveEx();

        if (!addition.processIt(DocAction.ACTION_Complete)) {
            throw new AdempiereException("Gagal complete Asset Addition: " + addition.getProcessMsg());
        }

        addition.saveEx();

        return addition;
    }

    private int getSourceProductId(MREDAssetAdditionRequest request, MInvoiceLine invoiceLine) {

        if (invoiceLine != null && invoiceLine.getM_Product_ID() > 0) {
            return invoiceLine.getM_Product_ID();
        }

        int M_Product_ID = getInt(request, "M_Product_ID");

        if (M_Product_ID > 0) {
            return M_Product_ID;
        }

        return 0;
    }

    private String getAssetPostingType(int A_Asset_ID) {

        String postingType = DB.getSQLValueStringEx(
                get_TrxName(),
                "SELECT PostingType "
              + "FROM A_Depreciation_Workfile "
              + "WHERE A_Asset_ID=? "
              + "ORDER BY CASE WHEN PostingType='A' THEN 0 ELSE 1 END, A_Depreciation_Workfile_ID",
                A_Asset_ID
        );

        if (postingType == null || postingType.trim().isEmpty()) {
            throw new AdempiereException(
                    "Depreciation Workfile belum terbentuk untuk Asset ID " + A_Asset_ID
                  + ". Cek setup Asset Group Accounting."
            );
        }

        return postingType;
    }

    private void updateSerialRegistered(
            MREDAssetAdditionRequest request,
            MAsset asset,
            MAssetAddition addition,
            SerialInfo serialInfo) {

        int updated = DB.executeUpdateEx(
                "UPDATE RED_InOutLineSerial "
              + "SET IsAssetRegistered='Y', "
              + "    Processed='Y', "
              + "    RED_AssetAdditionRequest_ID=?, "
              + "    A_Asset_ID=?, "
              + "    A_Asset_Addition_ID=?, "
              + "    Updated=now(), "
              + "    UpdatedBy=? "
              + "WHERE RED_InOutLineSerial_ID=? "
              + "AND COALESCE(IsAssetRegistered,'N')='N'",
                new Object[] {
                        request.get_ID(),
                        asset.getA_Asset_ID(),
                        addition.getA_Asset_Addition_ID(),
                        getAD_User_ID(),
                        serialInfo.RED_InOutLineSerial_ID
                },
                get_TrxName()
        );

        if (updated <= 0) {
            throw new AdempiereException(
                    "Serial number gagal diupdate atau sudah dipakai asset lain: " + serialInfo.SerialNo
            );
        }
    }

    private String generateAssetNo(int M_Product_ID, int A_Asset_Group_ID, Timestamp dateAcct) {

        if (M_Product_ID <= 0) {
            throw new AdempiereException("Product wajib diisi untuk generate Asset No.");
        }

        if (A_Asset_Group_ID <= 0) {
            throw new AdempiereException("Asset Group wajib diisi untuk generate Asset No.");
        }

        if (dateAcct == null) {
            throw new AdempiereException("Date Acct wajib diisi untuk generate Asset No.");
        }

        String productCategoryValue = DB.getSQLValueStringEx(
                get_TrxName(),
                "SELECT pc.Value "
              + "FROM M_Product p "
              + "JOIN M_Product_Category pc ON pc.M_Product_Category_ID = p.M_Product_Category_ID "
              + "WHERE p.M_Product_ID=?",
                M_Product_ID
        );

        if (productCategoryValue == null || productCategoryValue.trim().isEmpty()) {
            throw new AdempiereException("Value Product Category belum diisi.");
        }

        String assetGroupName = DB.getSQLValueStringEx(
                get_TrxName(),
                "SELECT Name "
              + "FROM A_Asset_Group "
              + "WHERE A_Asset_Group_ID=?",
                A_Asset_Group_ID
        );

        if (assetGroupName == null || assetGroupName.trim().isEmpty()) {
            throw new AdempiereException("Name Asset Group belum diisi.");
        }

        String yy = new SimpleDateFormat("yy").format(dateAcct);

        productCategoryValue = cleanCodePart(productCategoryValue);
        assetGroupName = cleanCodePart(assetGroupName);

        String prefix = productCategoryValue + "-" + yy + "-" + assetGroupName + "-";

        int nextNo = getNextAssetRunningNo(prefix);

        return prefix + String.format("%05d", nextNo);
    }

    private int getNextAssetRunningNo(String prefix) {

        String lastValue = DB.getSQLValueStringEx(
                get_TrxName(),
                "SELECT Value "
              + "FROM A_Asset "
              + "WHERE AD_Client_ID=? "
              + "AND Value LIKE ? "
              + "ORDER BY Value DESC "
              + "LIMIT 1",
                getAD_Client_ID(),
                prefix + "%"
        );

        if (lastValue == null || lastValue.trim().isEmpty()) {
            return 1;
        }

        if (!lastValue.startsWith(prefix)) {
            return 1;
        }

        String numberPart = lastValue.substring(prefix.length());

        try {
            return Integer.parseInt(numberPart) + 1;
        } catch (Exception e) {
            return 1;
        }
    }

    private String cleanCodePart(String value) {

        if (value == null) {
            return "";
        }

        return value.trim()
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "");
    }

    private int getInt(PO po, String columnName) {

        if (!columnExists(po, columnName)) {
            return 0;
        }

        Object value = po.get_Value(columnName);

        if (value == null) {
            return 0;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        String text = value.toString();

        if (text.trim().isEmpty()) {
            return 0;
        }

        return Integer.parseInt(text);
    }

    private BigDecimal getBigDecimal(PO po, String columnName) {

        if (!columnExists(po, columnName)) {
            return null;
        }

        Object value = po.get_Value(columnName);

        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        }

        String text = value.toString();

        if (text.trim().isEmpty()) {
            return null;
        }

        return new BigDecimal(text);
    }

    private String getString(PO po, String columnName) {

        if (!columnExists(po, columnName)) {
            return null;
        }

        Object value = po.get_Value(columnName);

        if (value == null) {
            return null;
        }

        return value.toString();
    }

    private void setIfColumnExists(PO po, String columnName, Object value) {

        if (columnExists(po, columnName)) {
            po.set_ValueOfColumn(columnName, value);
        }
    }

    private boolean columnExists(PO po, String columnName) {

        return po.get_ColumnIndex(columnName) >= 0;
    }

    private String cut(String value, int maxLength) {

        if (value == null) {
            return null;
        }

        if (value.length() <= maxLength) {
            return value;
        }

        return value.substring(0, maxLength);
    }

    private static class SerialInfo {
        int RED_InOutLineSerial_ID;
        int M_InOutLine_ID;
        int M_Product_ID;
        String SerialNo;
        String IsAssetRegistered;
    }
}