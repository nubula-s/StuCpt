package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.TagDto;
import com.stucpt.domain.dto.TagListDto;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.domain.vo.TagVo;
import com.stucpt.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/16/21:57
 * @Description:
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 标签分页功能
     * @param pageNum
     * @param pageSize
     * @param dto
     * @return
     */

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto dto){

        return tagService.pageTagList(pageNum,pageSize,dto);

    }

    /**
     * 返回所有标签
     * @return
     */
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return  ResponseResult.okResult(list);
    }


    /**
     * 标签添加
     * @param tagListDto
     * @return
     */
    @PostMapping()
    public  ResponseResult addTag(@RequestBody TagListDto tagListDto){

        return tagService.addTag(tagListDto);
    }

    /**
     * 删除
     * @param id
     * @return
     */

    @DeleteMapping("/{id}")
    public  ResponseResult deleteTag(@PathVariable("id") Long id){
        return tagService.deleteTag(id);
    }


    @GetMapping("{id}")
    public ResponseResult getTagById(@PathVariable("id")Long id){
        return tagService.getTagById(id);
    }

    @PutMapping()
    public ResponseResult updateTag(@RequestBody  TagDto tagDto){
        return  tagService.updateTag(tagDto);
    }
}
