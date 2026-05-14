package id.rekaestudigital.mumu.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Model wrapper for RED_Stage
 * 
 * @author Reka Estu Digital
 */
public class MREDStage extends X_RED_Stage {

    private static final long serialVersionUID = 1L;

    public MREDStage(Properties ctx, int RED_Stage_ID, String trxName) {
        super(ctx, RED_Stage_ID, trxName);
    }

    public MREDStage(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    /**
     * Get stage by value.
     */
    public static MREDStage getByValue(Properties ctx, String value, String trxName) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return new org.compiere.model.Query(
                ctx,
                Table_Name,
                "Value=? AND IsActive='Y'",
                trxName
        )
                .setParameters(value.trim())
                .setOnlyActiveRecords(true)
                .first();
    }

    /**
     * Convenience method for IsDefault.
     * 
     * Dipakai supaya aman kalau method generated isDefault()
     * tidak terbentuk sesuai ekspektasi.
     */
    public boolean isDefaultStage() {
        return "Y".equals(get_ValueAsString("IsDefault"));
    }

    /**
     * Convenience method for IsMandatory.
     */
    public boolean isMandatoryStage() {
        return "Y".equals(get_ValueAsString("IsMandatory"));
    }

    /**
     * Get stage display name.
     */
    public String getDisplayName() {
        String value = getValue();
        String name = getName();

        if (value == null || value.trim().isEmpty()) {
            return name;
        }

        if (name == null || name.trim().isEmpty()) {
            return value;
        }

        return value + " - " + name;
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {
        if (getValue() != null) {
            setValue(getValue().trim().toUpperCase());
        }

        if (getName() != null) {
            setName(getName().trim());
        }

        return true;
    }
}