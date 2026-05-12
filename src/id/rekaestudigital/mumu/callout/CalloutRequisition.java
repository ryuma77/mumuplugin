package id.rekaestudigital.mumu.callout;

import java.util.Properties;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;

public class CalloutRequisition extends CalloutEngine {

    public String bpartner1(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) {
            mTab.setValue("IsTrusted", "N");
            return "";
        }

        int C_BPartner1_ID = 0;

        try {
            C_BPartner1_ID = Integer.parseInt(value.toString());
        } catch (Exception e) {
            mTab.setValue("IsTrusted", "N");
            return "";
        }

        if (C_BPartner1_ID <= 0) {
            mTab.setValue("IsTrusted", "N");
            return "";
        }

        String isTrusted = DB.getSQLValueString(
                null,
                "SELECT IsTrusted FROM C_BPartner WHERE C_BPartner_ID=?",
                C_BPartner1_ID
        );

        if (isTrusted == null || isTrusted.trim().isEmpty()) {
            isTrusted = "N";
        }

        mTab.setValue("IsTrusted", isTrusted);

        return "";
    }
}