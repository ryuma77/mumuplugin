package id.rekaestudigital.mumu.process;

import org.compiere.model.MClient;
import org.compiere.model.MRequisition;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.EMail;

public class SendRequisitionEmail extends SvrProcess {

    private String p_EMail = null;
    private int p_M_Requisition_ID = 0;

    @Override
    protected void prepare() {
        p_M_Requisition_ID = getRecord_ID();

        for (ProcessInfoParameter para : getParameter()) {
            String name = para.getParameterName();

            if ("EMail".equalsIgnoreCase(name)) {
                p_EMail = para.getParameterAsString();
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        if (p_M_Requisition_ID <= 0) {
            throw new Exception("Process harus dijalankan dari record Requisition.");
        }

        if (p_EMail == null || p_EMail.trim().isEmpty()) {
            throw new Exception("Email tujuan wajib diisi.");
        }

        MRequisition req = new MRequisition(
                getCtx(),
                p_M_Requisition_ID,
                get_TrxName()
        );

        String baseUrl = "https://ekonid.rekaestudigital.id/webui";

        String link = baseUrl
                + "/?Action=Zoom"
                + "&AD_Table_ID=702"
                + "&Record_ID=" + req.getM_Requisition_ID();

        String subject = "Requisition " + req.getDocumentNo();

        String message =
                "<p>Dear User,</p>"
              + "<p>Mohon cek Requisition berikut:</p>"
              + "<table>"
              + "<tr><td>Document No</td><td>: " + safe(req.getDocumentNo()) + "</td></tr>"
              + "<tr><td>Description</td><td>: " + safe(req.getDescription()) + "</td></tr>"
              + "<tr><td>Total Lines</td><td>: " + req.getTotalLines() + "</td></tr>"
              + "</table>"
              + "<br/>"
              + "<p>"
              + "<a href='" + link + "' "
              + "style='background:#0d6efd;color:white;padding:10px 18px;text-decoration:none;border-radius:4px;'>"
              + "Buka Requisition"
              + "</a>"
              + "</p>"
              + "<p>Atau copy link berikut:</p>"
              + "<p><a href='" + link + "'>" + link + "</a></p>";

        MClient client = MClient.get(getCtx());

        EMail email = client.createEMail(
                p_EMail,
                subject,
                message,
                true
        );

        if (email == null) {
            throw new Exception("Gagal membuat object email. Cek SMTP, Request User, dan From Email.");
        }

        String status = email.send();

        if (!EMail.SENT_OK.equals(status)) {
            throw new Exception("Email gagal dikirim: " + status);
        }

        return "Email berhasil dikirim ke " + p_EMail;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}