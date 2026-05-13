package id.rekaestudigital.mumu.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MInOutLine;

public class CalloutInOutLineSerial implements IColumnCallout {

    @Override
    public String start(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {

        int M_InOutLine_ID = 0;

        if (value != null && value instanceof Number) {
            M_InOutLine_ID = ((Number) value).intValue();
        } else {
            Object parentValue = mTab.getValue("M_InOutLine_ID");
            if (parentValue instanceof Number) {
                M_InOutLine_ID = ((Number) parentValue).intValue();
            }
        }

        if (M_InOutLine_ID <= 0) {
            return "";
        }

        MInOutLine line = new MInOutLine(ctx, M_InOutLine_ID, null);

        if (line.get_ID() <= 0) {
            return "Receipt Line tidak ditemukan.";
        }

        mTab.setValue("M_InOut_ID", line.getM_InOut_ID());
        mTab.setValue("M_Product_ID", line.getM_Product_ID());
        mTab.setValue("AD_Org_ID", line.getAD_Org_ID());

        Object isAssetRegistered = mTab.getValue("IsAssetRegistered");
        if (isAssetRegistered == null) {
            mTab.setValue("IsAssetRegistered", false);
        }

        Object processed = mTab.getValue("Processed");
        if (processed == null) {
            mTab.setValue("Processed", false);
        }

        return "";
    }
}