package id.rekaestudigital.mumu.component;

import org.adempiere.base.IColumnCallout;
import org.adempiere.base.IColumnCalloutFactory;

import id.rekaestudigital.mumu.callout.CalloutAssetAdditionRequestInvoice;
import id.rekaestudigital.mumu.callout.CalloutOrderDocTypeToDescription;

public class MyCalloutFactory implements IColumnCalloutFactory{

	@Override
	public IColumnCallout[] getColumnCallouts(String tableName, String columnName) {
		
		 if ("C_Order".equals(tableName) && "C_DocTypeTarget_ID".equals(columnName)) {
	            return new IColumnCallout[] {
	                new CalloutOrderDocTypeToDescription()
	            };
	        }
		 
		 if ("RED_AssetAdditionRequest".equals(tableName) && "C_Invoice_ID".equals(columnName)) {
	            return new IColumnCallout[] {
	                new CalloutAssetAdditionRequestInvoice()
	            };
	        }
		 
		return null;
	}

}
