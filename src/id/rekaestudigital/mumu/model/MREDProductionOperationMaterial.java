package id.rekaestudigital.mumu.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MREDProductionOperationMaterial extends X_RED_ProductionOperationMaterial{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8799394569720071640L;
	
	  public MREDProductionOperationMaterial(Properties ctx, int RED_ProductionOperationMaterial_ID, String trxName) {
	        super(ctx, RED_ProductionOperationMaterial_ID, trxName);
	    }

	public MREDProductionOperationMaterial(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
