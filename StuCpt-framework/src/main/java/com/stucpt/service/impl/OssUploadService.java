package com.stucpt.service.impl;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.stucpt.domain.ResponseResult;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.exception.SystemException;
import com.stucpt.service.UploadService;
import com.stucpt.utils.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/15/23:49
 * @Description:
 */
@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class OssUploadService implements UploadService {

    @Override
    public ResponseResult uploadImg(MultipartFile img) {

        //1.判断文件类型
        //2.获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //3.对原始文件名进行判断
//            if(!originalFilename.endsWith(".png")){
//                throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
//            }
//
        //toLowerCase转化成小写  怕误判
        if (!originalFilename.toLowerCase().endsWith(".png") &&
                !originalFilename.toLowerCase().endsWith(".jpg")) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }


        //4.如果判断通过上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);

        //5.返回七牛云文件的外链地址
        String url = uploadOss(img, filePath);//  2099/2/3/wqeqeqe.png
        return ResponseResult.okResult(url);
    }


    //yml的key值  data注释来设置set 赋值给下面
    private String accessKey;
    private String secretKey;
    private String bucket;


    private String uploadOss(MultipartFile imgFile, String filePath) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                //返回七牛云外链域名
                return "http://rt86r1nhk.hn-bkt.clouddn.com/" + key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "www";
    }
}
