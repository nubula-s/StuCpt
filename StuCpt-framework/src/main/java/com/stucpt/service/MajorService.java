package com.stucpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddMajorDto;
import com.stucpt.domain.dto.MajorDto;
import com.stucpt.domain.dto.QueryMajorDto;
import com.stucpt.domain.entity.Major;
import org.springframework.stereotype.Service;


/**
 * (Major)表服务接口
 *
 * @author makejava
 * @since 2023-03-27 16:46:31
 */

public interface MajorService extends IService<Major> {

    ResponseResult listAllMajorToUser();

    ResponseResult addMajor(AddMajorDto addMajorDto);


    ResponseResult updateMajor(MajorDto majorDto);

    ResponseResult getMajorById(Long id);

    ResponseResult deleteMajorById(Long id);

    ResponseResult getMajorList(Integer pageNum, Integer pageSize, QueryMajorDto queyMajorDto);


}

