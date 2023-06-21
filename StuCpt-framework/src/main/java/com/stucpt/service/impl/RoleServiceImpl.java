package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.RoleDto;
import com.stucpt.domain.dto.RoleMenuIdsDto;
import com.stucpt.domain.dto.QueryRoleDto;
import com.stucpt.domain.dto.RoleStatusDto;
import com.stucpt.domain.entity.Role;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.domain.vo.RoleVo;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.mapper.RoleMapper;
import com.stucpt.service.RoleService;
import com.stucpt.utils.BeanCopyUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-17 01:28:42
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 是就返回集合中只需要有admin
        if(id==1L){
            List<String> rolekeys = new ArrayList<>();
            rolekeys.add("admin");
            return rolekeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, QueryRoleDto queryRoleDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(queryRoleDto.getRoleName()),Role::getRoleName, queryRoleDto.getRoleName());
        queryWrapper.eq(Strings.isNotEmpty(queryRoleDto.getStatus()),Role::getStatus,queryRoleDto.getStatus());
        queryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        List<Role> roles = page.getRecords();
        List<RoleVo> roleVoList = BeanCopyUtils.copyBeanList(roles,RoleVo.class);


        return ResponseResult.okResult(new PageVo(roleVoList,page.getTotal()));
    }

    @Override
    public ResponseResult updateStatus(RoleStatusDto roleStatusDto) {

        Role role = getById(roleStatusDto.getRoleId());
        role.setStatus(roleStatusDto.getStatus());
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRole(Long id) {
        /*LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getId,id);
        List<Role> list = list(queryWrapper);
        List<Role> roles = BeanCopyUtils.copyBeanList(list, Role.class);*/
        Role role = getById(id);
        return ResponseResult.okResult(role);
    }

    @Override
    public ResponseResult updateRole(RoleMenuIdsDto roleMenuIdsDto) {
        //获取mapper
        RoleMapper roleMapper = getBaseMapper();
        //转换dto
        Role role = BeanCopyUtils.copyBean(roleMenuIdsDto, Role.class);
        //更新role
        updateById(role);
        //将MenuIds装到list集合中然后再通过mapper赋值给数据库的role menu表 其中得先删除再插入
        List<Long> menuIds = roleMenuIdsDto.getMenuIds();
        if (menuIds != null && menuIds.size() > 0) {
            roleMapper.deleteRoleMenuByRoleId(role.getId());
            roleMapper.insertRoleMenu(menuIds, role.getId());
        }else {
            roleMapper.deleteRoleMenuByRoleId(role.getId());
        }
        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult addRole(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto,Role.class);
        save(role);
        List<Long> menuIds = roleDto.getMenuIds();
        if(menuIds != null && menuIds.size() > 0){
            baseMapper.insertRoleMenu(menuIds,role.getId());
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(List<Long> roleIds) {
        if (roleIds.contains(SystemConstants.ADMIN)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.CAN_NOT_DELETE_ADMIN);
        }
        /*LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getId,roleIds)
                .orderByDesc(Role::getRoleSort);

        List<Role> roles  = list(queryWrapper);*/

        baseMapper.deleteBatchIds(roleIds);
/*
        baseMapper.deleteRoleMenuByRoleIdList(roleIds);
*/
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRoleToUser() {
        List<Role> roleList = list();
        List<RoleVo> roleVoList = BeanCopyUtils.copyBeanList(roleList, RoleVo.class);
        return ResponseResult.okResult(roleVoList);
    }

    @Override
    public List<RoleVo> getAllRole() {
        List<Role> roleList = list();
        List<RoleVo> roleVoList = BeanCopyUtils.copyBeanList(roleList, RoleVo.class);
        return roleVoList;
    }
}

