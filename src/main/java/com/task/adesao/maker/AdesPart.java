package com.task.adesao.maker;

import com.task.adesao.util.StrUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 14/09/18.
 */
public class AdesPart {
    public static final String NPC_ADES_PART_1 = "\"UPDATE NPC_ADES_PART SET IC_SIT_ADES_PART = 'I' WHERE ID_PART_ADMTD = (SELECT I.ID_INST FROM NPC_INST_CAD I WHERE I.NR_ISPB_INST = \"&A§&\");\"";
    public static final String NPC_ADES_PART_2_1 = "\"MERGE INTO NPC_ADES_PART AS IC USING (   SELECT (SELECT I.ID_INST FROM NPC_INST_CAD I WHERE I.NR_ISPB_INST = \"&A§&\") AS ID_PART_ADMTD, (SELECT ID_FUNCDD FROM NPC_FUNCDD_CAD F JOIN DOM_GRUPO_FUNCDD DGF ON DGF.ID_GRUPO_FUNCDD = F.ID_GRUPO_FUNCDD WHERE CD_GRUPO_FUNCDD = '\"&B§&\"' AND CD_TP_ACAO = '\"&D§&\"' AND ID_CAN_TRANSM = (SELECT CASE WHEN (SUBSTR('\"&B§&\"',1,4) like 'ADDA%' OR SUBSTR('\"&B§&\"',1,4) like 'AGEN%')  THEN ID_CAN_TRANSM_ADES_ARQ ELSE ID_CAN_TRANSM_ADES_MSG END FROM NPC_INST_CAD I JOIN NPC_PART_CAD P ON I.ID_INST = P.ID_PART WHERE I.NR_ISPB_INST = \"&A§&\")) AS ID_FUNCDD, TIMESTAMP(DATE ('\"& TEXTO(E§;\"aaaa-mm-dd\") &\"')) AS DT_ADES, '\"&F§&\"' AS IC_SIT_ADES_PART, '\"&I§&\"' AS IC_RECBT_DISTRIBC_RR2_PROPRIO_EMISSOR FROM SYSIBM.DUAL) \"";
    public static final String NPC_ADES_PART_2_2 = "AS R ON (IC.ID_PART_ADMTD = R.ID_PART_ADMTD AND IC.ID_FUNCDD = R.ID_FUNCDD)  WHEN MATCHED THEN    UPDATE SET IC.DT_ADES = R.DT_ADES ,IC.IC_SIT_ADES_PART = R.IC_SIT_ADES_PART, IC.IC_RECBT_DISTRIBC_RR2_PROPRIO_EMISSOR = R.IC_RECBT_DISTRIBC_RR2_PROPRIO_EMISSOR, DH_ULT_ALT = current timestamp, NM_USU_ULT_ALT = current user WHEN NOT MATCHED THEN    INSERT (ID_PART_ADMTD, ID_FUNCDD, IC_RECBT_DISTRIBC_RR2_PROPRIO_EMISSOR, DT_ADES, IC_SIT_ADES_PART) VALUES (R.ID_PART_ADMTD, R.ID_FUNCDD, R.IC_RECBT_DISTRIBC_RR2_PROPRIO_EMISSOR, R.DT_ADES, R.IC_SIT_ADES_PART);";

    public List<String> getSQL(Sheet sheet) {
        List<String> sqlList = new ArrayList<>();
        FormulaEvaluator eval = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet) {
            if (row.getRowNum() > 0) {
                Cell acell = row.getCell(0);

                if (acell != null && CellType.NUMERIC.equals(acell.getCellTypeEnum()) &&
                        !NumberToTextConverter.toText(acell.getNumericCellValue()).isEmpty()) {
                    int idxData = 'E' - 'A';
                    String dtAdesao = null;
                    Cell dataCell = row.getCell(idxData);
                    if (CellType.NUMERIC.equals(dataCell.getCellTypeEnum())) {
                        try {
                            if (DateUtil.isCellDateFormatted(dataCell)) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                dtAdesao = format.format(dataCell.getDateCellValue());
                            } else
                                throw new Exception("Data de adesão não encontrada ou com erro.");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.exit(-99);
                        }
                    }

                    int pos = 'R' - 'A';
                    acell = row.getCell(pos);
                    if (acell == null) {
                        acell = row.createCell(pos);
                    }
                    acell.setCellType(CellType.FORMULA);
                    String txtFormula = StrUtil.formatFormulaToLine(NPC_ADES_PART_2_1, acell.getRowIndex() + 1);
                    int x = acell.getRowIndex() + 1;
                    txtFormula = StringUtils.replace(txtFormula, "\"& TEXTO(E" + x + ";\"aaaa-mm-dd\") &\"", dtAdesao);
                    acell.setCellFormula(txtFormula);

                    eval = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
                    eval.evaluateInCell(acell);
                    sqlList.add(acell.getStringCellValue() + NPC_ADES_PART_2_2);
                }
            } else {
                Cell acell;
                int pos = 'R' - 'A';
                acell = row.getCell(pos);
                if (acell == null) {
                    acell = row.createCell(pos);
                }
                acell.setCellType(CellType.FORMULA);
                acell.setCellFormula(StrUtil.formatFormulaToLine(NPC_ADES_PART_1, 2));

                eval.evaluateInCell(acell);
                sqlList.add(acell.getStringCellValue());
            }
        }

        return sqlList;
    }
}
