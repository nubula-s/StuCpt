package com.stucpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stucpt.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-14 01:06:05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    void insertUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    void deleteUserRole(@Param("userIds") List<Long> userIds);

    void deleteUserRoleById(@Param("userId") Long userId);


    List<Long> getAllRole(Long id);

    Long getUserByUserName(@Param("userName") String userName);

}
