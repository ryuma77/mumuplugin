package id.rekaestudigital.mumu.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MREDProductionPlan extends X_RED_ProductionPlan{
	private static final long serialVersionUID = 1L;

    public MREDProductionPlan(Properties ctx, int RED_ProductionPlan_ID, String trxName) {
        super(ctx, RED_ProductionPlan_ID, trxName);
    }

    public MREDProductionPlan(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

}
