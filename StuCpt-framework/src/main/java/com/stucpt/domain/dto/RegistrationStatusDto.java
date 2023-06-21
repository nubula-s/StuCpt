package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/10/13:51
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationStatusDto {
    private Long registrationId;
    private String isApproved;

}
