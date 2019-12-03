package com.paramount.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paramount.common.dto.base.RestMessage;
import com.paramount.entity.Movie;
import com.paramount.service.GjFilmService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author panteng
 * @description:
 * @date 2019/11/7 9:28
 */
@RestController
@Api(value="电影配置数据",description = "电影配置数据")
@RequestMapping("/api/film")
public class GjFilmController {

    @Autowired
    private GjFilmService gjFilmService;

    @ApiOperation(value="初始导入电影数据",notes="初始导入电影数据")
    @PostMapping("/initData")
    public RestMessage<String> initData() throws Exception{
        long ins = gjFilmService.initData();
        return RestMessage.newInstance(true, "初始导入电影数据", MessageFormat.format("导入电影数据:{0}",ins));
    }

    @ApiOperation(value="分页查询电影数据",notes="分页查询电影数据")
    @PostMapping("/pageQryMovie")
    public IPage<Movie> pageQryMovie(@RequestBody Page page) throws Exception{
        IPage<Movie> pageData =  gjFilmService.pageQryMovie(page);
        return pageData;
    }

    @ApiOperation(value="查询电影数据",notes="查询电影数据")
    @PostMapping("/qryByMovie")
    public List<Movie> qryByMovie(@RequestBody Movie movie) throws Exception{
        List<Movie> pageData =  gjFilmService.qryByMovie(movie);
        return pageData;
    }
}
