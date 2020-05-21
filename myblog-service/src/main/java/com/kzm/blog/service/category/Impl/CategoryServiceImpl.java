package com.kzm.blog.service.category.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.entity.article.ArticleEntity;
import com.kzm.blog.common.entity.category.CategoryEntity;
import com.kzm.blog.common.entity.category.bo.CategoryBo;
import com.kzm.blog.common.entity.category.vo.*;
import com.kzm.blog.common.utils.MyPage;
import com.kzm.blog.mapper.article.ArticleMapper;
import com.kzm.blog.mapper.category.CategoryMapper;
import com.kzm.blog.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 22:29 2020/3/1
 * @Version
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;


    /**
     * 分页查询categoryid下的类
     * * @param categoryBo
     *
     * @return
     */
    @Override
    public MyPage<CategoryVo> listCategoryById(CategoryBo categoryBo) {
        Page<CategoryVo> page = new Page<>(categoryBo.getPageNum(), categoryBo.getPageSize());
        page = (Page<CategoryVo>) categoryMapper.selectCategoryVoPage(page, categoryBo);
        //获取分类下的文章数
        List<CategoryVo> records = page.getRecords();
        List<CategoryVo> categoryVos = categoryMapper.selectArticles();
        for (CategoryVo record : records) {
            boolean flag=false;
            for (CategoryVo categoryVo : categoryVos) {
                if (record.getId() == categoryVo.getId()) {
                        record.setArticles(categoryVo.getArticles());
                        flag=true;
                        break;
                }
            }
            if (flag!=true){
                record.setArticles(0);
            }
        }
        page.setRecords(records);
        return new MyPage<CategoryVo>().getMyPage(page);
    }

    /**
     * 获取首页文章分类
     *
     * @return
     */
    @Override
    public Result getIndexCategory() {
        List<CategoryEntity> entities = categoryMapper.selectList(new QueryWrapper<CategoryEntity>().lambda()
                .eq(CategoryEntity::getParentId, -1).select(CategoryEntity::getId, CategoryEntity::getCategoryName));
        List<CategoryShortVo> categoryShortVos = new ArrayList<>();
        categoryShortVos.add(new CategoryShortVo().setId(0).setCategoryName("全部"));
        for (CategoryEntity entity : entities) {
            CategoryShortVo categoryShortVo = new CategoryShortVo();
            BeanUtil.copyProperties(entity, categoryShortVo);
            categoryShortVos.add(categoryShortVo);
        }
        categoryShortVos.add(new CategoryShortVo().setId(-1).setCategoryName("热点"));
        return Result.success(categoryShortVos);
    }

    @Override
    public Result getTag(Integer id) {
        //获取热点数据
        if (id == -1) {
            //todo 热点标签
            id = 0;
        }
        List<CategoryShortVo> categoryShortVos = categoryMapper.selectTagByparent(id);
        return Result.success(categoryShortVos);
    }

    @Override
    public Result getHot() {
        List<CategoryNameVo> categoryNameVos = categoryMapper.getHot();
        return Result.success(categoryNameVos);
    }

    @Override
    public Result getMessage(Integer id) {
        CategoryEntity categoryEntity = categoryMapper.selectById(id);
        CategoryMessageVo categoryMessageVo=new CategoryMessageVo();
        BeanUtil.copyProperties(categoryEntity,categoryMessageVo);
        return Result.success(categoryMessageVo);
    }

    @Override
    public Result getCategorySys() {
        List<CategoryEntity> categoryEntities = categoryMapper.selectList(new QueryWrapper<CategoryEntity>().lambda().eq(CategoryEntity::getParentId, -1));
        List<CategoryPie> categoryPies = categoryEntities.stream().map(toPie).collect(Collectors.toList());
        for (CategoryPie categoryPY : categoryPies) {
             int count= categoryMapper.getArticleCount(categoryPY.getCategroyId());
             categoryPY.setCount(count);
        }
        return Result.success(categoryPies);
    }

    private Function<CategoryEntity,CategoryPie> toPie=categoryEntity -> {
            CategoryPie categoryPie=new CategoryPie();
            categoryPie.setCategoryName(categoryEntity.getCategoryName());
            categoryPie.setCategroyId(categoryEntity.getId());
            return categoryPie;
    };
}
