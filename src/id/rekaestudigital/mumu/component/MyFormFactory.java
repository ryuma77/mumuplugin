package id.rekaestudigital.mumu.component;

import org.adempiere.webui.factory.IFormFactory;
import org.adempiere.webui.panel.ADForm;

import id.rekaestudigital.mumu.form.WAssetRegisterFromInvoice;
import id.rekaestudigital.mumu.form.WGenerateProductionStage;
import id.rekaestudigital.mumu.form.WProductionFlowViewer;
import id.rekaestudigital.mumu.form.WProductionKanbanViewer;

public class MyFormFactory implements IFormFactory {

    @Override
    public ADForm newFormInstance(String formName) {

        System.out.println("FORM REQUEST = " + formName);

        if ("id.rekaestudigital.mumu.form.WProductionFlowViewer".equals(formName)) {
            System.out.println("LOAD WProductionFlowViewer");
            return new WProductionFlowViewer();
        }
        
        if ("id.rekaestudigital.mumu.form.WProductionKanbanViewer".equals(formName)) {
            return new WProductionKanbanViewer();
        }
        
        if ("id.rekaestudigital.mumu.form.WAssetRegisterFromInvoice".equals(formName)) {
            return new WAssetRegisterFromInvoice();
        }
        
        if ("id.rekaestudigital.mumu.form.WGenerateProductionStage".equals(formName)) {
            return new WGenerateProductionStage();
        }

        return null;
    }
}