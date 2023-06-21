package com.stucpt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/21/13:13
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuRoleIdsVo {

    private List<RoleMenuVO> menus;

    private List<Long> checkedKeys;


}
