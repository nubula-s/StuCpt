package com.stucpt.service;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.entity.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/16/23:42
 * @Description:
 */
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
