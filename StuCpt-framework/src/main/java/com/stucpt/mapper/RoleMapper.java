package com.stucpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stucpt.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-17 01:28:39
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    void deleteRoleMenuByRoleId(@Param("roleId") Long roleId);

    void insertRoleMenu(@Param("menuIds")List<Long> menuIds, @Param("roleId") Long roleId);

    void deleteRoleMenuByRoleIdList(@Param("roleIds") List<Long> roleIds);

}
