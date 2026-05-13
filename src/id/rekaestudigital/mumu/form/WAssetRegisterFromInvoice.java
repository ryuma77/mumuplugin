package id.rekaestudigital.mumu.form;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ProcessUtil;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WStringEditor;
import org.adempiere.webui.panel.ADForm;
import org.compiere.model.MInvoice;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.West;

import id.rekaestudigital.mumu.model.MREDAssetAdditionRequest;

public class WAssetRegisterFromInvoice extends ADForm implements EventListener<Event> {

    private static final long serialVersionUID = 1L;

    private final Properties ctx = Env.getCtx();

    private Borderlayout mainLayout = new Borderlayout();

    private WStringEditor invoiceNoEditor =
            new WStringEditor("InvoiceNo", false, false, true, 30, 30, null, null);

    private Button btnLoadInvoice = new Button("Load Invoice");

    private Label lblInvoiceInfo = new Label("-");
    private Label lblVendorInfo = new Label("-");
    private Label lblStatusInfo = new Label("-");

    private Listbox lstInvoiceLines = new Listbox();
    private Listbox lstSerials = new Listbox();

    private Combobox cmbAssetGroup = new Combobox();

    private WStringEditor assetNameEditor =
            new WStringEditor("AssetName", false, false, true, 120, 120, null, null);

    private WStringEditor assetNoEditor =
            new WStringEditor("AssetNo", false, false, true, 60, 60, null, null);

    /*
     * Pakai WStringEditor dulu untuk amount agar aman dari constructor WNumberEditor
     * yang bisa beda antar versi iDempiere/ZK.
     */
    private WStringEditor assetAmountEditor =
            new WStringEditor("AssetUnitAmount", false, false, true, 30, 30, null, null);

    private WDateEditor assetServiceDateEditor =
            new WDateEditor("AssetServiceDate", false, false, true, "AssetServiceDate");

    private WDateEditor dateAcctEditor =
            new WDateEditor("DateAcct", false, false, true, "DateAcct");

    private Button btnCreate = new Button("Create Asset Register");
    private Button btnClear = new Button("Clear");

    private MInvoice selectedInvoice = null;
    private InvoiceLineRow selectedLine = null;
    private SerialRow selectedSerial = null;

    private boolean serialRequired = false;

    @Override
    protected void initForm() {
        setWidth("100%");
        setHeight("100%");

        buildLayout();
        loadAssetGroups();

        btnLoadInvoice.addEventListener(Events.ON_CLICK, this);
        btnCreate.addEventListener(Events.ON_CLICK, this);
        btnClear.addEventListener(Events.ON_CLICK, this);
        lstInvoiceLines.addEventListener(Events.ON_SELECT, this);
        lstSerials.addEventListener(Events.ON_SELECT, this);

        appendChild(mainLayout);
    }

    private void buildLayout() {

        mainLayout.setWidth("100%");
        mainLayout.setHeight("100%");

        North north = new North();
        north.setHeight("115px");
        north.setCollapsible(false);
        north.appendChild(buildInvoicePanel());
        mainLayout.appendChild(north);

        Center center = new Center();
        center.appendChild(buildCenterPanel());
        mainLayout.appendChild(center);

        South south = new South();
        south.setHeight("160px");
        south.setCollapsible(false);
        south.appendChild(buildAssetPanel());
        mainLayout.appendChild(south);
    }

    private Div buildInvoicePanel() {

        Div div = new Div();
        div.setStyle("padding:10px;");

        Vlayout v = new Vlayout();
        v.setSpacing("6px");

        Hlayout row1 = new Hlayout();
        row1.setSpacing("8px");

        Label lbl = new Label("Invoice No");
        lbl.setStyle("font-weight:bold;");

        invoiceNoEditor.getComponent().setWidth("220px");
        btnLoadInvoice.setStyle("font-weight:bold;");

        row1.appendChild(lbl);
        row1.appendChild(invoiceNoEditor.getComponent());
        row1.appendChild(btnLoadInvoice);

        Hlayout row2 = new Hlayout();
        row2.setSpacing("20px");
        row2.appendChild(new Label("Invoice:"));
        row2.appendChild(lblInvoiceInfo);
        row2.appendChild(new Label("Vendor:"));
        row2.appendChild(lblVendorInfo);
        row2.appendChild(new Label("Status:"));
        row2.appendChild(lblStatusInfo);

        v.appendChild(row1);
        v.appendChild(row2);

        div.appendChild(v);
        return div;
    }

    private Borderlayout buildCenterPanel() {

        Borderlayout layout = new Borderlayout();
        layout.setWidth("100%");
        layout.setHeight("100%");

        West west = new West();
        west.setTitle("Invoice Lines");
        west.setWidth("60%");
        west.setSplittable(true);
        west.setCollapsible(false);
        west.appendChild(buildInvoiceLineList());
        layout.appendChild(west);

        Center center = new Center();
        center.setTitle("Serial Numbers");
        center.appendChild(buildSerialList());
        layout.appendChild(center);

        return layout;
    }

    private Listbox buildInvoiceLineList() {

        lstInvoiceLines.setWidth("100%");
        lstInvoiceLines.setHeight("350px");

        Listhead head = new Listhead();
        head.appendChild(new Listheader("Line"));
        head.appendChild(new Listheader("Product"));
        head.appendChild(new Listheader("Description"));
        head.appendChild(new Listheader("Qty"));
        head.appendChild(new Listheader("Price"));
        head.appendChild(new Listheader("Line Net"));
        head.appendChild(new Listheader("Unit Amount"));

        lstInvoiceLines.appendChild(head);

        return lstInvoiceLines;
    }

    private Listbox buildSerialList() {

        lstSerials.setWidth("100%");
        lstSerials.setHeight("350px");

        Listhead head = new Listhead();
        head.appendChild(new Listheader("Receipt No"));
        head.appendChild(new Listheader("Receipt Line"));
        head.appendChild(new Listheader("Serial No"));
        head.appendChild(new Listheader("Status"));

        lstSerials.appendChild(head);

        return lstSerials;
    }

    private Div buildAssetPanel() {

        Div div = new Div();
        div.setStyle("padding:10px;");

        Vlayout v = new Vlayout();
        v.setSpacing("8px");

        Hlayout row1 = new Hlayout();
        row1.setSpacing("8px");

        cmbAssetGroup.setWidth("250px");
        assetNameEditor.getComponent().setWidth("300px");
        assetNoEditor.getComponent().setWidth("180px");

        row1.appendChild(new Label("Asset Group"));
        row1.appendChild(cmbAssetGroup);
        row1.appendChild(new Label("Asset Name"));
        row1.appendChild(assetNameEditor.getComponent());
        row1.appendChild(new Label("Asset No"));
        row1.appendChild(assetNoEditor.getComponent());

        Hlayout row2 = new Hlayout();
        row2.setSpacing("8px");

        assetAmountEditor.getComponent().setWidth("160px");
        assetServiceDateEditor.getComponent().setWidth("140px");
        dateAcctEditor.getComponent().setWidth("140px");

        row2.appendChild(new Label("Asset Amount"));
        row2.appendChild(assetAmountEditor.getComponent());
        row2.appendChild(new Label("Service Date"));
        row2.appendChild(assetServiceDateEditor.getComponent());
        row2.appendChild(new Label("Date Acct"));
        row2.appendChild(dateAcctEditor.getComponent());

        Hlayout row3 = new Hlayout();
        row3.setSpacing("8px");

        btnCreate.setStyle("font-weight:bold;");
        row3.appendChild(btnCreate);
        row3.appendChild(btnClear);

        v.appendChild(row1);
        v.appendChild(row2);
        v.appendChild(row3);

        div.appendChild(v);
        return div;
    }

    @Override
    public void onEvent(Event event) throws Exception {

        Object target = event.getTarget();

        if (target == btnLoadInvoice) {
            loadInvoice();
        } else if (target == lstInvoiceLines) {
            onSelectInvoiceLine();
        } else if (target == lstSerials) {
            onSelectSerial();
        } else if (target == btnCreate) {
            createAssetRegister();
        } else if (target == btnClear) {
            clearForm();
        }
    }

    private void loadInvoice() {

        String documentNo = getEditorString(invoiceNoEditor);

        if (documentNo.isEmpty()) {
            showError("Isi Invoice No terlebih dahulu.");
            return;
        }

        MInvoice invoice = new Query(ctx, MInvoice.Table_Name,
                "DocumentNo=? AND IsSOTrx='N' AND AD_Client_ID=?",
                null)
                .setParameters(documentNo, Env.getAD_Client_ID(ctx))
                .first();

        if (invoice == null || invoice.get_ID() <= 0) {
            showError("Purchase Invoice tidak ditemukan: " + documentNo);
            return;
        }

        if (!MInvoice.DOCSTATUS_Completed.equals(invoice.getDocStatus())
                && !MInvoice.DOCSTATUS_Closed.equals(invoice.getDocStatus())) {
            showError("Invoice harus Completed atau Closed.");
            return;
        }

        selectedInvoice = invoice;
        selectedLine = null;
        selectedSerial = null;
        serialRequired = false;

        lblInvoiceInfo.setValue(invoice.getDocumentNo()
                + " | Date: " + invoice.getDateInvoiced()
                + " | Grand Total: " + invoice.getGrandTotal());

        lblVendorInfo.setValue(String.valueOf(invoice.getC_BPartner_ID()));
        lblStatusInfo.setValue(invoice.getDocStatus());

        if (invoice.getDateInvoiced() != null) {
            assetServiceDateEditor.setValue(invoice.getDateInvoiced());
        }

        if (invoice.getDateAcct() != null) {
            dateAcctEditor.setValue(invoice.getDateAcct());
        }

        loadInvoiceLines(invoice.getC_Invoice_ID());
        clearSerialList();

        showInfo("Invoice loaded.");
    }

    private void loadInvoiceLines(int C_Invoice_ID) {

        clearInvoiceLineList();

        String sql =
                "SELECT "
              + "    il.C_InvoiceLine_ID, "
              + "    il.Line, "
              + "    il.M_Product_ID, "
              + "    p.Value AS ProductValue, "
              + "    p.Name AS ProductName, "
              + "    il.Description, "
              + "    il.QtyInvoiced, "
              + "    il.PriceActual, "
              + "    il.LineNetAmt "
              + "FROM C_InvoiceLine il "
              + "LEFT JOIN M_Product p ON p.M_Product_ID = il.M_Product_ID "
              + "WHERE il.C_Invoice_ID=? "
              + "AND il.M_Product_ID IS NOT NULL "
              + "ORDER BY il.Line";

        try (PreparedStatement pstmt = DB.prepareStatement(sql, null)) {

            pstmt.setInt(1, C_Invoice_ID);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    InvoiceLineRow row = new InvoiceLineRow();

                    row.C_InvoiceLine_ID = rs.getInt("C_InvoiceLine_ID");
                    row.Line = rs.getInt("Line");
                    row.M_Product_ID = rs.getInt("M_Product_ID");
                    row.ProductValue = rs.getString("ProductValue");
                    row.ProductName = rs.getString("ProductName");
                    row.Description = rs.getString("Description");
                    row.QtyInvoiced = rs.getBigDecimal("QtyInvoiced");
                    row.PriceActual = rs.getBigDecimal("PriceActual");
                    row.LineNetAmt = rs.getBigDecimal("LineNetAmt");
                    row.AssetUnitAmount = calculateUnitAmount(row.LineNetAmt, row.QtyInvoiced);

                    Listitem item = new Listitem();
                    item.setValue(row);

                    item.appendChild(new Listcell(String.valueOf(row.Line)));
                    item.appendChild(new Listcell(nullSafe(row.ProductValue) + " - " + nullSafe(row.ProductName)));
                    item.appendChild(new Listcell(nullSafe(row.Description)));
                    item.appendChild(new Listcell(format(row.QtyInvoiced)));
                    item.appendChild(new Listcell(format(row.PriceActual)));
                    item.appendChild(new Listcell(format(row.LineNetAmt)));
                    item.appendChild(new Listcell(format(row.AssetUnitAmount)));

                    lstInvoiceLines.appendChild(item);
                }
            }

        } catch (Exception e) {
            throw new AdempiereException("Gagal load invoice line: " + e.getMessage(), e);
        }
    }

    private void onSelectInvoiceLine() {

        Listitem item = lstInvoiceLines.getSelectedItem();

        if (item == null) {
            return;
        }

        selectedLine = item.getValue();
        selectedSerial = null;

        if (selectedLine == null) {
            return;
        }

        assetAmountEditor.setValue(selectedLine.AssetUnitAmount == null ? "" : selectedLine.AssetUnitAmount.toPlainString());

        String assetName = getEditorString(assetNameEditor);
        if (assetName.isEmpty()) {
            assetNameEditor.setValue(selectedLine.ProductName);
        }

        loadSerials(selectedLine.C_InvoiceLine_ID);
    }

    private void loadSerials(int C_InvoiceLine_ID) {

        clearSerialList();

        String sql =
                "SELECT "
              + "    s.RED_InOutLineSerial_ID, "
              + "    s.SerNo, "
              + "    s.M_InOutLine_ID, "
              + "    io.DocumentNo AS ReceiptNo, "
              + "    iol.Line AS ReceiptLine, "
              + "    COALESCE(s.IsAssetRegistered,'N') AS IsAssetRegistered "
              + "FROM RED_InOutLineSerial s "
              + "JOIN M_InOutLine iol ON iol.M_InOutLine_ID = s.M_InOutLine_ID "
              + "JOIN M_InOut io ON io.M_InOut_ID = iol.M_InOut_ID "
              + "JOIN M_MatchInv mi ON mi.M_InOutLine_ID = iol.M_InOutLine_ID "
              + "WHERE mi.C_InvoiceLine_ID=? "
              + "AND COALESCE(s.IsAssetRegistered,'N')='N' "
              + "ORDER BY io.DocumentNo, iol.Line, s.SerNo";

        int count = 0;

        try (PreparedStatement pstmt = DB.prepareStatement(sql, null)) {

            pstmt.setInt(1, C_InvoiceLine_ID);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    SerialRow row = new SerialRow();

                    row.RED_InOutLineSerial_ID = rs.getInt("RED_InOutLineSerial_ID");
                    row.SerNo = rs.getString("SerNo");
                    row.M_InOutLine_ID = rs.getInt("M_InOutLine_ID");
                    row.ReceiptNo = rs.getString("ReceiptNo");
                    row.ReceiptLine = rs.getInt("ReceiptLine");
                    row.IsAssetRegistered = rs.getString("IsAssetRegistered");

                    Listitem item = new Listitem();
                    item.setValue(row);

                    item.appendChild(new Listcell(nullSafe(row.ReceiptNo)));
                    item.appendChild(new Listcell(String.valueOf(row.ReceiptLine)));
                    item.appendChild(new Listcell(nullSafe(row.SerNo)));
                    item.appendChild(new Listcell("Y".equals(row.IsAssetRegistered) ? "Registered" : "Available"));

                    lstSerials.appendChild(item);
                    count++;
                }
            }

        } catch (Exception e) {
            throw new AdempiereException("Gagal load serial number: " + e.getMessage(), e);
        }

        serialRequired = count > 0;

        if (serialRequired) {
            showInfo("Invoice line ini memiliki serial number. Pilih salah satu serial number.");
        } else {
            showInfo("Invoice line ini tidak memiliki serial number. Bisa lanjut tanpa serial.");
        }
    }

    private void onSelectSerial() {

        Listitem item = lstSerials.getSelectedItem();

        if (item == null) {
            return;
        }

        selectedSerial = item.getValue();

        if (selectedSerial != null) {

            String assetNo = getEditorString(assetNoEditor);
            if (assetNo.isEmpty()) {
                assetNoEditor.setValue(selectedSerial.SerNo);
            }

            String assetName = getEditorString(assetNameEditor);
            if (assetName.isEmpty()) {
                assetNameEditor.setValue(
                        selectedLine != null
                                ? selectedLine.ProductName + " - " + selectedSerial.SerNo
                                : selectedSerial.SerNo
                );
            }
        }
    }

    private void createAssetRegister() {

        validateBeforeCreate();

        String trxName = Trx.createTrxName("AssetRegisterFromInvoice");
        Trx trx = Trx.get(trxName, true);

        try {
            MREDAssetAdditionRequest request = createRequestRecord(trxName);

            runCreateAssetAdditionProcess(request.get_ID(), trx);

            trx.commit();

            showInfo("Asset Register berhasil dibuat. Request ID: " + request.get_ID());

            clearAfterSuccess();

        } catch (Exception e) {
            trx.rollback();
            throw new AdempiereException("Gagal create asset register: " + e.getMessage(), e);
        } finally {
            trx.close();
        }
    }

    private void validateBeforeCreate() {

        if (selectedInvoice == null || selectedInvoice.get_ID() <= 0) {
            throw new AdempiereException("Invoice belum dipilih.");
        }

        if (selectedLine == null || selectedLine.C_InvoiceLine_ID <= 0) {
            throw new AdempiereException("Invoice Line belum dipilih.");
        }

        if (serialRequired && selectedSerial == null) {
            throw new AdempiereException("Invoice Line ini memiliki serial number. Pilih serial number terlebih dahulu.");
        }

        int assetGroupId = getSelectedAssetGroupId();

        if (assetGroupId <= 0) {
            throw new AdempiereException("Asset Group wajib dipilih.");
        }

        if (getEditorString(assetNameEditor).isEmpty()) {
            throw new AdempiereException("Asset Name wajib diisi.");
        }

        BigDecimal amount = getEditorBigDecimal(assetAmountEditor);

        if (amount == null || amount.signum() <= 0) {
            throw new AdempiereException("Asset Amount harus lebih besar dari 0.");
        }

        if (getEditorTimestamp(dateAcctEditor) == null) {
            throw new AdempiereException("Date Acct wajib diisi.");
        }
    }

    private MREDAssetAdditionRequest createRequestRecord(String trxName) {

        MREDAssetAdditionRequest request = new MREDAssetAdditionRequest(ctx, 0, trxName);

        request.setAD_Org_ID(selectedInvoice.getAD_Org_ID());
        request.setC_Invoice_ID(selectedInvoice.getC_Invoice_ID());
        request.setDateDoc(selectedInvoice.getDateInvoiced());
        request.setDateAcct(getEditorTimestamp(dateAcctEditor));
        request.setA_Asset_Group_ID(getSelectedAssetGroupId());

        Timestamp serviceDate = getEditorTimestamp(assetServiceDateEditor);

        if (serviceDate != null) {
            request.setAssetServiceDate(serviceDate);
        } else {
            request.setAssetServiceDate(selectedInvoice.getDateInvoiced());
        }

        request.set_ValueOfColumn("AssetName", getEditorString(assetNameEditor));

        String assetNo = getEditorString(assetNoEditor);
        if (!assetNo.isEmpty()) {
            request.set_ValueOfColumn("AssetNo", assetNo);
        }

        request.set_ValueOfColumn("C_InvoiceLine_ID", selectedLine.C_InvoiceLine_ID);
        request.set_ValueOfColumn("M_Product_ID", selectedLine.M_Product_ID);
        request.set_ValueOfColumn("Qty", selectedLine.QtyInvoiced);
        request.set_ValueOfColumn("PriceActual", selectedLine.PriceActual);
        request.set_ValueOfColumn("LineNetAmt", selectedLine.LineNetAmt);
        request.set_ValueOfColumn("AssetUnitAmount", getEditorBigDecimal(assetAmountEditor));

        if (selectedSerial != null) {
            request.set_ValueOfColumn("RED_InOutLineSerial_ID", selectedSerial.RED_InOutLineSerial_ID);
            request.set_ValueOfColumn("M_InOutLine_ID", selectedSerial.M_InOutLine_ID);
            request.set_ValueOfColumn("SerNo", selectedSerial.SerNo);
        }

        request.saveEx();

        return request;
    }

    private void runCreateAssetAdditionProcess(int recordId, Trx trx) {

        String className = "id.rekaestudigital.mumu.process.CreateAssetAdditionRequest";

        int AD_Process_ID = DB.getSQLValueEx(
                trx.getTrxName(),
                "SELECT AD_Process_ID "
              + "FROM AD_Process "
              + "WHERE Classname=? "
              + "AND IsActive='Y'",
                className
        );

        if (AD_Process_ID <= 0) {
            throw new AdempiereException("Process CreateAssetAdditionRequest belum terdaftar di AD_Process.");
        }

        ProcessInfo pi = new ProcessInfo("Create Asset Addition Request", AD_Process_ID);
        pi.setRecord_ID(recordId);
        pi.setAD_Client_ID(Env.getAD_Client_ID(ctx));
        pi.setAD_User_ID(Env.getAD_User_ID(ctx));

        /*
         * Penting:
         * Di environment kamu, className harus di-set manual.
         * Kalau tidak, ProcessUtil akan error className null.
         */
        pi.setClassName(className);

        ProcessUtil.startJavaProcess(ctx, pi, trx);

        if (pi.isError()) {
            String msg = pi.getSummary();

            if (msg == null || msg.trim().isEmpty()) {
                msg = "Process CreateAssetAdditionRequest gagal.";
            }

            throw new AdempiereException(msg);
        }
    }

    private void loadAssetGroups() {

        cmbAssetGroup.getItems().clear();

        String sql =
                "SELECT A_Asset_Group_ID, Name "
              + "FROM A_Asset_Group "
              + "WHERE IsActive='Y' "
              + "AND AD_Client_ID IN (0, ?) "
              + "ORDER BY Name";

        try (PreparedStatement pstmt = DB.prepareStatement(sql, null)) {

            pstmt.setInt(1, Env.getAD_Client_ID(ctx));

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Comboitem item = new Comboitem();
                    item.setLabel(rs.getString("Name"));
                    item.setValue(rs.getInt("A_Asset_Group_ID"));
                    cmbAssetGroup.appendChild(item);
                }
            }

        } catch (Exception e) {
            throw new AdempiereException("Gagal load Asset Group: " + e.getMessage(), e);
        }
    }

    private int getSelectedAssetGroupId() {

        Comboitem item = cmbAssetGroup.getSelectedItem();

        if (item == null || item.getValue() == null) {
            return 0;
        }

        Object value = item.getValue();

        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        return Integer.parseInt(value.toString());
    }

    private BigDecimal calculateUnitAmount(BigDecimal lineNetAmt, BigDecimal qty) {

        if (lineNetAmt == null || qty == null || qty.signum() == 0) {
            return Env.ZERO;
        }

        return lineNetAmt.divide(qty, 2, RoundingMode.HALF_UP);
    }

    private void clearInvoiceLineList() {

        while (lstInvoiceLines.getItemCount() > 0) {
            lstInvoiceLines.removeItemAt(0);
        }
    }

    private void clearSerialList() {

        while (lstSerials.getItemCount() > 0) {
            lstSerials.removeItemAt(0);
        }

        selectedSerial = null;
        serialRequired = false;
    }

    private void clearForm() {

        selectedInvoice = null;
        selectedLine = null;
        selectedSerial = null;
        serialRequired = false;

        invoiceNoEditor.setValue("");
        lblInvoiceInfo.setValue("-");
        lblVendorInfo.setValue("-");
        lblStatusInfo.setValue("-");

        clearInvoiceLineList();
        clearSerialList();

        assetNameEditor.setValue("");
        assetNoEditor.setValue("");
        assetAmountEditor.setValue("");
        assetServiceDateEditor.setValue(null);
        dateAcctEditor.setValue(null);

        cmbAssetGroup.setSelectedItem(null);
    }

    private void clearAfterSuccess() {

        selectedLine = null;
        selectedSerial = null;
        serialRequired = false;

        clearInvoiceLineList();
        clearSerialList();

        assetNameEditor.setValue("");
        assetNoEditor.setValue("");
        assetAmountEditor.setValue("");

        /*
         * Invoice tetap dipertahankan agar user bisa load ulang invoice yang sama
         * untuk serial berikutnya.
         */
        if (selectedInvoice != null) {
            loadInvoiceLines(selectedInvoice.getC_Invoice_ID());
        }
    }

    private String format(BigDecimal value) {

        if (value == null) {
            return "";
        }

        return value.toPlainString();
    }

    private String nullSafe(String value) {

        return value == null ? "" : value;
    }

    private void showInfo(String message) {
        lblStatusInfo.setValue(message);
    }

    private void showError(String message) {
        lblStatusInfo.setValue("ERROR: " + message);
    }

    private String getEditorString(WStringEditor editor) {

        Object value = editor.getValue();

        if (value == null) {
            return "";
        }

        return value.toString().trim();
    }

    private BigDecimal getEditorBigDecimal(WStringEditor editor) {

        Object value = editor.getValue();

        if (value == null) {
            return null;
        }

        String text = value.toString().trim();

        if (text.isEmpty()) {
            return null;
        }

        return new BigDecimal(text.replace(",", ""));
    }

    private Timestamp getEditorTimestamp(WDateEditor editor) {

        Object value = editor.getValue();

        if (value == null) {
            return null;
        }

        if (value instanceof Timestamp) {
            return (Timestamp) value;
        }

        if (value instanceof java.util.Date) {
            return new Timestamp(((java.util.Date) value).getTime());
        }

        throw new AdempiereException("Tanggal tidak valid: " + value);
    }

    private static class InvoiceLineRow {
        int C_InvoiceLine_ID;
        int Line;
        int M_Product_ID;
        String ProductValue;
        String ProductName;
        String Description;
        BigDecimal QtyInvoiced;
        BigDecimal PriceActual;
        BigDecimal LineNetAmt;
        BigDecimal AssetUnitAmount;
    }

    private static class SerialRow {
        int RED_InOutLineSerial_ID;
        String SerNo;
        int M_InOutLine_ID;
        String ReceiptNo;
        int ReceiptLine;
        String IsAssetRegistered;
    }
}