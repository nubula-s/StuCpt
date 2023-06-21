package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddCollegeDto;
import com.stucpt.domain.dto.CollegeDto;
import com.stucpt.domain.dto.MajorDto;
import com.stucpt.domain.dto.QueryCollegeDto;
import com.stucpt.domain.entity.*;
import com.stucpt.domain.vo.CategoryVo;
import com.stucpt.domain.vo.CollegeVo;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.exception.SystemException;
import com.stucpt.mapper.CollegeMapper;
import com.stucpt.service.CollegeService;
import com.stucpt.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * (College)表服务实现类
 *
 * @author makejava
 * @since 2023-03-27 14:06:22
 */
@Service("collegeService")
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {

    @Override
    public ResponseResult listAllCollegeToUser() {
        List<College> collegeList = list();
        List<CollegeVo> collegeVos = BeanCopyUtils.copyBeanList(collegeList, CollegeVo.class);
        return ResponseResult.okResult(collegeVos);
    }



    @Override
    public ResponseResult getCollegeList(Integer pageNum, Integer pageSize, QueryCollegeDto queryCollegeDto) {
        LambdaQueryWrapper<College> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.hasText(queryCollegeDto.getName()),College::getName, queryCollegeDto.getName());
        // 对isTop进行降序
        lambdaQueryWrapper.orderByAsc(College::getId);

        Page<College> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        page(page, lambdaQueryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());


        return ResponseResult.okResult(pageVo);
    }




    @Override
    public ResponseResult addCollege(AddCollegeDto addCollegeDto) {

        if (CollegeNameExit(addCollegeDto.getName()))
            throw new SystemException(AppHttpCodeEnum.COLLEGE_NAME_EXIST);
        College college = BeanCopyUtils.copyBean(addCollegeDto,College.class);
        save(college);
        return ResponseResult.okResult();
    }




    @Override
    public ResponseResult getCollegeById(Long id) {
        College college = getById(id);
        CollegeDto collegeDto = BeanCopyUtils.copyBean(college,CollegeDto.class);
        return ResponseResult.okResult(collegeDto);
    }

    @Override
    public ResponseResult deleteCollegeById(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }



    @Override
    public ResponseResult updateCollege(CollegeDto collegeDto) {
        College college = BeanCopyUtils.copyBean(collegeDto,College.class);
        updateById(college);
        return ResponseResult.okResult();
    }



    private boolean CollegeNameExit(String name) {
        LambdaQueryWrapper<College> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(College::getName,name);

        return count(queryWrapper)>0;
    }



}

