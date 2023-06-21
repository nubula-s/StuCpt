package com.stucpt.job;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.stucpt.domain.entity.Article;
import com.stucpt.domain.entity.Notice;
import com.stucpt.service.ArticleService;
import com.stucpt.service.NoticeService;
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
 * @Date: 2023/03/16/20:01
 * @Description:
 */
@Component
public class  UpdateNoticeViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private NoticeService noticeService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void updateViewCount() {
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("notice:viewCount");

        List<Notice> notices = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Notice(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        for (Notice notice : notices) {
            LambdaUpdateWrapper<Notice> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Notice::getId, notice.getId());
            updateWrapper.set(Notice::getViewCount, notice.getViewCount());
            noticeService.update(updateWrapper);
/*
        //articleService.updateBatchById(articles);
*/

        }

    }
}
