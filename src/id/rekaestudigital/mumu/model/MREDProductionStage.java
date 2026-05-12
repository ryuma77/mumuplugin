package id.rekaestudigital.mumu.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MREDProductionStage extends X_RED_ProductionStage{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1931734196475917145L;
	
	public MREDProductionStage(Properties ctx, int RED_ProductionStage_ID, String trxName) {
        super(ctx, RED_ProductionStage_ID, trxName);
    }

	public MREDProductionStage(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	

}
