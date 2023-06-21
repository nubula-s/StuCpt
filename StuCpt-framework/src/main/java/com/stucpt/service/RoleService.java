package com.stucpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.RoleDto;
import com.stucpt.domain.dto.RoleMenuIdsDto;
import com.stucpt.domain.dto.QueryRoleDto;
import com.stucpt.domain.dto.RoleStatusDto;
import com.stucpt.domain.entity.Role;
import com.stucpt.domain.vo.RoleVo;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-17 01:28:41
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult roleList(Integer pageNum, Integer pageSize, QueryRoleDto queryRoleDto);

    ResponseResult updateStatus(RoleStatusDto roleStatusDto);

    ResponseResult getRole(Long id);

    ResponseResult updateRole(RoleMenuIdsDto menuRoleIdsDto);

    ResponseResult addRole(RoleDto roleDto);

    ResponseResult deleteRole(List<Long> ids);

    ResponseResult listAllRoleToUser();

    List<RoleVo> getAllRole();
}

