package com.stucpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.domain.entity.Score;


/**
 * (Score)表服务接口
 *
 * @author makejava
 * @since 2023-04-03 14:50:09
 */
public interface ScoreService extends IService<Score> {

    ResponseResult getScoreList(Integer pageNum, Integer pageSize, QueryScoreDto queryScoreDto);

    ResponseResult addScore(AddScoreDto addScoreDto);

    ResponseResult getScoreById(Long id);

    ResponseResult updateScore(ScoreDto scoreDto);

    ResponseResult deleteScoreById(Long id);

    ResponseResult getScoreListByStudentId(Long studentId);

    void addScores(AddScoreDto addScoreDto);

    ResponseResult getScoreListToFront(Integer pageNum, Integer pageSize, Long id);

    ResponseResult gradeScore(GradeScoreDto gradeScoreDto);

    ResponseResult getScoreListToGrade(Integer pageNum, Integer pageSize, QueryGradeScoreDto queryGradeScoreDto);

    ResponseResult getScoreToTeacher(Long id);

}

