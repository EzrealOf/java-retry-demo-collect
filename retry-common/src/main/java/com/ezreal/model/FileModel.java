package com.ezreal.model;

import lombok.Data;

/**
 * @author ezeal
 */
@Data
public class FileModel {

    private String fileName;

    private String fileUrl;

    public FileModel() {
    }

    public FileModel(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

}
