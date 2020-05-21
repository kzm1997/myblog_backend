package com.kzm.blog.common.entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 12:11 2020/5/5
 * @Version
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tree<T> {


    private Integer menuId;

    private Integer parentId;

    private String menuName;

    private String path;

    private  String component;

    private  String perms;

    private  String icon;

    private  Integer type;

    private  Integer orderNum;


    private boolean hasParent = false;

    private boolean hasChildren = false;

    private List<Tree<T>> children;

    public void initChildren(){
        this.children = new ArrayList<>();
    }

}
