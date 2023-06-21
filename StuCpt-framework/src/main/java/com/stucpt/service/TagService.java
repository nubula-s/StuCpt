package com.stucpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.TagDto;
import com.stucpt.domain.dto.TagListDto;
import com.stucpt.domain.entity.Tag;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-03-16 21:51:53
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto dto);

    List<TagVo> listAllTag();

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult deleteTag(Long id);

    ResponseResult updateTag(TagDto tagDto);

    ResponseResult getTagById(Long id);
}

