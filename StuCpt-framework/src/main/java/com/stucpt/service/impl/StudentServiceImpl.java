package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.primitives.Longs;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.ResponseResult;

import com.stucpt.domain.dto.AddStudentDto;
import com.stucpt.domain.dto.StudentListDto;
import com.stucpt.domain.dto.StudentDto;
import com.stucpt.domain.dto.UserDto;
import com.stucpt.domain.entity.Major;
import com.stucpt.domain.entity.Student;
import com.stucpt.domain.entity.User;
import com.stucpt.domain.vo.*;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.exception.SystemException;
import com.stucpt.mapper.StudentMapper;
import com.stucpt.service.*;
import com.stucpt.utils.BeanCopyUtils;
import io.jsonwebtoken.lang.Strings;
import lombok.AllArgsConstructor;
import org.assertj.core.internal.Integers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (Student)表服务实现类
 *
 * @author makejava
 * @since 2023-03-24 16:25:58
 */
@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {


    @Autowired
    private UserService userService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private MajorService majorService;

    @Autowired
    RoleService roleService;


    /*@Override
    public ResponseResult updateStudentInfo(Student student) {
        //接口可以靠token来更改验证
        updateById(student);
        return ResponseResult.okResult();
    }*/

    //密码加密
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public ResponseResult getStudentList(Integer pageNum, Integer pageSize, StudentListDto studentListDto) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.hasText(studentListDto.getUserName()),Student::getUserName,studentListDto.getUserName());
        queryWrapper.like(Strings.hasText(studentListDto.getNickName()),Student::getPhonenumber,studentListDto.getNickName());
        queryWrapper.eq(Strings.hasText(studentListDto.getStatus()),Student::getStatus,studentListDto.getStatus());
        queryWrapper.eq(Objects.nonNull(studentListDto.getCollegeId()),Student::getCollegeId,studentListDto.getCollegeId());
        queryWrapper.orderByAsc(Student::getId);

        Page<Student> studentPage = new Page<>(pageNum,pageSize);
        studentPage = page(studentPage,queryWrapper);
        List<Student> students = studentPage.getRecords();
        students.stream()
                .map(student ->
                        student.setCollegeName(collegeService.getById(student.getCollegeId()).getName()))
                .collect(Collectors.toList());
        students.stream()
                .map(student ->
                        student.setMajorName(majorService.getById(student.getMajorId()).getName()))
                .collect(Collectors.toList());

        List<StudentVo> studentVos = BeanCopyUtils.copyBeanList(students, StudentVo.class);
        PageVo pageVo = new PageVo(studentVos,studentPage.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addStudent(AddStudentDto addStudentDto) {
        if (studentNameExist(addStudentDto.getUserName()))
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        if (emailExit(addStudentDto.getEmail()))
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        if (phoneExit(addStudentDto.getPhonenumber()))
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);

        //对密码进行加密
        addStudentDto.setPassword(passwordEncoder.encode(addStudentDto.getPassword()));
        Student student=BeanCopyUtils.copyBean(addStudentDto,Student.class);
        //保存到数据库
        save(student);

        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult deleteStudent(List<Long> ids) {
        //逻辑删除
        removeByIds(ids);
        userService.deleteUser(ids);
        return ResponseResult.okResult();
    }

    @Override
    public UpdateStudentVo getStudentById(Long id) {
        Student student = getById(id);
        UpdateStudentInfoVo updateStudentInfoVo = BeanCopyUtils.copyBean(student, UpdateStudentInfoVo.class);
        UpdateStudentVo updateStudentVo = new UpdateStudentVo();
        updateStudentVo.setStudent(updateStudentInfoVo);
        // List<Long> roleIds = baseMapper.getAllRole(id);
        // updateStudentVo.setRoleIds(roleIds);
        return updateStudentVo;
    }

    @Override
    @Transactional
    public ResponseResult updateStudent(StudentDto studentDto) {

        Student nowStudent = getById(studentDto.getId());
        if (!nowStudent.getUserName().equals(studentDto.getUserName())&&studentNameExist(studentDto.getUserName()))
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        if (!nowStudent.getEmail().equals(studentDto.getEmail())&&emailExit(studentDto.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (!nowStudent.getPhonenumber().equals(studentDto.getPhonenumber())&&emailExit(studentDto.getPhonenumber())) {
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        Student student=BeanCopyUtils.copyBean(studentDto,Student.class);
        String encodePassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encodePassword);
        updateById(student);
        UserDto userDto = BeanCopyUtils.copyBean(student, UserDto.class);
        Long aLong = userService.updateUserAndStudent(student.getUserName());
        userDto.setId(aLong);
        userService.updateUser(userDto);

        return ResponseResult.okResult();
    }


    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getNickName,nickName);

        return count(queryWrapper)>0;
    }

    private boolean studentNameExist(String studentName) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getUserName,studentName);

        return count(queryWrapper)>0;
    }
    private boolean emailExit(String email){
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getEmail,email);

        return count(queryWrapper)>0;
    }
    private boolean phoneExit(String phonenumber){
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getPhonenumber,phonenumber);

        return count(queryWrapper)>0;
    }
}

