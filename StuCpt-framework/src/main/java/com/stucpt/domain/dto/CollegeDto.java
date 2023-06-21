package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/03/15:29
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollegeDto {
    //学院id
    private Long id;
    //学院名称
    private String name;
}
