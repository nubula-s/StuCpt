package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.entity.Article;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import com.stucpt.service.ArticleService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/09/11:42
 * @Description:
 */
@RestController
@Api(tags = "文章",value = "文章相关的接口")
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @GetMapping("/hotArticleList")
    @ApiOperation("获取热门文章列表")
    public ResponseResult hotArticleList() {
        //查询热门文章
        ResponseResult result = articleService.hotArticleList();

        return result;

    }

    /*
     *
     * 分类文章分页功能
     * querry
     * */
    @GetMapping("/articleList")
    @ApiOperation("分页获取文章列表")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){

        return articleService.articleList(pageNum,pageSize,categoryId);

    }

    /*
    * 更新阅读数
    * */
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }


    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

}
