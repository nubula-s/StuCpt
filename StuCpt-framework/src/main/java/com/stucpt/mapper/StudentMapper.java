package com.stucpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stucpt.domain.entity.Student;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Student)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-24 16:25:52
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    void deleteStudentRoleById(Long id);
}
