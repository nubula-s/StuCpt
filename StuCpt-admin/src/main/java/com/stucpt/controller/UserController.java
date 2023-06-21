package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.UserDto;
import com.stucpt.domain.dto.UserListDto;
import com.stucpt.domain.dto.UserStatusDto;
import com.stucpt.domain.vo.RoleVo;
import com.stucpt.domain.vo.UpdateUserVo;
import com.stucpt.service.RoleService;
import com.stucpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/20/18:08
 * @Description:
 */

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto){
        return userService.getUserList(pageNum,pageSize,userListDto);
    }

    @GetMapping("/grade")
    public  ResponseResult listGrade(){
        return userService.getGradeUserList();
    }


    @PostMapping
    public ResponseResult addUser(@RequestBody UserDto userDto){
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable("id") List<Long> ids){
        return userService.deleteUser(ids);
    }


    /**
     * update模块
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getUserById(@PathVariable("id")Long id){
       UpdateUserVo updateUserVo = userService.getUserById(id);
        List<RoleVo> allRole = roleService.getAllRole();
        updateUserVo.setRoles(allRole);

        return ResponseResult.okResult(updateUserVo);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UserDto userDto){
        return userService.updateUser(userDto);
    }

    @PutMapping("/changeStatus")
    public  ResponseResult updateUserStatus(@RequestBody UserStatusDto userStatusDto){
        return userService.updateUserStatus(userStatusDto);
    }

}
