package com.stucpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stucpt.domain.entity.College;
import org.apache.ibatis.annotations.Mapper;


/**
 * (College)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-27 14:06:21
 */
@Mapper
public interface CollegeMapper extends BaseMapper<College> {

}
