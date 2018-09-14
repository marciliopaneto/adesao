package com.task.adesao.maker;

import com.task.adesao.util.StrUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 14/09/18.
 */
public class PartCad {
    public static final String NPC_PART_CAD_1_1 = "\"MERGE INTO NPC_PART_CAD AS IC USING ( SELECT (SELECT I.ID_INST FROM NPC_INST_CAD I WHERE I.NR_ISPB_INST = \"&A§&\") AS ID_PART, (SELECT I.ID_INST FROM NPC_INST_CAD I WHERE I.NR_ISPB_INST = \"&B§&\") AS ID_PART_PRINCIPAL, '\"&C§&\"' AS IC_SIT_PART, '\"&D§&\"' IC_EMISSOR_TIT_PROPRIO, '\"&E§&\"' IC_ADES_DDA, '\"&F§&\"' CD_TP_ATUACAO,  '\"&G§&\"' CD_TP_PART, '\"&J§&\"' CD_RESPDE_COA, '\"&K§&\"' CD_RESPDE_COD, (select ID_CAN_TRANSM from DOM_CAN_TRANSM where CD_CAN_TRANSM = '\"&L§&\"') ID_CAN_TRANSM_ADES_ARQ,  (select ID_CAN_TRANSM from DOM_CAN_TRANSM where CD_CAN_TRANSM = '\"&M§&\"') ID_CAN_TRANSM_ADES_MSG FROM SYSIBM.DUAL) \"";
    public static final String NPC_PART_CAD_1_2 = " AS R ON (IC.ID_PART = R.ID_PART)  WHEN MATCHED THEN  UPDATE SET IC.ID_PART_PRINCIPAL = R.ID_PART_PRINCIPAL, IC.IC_SIT_PART = R.IC_SIT_PART, IC.IC_EMISSOR_TIT_PROPRIO = R.IC_EMISSOR_TIT_PROPRIO, IC.IC_ADES_DDA = R.IC_ADES_DDA, IC.CD_TP_ATUACAO = R.CD_TP_ATUACAO, IC.CD_TP_PART = R.CD_TP_PART, IC.CD_RESPDE_COA = R.CD_RESPDE_COA, IC.CD_RESPDE_COD = R.CD_RESPDE_COD, ID_CAN_TRANSM_ADES_ARQ = R.ID_CAN_TRANSM_ADES_ARQ, IC.ID_CAN_TRANSM_ADES_MSG = R.ID_CAN_TRANSM_ADES_MSG, DH_ULT_ALT = current timestamp, NM_USU_ULT_ALT = current timestamp WHEN NOT MATCHED THEN  INSERT (ID_PART, ID_PART_PRINCIPAL, IC_SIT_PART, IC_EMISSOR_TIT_PROPRIO, IC_ADES_DDA, CD_TP_ATUACAO, CD_TP_PART, CD_RESPDE_COA, CD_RESPDE_COD,  ID_CAN_TRANSM_ADES_ARQ, ID_CAN_TRANSM_ADES_MSG) VALUES (R.ID_PART, R.ID_PART_PRINCIPAL, R.IC_SIT_PART, R.IC_EMISSOR_TIT_PROPRIO, R.IC_ADES_DDA, R.CD_TP_ATUACAO, R.CD_TP_PART, R.CD_RESPDE_COA, R.CD_RESPDE_COD, R.ID_CAN_TRANSM_ADES_ARQ, R.ID_CAN_TRANSM_ADES_MSG);";

    public List<String> getSQL (Sheet sheet) {
        List<String> sqlList = new ArrayList<>();
        FormulaEvaluator eval;

        for (Row row : sheet) {
            if (row.getRowNum() > 0) {
                Cell acell = row.getCell(0);

                if (acell != null && CellType.NUMERIC.equals(acell.getCellTypeEnum()) &&
                        !NumberToTextConverter.toText(acell.getNumericCellValue()).isEmpty()){
                    int pos = 'X'-'A';
                    acell = row.getCell(pos);
                    if (acell == null) {
                        acell = row.createCell(pos);
                    }
                    acell.setCellType(CellType.FORMULA);
                    acell.setCellFormula(StrUtil.formatFormulaToLine(NPC_PART_CAD_1_1, acell.getRowIndex() + 1));

                    eval = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
                    eval.evaluateInCell(acell);
                    sqlList.add(acell.getStringCellValue() + NPC_PART_CAD_1_2);
                }
            }
        }

        return sqlList;
    }
}
