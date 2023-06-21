package com.stucpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.UserDto;
import com.stucpt.domain.dto.UserListDto;
import com.stucpt.domain.dto.UserStatusDto;
import com.stucpt.domain.entity.User;
import com.stucpt.domain.vo.UpdateUserVo;

import java.util.List;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-14 23:58:46
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto);

    ResponseResult addUser(UserDto userDto);

    ResponseResult deleteUser(List<Long> id);

    UpdateUserVo getUserById(Long id);

    ResponseResult updateUser(UserDto userDto);

    Long updateUserAndStudent(String userName);

    ResponseResult updateUserStatus(UserStatusDto userStatusDto);

    ResponseResult getGradeUserList();

}



