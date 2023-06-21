package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddArticleDto;
import com.stucpt.domain.dto.ArticleListDto;
import com.stucpt.domain.dto.ArticleStatusDto;
import com.stucpt.domain.entity.*;
import com.stucpt.domain.vo.ArticleDetailVo;
import com.stucpt.domain.vo.ArticleListVo;
import com.stucpt.domain.vo.HotArticleVo;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.mapper.ArticleMapper;
import com.stucpt.service.ArticleService;
import com.stucpt.service.ArticleTagService;
import com.stucpt.service.CategoryService;
import com.stucpt.utils.BeanCopyUtils;
import com.stucpt.utils.RedisCache;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/09/11:39
 * @Description:
 */
@Service("ArticleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private  RedisCache redisCache;

    /**
     * 热门文章列表 通过浏览量才排序
     * @return
     */
    @Override
    public ResponseResult hotArticleList() {

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //接口需求，必须是正式文档
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //且按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多查询10篇
        Page<Article> page = new  Page(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();

        List<HotArticleVo> vos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        for (Article article : articles) {
            HotArticleVo vo = new HotArticleVo();
            BeanUtils.copyProperties(article,vo);
            vos.add(vo);
        }

        return ResponseResult.okResult(vos);
    }

    /*

    前台分类文章分页功能
    * */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {

        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        List<Article> articles = page.getRecords();


        //articleId去查询articleName进行设置
        articles.stream()
                .map(article ->
                        article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);

    }

    /*
    * id查询
    * */
    @Override
    public ResponseResult getArticleDetail(Long id) {

        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewcount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //根据分类id查询分类名并且可以实现跳转
        Long categoryId = articleDetailVo.getCategoryId();
        //获取分类的对象和名字
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    /**
     * 通过redis实现实时显示参观人数
     * @param id
     * @return
     */

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();

    }

    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 后台文章添加功能
     * @param articleDto
     * @return
     */
    @Override
    @Transactional  //事务回滚
    public ResponseResult add(AddArticleDto articleDto) {
        //添加文章
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        //在articleServiceImpl下调用save保存到数据库
        save(article);

        //对articleDto中的tag进行流处理，将文章id和tagId传到articleTag中
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //添加文章和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    /*
    *
    * 后台文章模糊查询
    *
    * */
    @Override
    public ResponseResult<PageVo> pageArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        //1.分页查询
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //判断getname是否值
        queryWrapper.like(StringUtils.hasText(articleListDto.getTitle()),Article::getTitle,articleListDto.getTitle());
        queryWrapper.like(StringUtils.hasText(articleListDto.getSummary()),Article::getSummary,articleListDto.getSummary());
        queryWrapper.eq(Strings.hasText(articleListDto.getStatus()),Article::getStatus,articleListDto.getStatus());
        queryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());


        return  ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleById(Long id) {
        //获取到文章 并转化成带tags类型
        Article article = getById(id);
        AddArticleDto addArticleDto = BeanCopyUtils.copyBean(article, AddArticleDto.class);

        //查询article-tag表中对应的tags
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<ArticleTag>();
        queryWrapper.select(ArticleTag::getTagId);
        queryWrapper.eq(ArticleTag::getArticleId, addArticleDto.getId());
        List<Long> tagList = articleTagService.list(queryWrapper)
                .stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        addArticleDto.setTags(tagList);
        return ResponseResult.okResult(addArticleDto);
    }

    @Override
    public ResponseResult updateArticle(AddArticleDto addArticleDto) {
        //1.转化
        Article article = BeanCopyUtils.copyBean(addArticleDto,Article.class);
        //2.更新article的内容
        updateById(article);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,article.getId());
        //3.移除原来的标签
        articleTagService.remove(queryWrapper);
        //对articleDto中的tag进行流处理，将文章id和tagId传到articleTag中
        List<Long> tags = addArticleDto.getTags();
        List<ArticleTag> articleTags = tags.stream()
                .map(tagId -> new ArticleTag(article.getId(),tagId))
                .collect(Collectors.toList());
        //添加博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult deleteArticleById(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateArticleStatus(ArticleStatusDto articleStatusDto) {
        Article article = getById(articleStatusDto.getArticleId());
        article.setStatus(articleStatusDto.getStatus());
        updateById(article);
        return ResponseResult.okResult();
    }
}
