package com.stucpt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/14/14:47
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontUserLoginVo {

    private String token;
    private UserInfoVo userInfo;
}
