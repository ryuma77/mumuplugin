package id.rekaestudigital.mumu.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MInvoice;

public class CalloutAssetAdditionRequestInvoice implements IColumnCallout {

    @Override
    public String start(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {

        if (value == null)
            return "";

        int C_Invoice_ID = ((Number) value).intValue();

        if (C_Invoice_ID <= 0)
            return "";

        MInvoice invoice = new MInvoice(ctx, C_Invoice_ID, null);

        if (invoice.get_ID() <= 0)
            return "Invoice tidak ditemukan.";

        if (invoice.isSOTrx())
            return "Invoice yang dipilih bukan Purchase Invoice.";

        if (!MInvoice.DOCSTATUS_Completed.equals(invoice.getDocStatus())
                && !MInvoice.DOCSTATUS_Closed.equals(invoice.getDocStatus())) {
            return "Invoice harus Completed atau Closed.";
        }

        BigDecimal grandTotal = invoice.getGrandTotal();

        if (grandTotal == null || grandTotal.signum() <= 0)
            return "Grand Total invoice harus lebih besar dari 0.";

        mTab.setValue("C_BPartner_ID", invoice.getC_BPartner_ID());
        mTab.setValue("C_Currency_ID", invoice.getC_Currency_ID());
        mTab.setValue("InvoiceGrandTotal", grandTotal);

        Timestamp dateInvoiced = invoice.getDateInvoiced();
        Timestamp dateAcct = invoice.getDateAcct();

        if (dateInvoiced != null)
            mTab.setValue("DateDoc", dateInvoiced);

        if (dateAcct != null)
            mTab.setValue("DateAcct", dateAcct);

        Object assetName = mTab.getValue("AssetName");
        if (assetName == null || assetName.toString().trim().isEmpty()) {
            mTab.setValue("AssetName", "Asset dari Invoice " + invoice.getDocumentNo());
        }

        Object assetNo = mTab.getValue("AssetNo");
        if (assetNo == null || assetNo.toString().trim().isEmpty()) {
            mTab.setValue("AssetNo", invoice.getDocumentNo());
        }

        Object serviceDate = mTab.getValue("AssetServiceDate");
        if (serviceDate == null && dateInvoiced != null) {
            mTab.setValue("AssetServiceDate", dateInvoiced);
        }

        return "";
    }
}