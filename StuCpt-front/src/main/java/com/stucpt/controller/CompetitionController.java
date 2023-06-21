package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AttendCompetitionDto;
import com.stucpt.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/25/14:28
 * @Description:
 */
@RestController
@RequestMapping("/competition")
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;

   /*  @GetMapping("/list")
     public List<Competition> test(){
 
         return competitionService.list();
 
     }*/
    @GetMapping("/hotCompetitionList")
    public ResponseResult hotCompetitionList(){
        //查询热门文章
        ResponseResult result = competitionService.hotCompetitionList();

        return result;


    }

    /*
     *
     * 分类文章分页功能
     * querry
     * */
    @GetMapping("/competitionListByCategoryId")
    public ResponseResult competitionListByCategoryId(Integer pageNum,Integer pageSize,Long categoryId){

        return competitionService.competitionListByCategoryId(pageNum,pageSize,categoryId);

    }

    @GetMapping("/competitionListByName")
    public ResponseResult competitionListByName(Integer pageNum,Integer pageSize,String competitionName){

        return competitionService.competitionListByName(pageNum,pageSize,competitionName);

    }


    @GetMapping("/competitionList")
    public ResponseResult competitionList(Integer pageNum,Integer pageSize){

        return competitionService.competitionList(pageNum,pageSize);

    }

    /*
     * 更新报名人数
     * */
    @PutMapping("/updateSignPeople/{id}")
    public ResponseResult updateSignPeople(@PathVariable("id") Long id){
        return competitionService.updateSignPeople(id);
    }


    @GetMapping("/{id}")
    public ResponseResult getCompetitionDetail(@PathVariable("id") Long id){
        return competitionService.getCompetitionDetail(id);
    }


    @PostMapping
    public ResponseResult attendCompetition(@RequestBody AttendCompetitionDto attendCompetitionDto){

        return competitionService.attendCompetition(attendCompetitionDto);
    }



}
