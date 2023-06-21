package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/18/0:53
 * @Description:
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto articleDto){
        return  articleService.add(articleDto);
    }

    /*
     * 查询显示
     * */
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){

        return articleService.pageArticleList(pageNum,pageSize,articleListDto);

    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    public ResponseResult getArticle(@PathVariable("id") Long id){

        return articleService.getArticleById(id);
    }

    @PutMapping()
    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    public  ResponseResult updateArticle(@RequestBody AddArticleDto articleDto){
        return articleService.updateArticle(articleDto);
    }

    @DeleteMapping("{id}")
    public  ResponseResult deleteArticle(@PathVariable("id") Long id){

        return articleService.deleteArticleById(id);

    }

    @PutMapping("/changeStatus")
    public  ResponseResult updateArticleStatus(@RequestBody ArticleStatusDto articleStatusDto){
        return articleService.updateArticleStatus(articleStatusDto);
    }

}
