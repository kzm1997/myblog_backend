package test;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kzm.blog.common.entity.category.CategoryEntity;
import com.kzm.blog.common.utils.JsonUtils;
import com.kzm.blog.mapper.category.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:00 2020/3/2
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PaginationTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void testLambdaPaginationTest() {
        Page<CategoryEntity> page = new Page<>(2, 3);
        Page<CategoryEntity> page1 = (Page<CategoryEntity>) categoryMapper.selectPage(page, Wrappers.<CategoryEntity>lambdaQuery().ge(CategoryEntity::getParentId, "-1"));
        System.out.println(111);

    }

    @Test
    public void test2() {
        Page<CategoryEntity> page = new Page<>(1, 3);
        String json = JsonUtils.toJson(page);
        log.info("json-----{}", json);

    }
}
