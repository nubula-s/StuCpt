package com.stucpt.handler.exception;

import com.stucpt.domain.ResponseResult;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/14/20:34
 * @Description:
 *
 *
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(SystemException.class)  //处理异常的字节码对象
    public ResponseResult systemExceptionHandler(SystemException e){
        //1.打印异常信息
        log.error("出现了异常！ {}",e);
        //2.从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //1.打印异常信息
        log.error("出现了异常！ {}",e);
        //2.从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
