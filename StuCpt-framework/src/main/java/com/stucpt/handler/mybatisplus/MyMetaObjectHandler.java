package com.stucpt.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.stucpt.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.TimeZone;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {




    @Override
    public void insertFill(MetaObject metaObject) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

        Long userId = null;
      /*  try {*/
            userId = SecurityUtils.getUserId();
      /*  } catch (Exception e) {
            e.printStackTrace();
            userId = -1L;//表示是自己创建
        }*/
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy",userId , metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        TimeZone time = TimeZone.getTimeZone("Etc/GMT-8");  //转换为中国时区

        TimeZone.setDefault(time);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName(" ", SecurityUtils.getUserId(), metaObject);
    }
}