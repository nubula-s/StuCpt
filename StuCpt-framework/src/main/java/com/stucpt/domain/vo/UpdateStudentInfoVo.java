package com.stucpt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/25/0:24
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentInfoVo {
    //主键@TableId
    private Long id;

    //用户名
    private String userName;
    //昵称
    private String nickName;
    //密码
    private String password;

    //账号状态（0正常 1停用）
    private String status;

    private Long collegeId;
    //学院

    private Long majorId;
    //邮箱
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
}
