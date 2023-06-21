package com.stucpt.job;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.stucpt.domain.entity.Article;
import com.stucpt.domain.entity.Competition;
import com.stucpt.service.ArticleService;
import com.stucpt.service.CompetitionService;
import com.stucpt.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/28/16:51
 * @Description:
 */
@Component
public class UpdateSignPeopleJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private CompetitionService competitionService;


    @Scheduled(cron = "0/30 * * * * ?")
    public void updateSignPeople() {
        //获取redis中的浏览量
        Map<String, Integer> signPeopleMap = redisCache.getCacheMap("competition:signPeople");

        List<Competition> competitions = signPeopleMap.entrySet()
                .stream()
                .map(entry -> new Competition(Long.valueOf(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        for (Competition competition : competitions) {
            LambdaUpdateWrapper<Competition> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Competition::getId, competition.getId());
            updateWrapper.set(Competition::getSignPeople, competition.getSignPeople());
            competitionService.update(updateWrapper);
/*
        //articleService.updateBatchById(articles);
*/

        }
    }
}
