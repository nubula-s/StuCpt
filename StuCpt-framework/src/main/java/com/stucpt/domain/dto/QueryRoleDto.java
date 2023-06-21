package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/20/18:10
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryRoleDto {
    //角色名称
    private String roleName;
    //角色状态（0正常 1停用）
    private String status;
}
