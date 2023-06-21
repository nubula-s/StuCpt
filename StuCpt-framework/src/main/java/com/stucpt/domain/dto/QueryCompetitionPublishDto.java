package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/05/06/15:37
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCompetitionPublishDto {
    //竞赛名称
    private String name;

    private String isPublish;
}
