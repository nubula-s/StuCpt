package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/10/13:48
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryRegistrationDto {


    private String name;

    private String nickName;
    //审核状态
    private String isApproved;
}

