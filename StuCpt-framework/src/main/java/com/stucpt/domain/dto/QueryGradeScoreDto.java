package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/08/1:21
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryGradeScoreDto {
    private Long userIds;
    private String name;
    private String nickName;
}
