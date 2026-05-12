package id.rekaestudigital.mumu.util;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

public class BPCreditAlertUtil {

    public static BPCreditAlertData getNextUnreadAlert(Properties ctx) {

    	int AD_User_ID = Env.getAD_User_ID(ctx);
    	System.out.println(">>> Checking BP credit alert for AD_User_ID=" + AD_User_ID);

        String sql =
                "SELECT "
              + "r.red_bp_credit_alert_recipient_id, "
              + "a.red_bp_credit_alert_id, "
              + "a.c_bpartner_id, "
              + "bp.name AS bp_name, "
              + "a.old_credit_status, "
              + "a.new_credit_status, "
              + "a.old_credit_limit, "
              + "a.new_credit_limit, "
              + "a.changed_by, "
              + "u.name AS changed_by_name, "
              + "a.changed_at "
              + "FROM red_bp_credit_alert_recipient r "
              + "JOIN red_bp_credit_alert a "
              + "ON a.red_bp_credit_alert_id = r.red_bp_credit_alert_id "
              + "JOIN c_bpartner bp "
              + "ON bp.c_bpartner_id = a.c_bpartner_id "
              + "LEFT JOIN ad_user u "
              + "ON u.ad_user_id = a.changed_by "
              + "WHERE r.isactive = 'Y' "
              + "AND a.isactive = 'Y' "
              + "AND r.ad_user_id = ? "
              + "AND r.is_read = 'N' "
              + "ORDER BY a.changed_at DESC "
              + "LIMIT 1";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DB.prepareStatement(sql, null);
            pstmt.setInt(1, AD_User_ID);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                BPCreditAlertData data = new BPCreditAlertData();

                data.recipientId = rs.getInt("red_bp_credit_alert_recipient_id");
                data.alertId = rs.getInt("red_bp_credit_alert_id");
                data.cBPartnerId = rs.getInt("c_bpartner_id");
                data.bpName = rs.getString("bp_name");
                data.oldCreditStatus = rs.getString("old_credit_status");
                data.newCreditStatus = rs.getString("new_credit_status");
                data.oldCreditLimit = rs.getBigDecimal("old_credit_limit");
                data.newCreditLimit = rs.getBigDecimal("new_credit_limit");
                data.changedBy = rs.getInt("changed_by");
                data.changedByName = rs.getString("changed_by_name");
                data.changedAt = rs.getTimestamp("changed_at");

                return data;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }

        return null;
    }

    public static void markPopupShown(Properties ctx, int recipientId, String trxName) {

        int AD_User_ID = Env.getAD_User_ID(ctx);

        String sql =
                "UPDATE red_bp_credit_alert_recipient "
              + "SET ispopupshown = 'Y', "
              + "popupshown_at = now(), "
              + "updated = now(), "
              + "updatedby = ? "
              + "WHERE red_bp_credit_alert_recipient_id = ?";

        DB.executeUpdateEx(
                sql,
                new Object[] { AD_User_ID, recipientId },
                trxName
        );
    }

    public static void markRead(Properties ctx, int recipientId, String trxName) {

        int AD_User_ID = Env.getAD_User_ID(ctx);

        String sql =
                "UPDATE red_bp_credit_alert_recipient "
              + "SET is_read = 'Y', "
              + "read_at = now(), "
              + "updated = now(), "
              + "updatedby = ? "
              + "WHERE red_bp_credit_alert_recipient_id = ?";

        DB.executeUpdateEx(
                sql,
                new Object[] { AD_User_ID, recipientId },
                trxName
        );
    }

    public static class BPCreditAlertData {
        public int recipientId;
        public int alertId;
        public int cBPartnerId;
        public String bpName;
        public String oldCreditStatus;
        public String newCreditStatus;
        public BigDecimal oldCreditLimit;
        public BigDecimal newCreditLimit;
        public int changedBy;
        public String changedByName;
        public Timestamp changedAt;
    }
}