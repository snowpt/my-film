package com.paramount.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("movie")
public class Movie {
    /**
     * 主键
     */
    @TableId
    private Integer id;

    /**
     * 电影名称
     */
    private String name;

    /**
     * 发布时间
     */
    @TableField(value = "release_date")
    private String releaseDate;

    /**
     * 剧照
     */
    private String stillUrl;

    /**
     * 年　　代
     */
    private String year;

    /**
     * 产　　地
     */
    private String origin;

    /**
     * 类　　别
     */
    private String category;

    /**
     * 语　　言
     */
    private String language;

    /**
     * 字　　幕
     */
    private String subtitles;

    /**
     * 上映日期
     */
    private String showDate;

    /**
     * 豆瓣评分
     */
    private String doubanScore;

    /**
     * 片　　长
     */
    private String runningTime;

    /**
     * 导　　演
     */
    private String director;

    /**
     * 编　　剧
     */
    private String writers;

    /**
     * 主　　演
     */
    private String starring;

    /**
     * 简　　介
     */
    private String introduction;

    /**
     * 标　　签
     */
    private String label;

    /**
     * 下载地址
     */
    private String downloadAddress;

    /**
     * 插入时间
     */
    private Date createTime;

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
        this.name = name == null ? null : name.trim();
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate == null ? null : releaseDate.trim();
    }

    public String getStillUrl() {
        return stillUrl;
    }

    public void setStillUrl(String stillUrl) {
        this.stillUrl = stillUrl == null ? null : stillUrl.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles == null ? null : subtitles.trim();
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate == null ? null : showDate.trim();
    }

    public String getDoubanScore() {
        return doubanScore;
    }

    public void setDoubanScore(String doubanScore) {
        this.doubanScore = doubanScore == null ? null : doubanScore.trim();
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime == null ? null : runningTime.trim();
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director == null ? null : director.trim();
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers == null ? null : writers.trim();
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring == null ? null : starring.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getDownloadAddress() {
        return downloadAddress;
    }

    public void setDownloadAddress(String downloadAddress) {
        this.downloadAddress = downloadAddress == null ? null : downloadAddress.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}