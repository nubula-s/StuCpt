package com.stucpt.service;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.entity.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/14/13:51
 * @Description:
 */
public interface FrontLoginService {

     ResponseResult login(User user);

    ResponseResult logout();
}
