package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.domain.entity.Student;
import com.stucpt.domain.entity.User;
import com.stucpt.domain.vo.*;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.exception.SystemException;
import com.stucpt.mapper.UserMapper;
import com.stucpt.service.RoleService;
import com.stucpt.service.StudentService;
import com.stucpt.service.UserService;
import com.stucpt.utils.BeanCopyUtils;
import com.stucpt.utils.SecurityUtils;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 23:58:48
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    RoleService roleService;

    @Autowired
    StudentService studentService;



    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }


    @Override
    public ResponseResult updateUserInfo(User user) {
        //接口可以靠token来更改验证
        updateById(user);
        return ResponseResult.okResult();
    }

    //密码加密
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User user) {
        //1.对数据非空判断
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //2.对数据进行存在判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }

        //3.对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //4.存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
       LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
       queryWrapper.like(Strings.hasText(userListDto.getUserName()),User::getUserName,userListDto.getUserName());
        queryWrapper.like(Strings.hasText(userListDto.getPhonenumber()),User::getPhonenumber,userListDto.getPhonenumber());
        queryWrapper.eq(Strings.hasText(userListDto.getStatus()),User::getStatus,userListDto.getStatus());
        queryWrapper.eq(Strings.hasText(userListDto.getType()),User::getType,userListDto.getType());
        queryWrapper.orderByAsc(User::getId);

       Page<User> userPage = new Page<>(pageNum,pageSize);
       userPage = page(userPage,queryWrapper);
       List<User> users = userPage.getRecords();

        List<UserVo> userVos = BeanCopyUtils.copyBeanList(users, UserVo.class);
        PageVo pageVo = new PageVo(userVos,userPage.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addUser(UserDto userDto) {
        if (userNameExist(userDto.getUserName()))
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        if (emailExit(userDto.getEmail()))
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        if (phoneExit(userDto.getPhonenumber()))
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);

        //对密码进行加密
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user=BeanCopyUtils.copyBean(userDto,User.class);
        //保存到数据库
        save(user);
        //插入role user 联系表对应的id
        baseMapper.insertUserRole(user.getId(),userDto.getRoleIds());

        //匹配类型  如果是学生就添加到学生表中
        if(userDto.getType().equals(SystemConstants.Student)){

            AddStudentDto addstudentDto = BeanCopyUtils.copyBean(userDto, AddStudentDto.class);
            addstudentDto.setUserId(user.getId());
            studentService.addStudent(addstudentDto);
        }
        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult deleteUser(List<Long> ids) {
        //逻辑删除
        removeByIds(ids);
        baseMapper.deleteUserRole(ids);
        return ResponseResult.okResult();
    }

    @Override
    public UpdateUserVo getUserById(Long id) {
        User user = getById(id);
        UpdateUserInfoVo updateUserInfoVo = BeanCopyUtils.copyBean(user, UpdateUserInfoVo.class);
        UpdateUserVo updateUserVo = new UpdateUserVo();
        updateUserVo.setUser(updateUserInfoVo);
        List<Long> roleIds = baseMapper.getAllRole(id);
        updateUserVo.setRoleIds(roleIds);
        return updateUserVo;
    }

    @Override
    public ResponseResult updateUser(UserDto userDto) {

        User nowUser = getById(userDto.getId());
        if (!nowUser.getUserName().equals(userDto.getUserName())&&userNameExist(userDto.getUserName()))
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        if (!nowUser.getEmail().equals(userDto.getEmail())&&emailExit(userDto.getEmail()))
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        User user=BeanCopyUtils.copyBean(userDto,User.class);
        updateById(user);
        baseMapper.deleteUserRoleById(user.getId());
        baseMapper.insertUserRole(user.getId(),userDto.getRoleIds());
        return ResponseResult.okResult();
    }

    @Override
    public Long updateUserAndStudent(String userName) {
        /*LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        List<User> users = list(queryWrapper);
        BeanCopyUtils.copyBeanList(users,User.class);*/
        Long userid = baseMapper.getUserByUserName(userName);
        return userid;
    }

    @Override
    public ResponseResult updateUserStatus(UserStatusDto userStatusDto) {

        User user = getById(userStatusDto.getUserId());
        user.setStatus(userStatusDto.getStatus());
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getGradeUserList() {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getType,SystemConstants.Teacher);
        queryWrapper.eq(User::getStatus,SystemConstants.NORMAL);
        queryWrapper.orderByAsc(User::getId);

        List<User> user = list(queryWrapper);
        return ResponseResult.okResult(user);
    }


    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);

        return count(queryWrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);

        return count(queryWrapper)>0;
    }
    private boolean emailExit(String email){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);

        return count(queryWrapper)>0;
    }
    private boolean phoneExit(String phonenumber){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phonenumber);

        return count(queryWrapper)>0;
    }
}

