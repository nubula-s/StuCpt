package com.stucpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddArticleDto;
import com.stucpt.domain.dto.ArticleListDto;
import com.stucpt.domain.dto.ArticleStatusDto;
import com.stucpt.domain.entity.Article;
import com.stucpt.domain.vo.PageVo;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/09/11:38
 * @Description:
 */
@Service
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    /*
    * 分类文章分页功能*/
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /*
    根据查询文章详情
    * */
    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto articleDto);

    ResponseResult<PageVo> pageArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    ResponseResult getArticleById(Long id);

    ResponseResult updateArticle(AddArticleDto addArticleDto);

    ResponseResult deleteArticleById(Long id);

    ResponseResult updateArticleStatus(ArticleStatusDto articleStatusDto);

}
