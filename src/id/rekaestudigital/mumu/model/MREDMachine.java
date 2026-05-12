package id.rekaestudigital.mumu.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MREDMachine extends X_RED_Machine{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2944213252479741301L;
	
	public MREDMachine(Properties ctx, int RED_Machine_ID, String trxName) {
        super(ctx, RED_Machine_ID, trxName);
    }

	public MREDMachine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
