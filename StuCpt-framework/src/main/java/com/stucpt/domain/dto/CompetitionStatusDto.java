package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/09/18:22
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionStatusDto {
    private Long competitionId;
    private String status;
}
