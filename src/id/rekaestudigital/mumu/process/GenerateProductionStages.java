package id.rekaestudigital.mumu.process;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import id.rekaestudigital.mumu.model.I_RED_Operation;
import id.rekaestudigital.mumu.model.I_RED_ProductionStage;
import id.rekaestudigital.mumu.model.MREDOperation;
import id.rekaestudigital.mumu.model.MREDProductionOperation;
import id.rekaestudigital.mumu.model.MREDProductionPlan;
import id.rekaestudigital.mumu.model.MREDProductionStage;
import org.compiere.model.PO;

public class GenerateProductionStages extends SvrProcess {

    private int p_RED_ProductionPlan_ID = 0;

    @Override
    protected void prepare() {
        p_RED_ProductionPlan_ID = getRecord_ID();
    }

    @Override
    protected String doIt() throws Exception {

        if (p_RED_ProductionPlan_ID <= 0) {
            throw new AdempiereException("Production Plan belum dipilih");
        }

        MREDProductionPlan plan = new MREDProductionPlan(
                getCtx(),
                p_RED_ProductionPlan_ID,
                get_TrxName()
        );

        if (plan.get_ID() <= 0) {
            throw new AdempiereException("Production Plan tidak ditemukan");
        }

        int existing = new Query(
                getCtx(),
                I_RED_ProductionStage.Table_Name,
                "RED_ProductionPlan_ID=?",
                get_TrxName()
        )
        .setParameters(plan.get_ID())
        .count();

        if (existing > 0) {
            throw new AdempiereException("Stage sudah pernah digenerate untuk Production Plan ini");
        }

        int stageCount = 0;
        int operationCount = 0;

        MREDProductionStage knitting = createStage(plan, 10, "KNITTING", "Knitting");
        operationCount += generateOperations(knitting);
        stageCount++;

        MREDProductionStage dyeing = createStage(plan, 20, "DYEING", "Dyeing");
        operationCount += generateOperations(dyeing);
        stageCount++;

        MREDProductionStage finishing = createStage(plan, 30, "FINISHING", "Finishing");
        operationCount += generateOperations(finishing);
        stageCount++;

        return "Generated " + stageCount + " stage dan " + operationCount + " operation";
    }

    private MREDProductionStage createStage(
            MREDProductionPlan plan,
            int seqNo,
            String stageType,
            String name) {

        MREDProductionStage stage = new MREDProductionStage(
                getCtx(),
                0,
                get_TrxName()
        );

        stage.setAD_Org_ID(plan.getAD_Org_ID());
        stage.setRED_ProductionPlan_ID(plan.get_ID());

        stage.setSeqNo(seqNo);
        stage.setStageType(stageType);
        stage.setName(name);

        stage.setQtyTargeted(Env.ZERO);
        stage.setQtyActual(Env.ZERO);
        stage.setQtyLoss(Env.ZERO);

        // stage.setStatus("NOTSTARTED");
        stage.set_ValueNoCheck("RED_ProductionStatus", "NOTSTARTED");
        stage.setProcessed(false);

        stage.saveEx();

        return stage;
    }

    private int generateOperations(MREDProductionStage stage) {

        List<PO> operations = new Query(
                getCtx(),
                I_RED_Operation.Table_Name,
                "StageType=? AND IsActive='Y'",
                get_TrxName()
        )
        .setParameters(stage.getStageType())
        .setOrderBy("SeqNo")
        .list();

        int count = 0;

        for (PO op : operations) {

            MREDProductionOperation prodOp = new MREDProductionOperation(
                    getCtx(),
                    0,
                    get_TrxName()
            );

            prodOp.setAD_Org_ID(stage.getAD_Org_ID());
            prodOp.setRED_ProductionStage_ID(stage.get_ID());
            prodOp.setRED_Operation_ID(op.get_ID());

            prodOp.setSeqNo(op.get_ValueAsInt("SeqNo"));
            prodOp.setOperationType((String) op.get_Value("Value"));
            prodOp.setName((String) op.get_Value("Name"));

            prodOp.setQtyTargeted(stage.getQtyTargeted());
            prodOp.setQtyActual(Env.ZERO);

            // prodOp.setStatus("NOTSTARTED"); // sementara, lihat masalah nomor 2
            prodOp.set_ValueNoCheck("RED_ProductionStatus", "NOTSTARTED");
//            prodOp.setisMaterialRequired("Y".equals(op.get_ValueAsString("IsMaterialRequired")));
            prodOp.set_ValueOfColumn("isMaterialRequired",  op.get_Value("isMaterialRequired"));
            prodOp.setisQCRequired("Y".equals(op.get_ValueAsString("IsQCRequired")));
            prodOp.setProcessed(false);

            prodOp.saveEx();
            count++;
        }

        return count;
    }
}
