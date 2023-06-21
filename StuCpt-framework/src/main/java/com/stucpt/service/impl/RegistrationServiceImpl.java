package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddRegistrationDto;
import com.stucpt.domain.dto.AddScoreDto;
import com.stucpt.domain.dto.QueryRegistrationDto;
import com.stucpt.domain.dto.RegistrationStatusDto;
import com.stucpt.domain.entity.*;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.domain.vo.RegistrationVo;
import com.stucpt.domain.vo.ScoreVo;
import com.stucpt.mapper.RegistrationMapper;
import com.stucpt.service.*;
import com.stucpt.utils.BeanCopyUtils;
import com.stucpt.utils.RedisCache;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (Registration)表服务实现类
 *
 * @author makejava
 * @since 2023-04-10 13:39:53
 */
@Service("registrationService")
public class RegistrationServiceImpl extends ServiceImpl<RegistrationMapper, Registration> implements RegistrationService {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult add(AddRegistrationDto addRegistrationDto) {
//        Student student = baseMapper.getStudent(attendCompetitionDto.getUserId());

        return null;
    }

    @Override
    public ResponseResult updateRegistrationStatus(RegistrationStatusDto registrationStatusDto) {

        //根据ID查询到Registration的实体类
        //然后查询到score表里有没有创建过，通过count判断是否生成过score表，如果没有就生成一个，有就不生成
        Registration registration = getById(registrationStatusDto.getRegistrationId());
        Long id  = registration.getCompetitionId();
        LambdaQueryWrapper<Score> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Score::getRegistrationId,registrationStatusDto.getRegistrationId());
        int count = scoreService.count(queryWrapper);
        scoreService.list(queryWrapper);
        Student student = baseMapper.getStudent(registration.getStudentId());
        registration.setIsApproved(registrationStatusDto.getIsApproved());
        updateById(registration);
        System.out.println(registrationStatusDto.getIsApproved() == SystemConstants.IS_APPROVED);
        //验证要修改的状态
        if(registrationStatusDto.getIsApproved().equals(SystemConstants.IS_APPROVED)){
            if(count > 0){
                //更新score表的状态
                baseMapper.setScoreStatus(registrationStatusDto.getRegistrationId(),SystemConstants.IS_APPROVED);
            }else {

        AddScoreDto addScoreDto = new AddScoreDto();
        addScoreDto.setCollegeId(student.getCollegeId());
        addScoreDto.setStudentId(student.getId());
        addScoreDto.setCompetitionId(registration.getCompetitionId());
        addScoreDto.setRegistrationId(registration.getId());
        addScoreDto.setStatus(registrationStatusDto.getIsApproved());
        Score score = BeanCopyUtils.copyBean(addScoreDto, Score.class);
        scoreService.save(score);

            }

        }else {
            if (count > 0) {  //判断之前是否批准过，批过则有 得删除

                baseMapper.deleteScore(registrationStatusDto.getRegistrationId());
            }
            redisCache.incrementCacheMapValue("competition:signPeople",id.toString(),-1);

        }

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRegistrationList(Integer pageNum, Integer pageSize, QueryRegistrationDto queryRegistrationDto) {

//        List<ScoreVo> scoreVos = baseMapper.getRegistrationList(queryRegistrationDto.getCompetitionId(),queryScoreDto.getNickName(),queryScoreDto.getStatus());

       List<RegistrationVo> registrationVos = baseMapper.getRegistrationList(queryRegistrationDto.getName(),queryRegistrationDto.getNickName(),queryRegistrationDto.getIsApproved());


        registrationVos.stream()
                .map(registrationVo ->
                        registrationVo.setCollegeName(collegeService.getById(registrationVo.getCollegeId()).getName()))
                .collect(Collectors.toList());
        registrationVos.stream().map(registrationVo ->
                registrationVo.setMajorName(majorService.getById(registrationVo.getMajorId()).getName()))
                .collect(Collectors.toList());

        Page<RegistrationVo> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        PageVo pageVo = new PageVo(registrationVos,page.getTotal());


        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getRegistrationListByUserId(Integer pageNum, Integer pageSize,Long usedId) {

        List<RegistrationVo> registrationVos = baseMapper.getRegistrationListById(usedId);

        registrationVos.stream()
                .map(registrationVo ->
                        registrationVo.setCollegeName(collegeService.getById(registrationVo.getCollegeId()).getName()))
                .collect(Collectors.toList());
        registrationVos.stream().map(registrationVo ->
                registrationVo.setMajorName(majorService.getById(registrationVo.getMajorId()).getName()))
                .collect(Collectors.toList());

        Page<RegistrationVo> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        PageVo pageVo = new PageVo(registrationVos,page.getTotal());


        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addRegistration(AddRegistrationDto addRegistrationDto) {
        return null;
    }

    @Override
    public ResponseResult getRegistrationById(Long id) {
        Registration registration = getById(id);
        RegistrationVo registrationVo = BeanCopyUtils.copyBean(registration, RegistrationVo.class);
        registrationVo.setName(competitionService.getById(registrationVo.getCompetitionId()).getName());
        registrationVo.setNickName(studentService.getById(registrationVo.getStudentId()).getNickName());
        return ResponseResult.okResult(registrationVo);
    }

    @Override
    public ResponseResult deleteRegistrationById(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRegistrationByUser(Long id) {
//        Registration registration = getById(id);
//        Long competitionId = registration.getCompetitionId();
        baseMapper.deleteRegistrationByUser(id);
        redisCache.incrementCacheMapValue("competition:signPeople",id.toString(),-1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRegistrationListById(Long userId) {
       List<RegistrationVo> registrationVos = baseMapper.getStudentByUserId(userId);
        return ResponseResult.okResult(registrationVos);
    }

}

