package com.stucpt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.domain.entity.ArticleTag;
import com.stucpt.mapper.ArticleTagMapper;
import com.stucpt.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-18 11:59:14
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Override
    public boolean save(ArticleTag entity) {
        return false;
    }
}

