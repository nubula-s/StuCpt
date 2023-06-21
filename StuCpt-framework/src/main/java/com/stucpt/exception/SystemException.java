package com.stucpt.exception;

import com.stucpt.enums.AppHttpCodeEnum;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/14/20:29
 * @Description:
 * 直接抛出然后利用统一异常处理，把异常中的信息封装成ResponseResult响应给前端
 */

public class SystemException extends RuntimeException{


    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
