package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.RoleDto;
import com.stucpt.domain.dto.RoleMenuIdsDto;
import com.stucpt.domain.dto.QueryRoleDto;
import com.stucpt.domain.dto.RoleStatusDto;
import com.stucpt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/20/18:09
 * @Description:
 */

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;



    @GetMapping("/list")
    public ResponseResult roleList(Integer pageNum, Integer pageSize, QueryRoleDto queryRoleDto){
        return roleService.roleList(pageNum,pageSize, queryRoleDto);
    }

    @PutMapping("/changeStatus")
    public  ResponseResult updateRoleStatus(@RequestBody RoleStatusDto roleStatusDto){
        return roleService.updateStatus(roleStatusDto);
    }


    @PreAuthorize("@ps.hasPermission('system:role:add')")
    @PostMapping
    public ResponseResult addRole(@RequestBody RoleDto roleDto){
        return roleService.addRole(roleDto);
    }

    @GetMapping("/{id}")
    public  ResponseResult getRoleById(@PathVariable("id")Long id){
        return roleService.getRole(id);
    }

    @PreAuthorize("@ps.hasPermission('system:role:edit')")
    @PutMapping
    public ResponseResult updateRole(@RequestBody RoleMenuIdsDto roleMenuIdsDto){
        return roleService.updateRole(roleMenuIdsDto);
    }

    @PreAuthorize("@ps.hasPermission('system:role:remove')")
    @DeleteMapping("/{id}")
    public  ResponseResult deleteRole(@PathVariable("id") List<Long> ids){

        return  roleService.deleteRole(ids);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRoleToUser();
    }

}
