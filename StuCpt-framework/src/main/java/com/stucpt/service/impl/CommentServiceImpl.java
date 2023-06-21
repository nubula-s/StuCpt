package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.entity.Comment;
import com.stucpt.domain.vo.CommentVo;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.exception.SystemException;
import com.stucpt.mapper.CommentMapper;
import com.stucpt.service.CommentService;
import com.stucpt.service.UserService;
import com.stucpt.utils.BeanCopyUtils;
import com.stucpt.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 22:55:58
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {


        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(Comment::getArticleId,articleId);
        //根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ROOT_ID);

        //分页查询
        Page<Comment> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
//        for (CommentVo commentVo : commentVoList) {
//            //查询对应的子评论
//            List<CommentVo> children = getChildren(commentVo.getId());
//            //赋值
//            commentVo.setChildren(children);
//        }
        List<CommentVo> commentVoLists = commentVoList.stream()
                .peek(commentVo -> commentVo.setChildren(getChildren(commentVo.getId())))
                .collect(Collectors.toList());

        PageVo commentVoPageVo = new PageVo(commentVoLists,page.getTotal());

        return ResponseResult.okResult(commentVoPageVo);

    }

    @Override
    public ResponseResult addComment(Comment comment) {
        comment.setCreateBy(SecurityUtils.getUserId());
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }


    /*
    *
    * 获取子评论
    * */
    private  List<CommentVo> getChildren(Long id){

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);

        List<Comment> comments = list(queryWrapper);

         List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    /*
    *
    * 将comment转化成commentVo的格式并且通过toCommentUserId获取用户的昵称并赋值
    * */
    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合
        for (CommentVo commentVo : commentVos) {
            //通过creatyBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            String avatar = userService.getById(commentVo.getCreateBy()).getAvatar();
            commentVo.setUsername(nickName);
            commentVo.setAvatar(avatar);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if (commentVo.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }
}


