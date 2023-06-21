package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/28/14:53
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCompetitionDto {
    //竞赛名称
    private String name;
    //状态（0已发布，1草稿）
    private String isDum;

    private String status;

}
