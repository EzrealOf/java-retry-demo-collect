package com.ezreal.util;

import com.ezreal.model.FileModel;
import com.google.common.collect.Lists;

import java.util.List;

public class FileModelUtil {

    public static List<FileModel> init(Integer size) {
        List<FileModel> fileModelList = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            FileModel fileModel = new FileModel();
            fileModel.setFileName("fileName_" + i);
            fileModel.setFileUrl("fileUrl" + i);
            fileModelList.add(fileModel);
        }
        return fileModelList;
    }
}
