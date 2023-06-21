package com.stucpt.service;

import com.stucpt.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/15/23:46
 * @Description:
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);

}
