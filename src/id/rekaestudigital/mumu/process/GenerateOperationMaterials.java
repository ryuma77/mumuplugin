package id.rekaestudigital.mumu.process;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import id.rekaestudigital.mumu.model.I_RED_OperationMaterial;
import id.rekaestudigital.mumu.model.I_RED_ProductionOperationMaterial;
import id.rekaestudigital.mumu.model.MREDProductionOperation;
import id.rekaestudigital.mumu.model.MREDProductionOperationMaterial;
import id.rekaestudigital.mumu.model.X_RED_ProductionOperation;

public class GenerateOperationMaterials extends SvrProcess {

    private int p_RED_ProductionOperation_ID = 0;

    @Override
    protected void prepare() {

        p_RED_ProductionOperation_ID = getRecord_ID();
    }

    @Override
    protected String doIt() throws Exception {

        if (p_RED_ProductionOperation_ID <= 0) {
            throw new AdempiereException(
                    "Production Operation tidak ditemukan");
        }

        MREDProductionOperation operation =
                new MREDProductionOperation(
                        getCtx(),
                        p_RED_ProductionOperation_ID,
                        get_TrxName()
                );

        if (!operation.isMaterialRequired()) {

            return "Operation tidak membutuhkan material";
        }

        validateExistingMaterial(operation);

        int total = generateMaterials(operation);

        return total + " material generated";
    }

    /**
     * validasi duplicate
     */
    private void validateExistingMaterial(
            MREDProductionOperation operation) {

        int count = new Query(
                getCtx(),
                I_RED_ProductionOperationMaterial.Table_Name,
                "RED_ProductionOperation_ID=?",
                get_TrxName()
        )
        .setParameters(operation.get_ID())
        .count();

        if (count > 0) {

            throw new AdempiereException(
                    "Material sudah pernah digenerate");
        }
    }

    /**
     * generate material transaksi
     */
    private int generateMaterials(
            MREDProductionOperation operation) {

        List<PO> materials = new Query(
                getCtx(),
                I_RED_OperationMaterial.Table_Name,
                "RED_Operation_ID=? AND IsActive='Y'",
                get_TrxName()
        )
        .setParameters(operation.getRED_Operation_ID())
        .list();

        int count = 0;

        for (PO mat : materials) {

            MREDProductionOperationMaterial trxMat =
                    new MREDProductionOperationMaterial(
                            getCtx(),
                            0,
                            get_TrxName()
                    );

            trxMat.setAD_Org_ID(
                    operation.getAD_Org_ID());

            trxMat.setRED_ProductionOperation_ID(
                    operation.get_ID());

            /*
             * product
             */
            trxMat.setM_Product_ID(
                    mat.get_ValueAsInt("M_Product_ID"));

            /*
             * UOM
             */
            trxMat.setC_UOM_ID(
                    mat.get_ValueAsInt("C_UOM_ID"));

            Object qtyObj = mat.get_Value("QtyRequiered");

            BigDecimal qtyRequiered = Env.ZERO;

            if (qtyObj != null) {
                if (qtyObj instanceof BigDecimal) {
                    qtyRequiered = (BigDecimal) qtyObj;
                } else {
                    qtyRequiered = new BigDecimal(qtyObj.toString());
                }
            }

            trxMat.set_ValueNoCheck("QtyRequiered", qtyRequiered);

            trxMat.setQtyRequiered(qtyRequiered);

            /*
             * initial qty
             */
            trxMat.setQtyIssued(Env.ZERO);

            trxMat.setQtyConsumed(Env.ZERO);

            /*
             * optional material
             */
            trxMat.set_ValueNoCheck(
                    "IsOptional",
                    mat.get_Value("IsOptional"));

            /*
             * generated flag
             */
            trxMat.set_ValueNoCheck(
                    "IsGenerated",
                    true);

            /*
             * description
             */
            trxMat.setDescription(
                    mat.get_ValueAsString("Description"));

            trxMat.saveEx();

            count++;
        }

        return count;
    }
}