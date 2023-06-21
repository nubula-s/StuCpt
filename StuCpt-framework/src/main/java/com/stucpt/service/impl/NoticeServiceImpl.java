package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.domain.dto.AddNoticeDto;
import com.stucpt.domain.entity.*;
import com.stucpt.domain.entity.Notice;
import com.stucpt.domain.entity.Notice;
import com.stucpt.domain.vo.NoticeDetailVo;
import com.stucpt.domain.vo.NoticeListVo;
import com.stucpt.domain.vo.HotNoticeVo;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.mapper.NoticeMapper;
import com.stucpt.service.NoticeService;
import com.stucpt.utils.BeanCopyUtils;
import com.stucpt.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (Notice)表服务实现类
 *
 * @author makejava
 * @since 2023-04-09 00:33:31
 */
@Service("noticeService")
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {




    @Autowired
    private RedisCache redisCache;


    @Override
    public ResponseResult hotNoticeList() {
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        //接口需求，必须是正式文档
        queryWrapper.eq(Notice::getStatus, SystemConstants.STATUS_NORMAL);
        //且按照浏览量排序
        queryWrapper.orderByDesc(Notice::getViewCount);
        // 最多查询10篇
        Page<Notice> page = new  Page(1,10);
        page(page,queryWrapper);

        List<Notice> notices = page.getRecords();

        List<HotNoticeVo> vos = BeanCopyUtils.copyBeanList(notices, HotNoticeVo.class);
        for (Notice notice : notices) {
            HotNoticeVo vo = new HotNoticeVo();
            BeanUtils.copyProperties(notice,vo);
            vos.add(vo);
        }

        return ResponseResult.okResult(vos);
    }

    @Override
    public ResponseResult noticeList(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Notice> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Notice::getStatus,SystemConstants.STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Notice::getIsTop);

        //分页查询
        Page<Notice> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<Notice> notices = page.getRecords();


        //封装查询结果
        List<NoticeListVo> noticeListVos = BeanCopyUtils.copyBeanList(page.getRecords(), NoticeListVo.class);
        PageVo pageVo = new PageVo(noticeListVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("notice:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getNoticeDetail(Long id) {
        //根据id查询新闻公告
        Notice notice = getById(id);
        //从redis中获取viewcount
        Integer viewCount = redisCache.getCacheMapValue("notice:viewCount", id.toString());
        notice.setViewCount(viewCount.longValue());
        //转换成VO
        NoticeDetailVo noticeDetailVo = BeanCopyUtils.copyBean(notice, NoticeDetailVo.class);

        //封装响应返回
        return ResponseResult.okResult(noticeDetailVo);
    }

    @Override
    @Transactional  //事务回滚
    public ResponseResult add(AddNoticeDto addNoticeDto) {
        //添加博客
        Notice notice = BeanCopyUtils.copyBean(addNoticeDto,Notice.class);
        //在noticeServiceImpl下调用save保存到数据库
        save(notice);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<PageVo> pageNoticeList(Integer pageNum, Integer pageSize, NoticeListDto noticeListDto) {
        //1.分页查询
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        //判断getname是否值
        queryWrapper.like(StringUtils.hasText(noticeListDto.getTitle()),Notice::getTitle,noticeListDto.getTitle());
        queryWrapper.like(StringUtils.hasText(noticeListDto.getSummary()),Notice::getSummary,noticeListDto.getSummary());
        queryWrapper.eq(StringUtils.hasText(noticeListDto.getStatus()),Notice::getStatus,noticeListDto.getStatus());
        queryWrapper.eq(StringUtils.hasText(noticeListDto.getIsCarousel()),Notice::getIsCarousel,noticeListDto.getIsCarousel());
        queryWrapper.orderByDesc(Notice::getIsTop);

        //分页查询
        Page<Notice> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());


        return  ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getNoticeById(Long id) {
        //获取到新闻公告 并转化成带s类型
        Notice notice = getById(id);
        AddNoticeDto addNoticeDto = BeanCopyUtils.copyBean(notice,AddNoticeDto.class);
        
        return ResponseResult.okResult(addNoticeDto);
    }

    @Override
    public ResponseResult updateNotice(AddNoticeDto addNoticeDto) {
        //1.转化
        Notice notice = BeanCopyUtils.copyBean(addNoticeDto,Notice.class);
        //2.更新notice的内容
        updateById(notice);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteNoticeById(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateNoticeStatus(NoticeStatusDto noticeStatusDto) {

        Notice notice = getById(noticeStatusDto.getNoticeId());
        notice.setStatus(noticeStatusDto.getStatus());
        updateById(notice);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateNoticeCarousel(NoticeCarouselDto noticeCarouselDto) {
        Notice notice = getById(noticeCarouselDto.getNoticeId());
        notice.setStatus(noticeCarouselDto.getIsCarousel());
        updateById(notice);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listNoticeCarousel() {
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notice::getIsCarousel,SystemConstants.IS_CAROUSEL);
        queryWrapper.orderByAsc(Notice::getId);

        List<Notice> list = list(queryWrapper);
        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult noticeLists(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Notice> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Notice::getStatus,SystemConstants.STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Notice::getId);

        //分页查询
        Page<Notice> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<Notice> notices = page.getRecords();


        //封装查询结果
        List<NoticeListVo> noticeListVos = BeanCopyUtils.copyBeanList(page.getRecords(), NoticeListVo.class);
        PageVo pageVo = new PageVo(noticeListVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }
}

