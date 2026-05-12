package id.rekaestudigital.mumu.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import id.rekaestudigital.mumu.model.MREDAssetAdditionRequest;

public class ViewAssetAdditionPosting extends SvrProcess {

    private int p_RED_AssetAdditionRequest_ID = 0;

    @Override
    protected void prepare() {
        p_RED_AssetAdditionRequest_ID = getRecord_ID();
    }

    @Override
    protected String doIt() throws Exception {

        MREDAssetAdditionRequest request =
                new MREDAssetAdditionRequest(getCtx(), p_RED_AssetAdditionRequest_ID, get_TrxName());

        if (request.getA_Asset_Addition_ID() <= 0)
            throw new AdempiereException("Asset Addition belum terbentuk.");

        int AD_Table_ID = DB.getSQLValue(
                get_TrxName(),
                "SELECT AD_Table_ID FROM AD_Table WHERE TableName='A_Asset_Addition'"
        );

        StringBuilder result = new StringBuilder();

        String sql =
                "SELECT ev.Value, ev.Name, fa.AmtAcctDr, fa.AmtAcctCr "
              + "FROM Fact_Acct fa "
              + "JOIN C_ElementValue ev ON ev.C_ElementValue_ID = fa.Account_ID "
              + "WHERE fa.AD_Table_ID=? "
              + "AND fa.Record_ID=? "
              + "ORDER BY fa.Fact_Acct_ID";

        int rows = 0;

        try (PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName())) {
            pstmt.setInt(1, AD_Table_ID);
            pstmt.setInt(2, request.getA_Asset_Addition_ID());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    rows++;

                    String accountValue = rs.getString(1);
                    String accountName = rs.getString(2);
                    BigDecimal dr = rs.getBigDecimal(3);
                    BigDecimal cr = rs.getBigDecimal(4);

                    result.append(accountValue)
                          .append(" - ")
                          .append(accountName)
                          .append(" | Dr: ")
                          .append(dr)
                          .append(" | Cr: ")
                          .append(cr)
                          .append("\n");
                }
            }
        }

        if (rows == 0) {
            return "Belum ada posting Fact_Acct untuk Asset Addition ini. Coba jalankan posting standard dulu.";
        }

        return result.toString();
    }
}