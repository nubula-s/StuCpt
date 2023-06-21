package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.service.CollegeService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/27/16:53
 * @Description:
 */
@RestController
@RequestMapping("/system/college")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @GetMapping("/listAllCollege")
    public ResponseResult listAllCollege(){
        return collegeService.listAllCollegeToUser();

    }

    @GetMapping("/list")
    public ResponseResult listCollege(Integer pageNum, Integer pageSize, QueryCollegeDto queryCollegeDto){
        return collegeService.getCollegeList(pageNum,pageSize,queryCollegeDto);
    }


    @PostMapping()
    public  ResponseResult addCollege(@RequestBody AddCollegeDto addCollegeDto){
        return collegeService.addCollege(addCollegeDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getCollege(@PathVariable("id") Long id){

        return collegeService.getCollegeById(id);
    }


    @PutMapping
    public ResponseResult updateCollege(@RequestBody CollegeDto collegeDto){
        return collegeService.updateCollege(collegeDto);
    }


    @DeleteMapping("{id}")
    public  ResponseResult deleteCollege(@PathVariable("id") Long id){

        return collegeService.deleteCollegeById(id);

    }




}
