package id.rekaestudigital.mumu.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.process.ImportOrder;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class ImportOrderCustom extends ImportOrder {

    @Override
    protected String doIt() throws Exception {

        resolveWarehouseValue();

        String result = super.doIt();

        copyCustomColumnToOrder();

        return result + " - Custom WarehouseValue and CustomColumn processed";
    }

    private void resolveWarehouseValue() {

        String sql = ""
                + "UPDATE I_Order io "
                + "SET M_Warehouse_ID = ( "
                + "    SELECT w.M_Warehouse_ID "
                + "    FROM M_Warehouse w "
                + "    WHERE w.AD_Client_ID IN (0, io.AD_Client_ID) "
                + "      AND w.IsActive = 'Y' "
                + "      AND UPPER(w.Value) = UPPER(io.M_WarehouseValue) "
                + "    ORDER BY w.AD_Client_ID DESC "
                + "    FETCH FIRST 1 ROW ONLY "
                + ") "
                + "WHERE io.AD_Client_ID = ? "
                + "  AND COALESCE(io.I_IsImported, 'N') = 'N' "
                + "  AND io.M_Warehouse_ID IS NULL "
                + "  AND io.M_WarehouseValue IS NOT NULL";

        DB.executeUpdateEx(
                sql,
                new Object[] { Env.getAD_Client_ID(getCtx()) },
                get_TrxName()
        );

        String sqlError = ""
                + "UPDATE I_Order io "
                + "SET I_ErrorMsg = COALESCE(I_ErrorMsg, '') || ' WarehouseValue tidak ditemukan: ' || io.M_WarehouseValue || ';' "
                + "WHERE io.AD_Client_ID = ? "
                + "  AND COALESCE(io.I_IsImported, 'N') = 'N' "
                + "  AND io.M_Warehouse_ID IS NULL "
                + "  AND io.M_WarehouseValue IS NOT NULL "
                + "  AND NOT EXISTS ( "
                + "      SELECT 1 "
                + "      FROM M_Warehouse w "
                + "      WHERE w.AD_Client_ID IN (0, io.AD_Client_ID) "
                + "        AND w.IsActive = 'Y' "
                + "        AND UPPER(w.Value) = UPPER(io.M_WarehouseValue) "
                + "  )";

        DB.executeUpdateEx(
                sqlError,
                new Object[] { Env.getAD_Client_ID(getCtx()) },
                get_TrxName()
        );
    }

    private void copyCustomColumnToOrder() throws Exception {

        String sql = ""
                + "SELECT I_Order_ID, C_Order_ID, XX_CustomColumn "
                + "FROM I_Order "
                + "WHERE AD_Client_ID = ? "
                + "  AND I_IsImported = 'Y' "
                + "  AND C_Order_ID IS NOT NULL "
                + "  AND XX_CustomColumn IS NOT NULL";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DB.prepareStatement(sql, get_TrxName());
            pstmt.setInt(1, Env.getAD_Client_ID(getCtx()));
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("C_Order_ID");
                String customValue = rs.getString("XX_CustomColumn");

                updateOrderCustomColumn(orderId, customValue);
            }

        } finally {
            DB.close(rs, pstmt);
        }
    }

    private void updateOrderCustomColumn(int orderId, String customValue) {

        String sql = ""
                + "UPDATE C_Order "
                + "SET XX_CustomColumn = ? "
                + "WHERE C_Order_ID = ?";

        DB.executeUpdateEx(
                sql,
                new Object[] { customValue, orderId },
                get_TrxName()
        );
    }
}