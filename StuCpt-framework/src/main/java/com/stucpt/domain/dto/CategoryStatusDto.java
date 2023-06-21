package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/28/13:39
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryStatusDto {
    private Long id;
    private String status;

}
