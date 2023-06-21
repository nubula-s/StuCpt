package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/27/20:30
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCategoryDto {
    //分类名
    private String name;
    //状态0:正常,1禁用
    private String status;
}
