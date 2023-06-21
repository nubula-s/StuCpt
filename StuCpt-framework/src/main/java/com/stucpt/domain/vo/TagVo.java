package com.stucpt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/18/0:21
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    //标签id
    private Long id;

    //标签名字
    private String name;
}
