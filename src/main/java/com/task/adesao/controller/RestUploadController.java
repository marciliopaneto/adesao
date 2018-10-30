package com.task.adesao.controller;

import com.task.adesao.constants.Ambiente;
import com.task.adesao.exception.ResourceNotFoundException;
import com.task.adesao.maker.ScriptGenerator;
import com.task.adesao.maker.Templates;
import com.task.adesao.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marcus on 14/09/18.
 */
@RestController
public class RestUploadController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = System.getProperty("user.home") + "/temp/";

    // 3.1.1 Single file upload
    @PostMapping("/api/upload")
    // If not @RestController, uncomment this
    //@ResponseBody
    public ResponseEntity<?> uploadFile(
            @RequestParam("files") MultipartFile uploadfile,
            HttpServletRequest request) {

        logger.debug("Single file upload!");
        request.getSession().removeAttribute("fileLocation");

        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfile));
            String fileLocation = UPLOADED_FOLDER + uploadfile.getOriginalFilename();

            request.getSession().setAttribute("fileLocation", fileLocation);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/api/generate")
    public ResponseEntity<?> generateScript(
            HttpServletRequest request) {

        List<String> script = new ArrayList<>();
        String fileLocation = (String)request.getSession().getAttribute("fileLocation");
        if (fileLocation != null && !fileLocation.isEmpty()) {
            ScriptGenerator generator = new ScriptGenerator(fileLocation);

            Ambiente ambiente;
            if (fileLocation.contains("HEXT")) {
                ambiente = Ambiente.HEXT;
            } else
            if (fileLocation.contains("PROD")) {
                ambiente = Ambiente.PROD;
            } else
                throw new ResourceNotFoundException("Ambiente", fileLocation, "");

            script.addAll(Templates.getHeader(ambiente));
            script.addAll(generator.generate());
            script.add(Templates.getTrailer());
        } else {
            throw new ResourceNotFoundException("Arquivo não foi carregado", fileLocation, "");
        }

        return new ResponseEntity(StrUtil.arrayToString(script),
                new HttpHeaders(), HttpStatus.OK);
    }

    // 3.1.2 Multiple file upload
    @PostMapping("/api/upload/multi")
    public ResponseEntity<?> uploadFileMulti(
            @RequestParam("extraField") String extraField,
            @RequestParam("files") MultipartFile[] uploadfiles) {

        logger.debug("Multiple file upload!");

        // Get file name
        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfiles));

        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - "
                + uploadedFileName, HttpStatus.OK);

    }

    @PostMapping("/api/testScript")
    public ResponseEntity<?> testScriptOnDatabase(HttpServletRequest request,
                                                  @RequestParam("script") String[] script) {
        jdbcTemplate.batchUpdate();
        return new ResponseEntity("Script validated OK.",
                new HttpHeaders(), HttpStatus.OK);
    }

    //save file
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        }

    }
}
