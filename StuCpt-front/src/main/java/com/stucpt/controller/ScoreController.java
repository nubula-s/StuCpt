package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.QueryScoreDto;
import com.stucpt.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/28/16:48
 * @Description:
 */
@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;


    @GetMapping("/list")
    public ResponseResult getScoreListByCompetitionId(Integer pageNum,Integer pageSize,Long competitionId){
        return null;
    }

    @GetMapping("/getList/{id}")
    public ResponseResult scoreListByStudentId(@PathVariable("id")Long studentId){
        return scoreService.getScoreListByStudentId(studentId);
    }

    @GetMapping("/scoreList")
    public ResponseResult listScore(Integer pageNum, Integer pageSize, Long id){
        return scoreService.getScoreListToFront(pageNum,pageSize,id);
    }



}
