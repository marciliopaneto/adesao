package com.task.adesao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by marcus on 06/09/18.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException {
    private String fileName;

    public FileNotFoundException(String fileName, String description) {
        super(String.format("Could not open %s : '%s'", fileName, description));
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
