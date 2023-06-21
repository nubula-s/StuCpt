package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/08/1:09
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeScoreDto {
    private  Long id;
    private  String name;
    private  String userName;
    private  String nickName;
    private  Integer score;
    private String prize;
}
