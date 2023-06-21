package com.stucpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddStudentDto;
import com.stucpt.domain.dto.StudentDto;
import com.stucpt.domain.dto.StudentListDto;
import com.stucpt.domain.entity.Student;
import com.stucpt.domain.vo.UpdateStudentVo;

import java.util.List;


/**
 * (Student)表服务接口
 *
 * @author makejava
 * @since 2023-03-24 16:25:57
 */
public interface StudentService extends IService<Student> {

    ResponseResult getStudentList(Integer pageNum, Integer pageSize, StudentListDto studentListDto);

    ResponseResult addStudent(AddStudentDto addStudentDto);

    ResponseResult deleteStudent(List<Long> ids);

    UpdateStudentVo getStudentById(Long id);

    ResponseResult updateStudent(StudentDto studentDto);

}

