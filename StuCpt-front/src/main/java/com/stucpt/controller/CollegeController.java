package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.service.CollegeService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/07/20:19
 * @Description:
 */
@RestController
@RequestMapping("/major")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @GetMapping("/getCollegeList")
    public ResponseResult listAllCollege(){
        return collegeService.listAllCollegeToUser();
    }
}
