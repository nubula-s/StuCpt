package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.entity.User;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.exception.SystemException;
import com.stucpt.service.FrontLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/14/13:46
 * @Description:
 */
@RestController
public class FrontLoginController {
    @Autowired
    private FrontLoginService frontLoginService;


    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);

        }
        return frontLoginService.login(user);
    }


    @PostMapping("/logout")
    public ResponseResult logout(){
        return frontLoginService.logout();
    }
}
