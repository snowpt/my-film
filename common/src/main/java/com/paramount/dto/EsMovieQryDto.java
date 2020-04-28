package com.paramount.dto;


import java.util.Collections;
import java.util.List;

/**
 * @author panteng
 * @description:
 * @date 2019/12/4 14:02
 */
public class EsMovieQryDto {

    /**
     * 关键字
     */
    private String keyword;
    /**
     * 电影名称
     */
    private String name;

    /**
     * 豆瓣评分
     */
    private List<Double> doubanScore = Collections.emptyList();

    /**
     * 豆瓣评分排序(默认0,倒序;1,正序)
     */
    private int doubanScoreSort;
    /**
     * 主　　演
     */
    private String starring;

    /**
     * 标　　签
     */
    private String label;

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
    private List<String> releaseDate = Collections.emptyList();

    /**
     * 上映日期排序(默认0,倒序;1,正序)
     */
    private int releaseDateSort;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getDoubanScoreSort() {
        return doubanScoreSort;
    }

    public void setDoubanScoreSort(int doubanScoreSort) {
        this.doubanScoreSort = doubanScoreSort;
    }

    public int getReleaseDateSort() {
        return releaseDateSort;
    }

    public void setReleaseDateSort(int releaseDateSort) {
        this.releaseDateSort = releaseDateSort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public List<Double> getDoubanScore() {
        return doubanScore;
    }

    public void setDoubanScore(List<Double> doubanScore) {
        this.doubanScore = doubanScore;
    }

    public List<String> getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(List<String> releaseDate) {
        this.releaseDate = releaseDate;
    }
}
