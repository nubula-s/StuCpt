package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/10/13:49
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRegistrationDto {

    private Long competitionId;

    private Long studentId;


    private Long collegeId;

    private String isApproved;

}
