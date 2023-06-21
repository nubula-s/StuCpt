package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/21/23:49
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {
    //用户名
    private String userName;
    //手机号
    private String phonenumber;
    //账号状态（0正常 1停用）
    private String status;

    private  String type;
}
