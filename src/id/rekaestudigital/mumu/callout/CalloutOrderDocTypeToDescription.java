package id.rekaestudigital.mumu.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MDocType;

public class CalloutOrderDocTypeToDescription implements IColumnCallout{

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		if (value == null) {
            return "";
        }

        int docTypeId = ((Number) value).intValue();

        if (docTypeId <= 0) {
            return "";
        }

        MDocType docType = MDocType.get(ctx, docTypeId);

        if (docType == null || docType.get_ID() <= 0) {
            return "";
        }

        mTab.setValue("Description", "Document Type: " + docType.getName());

        return "";
	}

}
