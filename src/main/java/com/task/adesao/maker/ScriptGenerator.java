package com.task.adesao.maker;

import com.task.adesao.exception.FileNotFoundException;
import com.task.adesao.exception.ResourceNotFoundException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 14/09/18.
 */
public class ScriptGenerator {
    private Workbook workbook = null;
    private FileInputStream file = null;

    public ScriptGenerator(String file) {
        openExcel(file);
    }

    public List<String> generate() {
        List<String> result = new ArrayList<>();
        if (openExcel()) {
            int numOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numOfSheets; i++) {
                result.addAll(getSQL(i));
                result.add("");
            }
            closeExcel();
        } else {
            throw new ResourceNotFoundException("Workbook", "File", null);
        }
        return result;
    }

    private Boolean openExcel() throws FileNotFoundException {
        if (file == null) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private void openExcel(String fileLocation) throws FileNotFoundException {
        try {
            file = new FileInputStream(new File(fileLocation));
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            closeExcel();
            throw new FileNotFoundException(fileLocation, e.getMessage());
        }
    }

    private void closeExcel() {
        try {
            workbook.close();
            workbook = null;
            file.close();
            file = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSQL(int idxSheet) {
        switch (idxSheet) {
            case 0:
                InstCad instCad = new InstCad();
                return instCad.getSQL(workbook.getSheetAt(idxSheet));
            case 1:
                PartCad partCad = new PartCad();
                return partCad.getSQL(workbook.getSheetAt(idxSheet));
            case 2:
                PartTpAtuacao partTpAtuacao = new PartTpAtuacao();
                return partTpAtuacao.getSQL(workbook.getSheetAt(idxSheet));
            case 3:
                AdesPart adesPart = new AdesPart();
                return adesPart.getSQL(workbook.getSheetAt(idxSheet));
            default:
                return new ArrayList<String>();
        }
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public FileInputStream getFile() {
        return file;
    }

}
