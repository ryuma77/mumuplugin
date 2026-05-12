package id.rekaestudigital.mumu.component;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.osgi.service.component.annotations.Component;

import id.rekaestudigital.mumu.model.*;

@Component(service = IModelFactory.class)
public class MyModelFactory implements IModelFactory {

    @Override
    public Class<?> getClass(String tableName) {
        if (tableName.equals(I_RED_Machine.Table_Name))
            return MREDMachine.class;

        if (tableName.equals(I_RED_Operation.Table_Name))
            return MREDOperation.class;

        if (tableName.equals(I_RED_ProductionPlan.Table_Name))
            return MREDProductionPlan.class;

        if (tableName.equals(I_RED_ProductionStage.Table_Name))
            return MREDProductionStage.class;

        if (tableName.equals(I_RED_ProductionOperation.Table_Name))
            return MREDProductionOperation.class;

        if (tableName.equals(I_RED_ProductionOperationMaterial.Table_Name))
            return MREDProductionOperationMaterial.class;
        if (tableName.equals(I_RED_OperationMaterial.Table_Name))
            return MREDProductionOperationMaterial.class;

        return null;
    }

    @Override
    public PO getPO(String tableName, int Record_ID, String trxName) {
        if (tableName.equals(I_RED_Machine.Table_Name))
            return new MREDMachine(Env.getCtx(), Record_ID, trxName);

        if (tableName.equals(I_RED_Operation.Table_Name))
            return new MREDOperation(Env.getCtx(), Record_ID, trxName);

        if (tableName.equals(I_RED_ProductionPlan.Table_Name))
            return new MREDProductionPlan(Env.getCtx(), Record_ID, trxName);

        
        if (tableName.equals(I_RED_ProductionStage.Table_Name))
            return new MREDProductionStage(Env.getCtx(), Record_ID, trxName);

        if (tableName.equals(I_RED_ProductionOperation.Table_Name))
            return new MREDProductionOperation(Env.getCtx(), Record_ID, trxName);

        if (tableName.equals(I_RED_ProductionOperationMaterial.Table_Name))
            return new MREDProductionOperationMaterial(Env.getCtx(), Record_ID, trxName);
        
        if (tableName.equals(I_RED_OperationMaterial.Table_Name))
            return new MREDOperationMaterial(Env.getCtx(), Record_ID, trxName);

        return null;
    }

    @Override
    public PO getPO(String tableName, ResultSet rs, String trxName) {
        if (tableName.equals(I_RED_Machine.Table_Name))
            return new MREDMachine(Env.getCtx(), rs, trxName);

        if (tableName.equals(I_RED_Operation.Table_Name))
            return new MREDOperation(Env.getCtx(), rs, trxName);

        if (tableName.equals(I_RED_ProductionPlan.Table_Name))
            return new MREDProductionPlan(Env.getCtx(), rs, trxName);

        if (tableName.equals(I_RED_ProductionStage.Table_Name))
            return new MREDProductionStage(Env.getCtx(), rs, trxName);

        if (tableName.equals(I_RED_ProductionOperation.Table_Name))
            return new MREDProductionOperation(Env.getCtx(), rs, trxName);

        if (tableName.equals(I_RED_ProductionOperationMaterial.Table_Name))
            return new MREDProductionOperationMaterial(Env.getCtx(), rs, trxName);

        return null;
    }
}