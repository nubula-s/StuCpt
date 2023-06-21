package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/25/14:33
 * @Description:
 */
@RestController
@RequestMapping("/content/competition")
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;



    @PostMapping()
    public ResponseResult add(@RequestBody AddCompetitionDto competitionDto){
        return  competitionService.add(competitionDto);
    }


     /** 查询显示
     * */
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, QueryCompetitionDto queryCompetitionDto){

        return competitionService.pageCompetitionList(pageNum,pageSize,queryCompetitionDto);

    }

    @GetMapping("/publish/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, QueryCompetitionPublishDto queryCompetitionPublishDto){

        return competitionService.getCompetitionPublishList(pageNum,pageSize,queryCompetitionPublishDto);

    }

    @GetMapping("/{id}")
    public ResponseResult getCompetition(@PathVariable("id") Long id){

        return competitionService.getCompetitionById(id);
    }

    @PutMapping()
    public  ResponseResult updateCompetition(@RequestBody AddCompetitionDto competitionDto){
        return competitionService.updateCompetition(competitionDto);
    }

    @DeleteMapping("{id}")
    public  ResponseResult deleteCompetition(@PathVariable("id") Long id){

        return competitionService.deleteCompetitionById(id);
    }


    @PutMapping("/changeStatus")
    public  ResponseResult updateCompetitionStatus(@RequestBody CompetitionStatusDto competitionStatusDto){
        return competitionService.updateCompetitionStatus(competitionStatusDto);
    }

    @PutMapping("/changePublish")
    public ResponseResult updateCompetitionPublish(@RequestBody CompetitionPublishDto competitionPublishDto) {

        return competitionService.updateCompetitionPublish(competitionPublishDto);
    }



}
