package com.ezreal;

import com.ezreal.model.FileModel;
import com.ezreal.porxy.DynamicProxy;
import com.ezreal.service.FileService;
import com.ezreal.service.imp.FileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class DynamicProxyTest {

    @Test
    public void fileUploadTest() {
        //give
        FileService fileService = new FileServiceImpl();
        FileService proxy = (FileService) DynamicProxy.getProxy(fileService);
        //when
        //then
        assertThatThrownBy(()->proxy.upload(new FileModel("name", "url"))).isInstanceOf(RuntimeException.class);
    }
}
