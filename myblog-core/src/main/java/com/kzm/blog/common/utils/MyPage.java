package com.kzm.blog.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: kouzm
 * @Description: 封装自己的Page
 * @Date: Created in 22:21 2020/3/3
 * @Version
 */
@Data
public class MyPage<T> implements Serializable {
    @JsonIgnore
    public static final long serialVersionUID = -2822928619495261423L;

    private long pageSize;

    private long totalCount;

    private long totalPage;

    private long currentPage;

    private List<T> list;

    public  MyPage<T> getMyPage(Page<T> page){
        this.totalCount=page.getTotal();
        this.list=page.getRecords();
        this.currentPage=page.getCurrent();
        this.pageSize=page.getSize();
        this.totalPage=(this.totalCount%this.pageSize==0?this.totalCount/this.pageSize:(this.totalCount/pageSize)+1);
        return this;
    }

}
