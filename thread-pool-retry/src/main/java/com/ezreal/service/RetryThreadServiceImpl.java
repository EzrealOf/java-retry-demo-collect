package com.ezreal.service;

import com.ezreal.model.FileModel;
import com.ezreal.model.RetryModel;
import com.ezreal.util.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class RetryThreadServiceImpl {

    @Resource
    private ThreadPoolFactory factory;

    private static final String FILE_POOL = "file_pool";


    private static final Integer MAX_RETRY_TIMES = 2;



    /**
     * 批量更新 文件
     * @param fileModelList 文件列表
     */
    public void uploadFileInfoRetry(List<FileModel> fileModelList) {
        fileModelList.forEach(fileModel -> {
            RetryModel<FileModel> retryModel = new RetryModel<>();
            retryModel.init(fileModel);
            retry(retryModel);
        });

    }

    /**
     * 构建 runnable
     * @param retryModel 重试模型
     * @return 对应重试的数据
     */
    private Runnable getRunnable(RetryModel<FileModel> retryModel) {
        if (Objects.isNull(retryModel) || retryModel.getRetryTimes() > MAX_RETRY_TIMES) {
            return null;
        }
        return () -> {
            log.info("开始第{}次上传文件，文件名称:{}以及文件路径为{}",retryModel.getRetryTimes(), retryModel.getT().getFileName(), retryModel.getT().getFileUrl());
            retryModel.retryTimesPlus();
            try {
                upload(retryModel.getT());
            }catch (Exception e){
                retry(retryModel);
            }
        };
    }

    /**
     *
     * @param retryModel 重试模型
     */
    private void retry(RetryModel<FileModel> retryModel){
        ThreadPoolExecutor pool = factory.getThreadPool(FILE_POOL);
        Runnable runnable = getRunnable(retryModel);
        if (Objects.nonNull(runnable)){
            pool.submit(runnable);
        }
    }

    /**
     * 业务逻辑 上传图片
     * @param fileModel 文件模型
     */
    private void upload(FileModel fileModel){

    }
}
