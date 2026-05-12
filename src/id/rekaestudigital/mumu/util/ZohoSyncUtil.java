package id.rekaestudigital.mumu.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class ZohoSyncUtil {

    public static String formatDateTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String optString(JSONObject json, String key) {
        String value = json.optString(key, null);
        if (value == null || value.trim().isEmpty() || "null".equalsIgnoreCase(value)) {
            return null;
        }
        return value.trim();
    }

    public static Timestamp optTimestamp(JSONObject json, String key) {
        String value = optString(json, key);

        if (value == null) {
            return null;
        }

        try {
            return Timestamp.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}