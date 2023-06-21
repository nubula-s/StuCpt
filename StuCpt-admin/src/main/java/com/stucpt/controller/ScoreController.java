package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.service.MajorService;
import com.stucpt.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/03/14:51
 * @Description:
 */
@RestController
@RequestMapping("/contest/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;



    @GetMapping("/list")
    public ResponseResult listScore(Integer pageNum, Integer pageSize, QueryScoreDto queryScoreDto){
        return scoreService.getScoreList(pageNum,pageSize,queryScoreDto);
    }


    @GetMapping("/listToGrade")
    public ResponseResult listGradeScore(Integer pageNum, Integer pageSize, QueryGradeScoreDto queryGradeScoreDto){
        return scoreService.getScoreListToGrade(pageNum,pageSize,queryGradeScoreDto);
    }

    @PostMapping
    public  ResponseResult addScore(@RequestBody AddScoreDto addScoreDto){

        return scoreService.addScore(addScoreDto);
    }

    @GetMapping("/getScore/{id}")
    public ResponseResult getScoreToAdmin(@PathVariable("id") Long id){

        return scoreService.getScoreById(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getScoreToTeacher(@PathVariable("id") Long id){
        return scoreService.getScoreToTeacher(id);
    }


    @PutMapping
    public ResponseResult updateScore(@RequestBody ScoreDto scoreDto){
        return scoreService.updateScore(scoreDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteScore(@PathVariable("id") Long id){

        return scoreService.deleteScoreById(id);
    }

    /**
     * 评委判分
     * @param gradeScoreDto
     * @return
     */
    @PutMapping("/gradeScore")
    public ResponseResult gradeScore(@RequestBody GradeScoreDto gradeScoreDto){
        return scoreService.gradeScore(gradeScoreDto);
    }

}
