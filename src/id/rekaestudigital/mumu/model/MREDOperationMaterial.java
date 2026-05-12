package id.rekaestudigital.mumu.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MREDOperationMaterial extends X_RED_OperationMaterial{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3913030220779854288L;
	
	public MREDOperationMaterial(Properties ctx, int RED_OperationMaterial_ID, String trxName) {
        super(ctx, RED_OperationMaterial_ID, trxName);
    }

	public MREDOperationMaterial(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
