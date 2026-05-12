package id.rekaestudigital.mumu.form;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.webui.component.Label;
import org.adempiere.webui.panel.ADForm;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.North;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.adempiere.webui.editor.WStringEditor;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Button;

public class WProductionFlowViewer extends ADForm {

    private static final long serialVersionUID = 1L;

   
    private WStringEditor planNoEditor;
    private Button loadButton;
    private Vlayout content;

    @Override
    protected void initForm() {

        Borderlayout layout = new Borderlayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        this.appendChild(layout);

        North north = new North();
        north.setHeight("60px");
        north.setBorder("none");
        layout.appendChild(north);

        Hlayout filter = new Hlayout();
        filter.setStyle("padding:10px; align-items:center;");
        north.appendChild(filter);

        Label lbl = new Label("Production Plan ID / Document No:");
        filter.appendChild(lbl);

        planNoEditor = new WStringEditor();
        planNoEditor.getComponent().setWidth("220px");
        filter.appendChild(planNoEditor.getComponent());

        loadButton = new Button("Load Flow");
        filter.appendChild(loadButton);

        loadButton.addEventListener(Events.ON_CLICK, event -> loadFlow());

        Center center = new Center();
        center.setBorder("none");
        layout.appendChild(center);

        content = new Vlayout();
        content.setWidth("100%");
        content.setHeight("100%");
        content.setStyle("padding:15px; overflow:auto;");
        center.appendChild(content);
    }

    private void loadFlow() {

        content.getChildren().clear();

        Object obj = planNoEditor.getValue();
        String value = obj == null ? "" : obj.toString();

        if (value.trim().isEmpty()) {
            showMessage("Isi Production Plan ID atau Document No.");
            return;
        }

        PlanData plan = getPlan(value.trim());

        if (plan == null) {
            showMessage("Production Plan tidak ditemukan.");
            return;
        }

        renderPlan(plan);

        List<StageData> stages = getStages(plan.redProductionPlanId);

        if (stages.isEmpty()) {
            showMessage("Belum ada production stage.");
            return;
        }

        for (StageData stage : stages) {
            renderStage(stage);

            List<OperationData> operations = getOperations(stage.redProductionStageId);

            for (OperationData operation : operations) {
                renderOperation(operation);
            }

            Separator sep = new Separator();
            sep.setHeight("15px");
            content.appendChild(sep);
        }
    }

    private void showMessage(String message) {
        Label label = new Label(message);
        label.setStyle("font-size:14px; color:#777; padding:10px;");
        content.appendChild(label);
    }

    private void renderPlan(PlanData plan) {

        Div card = new Div();
        card.setStyle(
                "padding:15px; margin-bottom:15px; border-radius:10px;"
              + "background:#263238; color:white;"
              + "box-shadow:0 2px 6px rgba(0,0,0,0.2);"
        );

        Label title = new Label("Production Plan: " + safe(plan.documentNo));
        title.setStyle("font-size:20px; font-weight:bold;");
        card.appendChild(title);

        card.appendChild(new Separator());

        card.appendChild(new Label("Product: " + safe(plan.productName)));
        card.appendChild(new Separator());

        card.appendChild(new Label("Qty Planned: " + nvl(plan.qtyPlan)));
        card.appendChild(new Separator());

        card.appendChild(new Label("Qty Completed: " + nvl(plan.QtyDelivered)));
        card.appendChild(new Separator());

        card.appendChild(new Label("Status: " + safe(plan.docStatus)));

        content.appendChild(card);
    }

    private void renderStage(StageData stage) {

        String color = getStatusColor(stage.status);

        Div card = new Div();
        card.setStyle(
                "padding:15px; margin-bottom:8px; border-radius:10px;"
              + "border-left:8px solid " + color + ";"
              + "background:#ffffff;"
              + "box-shadow:0 2px 5px rgba(0,0,0,0.15);"
        );

        Label title = new Label("Stage: " + safe(stage.name));
        title.setStyle("font-size:17px; font-weight:bold; color:#333;");
        card.appendChild(title);

        card.appendChild(new Separator());

        Hlayout row1 = new Hlayout();
        row1.setSpacing("20px");
        row1.appendChild(new Label("Status: " + safe(stage.status)));
        row1.appendChild(new Label("Progress: " + nvl(stage.progressPercent) + "%"));
        row1.appendChild(new Label("Qty Targeted: " + nvl(stage.qtyTargeted)));
        row1.appendChild(new Label("Qty Actual: " + nvl(stage.qtyActual)));
        row1.appendChild(new Label("Qty Loss: " + nvl(stage.qtyLoss)));
        card.appendChild(row1);

        Hlayout row2 = new Hlayout();
        row2.setSpacing("20px");
        row2.appendChild(new Label("Start: " + formatDate(stage.startDate)));
        row2.appendChild(new Label("End: " + formatDate(stage.endDate)));
        card.appendChild(row2);

        content.appendChild(card);
    }

    private void renderOperation(OperationData op) {

        String color = getStatusColor(op.status);

        Div card = new Div();
        card.setStyle(
                "padding:10px; margin-left:35px; margin-bottom:6px; border-radius:8px;"
              + "border-left:6px solid " + color + ";"
              + "background:#fafafa;"
              + "box-shadow:0 1px 3px rgba(0,0,0,0.12);"
        );

        Label title = new Label("→ " + op.seqNo + " - " + safe(op.name));
        title.setStyle("font-size:14px; font-weight:bold; color:#333;");
        card.appendChild(title);

        card.appendChild(new Separator());

        Hlayout row1 = new Hlayout();
        row1.setSpacing("18px");
        row1.appendChild(new Label("Status: " + safe(op.status)));
        row1.appendChild(new Label("Machine: " + safe(op.machineName)));
        row1.appendChild(new Label("Qty Targeted: " + nvl(op.qtyTargeted)));
        row1.appendChild(new Label("Qty Actual: " + nvl(op.qtyActual)));
        card.appendChild(row1);

        Hlayout row2 = new Hlayout();
        row2.setSpacing("18px");
        row2.appendChild(new Label("Start: " + formatDate(op.startDate)));
        row2.appendChild(new Label("End: " + formatDate(op.endDate)));
        card.appendChild(row2);

        content.appendChild(card);
    }

    private String getStatusColor(String status) {

        if ("COMPLETED".equals(status)) {
            return "#4CAF50";
        }

        if ("INPROGRESS".equals(status)) {
            return "#FF9800";
        }

        if ("PAUSED".equals(status)) {
            return "#2196F3";
        }

        if ("CANCELLED".equals(status)) {
            return "#F44336";
        }

        if ("WAITINGMATERIAL".equals(status) || "WAITINGMACHINE".equals(status)) {
            return "#9C27B0";
        }

        if ("READY".equals(status)) {
            return "#03A9F4";
        }

        return "#9E9E9E";
    }

    private PlanData getPlan(String value) {

        String sql =
                "SELECT pp.red_productionplan_id, "
              + "       pp.documentno, "
              + "       pp.docstatus, "
              + "       pp.qtyplan, "
              + "       pp.QtyDelivered, "
              + "       p.name AS product_name "
              + "FROM red_productionplan pp "
              + "LEFT JOIN m_product p ON p.m_product_id = pp.m_product_id "
              + "WHERE pp.documentno = ? "
              + "   OR pp.red_productionplan_id = ?";

        int id = 0;

        try {
            id = Integer.parseInt(value);
        } catch (Exception e) {
            id = 0;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DB.prepareStatement(sql, null);
            pstmt.setString(1, value);
            pstmt.setInt(2, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                PlanData data = new PlanData();
                data.redProductionPlanId = rs.getInt("red_productionplan_id");
                data.documentNo = rs.getString("documentno");
                data.docStatus = rs.getString("docstatus");
                data.qtyPlan = rs.getBigDecimal("qtyplan");
                data.QtyDelivered = rs.getBigDecimal("QtyDelivered");
                data.productName = rs.getString("product_name");
                return data;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }

        return null;
    }

    private List<StageData> getStages(int redProductionPlanId) {

        List<StageData> list = new ArrayList<>();

        String sql =
                "SELECT red_productionstage_id, "
              + "       seqno, "
              + "       name, "
              + "       red_productionstatus, "
              + "       qtytargeted, "
              + "       qtyactual, "
              + "       qtyloss, "
              + "       progresspercent, "
              + "       startdate, "
              + "       enddate "
              + "FROM red_productionstage "
              + "WHERE red_productionplan_id = ? "
              + "ORDER BY seqno";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DB.prepareStatement(sql, null);
            pstmt.setInt(1, redProductionPlanId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                StageData data = new StageData();
                data.redProductionStageId = rs.getInt("red_productionstage_id");
                data.seqNo = rs.getInt("seqno");
                data.name = rs.getString("name");
                data.status = rs.getString("red_productionstatus");
                data.qtyTargeted = rs.getBigDecimal("qtytargeted");
                data.qtyActual = rs.getBigDecimal("qtyactual");
                data.qtyLoss = rs.getBigDecimal("qtyloss");
                data.progressPercent = rs.getBigDecimal("progresspercent");
                data.startDate = rs.getTimestamp("startdate");
                data.endDate = rs.getTimestamp("enddate");
                list.add(data);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }

        return list;
    }

    private List<OperationData> getOperations(int redProductionStageId) {

        List<OperationData> list = new ArrayList<>();

        String sql =
                "SELECT po.red_productionoperation_id, "
              + "       po.seqno, "
              + "       po.name, "
              + "       po.red_productionstatus, "
              + "       po.qtytargeted, "
              + "       po.qtyactual, "
              + "       po.startdate, "
              + "       po.enddate, "
              + "       m.name AS machine_name "
              + "FROM red_productionoperation po "
              + "LEFT JOIN red_machine m ON m.red_machine_id = po.red_machine_id "
              + "WHERE po.red_productionstage_id = ? "
              + "ORDER BY po.seqno";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DB.prepareStatement(sql, null);
            pstmt.setInt(1, redProductionStageId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                OperationData data = new OperationData();
                data.redProductionOperationId = rs.getInt("red_productionoperation_id");
                data.seqNo = rs.getInt("seqno");
                data.name = rs.getString("name");
                data.status = rs.getString("red_productionstatus");
                data.qtyTargeted = rs.getBigDecimal("qtytargeted");
                data.qtyActual = rs.getBigDecimal("qtyactual");
                data.startDate = rs.getTimestamp("startdate");
                data.endDate = rs.getTimestamp("enddate");
                data.machineName = rs.getString("machine_name");
                list.add(data);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }

        return list;
    }

    private String safe(String value) {
        return value == null ? "-" : value;
    }

    private String nvl(BigDecimal value) {
        return value == null ? "0" : value.stripTrailingZeros().toPlainString();
    }

    private String formatDate(Timestamp ts) {
        return ts == null ? "-" : ts.toString();
    }

    private static class PlanData {
        int redProductionPlanId;
        String documentNo;
        String docStatus;
        String productName;
        BigDecimal qtyPlan;
        BigDecimal QtyDelivered;
    }

    private static class StageData {
        int redProductionStageId;
        int seqNo;
        String name;
        String status;
        BigDecimal qtyTargeted;
        BigDecimal qtyActual;
        BigDecimal qtyLoss;
        BigDecimal progressPercent;
        Timestamp startDate;
        Timestamp endDate;
    }

    private static class OperationData {
        int redProductionOperationId;
        int seqNo;
        String name;
        String status;
        String machineName;
        BigDecimal qtyTargeted;
        BigDecimal qtyActual;
        Timestamp startDate;
        Timestamp endDate;
    }
}