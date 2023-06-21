package com.stucpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stucpt.domain.entity.Major;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Major)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-27 16:46:29
 */
@Mapper
public interface MajorMapper extends BaseMapper<Major> {

}
