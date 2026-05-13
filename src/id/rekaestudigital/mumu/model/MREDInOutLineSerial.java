package id.rekaestudigital.mumu.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInOutLine;
import org.compiere.model.Query;
import org.compiere.util.Env;

public class MREDInOutLineSerial extends X_RED_InOutLineSerial {

    private static final long serialVersionUID = 1L;

    public MREDInOutLineSerial(Properties ctx, int RED_InOutLineSerial_ID, String trxName) {
        super(ctx, RED_InOutLineSerial_ID, trxName);
    }

    public MREDInOutLineSerial(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        if (getM_InOutLine_ID() <= 0) {
            throw new AdempiereException("Receipt Line wajib diisi.");
        }

        if (getSerNo() == null || getSerNo().trim().isEmpty()) {
            throw new AdempiereException("Serial No wajib diisi.");
        }

        MInOutLine line = new MInOutLine(getCtx(), getM_InOutLine_ID(), get_TrxName());

        if (line.get_ID() <= 0) {
            throw new AdempiereException("Receipt Line tidak ditemukan.");
        }

        setM_InOut_ID(line.getM_InOut_ID());
        setM_Product_ID(line.getM_Product_ID());
        setAD_Org_ID(line.getAD_Org_ID());

        if (get_Value("IsAssetRegistered") == null) {
            setIsAssetRegistered(false);
        }

        if (get_Value("Processed") == null) {
            setProcessed(false);
        }

        validateDuplicateSerial();
        
        validateSerialQtyLimit();

        return true;
    }

    private void validateDuplicateSerial() {

        String whereClause =
                "M_InOutLine_ID=? "
              + "AND UPPER(TRIM(SerialNo))=UPPER(TRIM(?)) "
              + "AND RED_InOutLineSerial_ID<>?";

        int count = new Query(getCtx(), Table_Name, whereClause, get_TrxName())
                .setParameters(
                        getM_InOutLine_ID(),
                        getSerNo(),
                        getRED_InOutLineSerial_ID()
                )
                .count();

        if (count > 0) {
            throw new AdempiereException("Serial No sudah ada untuk Receipt Line ini: " + getSerNo());
        }
    }
    
    private void validateSerialQtyLimit() {

        MInOutLine line = new MInOutLine(getCtx(), getM_InOutLine_ID(), get_TrxName());

        int movementQty = line.getMovementQty().intValue();

        int serialCount = new Query(getCtx(), Table_Name,
                "M_InOutLine_ID=? AND RED_InOutLineSerial_ID<>?",
                get_TrxName())
                .setParameters(getM_InOutLine_ID(), getRED_InOutLineSerial_ID())
                .count();

        if (serialCount + 1 > movementQty) {
            throw new AdempiereException(
                    "Jumlah serial number melebihi Qty Receipt Line. Qty = "
                    + movementQty + ", Serial yang diinput = " + (serialCount + 1)
            );
        }
    }
}