package com.stucpt.domain.dto;

import com.stucpt.domain.vo.RoleMenuVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/21/13:35
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuIdsDto {

    //角色ID@TableId
    private Long id;
    //备注
    private String remark;

    //角色权限字符串
    private String roleKey;

    //角色名称
    private String roleName;

    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;

    //菜单ids
    private List<Long> menuIds;
}
