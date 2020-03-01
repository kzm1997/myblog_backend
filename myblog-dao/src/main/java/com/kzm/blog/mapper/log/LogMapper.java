package com.kzm.blog.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kzm.blog.common.entity.log.LogEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper extends BaseMapper<LogEntity> {
}
