package com.stucpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddNoticeDto;
import com.stucpt.domain.dto.NoticeCarouselDto;
import com.stucpt.domain.dto.NoticeListDto;
import com.stucpt.domain.dto.NoticeStatusDto;
import com.stucpt.domain.entity.Notice;
import com.stucpt.domain.vo.CategoryVo;
import com.stucpt.domain.vo.PageVo;

import java.util.List;


/**
 * (Notice)表服务接口
 *
 * @author makejava
 * @since 2023-04-09 00:33:30
 */
public interface NoticeService extends IService<Notice> {

    ResponseResult hotNoticeList();

    ResponseResult noticeList(Integer pageNum, Integer pageSize);

    ResponseResult updateViewCount(Long id);

    ResponseResult getNoticeDetail(Long id);

    ResponseResult add(AddNoticeDto addNoticeDto);

    ResponseResult<PageVo> pageNoticeList(Integer pageNum, Integer pageSize, NoticeListDto noticeListDto);

    ResponseResult getNoticeById(Long id);

    ResponseResult updateNotice(AddNoticeDto noticeDto);

    ResponseResult deleteNoticeById(Long id);

    ResponseResult updateNoticeStatus(NoticeStatusDto noticeStatusDto);


    ResponseResult updateNoticeCarousel(NoticeCarouselDto noticeCarouselDto);


    ResponseResult listNoticeCarousel();

    ResponseResult noticeLists(Integer pageNum, Integer pageSize);

}

