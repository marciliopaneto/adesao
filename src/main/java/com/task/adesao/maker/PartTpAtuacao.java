package com.task.adesao.maker;

import com.task.adesao.util.StrUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 14/09/18.
 */
public class PartTpAtuacao {
    public static final String NPC_PART_TP_ATUACAO_1_1 = "\"MERGE INTO NPC_PART_TP_ATUACAO AS IC USING (SELECT (SELECT I.ID_INST FROM NPC_INST_CAD I WHERE I.NR_ISPB_INST = \"&A§&\") AS ID_PART, (SELECT ID_TP_ATUACAO FROM DOM_TP_ATUACAO WHERE CD_TP_ATUACAO = '\"&B§&\"') AS ID_TP_ATUACAO, '\"&C§&\"' IC_SIT_ATUACAO_PART \"";
    public static final String NPC_PART_TP_ATUACAO_1_2 = " FROM SYSIBM.DUAL) AS R ON (IC.ID_PART = R.ID_PART AND IC.ID_TP_ATUACAO = R.ID_TP_ATUACAO)  WHEN MATCHED THEN UPDATE SET IC.IC_SIT_ATUACAO_PART = R.IC_SIT_ATUACAO_PART, DH_ULT_ALT= current timestamp, NM_USU_ULT_ALT = current user WHEN NOT MATCHED THEN INSERT (ID_PART, ID_TP_ATUACAO, IC_SIT_ATUACAO_PART) VALUES (R.ID_PART, R.ID_TP_ATUACAO, R.IC_SIT_ATUACAO_PART);";

    public List<String> getSQL(Sheet sheet) {
        List<String> sqlList = new ArrayList<>();
        FormulaEvaluator eval;

        for (Row row : sheet) {
            if (row.getRowNum() > 0) {
                Cell acell = row.getCell(0);

                if (acell != null && CellType.NUMERIC.equals(acell.getCellTypeEnum()) &&
                        !NumberToTextConverter.toText(acell.getNumericCellValue()).isEmpty()) {
                    int pos = 'K' - 'A';
                    acell = row.getCell(pos);
                    if (acell == null) {
                        acell = row.createCell(pos);
                    }
                    acell.setCellType(CellType.FORMULA);
                    acell.setCellFormula(StrUtil.formatFormulaToLine(NPC_PART_TP_ATUACAO_1_1, acell.getRowIndex() + 1));

                    eval = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
                    eval.evaluateInCell(acell);
                    sqlList.add(acell.getStringCellValue() + NPC_PART_TP_ATUACAO_1_2);
                }
            }
        }

        return sqlList;
    }
}
