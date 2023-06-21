package com.stucpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.AddRegistrationDto;
import com.stucpt.domain.dto.QueryRegistrationDto;
import com.stucpt.domain.dto.RegistrationStatusDto;
import com.stucpt.domain.entity.Registration;


/**
 * (Registration)表服务接口
 *
 * @author makejava
 * @since 2023-04-10 13:39:53
 */
public interface RegistrationService extends IService<Registration> {

    ResponseResult add(AddRegistrationDto addRegistrationDto);

    ResponseResult updateRegistrationStatus(RegistrationStatusDto registrationStatusDto);

    ResponseResult getRegistrationList(Integer pageNum, Integer pageSize, QueryRegistrationDto queryRegistrationDto);

    ResponseResult getRegistrationListByUserId(Integer pageNum, Integer pageSize,Long userId);

    ResponseResult addRegistration(AddRegistrationDto addRegistrationDto);

    ResponseResult getRegistrationById(Long id);

    ResponseResult deleteRegistrationById(Long id);

    ResponseResult deleteRegistrationByUser(Long id);


    ResponseResult getRegistrationListById(Long userId);

}

