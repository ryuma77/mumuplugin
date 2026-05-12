package id.rekaestudigital.mumu.component;

import org.adempiere.base.IProcessFactory;
import org.compiere.process.ProcessCall;

import id.rekaestudigital.mumu.process.CompleteProductionOperation;
import id.rekaestudigital.mumu.process.CreateAssetAdditionRequest;
import id.rekaestudigital.mumu.process.GenerateOperationMaterials;
import id.rekaestudigital.mumu.process.GenerateProductionStages;
import id.rekaestudigital.mumu.process.GetOrderFromZoho;
import id.rekaestudigital.mumu.process.GetPartnerContactFromZoho;
import id.rekaestudigital.mumu.process.GetProductFromZoho;
import id.rekaestudigital.mumu.process.ImportOrderCustom;
import id.rekaestudigital.mumu.process.SendRequisitionEmail;
import id.rekaestudigital.mumu.process.StartProductionOperation;
import id.rekaestudigital.mumu.process.ViewAssetAdditionPosting;

public class MyProcessFactory implements IProcessFactory{

	 @Override
	    public ProcessCall newProcessInstance(String className) {

	        if (className == null) {
	            return null;
	        }

	        className = className.trim();

	        if (ImportOrderCustom.class.getName().equals(className)) {
	            return new ImportOrderCustom();
	        }

	        if (GetProductFromZoho.class.getName().equals(className)) {
	            return new GetProductFromZoho();
	        }

	        if (GetPartnerContactFromZoho.class.getName().equals(className)) {
	            return new GetPartnerContactFromZoho();
	        }

	        if (GetOrderFromZoho.class.getName().equals(className)) {
	            return new GetOrderFromZoho();
	        }
	        
	        if (SendRequisitionEmail.class.getName().equals(className)) {
	            return new SendRequisitionEmail();
	        }
	        
	        if (GenerateProductionStages.class.getName().equals(className)) {
	            return new GenerateProductionStages();
	        }
	        
	        if (GenerateOperationMaterials.class.getName().equals(className)) {
	            return new GenerateOperationMaterials();
	        }
	        
	        if (CompleteProductionOperation.class.getName().equals(className)) {
	            return new CompleteProductionOperation();
	        }
	        
	        if (StartProductionOperation .class.getName().equals(className)) {
	            return new StartProductionOperation ();
	        }
	        
	        if (ViewAssetAdditionPosting .class.getName().equals(className)) {
	            return new ViewAssetAdditionPosting ();
	        }
	        
	        if (CreateAssetAdditionRequest .class.getName().equals(className)) {
	            return new CreateAssetAdditionRequest ();
	        }

	        return null;
	    }
	}
