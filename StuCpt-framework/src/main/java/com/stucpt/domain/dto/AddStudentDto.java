package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/06/21:16
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentDto {

    private Long userId;
    //用户名
    private String userName;
    //昵称
    private String nickName;
    //密码
    private String password;

    //账号状态（0正常 1停用）
    private String status;
    //学院

    private Long collegeId;

    //专业
    private Long majorId;
    //邮箱
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
}
