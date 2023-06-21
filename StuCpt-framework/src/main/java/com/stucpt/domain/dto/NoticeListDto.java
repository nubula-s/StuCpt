package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/09/0:55
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeListDto {
    //标题
    private String title;
    //文章摘要
    private String summary;

    private String status;

    private String isCarousel;
}
