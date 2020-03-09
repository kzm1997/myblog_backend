package com.kzm.blog.common.entity.category;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 15:28 2020/3/1
 * @Version
 */
@TableName("category")
@Data
@Accessors(chain = true)
public class CategoryEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String  categoryName;

    private String avatar;

    private Integer parentId;
}
