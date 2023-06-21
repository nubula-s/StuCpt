package com.stucpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddCollegeDto;
import com.stucpt.domain.dto.CollegeDto;
import com.stucpt.domain.dto.QueryCollegeDto;
import com.stucpt.domain.entity.College;


/**
 * (College)表服务接口
 *
 * @author makejava
 * @since 2023-03-27 14:06:22
 */
public interface CollegeService extends IService<College> {

    ResponseResult listAllCollegeToUser();

    ResponseResult addCollege(AddCollegeDto addCollegeDto);

    ResponseResult updateCollege(CollegeDto collegeDto);

    ResponseResult getCollegeById(Long id);

    ResponseResult deleteCollegeById(Long id);


    ResponseResult getCollegeList(Integer pageNum, Integer pageSize, QueryCollegeDto queryCollegeDto);

}

