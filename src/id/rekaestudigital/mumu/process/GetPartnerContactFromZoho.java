package id.rekaestudigital.mumu.process;

import java.sql.Timestamp;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.json.JSONArray;
import org.json.JSONObject;

import id.rekaestudigital.mumu.util.ZohoApiClient;
import id.rekaestudigital.mumu.util.ZohoSyncUtil;

public class GetPartnerContactFromZoho extends SvrProcess {

    private String p_ZApiKey;
    private Timestamp p_StartDate;
    private int p_PageCount = 100;

    @Override
    protected void prepare() {

        for (ProcessInfoParameter param : getParameter()) {
            String name = param.getParameterName();

            if (param.getParameter() == null) {
                continue;
            }

            if ("ZApiKey".equalsIgnoreCase(name)) {
                p_ZApiKey = param.getParameterAsString();
            } else if ("StartDate".equalsIgnoreCase(name)) {
                p_StartDate = (Timestamp) param.getParameter();
            } else if ("PageCount".equalsIgnoreCase(name)) {
                p_PageCount = param.getParameterAsInt();
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        validateParameter();

        int accounts = syncModule("accounts");
        int contacts = syncModule("contacts");

        return "Get Partner/Contact selesai. Accounts=" + accounts + ", Contacts=" + contacts;
    }

    private int syncModule(String moduleName) throws Exception {

        ZohoApiClient client = new ZohoApiClient();

        int pageNum = 1;
        int totalRows = 0;
        boolean moreRecords = true;
        String startDateString = ZohoSyncUtil.formatDateTime(p_StartDate);

        while (moreRecords) {

            addLog("Get " + moduleName + " page " + pageNum);

            String responseBody = client.getData(
                    p_ZApiKey,
                    moduleName,
                    startDateString,
                    pageNum,
                    p_PageCount
            );

            JSONObject responseJson = new JSONObject(responseBody);
            JSONObject details = responseJson.optJSONObject("details");

            if (details == null) {
                throw new IllegalStateException("Zoho response missing details: " + responseBody);
            }

            String output = details.optString("output", null);

            if (output == null || output.trim().isEmpty()) {
                throw new IllegalStateException("Zoho response missing details.output: " + responseBody);
            }

            JSONObject outputJson = new JSONObject(output);
            JSONArray data = outputJson.optJSONArray("data");
            JSONObject info = outputJson.optJSONObject("info");

            int pageRows = 0;

            if (data != null) {
                for (int i = 0; i < data.length(); i++) {
                    JSONObject row = data.getJSONObject(i);
                    savePartnerContact(moduleName, row);
                    pageRows++;
                    totalRows++;
                }
            }

            moreRecords = info != null && info.optBoolean("more_records", false);

            addLog(moduleName + " page " + pageNum + " rows = " + pageRows + ", more_records = " + moreRecords);

            pageNum++;
        }

        return totalRows;
    }

    private void savePartnerContact(String moduleName, JSONObject row) {

        String zohoId = ZohoSyncUtil.optString(row, "id");

        if (zohoId == null) {
            throw new IllegalArgumentException("Partner/Contact Zoho id kosong. JSON=" + row.toString());
        }

        String bpName = firstNotNull(
                ZohoSyncUtil.optString(row, "Account_Name"),
                ZohoSyncUtil.optString(row, "Full_Name"),
                ZohoSyncUtil.optString(row, "Name"),
                ZohoSyncUtil.optString(row, "Last_Name")
        );

        String bpValue = firstNotNull(
                ZohoSyncUtil.optString(row, "Account_Code"),
                ZohoSyncUtil.optString(row, "Vendor_Code"),
                ZohoSyncUtil.optString(row, "Code"),
                zohoId
        );

        String contactName = firstNotNull(
                ZohoSyncUtil.optString(row, "Full_Name"),
                ZohoSyncUtil.optString(row, "Contact_Name"),
                ZohoSyncUtil.optString(row, "Last_Name")
        );

        String email = ZohoSyncUtil.optString(row, "Email");
        String phone = firstNotNull(
                ZohoSyncUtil.optString(row, "Phone"),
                ZohoSyncUtil.optString(row, "Mobile")
        );

        String sql = ""
                + "INSERT INTO RED_PartnerTemp "
                + "(RED_PartnerTemp_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
                + " Zoho_ID, Zoho_ModuleName, BPValue, BPName, ContactName, Email, Phone, RawJSON, Processed) "
                + "VALUES "
                + "(nextidfunc(?, 'N'), ?, ?, 'Y', now(), ?, now(), ?, "
                + " ?, ?, ?, ?, ?, ?, ?, ?, 'N') "
                + "ON CONFLICT (AD_Client_ID, Zoho_ModuleName, Zoho_ID) DO UPDATE SET "
                + " Updated = now(), "
                + " UpdatedBy = EXCLUDED.UpdatedBy, "
                + " BPValue = EXCLUDED.BPValue, "
                + " BPName = EXCLUDED.BPName, "
                + " ContactName = EXCLUDED.ContactName, "
                + " Email = EXCLUDED.Email, "
                + " Phone = EXCLUDED.Phone, "
                + " RawJSON = EXCLUDED.RawJSON, "
                + " Processed = 'N', "
                + " I_ErrorMsg = NULL";

        DB.executeUpdateEx(
                sql,
                new Object[] {
                        "RED_PartnerTemp",
                        getAD_Client_ID(),
                        Env.getAD_Org_ID(getCtx()),
                        getAD_User_ID(),
                        getAD_User_ID(),
                        zohoId,
                        moduleName,
                        bpValue,
                        bpName,
                        contactName,
                        email,
                        phone,
                        row.toString()
                },
                get_TrxName()
        );
    }

    private void validateParameter() {

        if (p_ZApiKey == null || p_ZApiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("ZApiKey wajib diisi");
        }

        if (p_StartDate == null) {
            throw new IllegalArgumentException("StartDate wajib diisi");
        }

        if (p_PageCount <= 0) {
            p_PageCount = 100;
        }

        if (p_PageCount > 200) {
            throw new IllegalArgumentException("PageCount maksimal 200");
        }
    }

    private String firstNotNull(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) {
                return value;
            }
        }
        return null;
    }
}