package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/05/06/15:12
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionPublishDto {
    private Long competitionId;
    private String isPublish;
}
