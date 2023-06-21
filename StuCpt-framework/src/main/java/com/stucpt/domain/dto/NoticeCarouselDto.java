package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/09/18:27
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeCarouselDto {
    private Long noticeId;
    private String isCarousel;
}
