package com.paramount.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.paramount.dto.SwipersDto;
import com.paramount.entity.Movie;

import java.util.List;

/**
 * @author panteng
 * @description:
 * @date 2019/11/7 9:43
 */
public interface GjFilmService extends IService<Movie>{
    /**
     * 分页查询电影数据
     * @param page
     * @return
     */
    IPage<Movie> pageQryMovie(Page page);

    /**
     * 查询电影数据
     * @param movie
     * @return
     */
    List<Movie> qryByMovie(Movie movie);

    /**
     * 查询轮播图数据
     * @return
     */
    List<SwipersDto> qrySwipers();
}
