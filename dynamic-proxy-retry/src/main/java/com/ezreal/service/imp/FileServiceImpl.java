package com.ezreal.service.imp;


import com.ezreal.model.FileModel;
import com.ezreal.service.FileService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public void upload(FileModel fileModel) {
        log.info("上传图片, 图片名称:{}和图片地址:{}", fileModel.getFileName(), fileModel.getFileUrl());
        throw new NullPointerException("file not find");
    }
}
