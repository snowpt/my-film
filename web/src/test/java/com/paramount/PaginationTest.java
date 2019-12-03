package com.paramount;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paramount.entity.Movie;
import com.paramount.mapper.GjMovieMapper;
import com.paramount.web.FilmApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author panteng
 * @description: TODO
 * @date 2019/11/27 10:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FilmApplication.class)
public class PaginationTest {
    @Autowired
    private GjMovieMapper gjMovieMapper;

    @Test
    public void tests1() {
        System.out.println("----- baseMapper 自带分页 ------");
        Page<Movie> page = new Page<>(1, 5);
        IPage<Movie> userIPage = gjMovieMapper.selectPage(page, new QueryWrapper<>());
        System.out.println("总条数 ------> " + userIPage.getTotal());
        System.out.println("当前页数 ------> " + userIPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userIPage.getSize());
        System.out.println("----- baseMapper 自带分页 ------");

        System.out.println("json 正反序列化 begin");
        String json = JSON.toJSONString(page);
        Page<Movie> page1 = JSON.parseObject(json, Page.class);
        System.out.println("json 正反序列化 end");

    }
}
