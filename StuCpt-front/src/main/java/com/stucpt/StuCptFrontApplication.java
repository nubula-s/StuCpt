package com.stucpt;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/09/10:45
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.stucpt.mapper")
@EnableScheduling   //定时任务注解
@EnableSwagger2   //Swagger开启注解
public class StuCptFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(StuCptFrontApplication.class,args);
    }
}
