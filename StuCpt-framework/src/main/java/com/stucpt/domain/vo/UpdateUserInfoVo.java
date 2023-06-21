package com.stucpt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/22/14:08
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoVo {
    private Long id;
    private String userName;
    private String email;
    private String phonenumber;
    private String nickName;
    private String sex;
    private String type;
    private String status;

}
