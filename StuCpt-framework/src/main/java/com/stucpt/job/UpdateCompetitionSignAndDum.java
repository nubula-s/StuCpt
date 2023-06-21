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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    public void updateViewCount() {
        LocalDateTime nowTime = LocalDateTime.now();
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = nowTime.format(formatter);
        LocalDateTime localDateTime = LocalDateTime.parse(now, formatter);
        System.out.println(localDateTime);

        LambdaUpdateWrapper<Competition> updateWrapperSign = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<Competition> updateWrapperDum = new  LambdaUpdateWrapper<>();
        updateWrapperSign.eq(Competition::getIsSign, SystemConstants.BEFORE_SIGN)
                .apply("date_format (sign_time,'%Y-%m-%d %H:%i:%S') =< date_format ({0},'%Y-%m-%d %H:%i:%S')",localDateTime)
                .apply("date_format (sign_end,'%Y-%m-%d %H:%i:%S') >= date_format ({0},'%Y-%m-%d %H:%i:%S')",localDateTime)
                .set(Competition::getIsSign,SystemConstants.IN_SIGN);

        updateWrapperSign.apply("date_format (sign_end,'%Y-%m-%d %H:%i:%S') <= date_format ({0},'%Y-%m-%d %H:%i:%S')",localDateTime)
                .set(Competition::getIsSign,SystemConstants.END_SIGN);

        updateWrapperDum.eq(Competition::getIsDum,SystemConstants.BEFORE_DUM)
                .apply("date_format (start_time,'%Y-%m-%d %H:%i:%S') =< date_format ({0},'%Y-%m-%d %H:%i:%S')",localDateTime)
                .apply("date_format (end_time,'%Y-%m-%d %H:%i:%S') >= date_format ({0},'%Y-%m-%d %H:%i:%S')",localDateTime)
                .set(Competition::getIsDum,SystemConstants.IN_DUM);

        updateWrapperDum.ne(Competition::getIsDum,SystemConstants.END_DUM)
                .apply("date_format (sign_end,'%Y-%m-%d %H:%i:%S') <= date_format ({0},'%Y-%m-%d %H:%i:%S')",localDateTime)
                .set(Competition::getIsDum,SystemConstants.END_DUM);


//        List<Competition> competitions = (queryWrapper);

    }

}
