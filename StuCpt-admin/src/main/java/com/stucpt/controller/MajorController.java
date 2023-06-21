package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddMajorDto;
import com.stucpt.domain.dto.CollegeDto;
import com.stucpt.domain.dto.MajorDto;
import com.stucpt.domain.dto.QueryMajorDto;
import com.stucpt.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/27/17:24
 * @Description:
 */
@RestController
@RequestMapping("/system/major")
public class MajorController {
    @Autowired
    private MajorService majorService;

    @GetMapping("/listAllMajor")
    public ResponseResult listAllMajor(){
        return majorService.listAllMajorToUser();
    }

    @GetMapping("/list")
    public ResponseResult listMajor(Integer pageNum, Integer pageSize, QueryMajorDto queryMajorDto){
        return majorService.getMajorList(pageNum,pageSize,queryMajorDto);
    }

    @PostMapping
    public  ResponseResult addMajor(@RequestBody AddMajorDto addMajorDto){

        return majorService.addMajor(addMajorDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getMajor(@PathVariable("id") Long id){

        return majorService.getMajorById(id);
    }
    @PutMapping
    public ResponseResult updateMajor(@RequestBody MajorDto majorDto){
        return majorService.updateMajor(majorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteMajor(@PathVariable("id")Long id){
        return majorService.deleteMajorById(id);
    }
}
