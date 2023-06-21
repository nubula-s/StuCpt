package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/20/22:30
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleStatusDto {
    //角色ID@TableId
    private Long roleId;

    //角色状态（0正常 1停用）
    private String status;
}
