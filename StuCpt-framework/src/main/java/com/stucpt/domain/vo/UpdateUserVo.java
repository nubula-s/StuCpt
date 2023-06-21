package com.stucpt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/22/14:03
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserVo {
    private UpdateUserInfoVo user;
    private List<Long> roleIds;
    private List<RoleVo> roles;


}
