package com.task.adesao.maker;

import com.task.adesao.util.StrUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 14/09/18.
 */
public class InstCad {
    public static final String NPC_INST_CAD_1 = "\"MERGE INTO NPC_INST_CAD AS IC USING (SELECT \"&A§&\" NR_ISPB_INST, '\"&B§&\"' AS NR_COMP, \"&C§&\" AS NR_CNPJ, '\"&D§&\"' IC_INST_INCORPD, '\"&E§&\"' IC_SIT_INST, '\"&F§&\"' NM_INST, '\"&G§&\"' NM_INST_REDZ FROM SYSIBM.DUAL) AS R ON (IC.NR_ISPB_INST = R.NR_ISPB_INST) \"& \" WHEN MATCHED THEN\"& \" UPDATE SET IC.NR_COMP = R.NR_COMP, IC.NR_CNPJ = R.NR_CNPJ, IC.IC_INST_INCORPD = R.IC_INST_INCORPD, \"& \" IC.IC_SIT_INST = R.IC_SIT_INST, IC.NM_INST = R.NM_INST, IC.NM_INST_REDZ = R.NM_INST_REDZ, DH_ULT_ALT= current timestamp, NM_USU_ULT_ALT = current user \"& \" WHEN NOT MATCHED THEN\"& \" INSERT (ID_INST, NR_ISPB_INST, NR_COMP, NR_CNPJ, IC_INST_INCORPD, IC_SIT_INST, NM_INST, NM_INST_REDZ) \"& \"    VALUES ((select MAX(ID_INST)+1 ID_INST from NPC_INST_CAD), R.NR_ISPB_INST, R.NR_COMP, NR_CNPJ, R.IC_INST_INCORPD, R.IC_SIT_INST, R.NM_INST, R.NM_INST_REDZ)\"& \";\"";

    public List<String> getSQL (Sheet sheet) {
        List<String> sqlList = new ArrayList<>();
        FormulaEvaluator eval;

        for (Row row : sheet) {
            if (row.getRowNum() > 0) {
                Cell acell = row.getCell(0);

                if (acell != null && CellType.NUMERIC.equals(acell.getCellTypeEnum()) &&
                        !NumberToTextConverter.toText(acell.getNumericCellValue()).isEmpty()){
                    int pos = 'O'-'A';
                    acell = row.getCell(pos);
                    if (acell == null) {
                        acell = row.createCell(pos);
                    }
                    acell.setCellType(CellType.FORMULA);
                    acell.setCellFormula(StrUtil.formatFormulaToLine(NPC_INST_CAD_1, acell.getRowIndex() + 1));

                    eval = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
                    eval.evaluateInCell(acell);
                    sqlList.add(acell.getStringCellValue());
                }
            }
        }

        return sqlList;
    }

}
