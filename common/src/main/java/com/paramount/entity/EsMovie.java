package com.paramount.entity;

import com.paramount.common.constant.GjConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author panteng
 * @description: ES 电影
 * @date 2019/12/3 11:33
 */
@Document(indexName = "gj_movie",type = "movie") //indexName 索引  type 类型
public class EsMovie {
    @Id
    private Integer id;

    /**
     * 电影名称
     */
    private String name;

    /**
     * 豆瓣评分
     */
    private Double doubanScore;

    /**
     * 产　　地
     */
    private String origin;

    /**
     * 类　　别
     */
    private String category;

    /**
     * 上映日期
     */
    private String releaseDate;

    /**
     * 剧照
     */
    private String stillUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(StringUtils.isNotEmpty(name) && name.contains(GjConstant.left_full)){
            this.name = name.substring(name.indexOf(GjConstant.left_full)+1,name.indexOf(GjConstant.right_full));
        }else {
            this.name = name;
        }
    }

    public Double getDoubanScore() {
        return doubanScore;
    }

    public void setDoubanScore(Double doubanScore) {
        this.doubanScore = doubanScore;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getStillUrl() {
        return stillUrl;
    }

    public void setStillUrl(String stillUrl) {
        this.stillUrl = stillUrl;
    }
}
