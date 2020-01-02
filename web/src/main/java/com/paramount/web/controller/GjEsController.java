package com.paramount.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.paramount.common.dto.base.PageQueryResult;
import com.paramount.common.dto.base.RestMessage;
import com.paramount.dto.EsMovieQryDto;
import com.paramount.entity.EsMovie;
import com.paramount.service.GjEsService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

/**
 * @author panteng
 * @description:
 * @date 2019/11/7 9:28
 */
@RestController
@Api(value="电影配置数据",description = "电影配置数据")
@RequestMapping("/api/film")
public class GjEsController {

    @Autowired
    private GjEsService gjEsService;

    @ApiOperation(value="初始导入电影数据",notes="初始导入电影数据")
    @PostMapping("/es/initData")
    public RestMessage<String> initData(@RequestParam(value = "idxName") String idxName) throws Exception{
        long ins = gjEsService.initData(idxName);
        return RestMessage.newInstance(true, "初始导入电影数据", MessageFormat.format("导入电影数据:{0}",ins));
    }

    @ApiOperation(value="创建索引",notes="创建索引")
    @PostMapping("/es/createIndex")
    public RestMessage<String> createIndex(@RequestBody String json) throws Exception{
        JSONObject jsonObject = JSON.parseObject(json);
        String idxName = jsonObject.getString("idxName");
        String idxSql = jsonObject.getString("idxSql");
        gjEsService.createIndex(idxName, idxSql);
        return RestMessage.newInstance(true, "创建索引","创建索引:"+ json);
    }

    @ApiOperation(value="删除索引",notes="删除索引")
    @PostMapping("/es/deleteIndex")
    public RestMessage<String> deleteIndex(@RequestParam(value = "idxName") String idxName) throws Exception{
        gjEsService.deleteIndex(idxName);
        return RestMessage.newInstance(true, "删除索引","删除成功:"+idxName);
    }

    @ApiOperation(value="分页查询电影数据",notes="分页查询电影数据")
    @PostMapping("/es/pageQryEsMovie/{curPage}/{size}")
    public RestMessage<PageQueryResult<EsMovie>> pageQryEsMovie(@PathVariable("curPage") int curPage,@PathVariable("size") int size,@RequestBody EsMovieQryDto qryDto) throws Exception{
        PageQueryResult<EsMovie> pageData =  gjEsService.pageQryEsMovie(curPage,size,qryDto);
        return RestMessage.newInstance(true, "分页查询电影数据",pageData);
    }
}
