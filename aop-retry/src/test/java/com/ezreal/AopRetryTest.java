package com.ezreal;

import com.ezreal.model.FileModel;
import com.ezreal.service.FileServiceImpl;
import org.junit.jupiter.api.Test;


public class AopRetryTest {

    @Test
    public void retryTest(){
        FileServiceImpl fileService = new FileServiceImpl();
        fileService.upload(new FileModel("name", "url"));

    }
}
