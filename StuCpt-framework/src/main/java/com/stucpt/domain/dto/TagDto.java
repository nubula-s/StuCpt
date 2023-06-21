package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/18/19:51
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    private Long id;

    //标签名
    private String name;
    //备注
    private String remark;
}
