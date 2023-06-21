package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.domain.dto.ScoreDto;
import com.stucpt.domain.entity.Competition;
import com.stucpt.domain.entity.Score;
import com.stucpt.domain.entity.Score;
import com.stucpt.domain.entity.Student;
import com.stucpt.domain.vo.*;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.exception.SystemException;
import com.stucpt.mapper.ScoreMapper;
import com.stucpt.service.*;
import com.stucpt.utils.BeanCopyUtils;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (Score)表服务实现类
 *
 * @author makejava
 * @since 2023-04-03 14:50:09
 */
@Service("scoreService")
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {



    @Autowired
    private CollegeService collegeService;

    @Autowired
    private MajorService majorService ;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CompetitionService competitionService;



    @Override
    public ResponseResult getScoreList(Integer pageNum, Integer pageSize, QueryScoreDto queryScoreDto) {

        //1.获取查询结果 2.转化成Vo的格式
        List<ScoreVo> scoreVos = baseMapper.getScoreList(queryScoreDto.getName(),queryScoreDto.getNickName(),queryScoreDto.getStatus());

        scoreVos.stream()
                .map(scoreVo ->
                        scoreVo.setCollegeName(collegeService.getById(scoreVo.getCollegeId()).getName()))
                .collect(Collectors.toList());
        scoreVos.stream().map(scoreVo ->
                scoreVo.setMajorName(majorService.getById(scoreVo.getMajorId()).getName()))
                .collect(Collectors.toList());

        scoreVos.forEach(scoreVo -> System.out.println(scoreVo));

        System.out.println("------------------------------"+scoreVos.toString());
        Page<ScoreVo> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        PageVo pageVo = new PageVo(scoreVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addScore(AddScoreDto addScoreDto) {
        Score score = BeanCopyUtils.copyBean(addScoreDto,Score.class);
        save(score);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getScoreById(Long id) {


        ScoreByIdDto  scoreByIdDto=baseMapper.getScoreById(id);

        scoreByIdDto.setCollegeName(collegeService.getById(scoreByIdDto.getCollegeId()).getName());
        scoreByIdDto.setMajorName(majorService.getById(scoreByIdDto.getMajorId()).getName());
        return ResponseResult.okResult(scoreByIdDto);


    }

    @Override
    public ResponseResult updateScore(ScoreDto scoreDto) {
        Score score = BeanCopyUtils.copyBean(scoreDto,Score.class);
        updateById(score);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteScoreById(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getScoreListByStudentId(Long studentId) {
        LambdaQueryWrapper<Score> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Score::getStudentId,studentId);
        List<Score> list = list(queryWrapper);
        List<SignVo> signs = BeanCopyUtils.copyBeanList(list,SignVo.class);
        return ResponseResult.okResult(signs);
    }

    @Override
    public void addScores(AddScoreDto addScoreDto) {
        Score score = BeanCopyUtils.copyBean(addScoreDto,Score.class);
        save(score);
    }

    /**
     * 返给前端的成绩表啦
     * @param pageNum
     * @param pageSize
     * @param competitionId
     * @return
     */
    @Override
    public ResponseResult getScoreListToFront(Integer pageNum, Integer pageSize, Long id) {

//
//        LambdaQueryWrapper<Score> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Objects.nonNull(competitionId),Score::getCompetitionId,id);
//        queryWrapper.eq(Score::getStatus, SystemConstants.PASS);
//
//        Page<Score> page = new Page<>(pageNum,pageSize);
//        page(page,queryWrapper);
//
//        List<Score> scores = page.getRecords();
//
//        List<ScoreVo> scoreVos = BeanCopyUtils.copyBeanList(scores, ScoreVo.class);
//        scoreVos.stream()
//                .map(scoreVo ->
//                        scoreVo.setCollegeName(collegeService.getById(scoreVo.getCollegeId()).getName()))
//                .collect(Collectors.toList());
//        scoreVos.stream().map(scoreVo ->
//                scoreVo.setMajorName(majorService.getById(scoreVo.getMajorId()).getName()))
//                .collect(Collectors.toList());
//
//        scoreVos.forEach(scoreVo -> System.out.println(scoreVo));
//
//        PageVo pageVo = new PageVo(scoreVos,page.getTotal());


        List<ScoreVo> scoreVos  =  baseMapper.getScoreListToFront(id);

        Page<ScoreVo> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        PageVo pageVo = new PageVo(scoreVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 评分功能
     * @param gradeScoreDto
     * @return
     */
    @Override
    public ResponseResult gradeScore(GradeScoreDto gradeScoreDto) {

        Score score = getById(gradeScoreDto.getId());
        score.setScore(gradeScoreDto.getScore());
        score.setPrize(gradeScoreDto.getPrize());
        updateById(score);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getScoreListToGrade(Integer pageNum, Integer pageSize, QueryGradeScoreDto queryGradeScoreDto) {
        //1.获取查询结果 2.转化成Vo的格式
        List<ScoreVo> scoreVos = baseMapper.getScoreListToGrade(queryGradeScoreDto.getUserIds(),queryGradeScoreDto.getName(),queryGradeScoreDto.getNickName());

        scoreVos.stream()
                .map(scoreVo ->
                        scoreVo.setCollegeName(collegeService.getById(scoreVo.getCollegeId()).getName()))
                .collect(Collectors.toList());
        scoreVos.stream().map(scoreVo ->
                scoreVo.setMajorName(majorService.getById(scoreVo.getMajorId()).getName()))
                .collect(Collectors.toList());

        scoreVos.forEach(scoreVo -> System.out.println(scoreVo));
        Page<ScoreVo> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        PageVo pageVo = new PageVo(scoreVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getScoreToTeacher(Long id) {

        ScoreByIdDto  scoreByIdDto=baseMapper.getScoreById(id);

        scoreByIdDto.setCollegeName(collegeService.getById(scoreByIdDto.getCollegeId()).getName());
        scoreByIdDto.setMajorName(majorService.getById(scoreByIdDto.getMajorId()).getName());
        return ResponseResult.okResult(scoreByIdDto);
    }

    public void getCollegeAndMajor(List<ScoreVo> scoreVos,Long collegeId,Long majorId){

    }


}

