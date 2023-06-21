package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/03/15:32
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorDto {

    private Long id;

    private String name;

    private String director;

    private Long collegeId;
}
