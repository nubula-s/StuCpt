package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.entity.Comment;
import com.stucpt.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/14/23:21
 * @Description:
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(articleId,pageNum,pageSize);
    }

    @PostMapping
    public  ResponseResult addComment(@RequestBody Comment comment){
        return  commentService.addComment(comment);

    }
}
