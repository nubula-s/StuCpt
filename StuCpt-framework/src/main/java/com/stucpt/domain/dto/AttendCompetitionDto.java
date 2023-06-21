package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/03/19:42
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendCompetitionDto {

    private Long competitionId;
    private Long userId;

}
