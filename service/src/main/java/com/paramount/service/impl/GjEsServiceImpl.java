package com.paramount.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.paramount.common.constant.GjConstant;
import com.paramount.common.dto.base.PageQueryResult;
import com.paramount.dto.EsMovieQryDto;
import com.paramount.entity.EsMovie;
import com.paramount.entity.Movie;
import com.paramount.mapper.GjMovieMapper;
import com.paramount.service.GjEsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author panteng
 * @description: GjEsService
 * @date 2019/11/25 11:49
 */

@Slf4j
@Service
public class GjEsServiceImpl implements GjEsService {
    @Autowired
    private GjMovieMapper gjMovieMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final String INDEX_NAME = "gj_movie";

    @Override
    public void createIndex(String idxName, String idxSQL) {
        try {
            if (!this.indexExist(idxName)) {
                log.error(" idxName={} 已经存在,idxSql={}", idxName, idxSQL);
                return;
            }
            CreateIndexRequest request = new CreateIndexRequest(idxName);
            buildSetting(request);
            request.mapping(idxSQL, XContentType.JSON);
            CreateIndexResponse res = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            if (!res.isAcknowledged()) {
                throw new RuntimeException("初始化失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 同步更新前一天的movie数据
     *
     * @return
     */
    @Override
    public long syncUpdate() {
        List<Movie> movies = gjMovieMapper.selectList(null);
        log.info("查询到所有记录数量:{}", movies.size());

        if (CollectionUtils.isEmpty(movies)) {
            return 0;
        }
        List<EsMovie> esMovies = new ArrayList<>();
        movies.stream().forEach(t -> {
            EsMovie esMovie = new EsMovie();
            esMovie.setId(t.getId());
            esMovie.setName(t.getName());
            if (t.getDoubanScore().contains(GjConstant.SLASH)) {
                String[] split = t.getDoubanScore().split(GjConstant.SLASH);
                esMovie.setDoubanScore(Double.valueOf(split[0]));
            } else {
                esMovie.setDoubanScore(0d);
            }
            esMovie.setStillUrl(t.getStillUrl());
            esMovie.setOrigin(t.getOrigin());
            esMovie.setCategory(t.getCategory());
            esMovie.setReleaseDate(t.getReleaseDate());
            esMovies.add(esMovie);
        });


        BulkRequest request = new BulkRequest();

        esMovies.stream().forEach(esMovie -> request.add(new IndexRequest(INDEX_NAME)
                .id(String.valueOf(esMovie.getId()))
                .source(JSON.toJSONString(esMovie), XContentType.JSON)));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("保存到es错误:{1}", e.getMessage());
            return 0;
        }
        return esMovies.size();
    }

    /**
     * 断某个index是否存在
     *
     * @param idxName index名
     * @return boolean
     * @throws
     * @since
     */
    public boolean indexExist(String idxName) throws Exception {
        GetIndexRequest request = new GetIndexRequest(idxName);
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 设置分片
     *
     * @param request
     */
    public void buildSetting(CreateIndexRequest request) {
        request.settings(Settings.builder().put("index.number_of_shards", 2)
                .put("index.number_of_replicas", 1));
    }


    /** 删除index
     * @param idxName
     * @return void
     * @throws
     */
    @Override
    public void deleteIndex(String idxName) {
        try {
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(new DeleteIndexRequest(idxName), RequestOptions.DEFAULT);
            log.info("删除信息:{}",delete.isAcknowledged());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long initData(String idxName) {
        List<Movie> movies = gjMovieMapper.selectList(null);
        log.info("查询到所有记录数量:{}", movies.size());

        if (CollectionUtils.isEmpty(movies)) {
            return 0;
        }
        List<EsMovie> esMovies = new ArrayList<>();
        movies.stream().forEach(t -> {
            EsMovie esMovie = new EsMovie();
            esMovie.setId(t.getId());
            esMovie.setName(t.getName());
            if (t.getDoubanScore().contains(GjConstant.SLASH) && !t.getDoubanScore().startsWith(GjConstant.SLASH)) {
                String[] split = t.getDoubanScore().split(GjConstant.SLASH);
                esMovie.setDoubanScore(Double.valueOf(split[0]));
            } else {
                esMovie.setDoubanScore(0d);
            }
            esMovie.setStillUrl(t.getStillUrl());
            esMovie.setOrigin(t.getOrigin());
            esMovie.setCategory(t.getCategory());
            esMovie.setReleaseDate(t.getReleaseDate());
            esMovies.add(esMovie);
        });


        BulkRequest request = new BulkRequest();

        esMovies.stream().forEach(esMovie -> request.add(new IndexRequest(idxName)
                .id(String.valueOf(esMovie.getId()))
                .source(JSON.toJSONString(esMovie), XContentType.JSON)));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("保存到es错误:{}", e.getMessage());
            return 0;
        }
        return esMovies.size();
    }

    @Override
    public PageQueryResult<EsMovie> pageQryEsMovie(int curPage, int size, EsMovieQryDto qryDto) {
        if(qryDto == null){
            qryDto = new EsMovieQryDto();
        }
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //创建布尔查询对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //搜索条件
        //根据关键字搜索 多条件
        if(StringUtils.isNotEmpty(qryDto.getKeyword())){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(qryDto.getKeyword(), "name")
                    .minimumShouldMatch("70%")
                    .field("name", 10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }
        if(StringUtils.isNotEmpty(qryDto.getCategory())){
            //根据类别
            boolQueryBuilder.filter(QueryBuilders.termQuery("category",qryDto.getCategory()));
        }
        if(StringUtils.isNotEmpty(qryDto.getOrigin())){
            //根据产地
            boolQueryBuilder.filter(QueryBuilders.termQuery("origin",qryDto.getOrigin()));
        }
        //根据上映日期范围 组合查询 2头包含
        if(qryDto.getReleaseDate().size() != 0){
            List<String> releaseDate = qryDto.getReleaseDate();
            boolQueryBuilder.must(QueryBuilders.rangeQuery("releaseDate").gt(releaseDate.get(0)).lt(releaseDate.get(1)).includeLower(true).includeUpper(true));
        }

        //根据上映日期范围  组合查询
        if(qryDto.getDoubanScore().size() != 0){
            List<Double> doubanScore = qryDto.getDoubanScore();
            boolQueryBuilder.must(QueryBuilders.rangeQuery("doubanScore").gt(doubanScore.get(0)).lt(doubanScore.get(1)).includeLower(true).includeUpper(true));
        }

        //设置boolQueryBuilder到searchSourceBuilder 并设置排序
        searchSourceBuilder.query(boolQueryBuilder);
//               .sort("doubanScore",qryDto.getDoubanScoreSort() == 0? SortOrder.DESC:SortOrder.ASC);
//                                                    .sort("releaseDate",qryDto.getDoubanScoreSort() == 0?SortOrder.DESC:SortOrder.ASC);
//        //设置分页参数
        if(curPage<=0){
            curPage = 1;
        }
        if(size<=0){
            size = 12;
        }
        //起始记录下标
        int from  = (curPage-1)*size;
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);

        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        //设置高亮字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);

        PageQueryResult<EsMovie> queryResult = new PageQueryResult<>();
        List<EsMovie> list = new ArrayList<>();
        try {
            //执行搜索
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            //获取响应结果
            SearchHits hits = searchResponse.getHits();
            //匹配的总记录数
            long totalHits = hits.getTotalHits().value;
            queryResult.setTotal(totalHits);
            SearchHit[] searchHits = hits.getHits();
            for(SearchHit hit:searchHits){
                //源文档
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                EsMovie esMovie = BeanUtil.mapToBean(sourceAsMap, EsMovie.class, true);
                //取出高亮字段name
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                if(highlightFields!=null){
                    HighlightField highlightFieldName = highlightFields.get("name");
                    if(highlightFieldName!=null){
                        esMovie.setName(getHighlight(highlightFieldName));
                    }
//                    HighlightField highlightFieldNamelabel = highlightFields.get("label");
//                    if(highlightFieldNamelabel!=null){
//                        esMovie.setLabel(getHighlight(highlightFieldNamelabel));
//                    }
//                    HighlightField highlightFieldNameStarring= highlightFields.get("starring");
//                    if(highlightFieldNameStarring!=null){
//                        esMovie.setStarring(getHighlight(highlightFieldNameStarring));
//                    }
                }
                list.add(esMovie);
            }
        } catch (Exception e) {
            log.error("查询es错误{}:",e);
        }
        queryResult.setList(list);
        return queryResult;
    }

    private String getHighlight(HighlightField highlightFieldName) {
        Text[] fragments = highlightFieldName.fragments();
        StringBuffer stringBuffer = new StringBuffer();
        for(Text text:fragments){
            stringBuffer.append(text);
        }
        return stringBuffer.toString();
    }

}
