package com.stucpt.runner;

import com.stucpt.domain.entity.Article;
import com.stucpt.domain.entity.Competition;
import com.stucpt.mapper.ArticleMapper;
import com.stucpt.mapper.CompetitionMapper;
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
 * @Date: 2023/04/11/18:38
 * @Description:
 */
@Component
public class SignPeopleRunner implements CommandLineRunner {
    @Autowired
    private CompetitionMapper competitionMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {

        //查询公告信息  id  viewCount  作为键值对存到redis中去
        List<Competition> competitions = competitionMapper.selectList(null);
        System.out.println("competitions = " + competitions);
        Map<String, Integer> viewCountMap2 = competitions.stream()
                .collect(Collectors.toMap(competition -> competition.getId().toString(), competition -> {
                    return competition.getSignPeople();//
                }));
        //存储到redis中
        redisCache.setCacheMap("competition:signPeople",viewCountMap2);
    }

}
