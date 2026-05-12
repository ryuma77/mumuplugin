package id.rekaestudigital.mumu.component;

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventTopics;
import org.compiere.model.MBPartner;
import org.compiere.model.PO;
import org.osgi.service.event.Event;

import id.rekaestudigital.mumu.validator.BPartnerCreditAlertValidator;

public class MyModelValidatorFactory extends AbstractEventHandler {

    @Override
    protected void initialize() {

        System.out.println(">>> MyModelValidatorFactory initialized");

        registerTableEvent(IEventTopics.PO_AFTER_CHANGE, MBPartner.Table_Name);
    }

    @Override
    protected void doHandleEvent(Event event) {

        PO po = getPO(event);

        if (po == null) {
            return;
        }

        String msg = null;

        if (po.get_TableName().equals(MBPartner.Table_Name)) {
            BPartnerCreditAlertValidator validator =
                    new BPartnerCreditAlertValidator((MBPartner) po, event);

            msg = validator.run();
        }

        if (msg != null && msg.length() > 0) {
            throw new RuntimeException(msg);
        }
    }
}