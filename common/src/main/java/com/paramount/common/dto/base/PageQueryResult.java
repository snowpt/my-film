package com.paramount.common.dto.base;

import java.util.List;

/**
 * @author panteng
 * @description: 分页查询结果
 * @date 2019/12/4 17:03
 */
public class PageQueryResult<T> {
    //数据列表
    private List<T> list;
    //数据总数
    private long total;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
