package com.stucpt.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.entity.Competition;
import com.stucpt.service.CompetitionService;
import com.stucpt.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/28/17:00
 * @Description:
 */
@Component  //注入容器
public class UpdateCompetitionSignAndDum {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private CompetitionService competitionService;
    @Scheduled(cron = "0/30 * * * * ?")
    public void updateSignAndDum() {
        LocalDateTime nowTime = LocalDateTime.now();
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = nowTime.format(formatter);
        System.out.println(now);

        LambdaUpdateWrapper<Competition> updateWrapperSign = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<Competition> updateWrapperSign1 = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<Competition> updateWrapperDum = new  LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<Competition> updateWrapperDum1 = new  LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<Competition> updateWrapperGrade = new  LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<Competition> updateWrapperGrade1 = new  LambdaUpdateWrapper<>();


        updateWrapperSign.apply("date_format (sign_time,'%Y-%m-%d %H:%i:%S') <= {0}", now)
                .apply("date_format (sign_end,'%Y-%m-%d %H:%i:%S') >=  {0}", now)
                .set(Competition::getIsSign, SystemConstants.IN_SIGN);


        updateWrapperSign1.apply("date_format (sign_end,'%Y-%m-%d %H:%i:%S') <= {0}",now)
                .set(Competition::getIsSign,SystemConstants.END_SIGN);


        //0会比较  刚好在里面就赋值1
        updateWrapperDum.apply("date_format (start_time,'%Y-%m-%d %H:%i:%S') <= {0}",now)
                .apply("date_format (end_time,'%Y-%m-%d %H:%i:%S') >= {0}",now)
                .set(Competition::getIsDum,SystemConstants.IN_DUM);
        System.out.println("比较了没");

        //刚好是1再比较  是否过期了   再赋值2
        updateWrapperDum1.apply("date_format (end_time,'%Y-%m-%d %H:%i:%S') <= {0}",now)
                .set(Competition::getIsDum,SystemConstants.END_DUM);


        //三种状态
        updateWrapperGrade.apply("date_format (grade_start,'%Y-%m-%d %H:%i:%S') <= {0}",now)
                .apply("date_format (grade_end,'%Y-%m-%d %H:%i:%S') >=  {0}", now)
                .set(Competition::getIsGrade,SystemConstants.IN_GRADE);
        updateWrapperGrade1.apply("date_format (grade_end,'%Y-%m-%d %H:%i:%S') <= {0}",now)
                .set(Competition::getIsGrade,SystemConstants.END_GRADE);


        competitionService.update(updateWrapperSign);
        competitionService.update(updateWrapperSign1);
        competitionService.update(updateWrapperDum);
        competitionService.update(updateWrapperDum1);
        competitionService.update(updateWrapperGrade);
        competitionService.update(updateWrapperGrade1);


    }

}


