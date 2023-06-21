package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.service.CollegeService;
import com.stucpt.service.MajorService;
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
@RequestMapping("/college")
public class MajorController {

    @Autowired
    private MajorService majorService;

    @GetMapping("/getMajorList")
    public ResponseResult listAllCollege(){
        return majorService.listAllMajorToUser();
    }
}
