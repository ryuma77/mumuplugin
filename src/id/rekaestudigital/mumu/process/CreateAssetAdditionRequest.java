package id.rekaestudigital.mumu.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAsset;
import org.compiere.model.MAssetAddition;
import org.compiere.model.MInvoice;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import id.rekaestudigital.mumu.model.MREDAssetAdditionRequest;

public class CreateAssetAdditionRequest extends SvrProcess {

    private int p_RED_AssetAdditionRequest_ID = 0;

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
            validateRequest(request);

            MInvoice invoice = new MInvoice(getCtx(), request.getC_Invoice_ID(), get_TrxName());
            validateInvoice(invoice);

            BigDecimal amount = invoice.getGrandTotal();

            validateInvoiceNotUsed(invoice.getC_Invoice_ID());

            MAsset asset = createAsset(request, invoice);

            /*
             * Penting:
             * Setelah asset.saveEx(), MAsset.afterSave() seharusnya membuat:
             * - A_Asset_Acct
             * - A_Depreciation_Workfile
             *
             * Kalau tidak terbentuk, biasanya setup A_Asset_Group_Acct belum benar.
             */
            validateAssetWorkfileCreated(asset.getA_Asset_ID());

            MAssetAddition addition = createAssetAddition(request, invoice, asset, amount);

            request.setA_Asset_ID(asset.getA_Asset_ID());
            request.setA_Asset_Addition_ID(addition.getA_Asset_Addition_ID());
            request.setInvoiceGrandTotal(amount);
            request.setProcessed(true);
            request.setPosted(false);
            request.setPostingStatus("Asset Addition completed. Silakan cek posting dari Asset Addition.");
            request.setErrorMsg(null);
            request.saveEx();

            return "Berhasil membuat Asset " + asset.getValue()
                    + " dan Asset Addition " + addition.getDocumentNo();

        } catch (Exception e) {
            request.setErrorMsg(e.getMessage());
            request.saveEx();
            throw e;
        }
    }

    private void validateRequest(MREDAssetAdditionRequest request) {

        if (request.getC_Invoice_ID() <= 0) {
            throw new AdempiereException("Purchase Invoice wajib diisi.");
        }

        if (request.getM_Product_ID() <= 0) {
            throw new AdempiereException("Product sumber credit wajib diisi.");
        }

        if (request.getA_Asset_Group_ID() <= 0) {
            throw new AdempiereException("Asset Group wajib diisi.");
        }

        if (request.getAssetName() == null || request.getAssetName().trim().isEmpty()) {
            throw new AdempiereException("Asset Name wajib diisi.");
        }

        if (request.getDateAcct() == null) {
            throw new AdempiereException("Date Acct wajib diisi.");
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

        BigDecimal amount = invoice.getGrandTotal();

        if (amount == null || amount.signum() <= 0) {
            throw new AdempiereException("Grand Total invoice harus lebih besar dari 0.");
        }
    }

    private void validateInvoiceNotUsed(int C_Invoice_ID) {

        int count = new Query(
                getCtx(),
                MREDAssetAdditionRequest.Table_Name,
                "C_Invoice_ID=? AND Processed='Y' AND RED_AssetAdditionRequest_ID<>?",
                get_TrxName()
        )
                .setParameters(C_Invoice_ID, p_RED_AssetAdditionRequest_ID)
                .count();

        if (count > 0) {
            throw new AdempiereException("Invoice ini sudah pernah dipakai untuk membuat Asset Addition.");
        }
    }

    private MAsset createAsset(MREDAssetAdditionRequest request, MInvoice invoice) {

        MAsset asset = new MAsset(getCtx(), 0, get_TrxName());

        asset.setAD_Org_ID(request.getAD_Org_ID());
        asset.setA_Asset_Group_ID(request.getA_Asset_Group_ID());

        String assetNo = request.getAssetNo();

        if (assetNo == null || assetNo.trim().isEmpty()) {
            assetNo = getNextAssetNo();
        }

        asset.setValue(assetNo);
        asset.setName(request.getAssetName());

        /*
         * Product ini adalah source product / clearing product.
         * Sekalian kita set ke asset agar trace-nya jelas.
         */
        asset.setM_Product_ID(request.getM_Product_ID());

        asset.setC_BPartner_ID(invoice.getC_BPartner_ID());

        if (request.getAssetServiceDate() != null) {
            asset.setAssetServiceDate(request.getAssetServiceDate());
        } else {
            asset.setAssetServiceDate(invoice.getDateInvoiced());
        }

        if (request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
            asset.setDescription(request.getDescription());
        }

        /*
         * Jangan hardcode IsDepreciated / IsOwned di sini.
         * Dari potongan MAsset.afterSave() yang kamu temukan,
         * iDempiere akan mengambil nilai ini dari Asset Group.
         */
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
            MAsset asset,
            BigDecimal amount) {

        MAssetAddition addition = new MAssetAddition(getCtx(), 0, get_TrxName());

        addition.setAD_Org_ID(request.getAD_Org_ID());

        addition.setA_Asset_ID(asset.getA_Asset_ID());

        /*
         * Source credit pakai product.
         */
        addition.setM_Product_ID(request.getM_Product_ID());

        /*
         * Invoice hanya sebagai reference, bukan dari invoice line.
         */
        addition.setC_Invoice_ID(invoice.getC_Invoice_ID());

        if (request.getDateDoc() != null) {
            addition.setDateDoc(request.getDateDoc());
        } else {
            addition.setDateDoc(invoice.getDateInvoiced());
        }

        addition.setDateAcct(request.getDateAcct());

        addition.setAssetAmtEntered(amount);
        addition.setAssetSourceAmt(amount);
        addition.setAssetValueAmt(amount);

        addition.setA_QTY_Current(Env.ONE);

        /*
         * Source Type valid di database kamu:
         * IMP, INV, MAN, PRJ
         *
         * Untuk proses kita, harus MAN.
         */
        addition.setA_SourceType("MAN");

        /*
         * Jangan hardcode PostingType = "A".
         * Ambil dari depreciation workfile asset agar tidak error assetwk null / mismatch.
         */
        addition.setPostingType(getAssetPostingType(asset.getA_Asset_ID()));

        addition.saveEx();

        if (!addition.processIt(DocAction.ACTION_Complete)) {
            throw new AdempiereException("Gagal complete Asset Addition: " + addition.getProcessMsg());
        }

        addition.saveEx();

        return addition;
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

    private String getNextAssetNo() {

        String value = DB.getDocumentNo(getAD_Client_ID(), "A_Asset", get_TrxName());

        if (value == null || value.trim().isEmpty()) {
            value = "AST-" + System.currentTimeMillis();
        }

        return value;
    }
}