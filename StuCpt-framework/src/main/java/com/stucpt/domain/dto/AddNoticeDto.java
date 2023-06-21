package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/09/0:38
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNoticeDto {
    private Long id;

    //标题
    private String title;
    //公告内容
    private String content;

    //缩略图
    private String thumbnail;
    //公告摘要
    private String summary;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;

    private String isCarousel;


    //访问量
    private Long viewCount;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

}
