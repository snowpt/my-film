package com.paramount.service;

import com.paramount.common.dto.base.PageQueryResult;
import com.paramount.dto.EsMovieQryDto;
import com.paramount.entity.EsMovie;

/**
 * @author panteng
 * @description: 购机搜索引擎
 * @date 2019/11/25 10:52
 */
public interface GjEsService {

    /**
     * 同步更新ES
     * @return
     */
    long syncUpdate();

    /**
     * 创建索引
     * @param idxName
     * @param idxSQL
     */
    void createIndex(String idxName, String idxSQL);

    /**
     * 删除索引
     * @param idxName
     */
    void deleteIndex(String idxName);

    /**
     * gjEsService
     * @param idxName
     * @return
     */
    long initData(String idxName);

    /**
     *分页查询电影数据
     * @param curPage
     * @param size
     * @param qryDto
     * @return
     */
    PageQueryResult<EsMovie> pageQryEsMovie(int curPage, int size, EsMovieQryDto qryDto);
}
