package id.rekaestudigital.mumu.form;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.webui.editor.WStringEditor;
import org.adempiere.webui.panel.ADForm;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.North;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vlayout;

import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zul.Messagebox;

public class WProductionKanbanViewer extends ADForm {

    private static final long serialVersionUID = 1L;

    private WStringEditor searchEditor;
    private Button refreshButton;
    private Hlayout board;
    
    private static final String STATUS_NOTSTARTED = "NOTSTARTED";
    private static final String STATUS_READY = "READY";
    private static final String STATUS_WAITING_MATERIAL = "WAITINGMATERIAL";
    private static final String STATUS_WAITING_MACHINE = "WAITINGMACHINE";
    private static final String STATUS_INPROGRESS = "INPROGRESS";
    private static final String STATUS_PAUSED = "PAUSED";
    private static final String STATUS_COMPLETED = "COMPLETED";
    private static final String STATUS_CANCELLED = "CANCELLED";

    @Override
    protected void initForm() {

        Borderlayout layout = new Borderlayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        this.appendChild(layout);

        North north = new North();
        north.setHeight("65px");
        north.setBorder("none");
        layout.appendChild(north);

        Hlayout filter = new Hlayout();
        filter.setStyle("padding:10px; align-items:center;");
        filter.setSpacing("10px");
        north.appendChild(filter);

        Label lbl = new Label("Search Plan/Product/Customer:");
        filter.appendChild(lbl);

        searchEditor = new WStringEditor();
        searchEditor.getComponent().setWidth("260px");
        filter.appendChild(searchEditor.getComponent());

        refreshButton = new Button("Refresh");
        filter.appendChild(refreshButton);

        refreshButton.addEventListener(Events.ON_CLICK, e -> loadKanban());

        Center center = new Center();
        center.setBorder("none");
        layout.appendChild(center);

        board = new Hlayout();
        board.setWidth("100%");
        board.setHeight("100%");
        board.setSpacing("12px");
        board.setStyle("padding:12px; overflow:auto; background:#f5f5f5;");
        center.appendChild(board);

        loadKanban();
    }

    private void loadKanban() {

        board.getChildren().clear();

        String keyword = "";
        Object obj = searchEditor.getValue();

        if (obj != null) {
            keyword = obj.toString().trim();
        }

        List<KanbanCardData> cards = getProductionPlans(keyword);

        Vlayout readyCol = createColumn("READY / NOT STARTED", "#607D8B", STATUS_READY);
        Vlayout waitingMaterialCol = createColumn("WAITING MATERIAL", "#9C27B0", STATUS_WAITING_MATERIAL);
        Vlayout waitingMachineCol = createColumn("WAITING MACHINE", "#673AB7", STATUS_WAITING_MACHINE);
        Vlayout progressCol = createColumn("IN PROGRESS", "#FF9800", STATUS_INPROGRESS);
        Vlayout pausedCol = createColumn("PAUSED", "#2196F3", STATUS_PAUSED);
        Vlayout completedCol = createColumn("COMPLETED", "#4CAF50", STATUS_COMPLETED);
        Vlayout cancelledCol = createColumn("CANCELLED", "#F44336", STATUS_CANCELLED);

        for (KanbanCardData card : cards) {

            Div cardDiv = createCard(card);

            String bucket = getBucket(card.status);

            if (STATUS_WAITING_MATERIAL.equals(card.status)) {
                waitingMaterialCol.appendChild(cardDiv);
            } else if (STATUS_WAITING_MACHINE.equals(card.status)) {
                waitingMachineCol.appendChild(cardDiv);
            } else if (STATUS_INPROGRESS.equals(card.status)) {
                progressCol.appendChild(cardDiv);
            } else if (STATUS_PAUSED.equals(card.status)) {
                pausedCol.appendChild(cardDiv);
            } else if (STATUS_COMPLETED.equals(card.status)) {
                completedCol.appendChild(cardDiv);
            } else if (STATUS_CANCELLED.equals(card.status)) {
                cancelledCol.appendChild(cardDiv);
            } else {
                readyCol.appendChild(cardDiv);
            }
        }

        board.appendChild(readyCol);
        board.appendChild(waitingMaterialCol);
        board.appendChild(waitingMachineCol);
        board.appendChild(progressCol);
        board.appendChild(pausedCol);
        board.appendChild(completedCol);
        board.appendChild(cancelledCol);
    }

    private Vlayout createColumn(String title, String color, String targetStatus) {

        Vlayout col = new Vlayout();

        col.setAttribute("TargetStatus", targetStatus);
        col.setDroppable("true");

        col.addEventListener(Events.ON_DROP, event -> {
            handleDrop((DropEvent) event, col);
        });

        col.setWidth("280px");
        col.setHeight("100%");
        col.setStyle(
                "background:#ffffff;"
              + "border-radius:10px;"
              + "box-shadow:0 1px 5px rgba(0,0,0,0.15);"
              + "padding:10px;"
              + "overflow:auto;"
        );

        Div header = new Div();
        header.setStyle(
                "padding:10px;"
              + "border-radius:8px;"
              + "background:" + color + ";"
              + "color:white;"
              + "font-weight:bold;"
              + "text-align:center;"
              + "margin-bottom:8px;"
        );
        header.appendChild(new Label(title));

        col.appendChild(header);

        return col;
    }

    private Div createCard(KanbanCardData data) {

    	Div card = new Div();

        card.setDraggable("true");
        card.setAttribute("RED_ProductionPlan_ID", data.redProductionPlanId);
        card.setAttribute("DocumentNo", data.documentNo);
        card.setAttribute("CurrentStatus", data.status);
        card.setStyle(
                "padding:10px;"
              + "margin-bottom:10px;"
              + "border-radius:8px;"
              + "background:#fafafa;"
              + "border-left:6px solid " + getStatusColor(data.status) + ";"
              + "box-shadow:0 1px 4px rgba(0,0,0,0.18);"
              + "cursor:move;"
        );

        Label title = new Label(data.documentNo + " - " + safe(data.productName));
        title.setStyle("font-weight:bold; font-size:13px; color:#333;");
        card.appendChild(title);

        card.appendChild(new Separator());

        card.appendChild(line("Customer", safe(data.customerName)));
        card.appendChild(line("Qty Plan", nvl(data.qtyPlan)));
        card.appendChild(line("Qty Done", nvl(data.QtyDelivered)));
        card.appendChild(line("Progress", nvl(data.progressPercent) + "%"));
        card.appendChild(line("Stage", safe(data.currentStage)));
        card.appendChild(line("Operation", safe(data.currentOperation)));
        card.appendChild(line("Machine", safe(data.machineName)));
        card.appendChild(line("Status", safe(data.status)));
        card.appendChild(line("Promise", formatDate(data.DateAcct)));

        card.addEventListener(Events.ON_CLICK, e -> {
            showPlanInfo(data);
        });

        return card;
    }

    private Hlayout line(String label, String value) {

        Hlayout row = new Hlayout();
        row.setWidth("100%");
        row.setStyle("font-size:12px; color:#444;");

        Label l = new Label(label + ": ");
        l.setStyle("font-weight:bold; min-width:75px;");
        row.appendChild(l);

        Label v = new Label(value);
        row.appendChild(v);

        return row;
    }

    private void showPlanInfo(KanbanCardData data) {

        System.out.println(
                "Selected Production Plan: "
              + data.redProductionPlanId
              + " / "
              + data.documentNo
        );
    }

    private String getBucket(String status) {

        if ("WAITINGMATERIAL".equals(status) || "WAITINGMACHINE".equals(status)) {
            return "WAITING";
        }

        if ("INPROGRESS".equals(status)) {
            return "INPROGRESS";
        }

        if ("PAUSED".equals(status)) {
            return "PAUSED";
        }

        if ("COMPLETED".equals(status)) {
            return "COMPLETED";
        }

        if ("CANCELLED".equals(status)) {
            return "CANCELLED";
        }

        return "READY";
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

    private List<KanbanCardData> getProductionPlans(String keyword) {

        List<KanbanCardData> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append(" pp.red_productionplan_id, ");
        sql.append(" pp.documentno, ");
        sql.append(" pp.DateAcct, ");
        sql.append(" pp.qtyplan, ");
        sql.append(" pp.QtyDelivered, ");
        sql.append(" p.name AS product_name, ");
        sql.append(" bp.name AS customer_name, ");

        /*
         * progress rata-rata dari stage
         */
        sql.append(" COALESCE(stg.progress_percent, 0) AS progress_percent, ");

        /*
         * status plan:
         * ambil status stage pertama yang belum completed.
         * kalau semua completed, completed.
         */
        sql.append(" COALESCE(cur_stage.red_productionstatus, ");
        sql.append("          CASE WHEN stg.completed_count = stg.total_count AND stg.total_count > 0 ");
        sql.append("               THEN 'COMPLETED' ");
        sql.append("               ELSE 'NOTSTARTED' ");
        sql.append("          END) AS plan_status, ");

        sql.append(" cur_stage.name AS current_stage, ");
        sql.append(" cur_op.name AS current_operation, ");
        sql.append(" rm.name AS machine_name ");

        sql.append("FROM red_productionplan pp ");
        sql.append("LEFT JOIN m_product p ON p.m_product_id = pp.m_product_id ");
        sql.append("LEFT JOIN c_bpartner bp ON bp.c_bpartner_id = pp.c_bpartner_id ");

        /*
         * Aggregate stage progress
         */
        sql.append("LEFT JOIN ( ");
        sql.append("    SELECT ");
        sql.append("        red_productionplan_id, ");
        sql.append("        COUNT(*) AS total_count, ");
        sql.append("        SUM(CASE WHEN red_productionstatus = 'COMPLETED' THEN 1 ELSE 0 END) AS completed_count, ");
        sql.append("        AVG(COALESCE(progresspercent,0)) AS progress_percent ");
        sql.append("    FROM red_productionstage ");
        sql.append("    WHERE isactive='Y' ");
        sql.append("    GROUP BY red_productionplan_id ");
        sql.append(") stg ON stg.red_productionplan_id = pp.red_productionplan_id ");

        /*
         * current stage = stage pertama yang belum completed/cancelled
         */
        sql.append("LEFT JOIN LATERAL ( ");
        sql.append("    SELECT s.red_productionstage_id, s.name, s.red_productionstatus, s.seqno ");
        sql.append("    FROM red_productionstage s ");
        sql.append("    WHERE s.red_productionplan_id = pp.red_productionplan_id ");
        sql.append("      AND s.isactive='Y' ");
        sql.append("      AND COALESCE(s.red_productionstatus,'NOTSTARTED') NOT IN ('COMPLETED','CANCELLED') ");
        sql.append("    ORDER BY s.seqno ");
        sql.append("    LIMIT 1 ");
        sql.append(") cur_stage ON true ");

        /*
         * current operation = operation pertama yang belum completed/cancelled
         */
        sql.append("LEFT JOIN LATERAL ( ");
        sql.append("    SELECT o.red_productionoperation_id, o.name, o.red_machine_id, o.seqno ");
        sql.append("    FROM red_productionoperation o ");
        sql.append("    WHERE o.red_productionstage_id = cur_stage.red_productionstage_id ");
        sql.append("      AND o.isactive='Y' ");
        sql.append("      AND COALESCE(o.red_productionstatus,'NOTSTARTED') NOT IN ('COMPLETED','CANCELLED') ");
        sql.append("    ORDER BY o.seqno ");
        sql.append("    LIMIT 1 ");
        sql.append(") cur_op ON true ");

        sql.append("LEFT JOIN red_machine rm ON rm.red_machine_id = cur_op.red_machine_id ");

        sql.append("WHERE pp.isactive='Y' ");

        if (keyword != null && keyword.trim().length() > 0) {
            sql.append(" AND (");
            sql.append("      UPPER(pp.documentno) LIKE UPPER(?) ");
            sql.append("   OR UPPER(p.name) LIKE UPPER(?) ");
            sql.append("   OR UPPER(bp.name) LIKE UPPER(?) ");
            sql.append(" ) ");
        }

        sql.append("ORDER BY pp.created DESC ");

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DB.prepareStatement(sql.toString(), null);

            int index = 1;

            if (keyword != null && keyword.trim().length() > 0) {
                String like = "%" + keyword.trim() + "%";
                pstmt.setString(index++, like);
                pstmt.setString(index++, like);
                pstmt.setString(index++, like);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {

                KanbanCardData data = new KanbanCardData();

                data.redProductionPlanId = rs.getInt("red_productionplan_id");
                data.documentNo = rs.getString("documentno");
                data.productName = rs.getString("product_name");
                data.customerName = rs.getString("customer_name");
                data.qtyPlan = rs.getBigDecimal("qtyplan");
                data.QtyDelivered = rs.getBigDecimal("QtyDelivered");
                data.progressPercent = rs.getBigDecimal("progress_percent");
                data.status = rs.getString("plan_status");
                data.currentStage = rs.getString("current_stage");
                data.currentOperation = rs.getString("current_operation");
                data.machineName = rs.getString("machine_name");
                data.DateAcct = rs.getTimestamp("DateAcct");

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

    private static class KanbanCardData {
        int redProductionPlanId;
        String documentNo;
        String productName;
        String customerName;
        BigDecimal qtyPlan;
        BigDecimal QtyDelivered;
        BigDecimal progressPercent;
        String status;
        String currentStage;
        String currentOperation;
        String machineName;
        Timestamp DateAcct;
    }
    
    private void handleDrop(DropEvent event, Vlayout targetColumn) {

        if (!(event.getDragged() instanceof Div)) {
            return;
        }

        Div draggedCard = (Div) event.getDragged();

        Object idObj = draggedCard.getAttribute("RED_ProductionPlan_ID");
        Object docObj = draggedCard.getAttribute("DocumentNo");
        Object currentStatusObj = draggedCard.getAttribute("CurrentStatus");

        if (idObj == null) {
            return;
        }

        int productionPlanId = Integer.parseInt(idObj.toString());
        String documentNo = docObj == null ? "" : docObj.toString();
        String currentStatus = currentStatusObj == null ? "" : currentStatusObj.toString();

        String targetStatus = String.valueOf(targetColumn.getAttribute("TargetStatus"));

        if (targetStatus == null || targetStatus.trim().isEmpty()) {
            return;
        }

        if (targetStatus.equals(currentStatus)) {
            return;
        }

        if (!isAllowedDragStatus(targetStatus)) {
            showKanbanMessage(
                    "Status " + targetStatus
                  + " tidak boleh diubah langsung dari Kanban. "
                  + "Gunakan process Start / Complete / Cancel."
            );
            return;
        }

        updatePlanManualStatus(productionPlanId, targetStatus);

        showKanbanMessage(
                "Production Plan " + documentNo
              + " dipindahkan ke " + targetStatus
        );

        loadKanban();
    }
    
    private boolean isAllowedDragStatus(String targetStatus) {

        return STATUS_READY.equals(targetStatus)
            || STATUS_WAITING_MATERIAL.equals(targetStatus)
            || STATUS_WAITING_MACHINE.equals(targetStatus)
            || STATUS_PAUSED.equals(targetStatus);
    }
    
    private void updatePlanManualStatus(int productionPlanId, String targetStatus) {

        String sql =
                "UPDATE red_productionstage "
              + "SET red_productionstatus = ?, "
              + "    updated = now(), "
              + "    updatedby = ? "
              + "WHERE red_productionstage_id = ( "
              + "    SELECT red_productionstage_id "
              + "    FROM red_productionstage "
              + "    WHERE red_productionplan_id = ? "
              + "      AND isactive='Y' "
              + "      AND COALESCE(red_productionstatus,'NOTSTARTED') NOT IN ('COMPLETED','CANCELLED') "
              + "    ORDER BY seqno "
              + "    LIMIT 1 "
              + ")";

        DB.executeUpdateEx(
                sql,
                new Object[] {
                        targetStatus,
                        Env.getAD_User_ID(Env.getCtx()),
                        productionPlanId
                },
                null
        );
    }
    
    private void showKanbanMessage(String message) {
        Messagebox.show(message, "Info", Messagebox.OK, Messagebox.INFORMATION);
    }
}