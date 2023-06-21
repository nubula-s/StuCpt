package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddStudentDto;
import com.stucpt.domain.dto.StudentDto;
import com.stucpt.domain.dto.StudentListDto;
import com.stucpt.domain.vo.RoleVo;
import com.stucpt.domain.vo.UpdateStudentVo;
import com.stucpt.service.RoleService;
import com.stucpt.service.StudentService;
import com.stucpt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/24/16:28
 * @Description:
 */
@RestController
@RequestMapping("/system/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult getStudentList(Integer pageNum, Integer pageSize, StudentListDto studentListDto){
        return studentService.getStudentList(pageNum,pageSize,studentListDto);
    }

    @PostMapping
    public ResponseResult addStudent(@RequestBody AddStudentDto addStudentDto){
        return studentService.addStudent(addStudentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteStudent(@PathVariable("id") List<Long> ids){
        return studentService.deleteStudent(ids);
    }


    /**
     * update模块
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getStudentById(@PathVariable("id")Long id){
        UpdateStudentVo updateStudentVo = studentService.getStudentById(id);
        return ResponseResult.okResult(updateStudentVo);
    }

    @PutMapping
    public ResponseResult updateStudent(@RequestBody StudentDto studentDto){
        return studentService.updateStudent(studentDto);
    }


}
