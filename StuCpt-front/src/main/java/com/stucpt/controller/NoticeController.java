package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddNoticeDto;
import com.stucpt.domain.dto.AddNoticeDto;
import com.stucpt.domain.dto.NoticeListDto;
import com.stucpt.domain.vo.CategoryVo;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.service.NoticeService;
import com.stucpt.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/09/0:35
 * @Description:
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    
    /* @GetMapping("/list")
     public List<Notice> test(){
 
         return noticeService.list();
 
     }*/
    @GetMapping("/hotNoticeList")
    public ResponseResult hotNoticeList(){
        //查询热门文章
        ResponseResult result = noticeService.hotNoticeList();

        return result;


    }

    /*
     *
     * 分类文章分页功能
     * querry
     * */
    @GetMapping("/noticeList")
    public ResponseResult noticeList(Integer pageNum,Integer pageSize){

        return noticeService.noticeList(pageNum,pageSize);

    }
    @GetMapping("/list")
    public ResponseResult noticeLists(Integer pageNum,Integer pageSize){

        return noticeService.noticeLists(pageNum,pageSize);

    }

    @GetMapping("/listNoticeCarousel")
    public ResponseResult listNoticeCarousel(){

        return noticeService.listNoticeCarousel();
    }

    /*
     * 更新阅读数
     * */
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return noticeService.updateViewCount(id);
    }


    @GetMapping("/{id}")
    public ResponseResult getNoticeDetail(@PathVariable("id") Long id){
        return noticeService.getNoticeDetail(id);
    }
}
