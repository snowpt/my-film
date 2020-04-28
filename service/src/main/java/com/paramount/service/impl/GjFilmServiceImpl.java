package com.paramount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paramount.dto.SwipersDto;
import com.paramount.entity.Movie;
import com.paramount.mapper.GjMovieMapper;
import com.paramount.service.GjFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author panteng
 * @description: TODO
 * @date 2019/11/7 10:07
 */
@Service
public class GjFilmServiceImpl extends ServiceImpl<GjMovieMapper, Movie> implements GjFilmService {
    @Autowired
    private GjMovieMapper gjMovieMapper;

    @Override
    public IPage<Movie> pageQryMovie(Page page) {
//        Page<Movie> pages = new Page<>(1, 5);
//        IPage<Movie> userIPage = gjMovieMapper.selectPage(pages, new QueryWrapper<>());
//        return userIPage;
        return gjMovieMapper.selectPageVo(page);
    }

    @Override
    public List<Movie> qryByMovie(Movie movie) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("name", 1);
        return gjMovieMapper.selectList(queryWrapper);
    }

    @Override
    public List<SwipersDto> qrySwipers() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("release_date");
        queryWrapper.last("limit 5");
        List<Movie> list = gjMovieMapper.selectList(queryWrapper);
        List<SwipersDto> dtoList = new ArrayList<>();
        SwipersDto swipersDto;
        for (Movie movie : list) {
            swipersDto = new SwipersDto();
            swipersDto.setId(movie.getId());
            swipersDto.setStillUrl(movie.getStillUrl());
            dtoList.add(swipersDto);
        }
        return dtoList;
    }
}
