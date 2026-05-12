package id.rekaestudigital.mumu.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MREDAssetAdditionRequest extends X_RED_AssetAdditionRequest{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -560148149781840569L;
	
	public MREDAssetAdditionRequest(Properties ctx, int RED_AssetAdditionRequest_ID, String trxName) {
        super(ctx, RED_AssetAdditionRequest_ID, trxName);
    }

	public MREDAssetAdditionRequest(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
