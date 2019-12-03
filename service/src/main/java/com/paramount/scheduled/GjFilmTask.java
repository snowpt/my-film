package com.paramount.scheduled;

import com.paramount.service.GjEsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author panteng
 * @description: 定时任务
 * @date 2019/11/25 10:43
 */
@Component
public class GjFilmTask {
    private static final Logger gjFilmTaskLog = LoggerFactory.getLogger(GjFilmTask.class);

    @Autowired
    private GjEsService gjEsService;
    /**
     * 同步更新ES(每天凌晨2点更新)
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void syncUpdate() {
        try {
            gjFilmTaskLog.info("syncUpdate --> 开始同步ES");
            long ups = gjEsService.syncUpdate();
            gjFilmTaskLog.info("syncUpdate --> 同步ES成功,同步数量:{0}",ups);
        } catch (Exception e) {
            gjFilmTaskLog.error("syncUpdate --> 同步ES错误,错误信息:{0}",e.getMessage());
        }
    }

}
