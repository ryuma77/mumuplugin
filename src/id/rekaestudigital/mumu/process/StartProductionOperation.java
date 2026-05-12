package id.rekaestudigital.mumu.process;

import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import id.rekaestudigital.mumu.model.MREDProductionOperation;

public class StartProductionOperation extends SvrProcess {

    private int p_RED_ProductionOperation_ID = 0;

    @Override
    protected void prepare() {
        p_RED_ProductionOperation_ID = getRecord_ID();
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

        Timestamp now = TimeUtil.getDayTime(
                Env.getContextAsDate(getCtx(), "#Date"),
                new Timestamp(System.currentTimeMillis())
        );

        operation.set_ValueNoCheck("RED_ProductionStatus", "INPROGRESS");

        if (operation.get_Value("StartDate") == null) {
            operation.set_ValueNoCheck("StartDate", now);
        }

        if (operation.getAD_User_ID() <= 0) {
            operation.setAD_User_ID(Env.getAD_User_ID(getCtx()));
        }

        operation.saveEx();

        return "Operation started";
    }
}