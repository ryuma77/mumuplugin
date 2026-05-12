package id.rekaestudigital.mumu.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.MBPartner;
import org.compiere.util.DB;
import org.osgi.service.event.Event;

public class BPartnerCreditAlertValidator {

    private final MBPartner bp;
    private final Event event;

    public BPartnerCreditAlertValidator(MBPartner bp, Event event) {
        this.bp = bp;
        this.event = event;
    }

    public String run() {

        boolean creditStatusChanged = bp.is_ValueChanged("SOCreditStatus");
        boolean creditLimitChanged = bp.is_ValueChanged("SO_CreditLimit");

        if (!creditStatusChanged && !creditLimitChanged) {
            return null;
        }

        System.out.println(">>> Credit changed for BP: " + bp.getName());

        String oldStatus = creditStatusChanged
                ? toStringValue(bp.get_ValueOld("SOCreditStatus"))
                : toStringValue(bp.get_Value("SOCreditStatus"));

        String newStatus = toStringValue(bp.get_Value("SOCreditStatus"));

        BigDecimal oldLimit = creditLimitChanged
                ? toBigDecimal(bp.get_ValueOld("SO_CreditLimit"))
                : toBigDecimal(bp.get_Value("SO_CreditLimit"));

        BigDecimal newLimit = toBigDecimal(bp.get_Value("SO_CreditLimit"));

        int alertId = insertAlertHeader(oldStatus, newStatus, oldLimit, newLimit);

        List<Integer> recipients = getRecipients(bp.getAD_Client_ID());

        for (Integer AD_User_ID : recipients) {
            insertAlertRecipient(alertId, AD_User_ID);
        }

        return null;
    }

    private int insertAlertHeader(
            String oldStatus,
            String newStatus,
            BigDecimal oldLimit,
            BigDecimal newLimit) {

        int alertId = DB.getNextID(
                bp.getAD_Client_ID(),
                "red_bp_credit_alert",
                bp.get_TrxName()
        );

        String name = "Credit Status Changed - " + bp.getName();

        String sql =
                "INSERT INTO red_bp_credit_alert "
              + "(red_bp_credit_alert_id, ad_client_id, ad_org_id, "
              + "isactive, created, createdby, updated, updatedby, "
              + "name, "
              + "c_bpartner_id, "
              + "old_credit_status, new_credit_status, "
              + "old_credit_limit, new_credit_limit, "
              + "changed_by, changed_at) "
              + "VALUES (?, ?, ?, "
              + "'Y', now(), ?, now(), ?, "
              + "?, "
              + "?, "
              + "?, ?, "
              + "?, ?, "
              + "?, now())";

        DB.executeUpdateEx(
                sql,
                new Object[] {
                        alertId,              // 1 red_bp_credit_alert_id
                        bp.getAD_Client_ID(), // 2 ad_client_id
                        bp.getAD_Org_ID(),    // 3 ad_org_id
                        bp.getUpdatedBy(),    // 4 createdby
                        bp.getUpdatedBy(),    // 5 updatedby
                        name,                 // 6 name
                        bp.getC_BPartner_ID(),// 7 c_bpartner_id
                        oldStatus,            // 8 old_credit_status
                        newStatus,            // 9 new_credit_status
                        oldLimit,             // 10 old_credit_limit
                        newLimit,             // 11 new_credit_limit
                        bp.getUpdatedBy()     // 12 changed_by
                },
                bp.get_TrxName()
        );

        return alertId;
    }

    private void insertAlertRecipient(int alertId, int AD_User_ID) {

        int recipientId = DB.getNextID(
                bp.getAD_Client_ID(),
                "red_bp_credit_alert_recipient",
                bp.get_TrxName()
        );

        String name = "Recipient Alert " + alertId + " - User " + AD_User_ID;

        String sql =
                "INSERT INTO red_bp_credit_alert_recipient "
              + "(red_bp_credit_alert_recipient_id, ad_client_id, ad_org_id, "
              + "isactive, created, createdby, updated, updatedby, "
              + "name, "
              + "red_bp_credit_alert_id, ad_user_id, "
              + "is_read, ispopupshown) "
              + "VALUES (?, ?, ?, "
              + "'Y', now(), ?, now(), ?, "
              + "?, "
              + "?, ?, "
              + "'N', 'N')";

        DB.executeUpdateEx(
                sql,
                new Object[] {
                        recipientId,          // 1 red_bp_credit_alert_recipient_id
                        bp.getAD_Client_ID(), // 2 ad_client_id
                        bp.getAD_Org_ID(),    // 3 ad_org_id
                        bp.getUpdatedBy(),    // 4 createdby
                        bp.getUpdatedBy(),    // 5 updatedby
                        name,                 // 6 name
                        alertId,              // 7 red_bp_credit_alert_id
                        AD_User_ID            // 8 ad_user_id
                },
                bp.get_TrxName()
        );
    }

    private List<Integer> getRecipients(int AD_Client_ID) {

        List<Integer> recipients = new ArrayList<>();

        /*
         * Untuk tahap awal hardcode dulu.
         * Ganti dengan AD_User_ID yang benar.
         */
        recipients.add(100);
        recipients.add(1000000);
        recipients.add(1000001);
        recipients.add(1000002);

        return recipients;
    }

    private String toStringValue(Object value) {
        return value == null ? null : value.toString();
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        return new BigDecimal(value.toString());
    }
}