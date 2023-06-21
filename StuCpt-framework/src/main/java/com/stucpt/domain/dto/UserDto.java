package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/21/23:43
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String userName;

    private String password;

    private String nickName;
    private String phonenumber;
    private String email;
    private String sex;
    private String type;
    private Integer status;
    //学院
    private Long collegeId;

    //专业
    private Long majorId;
    private List<Long> roleIds;
}
