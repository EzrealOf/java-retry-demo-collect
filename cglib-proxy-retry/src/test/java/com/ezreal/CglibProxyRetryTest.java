package com.ezreal;

import com.ezreal.model.FileModel;
import com.ezreal.proxy.CglibProxy;
import com.ezreal.service.FileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class CglibProxyRetryTest {

    @Test
    public void fileUploadTest() {
        //give
        FileServiceImpl proxy = (FileServiceImpl) new CglibProxy().getProxy(FileServiceImpl.class);
        //when
        //then
        assertThatThrownBy(()->proxy.upload(new FileModel("name", "url"))).isInstanceOf(RuntimeException.class);

    }
}
