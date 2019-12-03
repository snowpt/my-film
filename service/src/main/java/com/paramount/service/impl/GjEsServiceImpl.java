package com.paramount.service.impl;

import com.paramount.service.GjEsService;
import org.springframework.stereotype.Service;

/**
 * @author panteng
 * @description: GjEsService
 * @date 2019/11/25 11:49
 */
@Service
public class GjEsServiceImpl implements GjEsService {


    /**
     * 同步更新前一天的movie数据
     * @return
     */
    @Override
    public long syncUpdate() {
        return 0;
    }
}
