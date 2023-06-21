package com.stucpt.runner;

import com.stucpt.domain.entity.Article;
import com.stucpt.domain.entity.Notice;
import com.stucpt.mapper.ArticleMapper;
import com.stucpt.mapper.NoticeMapper;
import com.stucpt.service.NoticeService;
import com.stucpt.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/11/12:21
 * @Description:
 */
@Component
public class ViewCountRunnerNotice implements CommandLineRunner {
    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {

        //查询公告信息  id  viewCount
        List<Notice> notices = noticeMapper.selectList(null);

        Map<String, Integer> viewCountMap1 = notices.stream()
                .collect(Collectors.toMap(notice -> notice.getId().toString(), notice -> {
                    return notice.getViewCount().intValue();//
                }));
        //存储到redis中
        redisCache.setCacheMap("notice:viewCount",viewCountMap1);
    }
}
