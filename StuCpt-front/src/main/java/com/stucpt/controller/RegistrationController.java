package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddArticleDto;
import com.stucpt.domain.dto.AddRegistrationDto;
import com.stucpt.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/10/13:42
 * @Description:
 */
@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;


    @GetMapping("/list")
    public ResponseResult registrationList(Integer pageNum, Integer pageSize, Long categoryId){
        return null;
    }

    @GetMapping("/getList")
    public ResponseResult getRegistrationListByUserId(Integer pageNum, Integer pageSize,Long userId){
        return registrationService.getRegistrationListByUserId(pageNum,pageSize,userId);
    }

    @GetMapping("/{id}")
    public ResponseResult getRegistrationListById(@PathVariable("id") Long id){
        return registrationService.getRegistrationListById(id);
    }



    @PostMapping
    public ResponseResult add(@RequestBody AddRegistrationDto addRegistrationDto){
        return  registrationService.add(addRegistrationDto);
    }

    @DeleteMapping("/{id}")
    public  ResponseResult deleteRegistration(@PathVariable("id")Long id){
        return registrationService.deleteRegistrationByUser(id);
    }




}
