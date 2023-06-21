package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.domain.dto.AddNoticeDto;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.service.NoticeService;
import com.stucpt.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/09/0:35
 * @Description:
 */
@RestController
@RequestMapping("/content/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @PostMapping
    public ResponseResult add(@RequestBody AddNoticeDto addNoticeDto){
        return  noticeService.add(addNoticeDto);
    }

    /*
     * 查询显示
     * */
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, NoticeListDto noticeListDto){

        return noticeService.pageNoticeList(pageNum,pageSize,noticeListDto);

    }

    @GetMapping("/{id}")
    public ResponseResult getNotice(@PathVariable("id") Long id){

        return noticeService.getNoticeById(id);
    }

    @PutMapping()
    public  ResponseResult updateNotice(@RequestBody AddNoticeDto noticeDto){
        return noticeService.updateNotice(noticeDto);
    }

    @DeleteMapping("{id}")
    public  ResponseResult deleteNotice(@PathVariable("id") Long id){

        return noticeService.deleteNoticeById(id);

    }

    @PutMapping("/changeStatus")
    public  ResponseResult updateNoticeStatus(@RequestBody NoticeStatusDto noticeStatusDto){
        return noticeService.updateNoticeStatus(noticeStatusDto);
    }

    @PutMapping("/changeCarousel")
    public  ResponseResult updateNoticeCarousel(@RequestBody NoticeCarouselDto noticeCarouselDto){
        return noticeService.updateNoticeCarousel(noticeCarouselDto);
    }

}
