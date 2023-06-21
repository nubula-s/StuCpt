package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.domain.entity.College;
import com.stucpt.domain.entity.Competition;
import com.stucpt.domain.entity.Major;
import com.stucpt.domain.vo.CollegeVo;
import com.stucpt.domain.vo.MajorCollegeVo;
import com.stucpt.domain.vo.MajorVo;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.exception.SystemException;
import com.stucpt.mapper.MajorMapper;
import com.stucpt.service.CollegeService;
import com.stucpt.service.MajorService;
import com.stucpt.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (Major)表服务实现类
 *
 * @author makejava
 * @since 2023-03-27 16:46:32
 */
@Service("majorService")
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {

    @Autowired
    private CollegeService collegeService;


    @Override
    public ResponseResult listAllMajorToUser() {
            List<Major> majors = list();
            List<MajorVo> majorVos = BeanCopyUtils.copyBeanList(majors, MajorVo.class);
            return ResponseResult.okResult(majorVos);
        }



    @Override
    public ResponseResult getMajorList(Integer pageNum, Integer pageSize, QueryMajorDto queryMajorDto) {
        LambdaQueryWrapper<Major> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.hasText(queryMajorDto.getName()),Major::getName, queryMajorDto.getName());
        // 对isTop进行降序
        lambdaQueryWrapper.orderByAsc(Major::getId);

        Page<Major> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        List<Major> majors = page.getRecords();

        List<MajorCollegeVo> majorCollegeVos = BeanCopyUtils.copyBeanList(majors, MajorCollegeVo.class);
        //competitionId去查询competitionName进行设置
        majorCollegeVos.stream()
                .map(majorCollegeVo ->
                        majorCollegeVo.setCollegeName(collegeService.getById(majorCollegeVo.getCollegeId()).getName()))
                .collect(Collectors.toList());

        PageVo pageVo = new PageVo(majorCollegeVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addMajor(AddMajorDto addMajorDto) {
            if (MajorNameExit(addMajorDto.getName()))
                throw new SystemException(AppHttpCodeEnum.MAJOR_NAME_EXIST);
        Major major = BeanCopyUtils.copyBean(addMajorDto,Major.class);
            save(major);
            return ResponseResult.okResult();
        }

    @Override
    public ResponseResult getMajorById(Long id) {
        Major major = getById(id);
        MajorDto majorDto = BeanCopyUtils.copyBean(major,MajorDto.class);
        return ResponseResult.okResult(majorDto);
    }

    @Override
    public ResponseResult deleteMajorById(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }



    @Override
    public ResponseResult updateMajor(MajorDto majorDto) {
        Major major = BeanCopyUtils.copyBean(majorDto,Major.class);
        updateById(major);
        return ResponseResult.okResult();
    }



    private boolean MajorNameExit(String name) {
            LambdaQueryWrapper<Major> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Major::getName,name);

            return count(queryWrapper)>0;
        }
}

