package com.stucpt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/09/0:43
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotNoticeVo {

    private Long id;
    //标题
    private String title;

    //访问量
    private Long viewCount;
}
