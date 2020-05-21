package test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kzm.blog.common.entity.category.CategoryEntity;
import com.kzm.blog.mapper.category.CategoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 16:53 2020/3/2
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void ainsert() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName("222");
        assertThat(categoryMapper.insert(categoryEntity)).isGreaterThan(0);
        assertThat(categoryEntity.getId()).isNotNull();
    }

    @Test
    public void bDelete() {
        assertThat(categoryMapper.deleteById(41)).isGreaterThan(0);
        assertThat(categoryMapper.delete(new QueryWrapper<CategoryEntity>().
                lambda().like(CategoryEntity::getCategoryName, "222"))).isGreaterThan(0);
    }

    @Test
    public void cupdate() {
        assertThat(categoryMapper.updateById(new CategoryEntity().setId(43).setCategoryName("3333"))).isGreaterThan(0);

        assertThat(categoryMapper.update(new CategoryEntity().setCategoryName("444"),
                Wrappers.<CategoryEntity>lambdaUpdate().
                        set(CategoryEntity::getAvatar, "111").eq(CategoryEntity::getCategoryName, "3333"))).isGreaterThan(0);

        categoryMapper.update(null, Wrappers.<CategoryEntity>lambdaUpdate()
                .set(CategoryEntity::getAvatar, null).eq(CategoryEntity::getCategoryName, "444"));
    }

    @Test
    public void selectMapsPage() {
        IPage<Map<String, Object>> page = categoryMapper.selectMapsPage(new Page<>(1, 5),
                Wrappers.<CategoryEntity>query().orderByAsc("parent_id"));
        List<Map<String, Object>> records = page.getRecords();
        records.forEach(System.out::println);
    }

    @Test
    public void testSelectMaxId(){
        QueryWrapper<CategoryEntity> wrapper=new QueryWrapper<>();
        wrapper.select("max(id) as id");  //选择找出的列
        categoryMapper.selectList(wrapper);


    }


    @Test
    public void test2(){
        List<CategoryEntity> categoryEntities = categoryMapper.selectList(new QueryWrapper<>());
        for (CategoryEntity categoryEntity : categoryEntities) {
            categoryEntity.setAvatar("http://localhost:8089/category"+categoryEntity.getAvatar());
            categoryMapper.update(categoryEntity,new QueryWrapper<CategoryEntity>().lambda().eq(CategoryEntity::getId,categoryEntity.getId()));
        }
    }
}
