package com.stucpt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.domain.entity.CompetitionPublish;
import com.stucpt.mapper.CompetitionPublishMapper;
import com.stucpt.service.CompetitionPublishService;
import org.springframework.stereotype.Service;

/**
 * (CompetitionPublish)表服务实现类
 *
 * @author makejava
 * @since 2023-05-06 15:16:57
 */
@Service("competitionPublishService")
public class CompetitionPublishServiceImpl extends ServiceImpl<CompetitionPublishMapper, CompetitionPublish> implements CompetitionPublishService {

}

