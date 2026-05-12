package id.rekaestudigital.mumu.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import id.rekaestudigital.mumu.model.I_RED_ProductionOperation;
import id.rekaestudigital.mumu.model.MREDProductionOperation;
import id.rekaestudigital.mumu.model.MREDProductionStage;

public class CompleteProductionOperation extends SvrProcess {

    private int p_RED_ProductionOperation_ID = 0;
    private BigDecimal p_QtyActual = Env.ZERO;

    @Override
    protected void prepare() {
        p_RED_ProductionOperation_ID = getRecord_ID();

        ProcessInfoParameter[] para = getParameter();

        for (ProcessInfoParameter p : para) {

            String name = p.getParameterName();

            if ("QtyActual".equals(name)) {
                p_QtyActual = (BigDecimal) p.getParameter();
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        if (p_RED_ProductionOperation_ID <= 0) {
            throw new AdempiereException("Production Operation tidak ditemukan");
        }

        MREDProductionOperation operation =
                new MREDProductionOperation(
                        getCtx(),
                        p_RED_ProductionOperation_ID,
                        get_TrxName()
                );

        if (operation.get_ID() <= 0) {
            throw new AdempiereException("Production Operation tidak valid");
        }

        String status = operation.get_ValueAsString("RED_ProductionStatus");

        if ("COMPLETED".equals(status)) {
            throw new AdempiereException("Operation sudah completed");
        }

        Object startDate = operation.get_Value("StartDate");

        if (startDate == null) {
            throw new AdempiereException("Operation belum di-start");
        }

//        BigDecimal qtyActual = getBigDecimal(operation.get_Value("QtyActual"));

        if (p_QtyActual == null || p_QtyActual.compareTo(Env.ZERO) <= 0) {
            throw new AdempiereException("Qty Actual harus diisi lebih dari 0");
        }

        operation.set_ValueNoCheck("QtyActual", p_QtyActual);

        Timestamp now = new Timestamp(System.currentTimeMillis());

        operation.set_ValueNoCheck("RED_ProductionStatus", "COMPLETED");
        operation.set_ValueNoCheck("EndDate", now);
        operation.saveEx();

        updateStage(operation);

        return "Operation completed";
    }

    private void updateStage(MREDProductionOperation completedOperation) {

        MREDProductionStage stage =
                new MREDProductionStage(
                        getCtx(),
                        completedOperation.getRED_ProductionStage_ID(),
                        get_TrxName()
                );

        /*
         * ambil semua operation dalam stage
         */
        List<PO> operations =
                new Query(
                        getCtx(),
                        I_RED_ProductionOperation.Table_Name,
                        "RED_ProductionStage_ID=? AND IsActive='Y'",
                        get_TrxName()
                )
                .setParameters(stage.get_ID())
                .setOrderBy("SeqNo")
                .list();

        int totalOperation = operations.size();

        int completedCount = 0;

        boolean hasStarted = false;

        PO lastCompleted = null;

        /*
         * loop operation
         */
        for (PO op : operations) {

            String status =
                    op.get_ValueAsString("RED_ProductionStatus");

            /*
             * operation sudah mulai
             */
            if ("INPROGRESS".equals(status)
                    || "COMPLETED".equals(status)) {

                hasStarted = true;
            }

            /*
             * operation completed
             */
            if ("COMPLETED".equals(status)) {

                completedCount++;

                lastCompleted = op;
            }
        }

        /*
         * progress %
         */
        BigDecimal progressPercent = Env.ZERO;

        if (totalOperation > 0) {

            progressPercent =
                    BigDecimal.valueOf(completedCount)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(
                            BigDecimal.valueOf(totalOperation),
                            2,
                            BigDecimal.ROUND_HALF_UP
                    );
        }

        stage.set_ValueNoCheck(
                "ProgressPercent",
                progressPercent);

        /*
         * kalau sudah ada operation jalan
         */
        if (hasStarted) {

            stage.set_ValueNoCheck(
                    "RED_ProductionStatus",
                    "INPROGRESS");

            /*
             * isi start date kalau kosong
             */
            if (stage.get_Value("StartDate") == null) {

                stage.set_ValueNoCheck(
                        "StartDate",
                        new Timestamp(System.currentTimeMillis()));
            }
        }

        /*
         * semua operation completed
         */
        if (completedCount == totalOperation
                && totalOperation > 0
                && lastCompleted != null) {

            BigDecimal qtyTargeted =
                    getBigDecimal(
                            stage.get_Value("QtyTargeted"));

            BigDecimal qtyActual =
                    getBigDecimal(
                            lastCompleted.get_Value("QtyActual"));

            if (qtyTargeted == null) {
                qtyTargeted = Env.ZERO;
            }

            if (qtyActual == null) {
                qtyActual = Env.ZERO;
            }

            BigDecimal qtyLoss =
                    qtyTargeted.subtract(qtyActual);

            /*
             * update stage
             */
            stage.set_ValueNoCheck(
                    "RED_ProductionStatus",
                    "COMPLETED");

            stage.set_ValueNoCheck(
                    "QtyActual",
                    qtyActual);

            stage.set_ValueNoCheck(
                    "QtyLoss",
                    qtyLoss);

            stage.set_ValueNoCheck(
                    "EndDate",
                    new Timestamp(System.currentTimeMillis()));

            stage.set_ValueNoCheck(
                    "ProgressPercent",
                    BigDecimal.valueOf(100));
        }

        stage.saveEx();
    }

    private BigDecimal getBigDecimal(Object value) {

        if (value == null) {
            return Env.ZERO;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        return new BigDecimal(value.toString());
    }
}