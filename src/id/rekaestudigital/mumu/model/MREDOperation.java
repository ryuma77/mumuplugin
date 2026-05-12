package id.rekaestudigital.mumu.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MREDOperation extends X_RED_Operation{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6792550677183328832L;
	
	public MREDOperation(Properties ctx, int RED_Operation_ID, String trxName) {
        super(ctx, RED_Operation_ID, trxName);
    }

	public MREDOperation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
