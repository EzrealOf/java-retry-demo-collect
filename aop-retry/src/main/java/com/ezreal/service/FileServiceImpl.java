package com.ezreal.service;


import com.ezreal.annotation.Retryable;
import com.ezreal.model.FileModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileServiceImpl {

    @Retryable(Exception.class)
    public void upload(FileModel fileModel){
        log.info("文件名称: {}, 地址: {}, 开始上传", fileModel.getFileName(), fileModel.getFileUrl());
        throw new NullPointerException("null point exception");
    }
}
