package id.rekaestudigital.mumu.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MREDProductionOperation extends X_RED_ProductionOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1524139799397390914L;
	
	public MREDProductionOperation(Properties ctx, int RED_ProductionOperation_ID, String trxName) {
        super(ctx, RED_ProductionOperation_ID, trxName);
    }

	public MREDProductionOperation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
