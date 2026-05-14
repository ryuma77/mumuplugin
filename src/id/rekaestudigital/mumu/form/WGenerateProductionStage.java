package id.rekaestudigital.mumu.form;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Combobox;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.panel.ADForm;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vbox;

import id.rekaestudigital.mumu.model.MREDOperation;
import id.rekaestudigital.mumu.model.MREDOperationMaterial;
import id.rekaestudigital.mumu.model.MREDProductionOperation;
import id.rekaestudigital.mumu.model.MREDProductionOperationMaterial;
import id.rekaestudigital.mumu.model.MREDProductionPlan;
import id.rekaestudigital.mumu.model.MREDProductionStage;
import id.rekaestudigital.mumu.model.MREDStage;

public class WGenerateProductionStage extends ADForm {

    private static final long serialVersionUID = 1L;

    private org.zkoss.zul.Textbox planNoBox = new org.zkoss.zul.Textbox();
    private Button loadButton = new Button("Load");

    private Combobox materialSourceBox = new Combobox();

    private Listbox stageOperationList = new Listbox();
    private Listbox previewList = new Listbox();

    private Button previewButton = new Button("Preview");
    private Button generateButton = new Button("Generate");

    private MREDProductionPlan currentPlan;

    private final Map<Integer, MREDStage> stageMap = new LinkedHashMap<>();
    private final Map<Integer, MREDOperation> operationMap = new LinkedHashMap<>();

    private static final String MATERIAL_SOURCE_OPERATION = "OPERATION";
    private static final String MATERIAL_SOURCE_BOM = "BOM";
    private static final String MATERIAL_SOURCE_BOTH = "BOTH";

    @Override
    protected void initForm() {
        buildLayout();
        bindEvents();
        initMaterialSource();
    }

    private void buildLayout() {
        this.setWidth("100%");
        this.setHeight("100%");

        Vbox main = new Vbox();
        main.setWidth("100%");
        main.setHeight("100%");
        main.setSpacing("8px");
        main.setStyle("padding: 12px;");

        Label title = new Label("Generate Production Stage");
        title.setStyle("font-size: 18px; font-weight: bold;");

        Hbox header = new Hbox();
        header.setSpacing("8px");
        header.setAlign("center");

        Label planLabel = new Label("Production Plan Document No / ID:");
        planNoBox.setWidth("260px");

        loadButton.setWidth("90px");

        header.appendChild(planLabel);
        header.appendChild(planNoBox);
        header.appendChild(loadButton);

        Hbox optionBox = new Hbox();
        optionBox.setSpacing("8px");
        optionBox.setAlign("center");

        Label materialSourceLabel = new Label("Material Source:");
        materialSourceBox.setWidth("220px");

        optionBox.appendChild(materialSourceLabel);
        optionBox.appendChild(materialSourceBox);

        Label stageLabel = new Label("Stage / Operation Selection");
        stageLabel.setStyle("font-weight: bold; margin-top: 8px;");

        setupStageOperationList();

        Hbox actionBox = new Hbox();
        actionBox.setSpacing("8px");

        previewButton.setWidth("100px");
        generateButton.setWidth("100px");
        generateButton.setDisabled(true);

        actionBox.appendChild(previewButton);
        actionBox.appendChild(generateButton);

        Label previewLabel = new Label("Preview");
        previewLabel.setStyle("font-weight: bold; margin-top: 8px;");

        setupPreviewList();

        main.appendChild(title);
        main.appendChild(new Separator());
        main.appendChild(header);
        main.appendChild(optionBox);
        main.appendChild(stageLabel);
        main.appendChild(stageOperationList);
        main.appendChild(actionBox);
        main.appendChild(previewLabel);
        main.appendChild(previewList);

        Div wrapper = new Div();
        wrapper.setWidth("100%");
        wrapper.setHeight("100%");
        wrapper.appendChild(main);

        this.appendChild(wrapper);
    }

    private void setupStageOperationList() {
        stageOperationList.setWidth("100%");
        stageOperationList.setHeight("280px");
        stageOperationList.setCheckmark(true);
        stageOperationList.setMultiple(true);

        Listhead head = new Listhead();

        Listheader typeHeader = new Listheader("Type");
        typeHeader.setWidth("120px");

        Listheader stageHeader = new Listheader("Stage");
        stageHeader.setWidth("220px");

        Listheader operationHeader = new Listheader("Operation");
        operationHeader.setWidth("260px");

        Listheader seqHeader = new Listheader("Seq");
        seqHeader.setWidth("80px");

        head.appendChild(typeHeader);
        head.appendChild(stageHeader);
        head.appendChild(operationHeader);
        head.appendChild(seqHeader);

        stageOperationList.appendChild(head);
    }

    private void setupPreviewList() {
        previewList.setWidth("100%");
        previewList.setHeight("260px");

        Listhead head = new Listhead();

        Listheader typeHeader = new Listheader("Type");
        typeHeader.setWidth("140px");

        Listheader stageHeader = new Listheader("Stage");
        stageHeader.setWidth("220px");

        Listheader operationHeader = new Listheader("Operation");
        operationHeader.setWidth("260px");

        Listheader materialHeader = new Listheader("Material / Info");
        materialHeader.setWidth("420px");

        Listheader sourceHeader = new Listheader("Source");
        sourceHeader.setWidth("140px");

        head.appendChild(typeHeader);
        head.appendChild(stageHeader);
        head.appendChild(operationHeader);
        head.appendChild(materialHeader);
        head.appendChild(sourceHeader);

        previewList.appendChild(head);
    }

    private void bindEvents() {
        loadButton.addEventListener("onClick", new EventListener<Event>() {
            @Override
            public void onEvent(Event event) {
                loadProductionPlan();
            }
        });

        previewButton.addEventListener("onClick", new EventListener<Event>() {
            @Override
            public void onEvent(Event event) {
                previewGenerate();
            }
        });

        generateButton.addEventListener("onClick", new EventListener<Event>() {
            @Override
            public void onEvent(Event event) {
                confirmGenerate();
            }
        });
    }

    private void initMaterialSource() {
        materialSourceBox.getItems().clear();

        addMaterialSourceItem("Operation Material Only", MATERIAL_SOURCE_OPERATION);
        addMaterialSourceItem("BOM Only", MATERIAL_SOURCE_BOM);
        addMaterialSourceItem("Operation Material + BOM", MATERIAL_SOURCE_BOTH);

        materialSourceBox.setSelectedIndex(0);
    }

    private void addMaterialSourceItem(String label, String value) {
        Comboitem item = new Comboitem(label);
        item.setValue(value);
        materialSourceBox.appendChild(item);
    }

    private String getMaterialSource() {
        if (materialSourceBox.getSelectedItem() == null) {
            return MATERIAL_SOURCE_OPERATION;
        }

        Object value = materialSourceBox.getSelectedItem().getValue();
        return value == null ? MATERIAL_SOURCE_OPERATION : value.toString();
    }

    private void loadProductionPlan() {
        Object rawValue = planNoBox.getRawValue();
        String input = rawValue == null ? "" : rawValue.toString().trim();

        if (input.isEmpty()) {
            showMessage("Isi Production Plan Document No atau ID.");
            return;
        }

        currentPlan = findProductionPlan(input);

        if (currentPlan == null || currentPlan.get_ID() <= 0) {
            showMessage("Production Plan tidak ditemukan: " + input);
            return;
        }

        loadStageOperation();
    }

    private MREDProductionPlan findProductionPlan(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }

        input = input.trim();

        try {
            int id = Integer.parseInt(input);

            boolean exists = DB.getSQLValue(
                    null,
                    "SELECT COUNT(*) FROM RED_ProductionPlan WHERE RED_ProductionPlan_ID=?",
                    id
            ) > 0;

            if (exists) {
                MREDProductionPlan plan = new MREDProductionPlan(Env.getCtx(), id, null);
                if (plan.get_ID() > 0) {
                    return plan;
                }
            }
        } catch (Exception ignored) {
        }

        int planId = DB.getSQLValue(
                null,
                "SELECT RED_ProductionPlan_ID "
                        + "FROM RED_ProductionPlan "
                        + "WHERE AD_Client_ID=? "
                        + "AND DocumentNo=? "
                        + "AND IsActive='Y'",
                Env.getAD_Client_ID(Env.getCtx()),
                input
        );

        if (planId > 0) {
            return new MREDProductionPlan(Env.getCtx(), planId, null);
        }

        if (isColumnExists("RED_ProductionPlan", "Value")) {
            planId = DB.getSQLValue(
                    null,
                    "SELECT RED_ProductionPlan_ID "
                            + "FROM RED_ProductionPlan "
                            + "WHERE AD_Client_ID=? "
                            + "AND Value=? "
                            + "AND IsActive='Y'",
                    Env.getAD_Client_ID(Env.getCtx()),
                    input
            );

            if (planId > 0) {
                return new MREDProductionPlan(Env.getCtx(), planId, null);
            }
        }

        return null;
    }

    private void loadStageOperation() {
        clearStageOperationList();
        clearPreviewList();

        stageMap.clear();
        operationMap.clear();

        String sqlStage =
                "SELECT RED_Stage_ID "
              + "FROM RED_Stage "
              + "WHERE IsActive='Y' "
              + "AND AD_Client_ID IN (0, ?) "
              + "ORDER BY SeqNo, Name";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DB.prepareStatement(sqlStage, null);
            pstmt.setInt(1, Env.getAD_Client_ID(Env.getCtx()));
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int stageId = rs.getInt("RED_Stage_ID");

                MREDStage stage = new MREDStage(Env.getCtx(), stageId, null);

                if (stage.get_ID() <= 0) {
                    continue;
                }

                stageMap.put(stage.getRED_Stage_ID(), stage);
                addStageRow(stage);

                loadOperationsByStage(stage);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error load stage: " + e.getMessage());
        } finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }

        showMessage("Production Plan berhasil diload. Silakan pilih Stage dan Operation.");
    }

    private void loadOperationsByStage(MREDStage stage) {
        if (stage == null || stage.get_ID() <= 0) {
            return;
        }

        String sqlOperation =
                "SELECT RED_Operation_ID "
              + "FROM RED_Operation "
              + "WHERE IsActive='Y' "
              + "AND RED_Stage_ID=? "
              + "AND AD_Client_ID IN (0, ?) "
              + "ORDER BY SeqNo, Name";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DB.prepareStatement(sqlOperation, null);
            pstmt.setInt(1, stage.getRED_Stage_ID());
            pstmt.setInt(2, Env.getAD_Client_ID(Env.getCtx()));

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int operationId = rs.getInt("RED_Operation_ID");

                MREDOperation operation = new MREDOperation(Env.getCtx(), operationId, null);

                if (operation.get_ID() <= 0) {
                    continue;
                }

                operationMap.put(operation.getRED_Operation_ID(), operation);
                addOperationRow(stage, operation);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error load operation untuk stage " + stage.getName() + ": " + e.getMessage());
        } finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }
    }

    private void addStageRow(MREDStage stage) {
        ListItem item = new ListItem();
        item.setValue("STAGE:" + stage.getRED_Stage_ID());

        item.appendChild(new Listcell("STAGE"));
        item.appendChild(new Listcell(stage.getName()));
        item.appendChild(new Listcell(""));
        item.appendChild(new Listcell(String.valueOf(stage.getSeqNo())));

        if (isYes(stage, "IsDefault")) {
            item.setSelected(true);
        }

        item.setStyle("font-weight: bold; background-color: #f5f5f5;");

        stageOperationList.appendChild(item);
    }

    private void addOperationRow(MREDStage stage, MREDOperation operation) {
        ListItem item = new ListItem();
        item.setValue("OPERATION:" + operation.getRED_Operation_ID());

        item.appendChild(new Listcell("OPERATION"));
        item.appendChild(new Listcell(stage.getName()));
        item.appendChild(new Listcell(operation.getName()));
        item.appendChild(new Listcell(String.valueOf(operation.getSeqNo())));

        if (isYes(stage, "IsDefault")) {
            item.setSelected(true);
        }

        stageOperationList.appendChild(item);
    }

    private void previewGenerate() {
        if (currentPlan == null || currentPlan.get_ID() <= 0) {
            showMessage("Load Production Plan dulu.");
            return;
        }

        clearPreviewList();

        String materialSource = getMaterialSource();
        List<Integer> selectedOperationIds = getSelectedOperationIds();

        if (selectedOperationIds.isEmpty()) {
            showMessage("Pilih minimal satu operation.");
            return;
        }

        for (Integer operationId : selectedOperationIds) {
            MREDOperation operation = operationMap.get(operationId);
            if (operation == null) {
                continue;
            }

            MREDStage stage = stageMap.get(operation.getRED_Stage_ID());
            String stageName = stage == null ? "" : stage.getName();

            addPreviewRow(
                    "OPERATION",
                    stageName,
                    operation.getName(),
                    "Operation akan digenerate",
                    ""
            );

            if (MATERIAL_SOURCE_OPERATION.equals(materialSource) || MATERIAL_SOURCE_BOTH.equals(materialSource)) {
                previewOperationMaterial(stageName, operation);
            }

            if (MATERIAL_SOURCE_BOM.equals(materialSource) || MATERIAL_SOURCE_BOTH.equals(materialSource)) {
                previewBOMMaterial(stageName, operation);
            }
        }

        generateButton.setDisabled(false);
    }

    private void previewOperationMaterial(String stageName, MREDOperation operation) {
        String sql =
                "SELECT RED_OperationMaterial_ID "
              + "FROM RED_OperationMaterial "
              + "WHERE IsActive='Y' "
              + "AND RED_Operation_ID=? "
              + "AND AD_Client_ID IN (0, ?) "
              + "ORDER BY RED_OperationMaterial_ID";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        boolean found = false;

        try {
            pstmt = DB.prepareStatement(sql, null);
            pstmt.setInt(1, operation.getRED_Operation_ID());
            pstmt.setInt(2, Env.getAD_Client_ID(Env.getCtx()));

            rs = pstmt.executeQuery();

            while (rs.next()) {
                found = true;

                int operationMaterialId = rs.getInt("RED_OperationMaterial_ID");
                MREDOperationMaterial mat = new MREDOperationMaterial(Env.getCtx(), operationMaterialId, null);

                int productId = getIntValue(mat, "M_Product_ID");
                int uomId = getIntValue(mat, "C_UOM_ID");

                BigDecimal qty = getBigDecimalValue(mat, "QtyRequiered");

                String info = getProductName(productId)
                        + " | Qty: " + qty
                        + " " + getUOMName(uomId);

                addPreviewRow(
                        "MATERIAL",
                        stageName,
                        operation.getName(),
                        info,
                        "OPERATION"
                );
            }

        } catch (Exception e) {
            found = true;
            e.printStackTrace();

            addPreviewRow(
                    "ERROR",
                    stageName,
                    operation.getName(),
                    "Error baca Operation Material: " + e.getMessage(),
                    "OPERATION"
            );
        } finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }

        if (!found) {
            addPreviewRow(
                    "MATERIAL",
                    stageName,
                    operation.getName(),
                    "Tidak ada Operation Material",
                    "OPERATION"
            );
        }
    }

    private void previewBOMMaterial(String stageName, MREDOperation operation) {
        int productId = getProductionPlanProductId();

        if (productId <= 0) {
            addPreviewRow(
                    "MATERIAL",
                    stageName,
                    operation.getName(),
                    "Product di Production Plan belum ditemukan, BOM tidak bisa dibaca",
                    "BOM"
            );
            return;
        }

        String sql =
                "SELECT bl.PP_Product_BOMLine_ID, "
              + "       bl.M_Product_ID, "
              + "       bl.C_UOM_ID, "
              + "       bl.QtyBOM, "
              + "       map.AllocationType, "
              + "       map.QtyAllocated, "
              + "       map.PercentAllocated "
              + "FROM PP_Product_BOM bom "
              + "JOIN PP_Product_BOMLine bl ON bl.PP_Product_BOM_ID = bom.PP_Product_BOM_ID "
              + "JOIN RED_BOMLineOperation map ON map.PP_Product_BOMLine_ID = bl.PP_Product_BOMLine_ID "
              + "WHERE bom.M_Product_ID = ? "
              + "  AND bom.IsActive = 'Y' "
              + "  AND bl.IsActive = 'Y' "
              + "  AND map.IsActive = 'Y' "
              + "  AND map.RED_Operation_ID = ? "
              + "ORDER BY bl.Line, map.SeqNo";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        boolean found = false;

        try {
            pstmt = DB.prepareStatement(sql, null);
            pstmt.setInt(1, productId);
            pstmt.setInt(2, operation.getRED_Operation_ID());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                found = true;

                int materialProductId = rs.getInt("M_Product_ID");
                int uomId = rs.getInt("C_UOM_ID");

                BigDecimal qtyBOM = rs.getBigDecimal("QtyBOM");
                String allocationType = rs.getString("AllocationType");
                BigDecimal qtyAllocated = rs.getBigDecimal("QtyAllocated");
                BigDecimal percentAllocated = rs.getBigDecimal("PercentAllocated");

                BigDecimal qty = calculateAllocatedQty(qtyBOM, allocationType, qtyAllocated, percentAllocated);

                String info = getProductName(materialProductId)
                        + " | Qty: " + qty
                        + " " + getUOMName(uomId)
                        + " | Allocation: " + allocationType;

                addPreviewRow(
                        "MATERIAL",
                        stageName,
                        operation.getName(),
                        info,
                        "BOM"
                );
            }

        } catch (Exception e) {
            found = true;
            e.printStackTrace();

            addPreviewRow(
                    "ERROR",
                    stageName,
                    operation.getName(),
                    "Error baca BOM material: " + e.getMessage(),
                    "BOM"
            );
        } finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }

        if (!found) {
            addPreviewRow(
                    "MATERIAL",
                    stageName,
                    operation.getName(),
                    "Tidak ada BOM material mapping untuk operation ini",
                    "BOM"
            );
        }
    }

    private void confirmGenerate() {
        if (currentPlan == null || currentPlan.get_ID() <= 0) {
            showMessage("Load Production Plan dulu.");
            return;
        }

        List<Integer> selectedOperationIds = getSelectedOperationIds();

        if (selectedOperationIds.isEmpty()) {
            showMessage("Pilih minimal satu operation.");
            return;
        }

        Messagebox.show(
                "Generate stage, operation, dan material sesuai pilihan?",
                "Konfirmasi Generate",
                Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                evt -> {
                    if (Messagebox.ON_YES.equals(evt.getName())) {
                        generateProductionStructure();
                    }
                }
        );
    }

    private void generateProductionStructure() {
        String trxName = null;

        try {
            String materialSource = getMaterialSource();
            List<Integer> selectedOperationIds = getSelectedOperationIds();

            Map<Integer, MREDProductionStage> generatedStageMap = new LinkedHashMap<>();

            for (Integer operationId : selectedOperationIds) {
                MREDOperation operation = operationMap.get(operationId);

                if (operation == null || operation.get_ID() <= 0) {
                    continue;
                }

                MREDStage stage = stageMap.get(operation.getRED_Stage_ID());

                if (stage == null || stage.get_ID() <= 0) {
                    continue;
                }

                MREDProductionStage prodStage = generatedStageMap.get(stage.getRED_Stage_ID());

                if (prodStage == null) {
                    prodStage = findExistingProductionStage(stage, trxName);

                    if (prodStage == null || prodStage.get_ID() <= 0) {
                        prodStage = createProductionStage(stage, trxName);
                    }

                    generatedStageMap.put(stage.getRED_Stage_ID(), prodStage);
                }

                MREDProductionOperation prodOperation = findExistingProductionOperation(prodStage, operation, trxName);

                if (prodOperation == null || prodOperation.get_ID() <= 0) {
                    prodOperation = createProductionOperation(prodStage, operation, trxName);
                }

                if (MATERIAL_SOURCE_OPERATION.equals(materialSource) || MATERIAL_SOURCE_BOTH.equals(materialSource)) {
                    generateMaterialFromOperation(prodOperation, operation, trxName);
                }

                if (MATERIAL_SOURCE_BOM.equals(materialSource) || MATERIAL_SOURCE_BOTH.equals(materialSource)) {
                    generateMaterialFromBOM(prodOperation, operation, trxName);
                }
            }

            showMessage("Generate berhasil.");
            previewGenerate();

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Generate gagal: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    private MREDProductionStage findExistingProductionStage(MREDStage stage, String trxName) {
        int prodStageId = DB.getSQLValue(
                trxName,
                "SELECT RED_ProductionStage_ID "
                        + "FROM RED_ProductionStage "
                        + "WHERE RED_ProductionPlan_ID=? "
                        + "AND RED_Stage_ID=? "
                        + "AND IsActive='Y'",
                currentPlan.get_ID(),
                stage.getRED_Stage_ID()
        );

        if (prodStageId > 0) {
            return new MREDProductionStage(Env.getCtx(), prodStageId, trxName);
        }

        return null;
    }

    private MREDProductionOperation findExistingProductionOperation(
            MREDProductionStage prodStage,
            MREDOperation operation,
            String trxName
    ) {
        int prodOperationId = DB.getSQLValue(
                trxName,
                "SELECT RED_ProductionOperation_ID "
                        + "FROM RED_ProductionOperation "
                        + "WHERE RED_ProductionStage_ID=? "
                        + "AND RED_Operation_ID=? "
                        + "AND IsActive='Y'",
                prodStage.getRED_ProductionStage_ID(),
                operation.getRED_Operation_ID()
        );

        if (prodOperationId > 0) {
            return new MREDProductionOperation(Env.getCtx(), prodOperationId, trxName);
        }

        return null;
    }

    private MREDProductionStage createProductionStage(MREDStage stage, String trxName) {
        MREDProductionStage prodStage = new MREDProductionStage(Env.getCtx(), 0, trxName);

        prodStage.setAD_Org_ID(currentPlan.getAD_Org_ID());

        setValueSafe(prodStage, "RED_ProductionPlan_ID", currentPlan.get_ID());
        setValueSafe(prodStage, "RED_Stage_ID", stage.getRED_Stage_ID());

        setValueIfExists(prodStage, "Name", stage.getName());
        setValueIfExists(prodStage, "Description", stage.getDescription());
        setValueIfExists(prodStage, "SeqNo", stage.getSeqNo());

        prodStage.saveEx();

        return prodStage;
    }

    private MREDProductionOperation createProductionOperation(
            MREDProductionStage prodStage,
            MREDOperation operation,
            String trxName
    ) {
        MREDProductionOperation prodOperation = new MREDProductionOperation(Env.getCtx(), 0, trxName);

        prodOperation.setAD_Org_ID(currentPlan.getAD_Org_ID());

        setValueIfExists(prodOperation, "RED_ProductionPlan_ID", currentPlan.get_ID());
        setValueSafe(prodOperation, "RED_ProductionStage_ID", prodStage.getRED_ProductionStage_ID());
        setValueSafe(prodOperation, "RED_Operation_ID", operation.getRED_Operation_ID());

        setValueIfExists(prodOperation, "Name", operation.getName());
        setValueIfExists(prodOperation, "Description", operation.getDescription());
        setValueIfExists(prodOperation, "SeqNo", operation.getSeqNo());

        prodOperation.saveEx();

        return prodOperation;
    }

    private void generateMaterialFromOperation(
            MREDProductionOperation prodOperation,
            MREDOperation operation,
            String trxName
    ) {
        String sql =
                "SELECT RED_OperationMaterial_ID "
              + "FROM RED_OperationMaterial "
              + "WHERE IsActive='Y' "
              + "AND RED_Operation_ID=? "
              + "AND AD_Client_ID IN (0, ?) "
              + "ORDER BY RED_OperationMaterial_ID";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DB.prepareStatement(sql, trxName);
            pstmt.setInt(1, operation.getRED_Operation_ID());
            pstmt.setInt(2, Env.getAD_Client_ID(Env.getCtx()));

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int operationMaterialId = rs.getInt("RED_OperationMaterial_ID");

                if (isProductionMaterialExists(prodOperation, "RED_OperationMaterial_ID", operationMaterialId, trxName)) {
                    continue;
                }

                MREDOperationMaterial mat = new MREDOperationMaterial(Env.getCtx(), operationMaterialId, trxName);

                MREDProductionOperationMaterial prodMat =
                        new MREDProductionOperationMaterial(Env.getCtx(), 0, trxName);

                prodMat.setAD_Org_ID(currentPlan.getAD_Org_ID());

                setValueSafe(prodMat, "RED_ProductionOperation_ID", prodOperation.getRED_ProductionOperation_ID());
                setValueSafe(prodMat, "RED_OperationMaterial_ID", mat.getRED_OperationMaterial_ID());

                setValueSafe(prodMat, "M_Product_ID", getIntValue(mat, "M_Product_ID"));
                setValueSafe(prodMat, "C_UOM_ID", getIntValue(mat, "C_UOM_ID"));

                setValueSafe(prodMat, "MaterialSource", MATERIAL_SOURCE_OPERATION);

                BigDecimal qty = getBigDecimalValue(mat, "QtyRequiered");
                setValueSafe(prodMat, "QtyRequiered", qty);

                prodMat.saveEx();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal generate Operation Material untuk "
                    + operation.getName() + ": " + e.getMessage(), e);
        } finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }
    }

    private void generateMaterialFromBOM(
            MREDProductionOperation prodOperation,
            MREDOperation operation,
            String trxName
    ) {
        int productId = getProductionPlanProductId();

        if (productId <= 0) {
            throw new RuntimeException("Product di Production Plan belum ditemukan.");
        }

        String sql =
                "SELECT bl.PP_Product_BOMLine_ID, "
              + "       bl.M_Product_ID, "
              + "       bl.C_UOM_ID, "
              + "       bl.QtyBOM, "
              + "       map.AllocationType, "
              + "       map.QtyAllocated, "
              + "       map.PercentAllocated "
              + "FROM PP_Product_BOM bom "
              + "JOIN PP_Product_BOMLine bl ON bl.PP_Product_BOM_ID = bom.PP_Product_BOM_ID "
              + "JOIN RED_BOMLineOperation map ON map.PP_Product_BOMLine_ID = bl.PP_Product_BOMLine_ID "
              + "WHERE bom.M_Product_ID = ? "
              + "  AND bom.IsActive = 'Y' "
              + "  AND bl.IsActive = 'Y' "
              + "  AND map.IsActive = 'Y' "
              + "  AND map.RED_Operation_ID = ? "
              + "ORDER BY bl.Line, map.SeqNo";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DB.prepareStatement(sql, trxName);
            pstmt.setInt(1, productId);
            pstmt.setInt(2, operation.getRED_Operation_ID());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int bomLineId = rs.getInt("PP_Product_BOMLine_ID");

                if (isProductionMaterialExists(prodOperation, "PP_Product_BOMLine_ID", bomLineId, trxName)) {
                    continue;
                }

                int materialProductId = rs.getInt("M_Product_ID");
                int uomId = rs.getInt("C_UOM_ID");

                BigDecimal qtyBOM = rs.getBigDecimal("QtyBOM");
                String allocationType = rs.getString("AllocationType");
                BigDecimal qtyAllocated = rs.getBigDecimal("QtyAllocated");
                BigDecimal percentAllocated = rs.getBigDecimal("PercentAllocated");

                BigDecimal qty = calculateAllocatedQty(qtyBOM, allocationType, qtyAllocated, percentAllocated);

                MREDProductionOperationMaterial prodMat =
                        new MREDProductionOperationMaterial(Env.getCtx(), 0, trxName);

                prodMat.setAD_Org_ID(currentPlan.getAD_Org_ID());

                setValueSafe(prodMat, "RED_ProductionOperation_ID", prodOperation.getRED_ProductionOperation_ID());
                setValueSafe(prodMat, "PP_Product_BOMLine_ID", bomLineId);

                setValueSafe(prodMat, "M_Product_ID", materialProductId);
                setValueSafe(prodMat, "C_UOM_ID", uomId);

                setValueSafe(prodMat, "MaterialSource", MATERIAL_SOURCE_BOM);
                setValueSafe(prodMat, "QtyRequiered", qty);

                prodMat.saveEx();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal generate BOM Material untuk "
                    + operation.getName() + ": " + e.getMessage(), e);
        } finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }
    }

    private boolean isProductionMaterialExists(
            MREDProductionOperation prodOperation,
            String sourceColumn,
            int sourceId,
            String trxName
    ) {
        if (sourceId <= 0) {
            return false;
        }

        String dbColumn = getExistingColumnName("RED_ProductionOperationMaterial", sourceColumn);

        if (dbColumn == null || dbColumn.trim().isEmpty()) {
            return false;
        }

        int count = DB.getSQLValue(
                trxName,
                "SELECT COUNT(*) "
                        + "FROM RED_ProductionOperationMaterial "
                        + "WHERE RED_ProductionOperation_ID=? "
                        + "AND " + dbColumn + "=? "
                        + "AND IsActive='Y'",
                prodOperation.getRED_ProductionOperation_ID(),
                sourceId
        );

        return count > 0;
    }

    private List<Integer> getSelectedOperationIds() {
        List<Integer> ids = new ArrayList<>();

        for (Object obj : stageOperationList.getItems()) {
            ListItem item = (ListItem) obj;

            if (!item.isSelected()) {
                continue;
            }

            Object value = item.getValue();
            if (value == null) {
                continue;
            }

            String val = value.toString();

            if (val.startsWith("OPERATION:")) {
                String idText = val.substring("OPERATION:".length());
                try {
                    ids.add(Integer.parseInt(idText));
                } catch (Exception ignored) {
                }
            }
        }

        return ids;
    }

    private void addPreviewRow(String type, String stage, String operation, String materialInfo, String source) {
        ListItem item = new ListItem();

        item.appendChild(new Listcell(type));
        item.appendChild(new Listcell(stage));
        item.appendChild(new Listcell(operation));
        item.appendChild(new Listcell(materialInfo));
        item.appendChild(new Listcell(source));

        previewList.appendChild(item);
    }

    private void clearStageOperationList() {
        while (stageOperationList.getItemCount() > 0) {
            stageOperationList.removeItemAt(0);
        }
    }

    private void clearPreviewList() {
        while (previewList.getItemCount() > 0) {
            previewList.removeItemAt(0);
        }
        generateButton.setDisabled(true);
    }

    private BigDecimal calculateAllocatedQty(
            BigDecimal qtyBOM,
            String allocationType,
            BigDecimal qtyAllocated,
            BigDecimal percentAllocated
    ) {
        if (qtyBOM == null) {
            qtyBOM = Env.ZERO;
        }

        if ("QTY".equals(allocationType)) {
            return qtyAllocated == null ? Env.ZERO : qtyAllocated;
        }

        if ("PERCENT".equals(allocationType)) {
            if (percentAllocated == null) {
                return Env.ZERO;
            }

            return qtyBOM.multiply(percentAllocated).divide(new BigDecimal("100"));
        }

        return qtyBOM;
    }

    private BigDecimal getBigDecimalValue(PO po, String columnName) {
        Object value = getValueSafe(po, columnName);

        if (value == null) {
            return Env.ZERO;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        try {
            return new BigDecimal(value.toString());
        } catch (Exception e) {
            return Env.ZERO;
        }
    }

    private int getIntValue(PO po, String columnName) {
        Object value = getValueSafe(po, columnName);

        if (value == null) {
            return 0;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        try {
            return Integer.parseInt(value.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    private Object getValueSafe(PO po, String columnName) {
        if (po == null || columnName == null) {
            return null;
        }

        if (po.get_ColumnIndex(columnName) >= 0) {
            return po.get_Value(columnName);
        }

        String lowerColumnName = columnName.toLowerCase();

        if (po.get_ColumnIndex(lowerColumnName) >= 0) {
            return po.get_Value(lowerColumnName);
        }

        return null;
    }

    private void setValueSafe(PO po, String columnName, Object value) {
        if (po == null || columnName == null) {
            throw new RuntimeException("PO atau columnName null.");
        }

        if (po.get_ColumnIndex(columnName) >= 0) {
            po.set_ValueNoCheck(columnName, value);
            return;
        }

        String lowerColumnName = columnName.toLowerCase();

        if (po.get_ColumnIndex(lowerColumnName) >= 0) {
            po.set_ValueNoCheck(lowerColumnName, value);
            return;
        }

        throw new RuntimeException("Column tidak ditemukan di model: " + columnName
                + " / " + lowerColumnName
                + " untuk table " + po.get_TableName());
    }

    private void setValueIfExists(PO po, String columnName, Object value) {
        if (po == null || columnName == null) {
            return;
        }

        if (po.get_ColumnIndex(columnName) >= 0) {
            po.set_ValueNoCheck(columnName, value);
            return;
        }

        String lowerColumnName = columnName.toLowerCase();

        if (po.get_ColumnIndex(lowerColumnName) >= 0) {
            po.set_ValueNoCheck(lowerColumnName, value);
        }
    }

    private boolean isYes(PO po, String columnName) {
        Object value = getValueSafe(po, columnName);
        return "Y".equals(value);
    }

    private String getProductName(int M_Product_ID) {
        if (M_Product_ID <= 0) {
            return "";
        }

        String sql = "SELECT COALESCE(Value,'') || ' - ' || COALESCE(Name,'') "
                + "FROM M_Product WHERE M_Product_ID=?";

        String name = DB.getSQLValueString(null, sql, M_Product_ID);

        return name == null ? "" : name;
    }

    private String getUOMName(int C_UOM_ID) {
        if (C_UOM_ID <= 0) {
            return "";
        }

        String sql = "SELECT Name FROM C_UOM WHERE C_UOM_ID=?";
        String name = DB.getSQLValueString(null, sql, C_UOM_ID);

        return name == null ? "" : name;
    }

    private int getProductionPlanProductId() {
        if (currentPlan == null) {
            return 0;
        }

        return getIntValue(currentPlan, "M_Product_ID");
    }

    private boolean isColumnExists(String tableName, String columnName) {
        int count = DB.getSQLValue(
                null,
                "SELECT COUNT(*) "
                        + "FROM AD_Column c "
                        + "JOIN AD_Table t ON t.AD_Table_ID = c.AD_Table_ID "
                        + "WHERE UPPER(t.TableName)=UPPER(?) "
                        + "AND UPPER(c.ColumnName)=UPPER(?)",
                tableName,
                columnName
        );

        return count > 0;
    }

    private String getExistingColumnName(String tableName, String columnName) {
        String result = DB.getSQLValueString(
                null,
                "SELECT c.ColumnName "
                        + "FROM AD_Column c "
                        + "JOIN AD_Table t ON t.AD_Table_ID = c.AD_Table_ID "
                        + "WHERE UPPER(t.TableName)=UPPER(?) "
                        + "AND UPPER(c.ColumnName)=UPPER(?)",
                tableName,
                columnName
        );

        return result;
    }

    private void showMessage(String message) {
        Messagebox.show(message, "Generate Production Stage", Messagebox.OK, Messagebox.INFORMATION);
    }
}