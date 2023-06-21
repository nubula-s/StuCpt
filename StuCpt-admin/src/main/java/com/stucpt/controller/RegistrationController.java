package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.service.RegistrationService;
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
@RequestMapping("/contest/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;




    @GetMapping("/list")
    public ResponseResult listRegistration(Integer pageNum, Integer pageSize, QueryRegistrationDto queryRegistrationDto){
        return registrationService.getRegistrationList(pageNum,pageSize,queryRegistrationDto);
    }


//    @GetMapping("/listToGrade")
//    public ResponseResult listGradeRegistration(Integer pageNum, Integer pageSize, QueryGradeRegistrationDto queryGradeRegistrationDto){
//        return registrationService.getRegistrationListToGrade(pageNum,pageSize,queryGradeRegistrationDto);
//    }

    @PostMapping
    public  ResponseResult addRegistration(@RequestBody AddRegistrationDto addRegistrationDto){

        return registrationService.addRegistration(addRegistrationDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getRegistration(@PathVariable("id") Long id){

        return registrationService.getRegistrationById(id);
    }


//    @PutMapping
//    public ResponseResult updateRegistration(@RequestBody RegistrationDto registrationDto){
//        return registrationService.updateRegistration(registrationDto);
//    }

    @PutMapping("/changeStatus")
    public  ResponseResult updateRegistrationStatus(@RequestBody RegistrationStatusDto registrationStatusDto){
        return registrationService.updateRegistrationStatus(registrationStatusDto);
    }


    @DeleteMapping("/{id}")
    public ResponseResult deleteRegistration(@PathVariable("id")Long id){

        return registrationService.deleteRegistrationById(id);
    }



}
