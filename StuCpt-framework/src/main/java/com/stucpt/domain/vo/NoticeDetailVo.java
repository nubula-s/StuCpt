package com.stucpt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/09/0:50
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDetailVo {

    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //缩略图
    private String thumbnail;
    //文章内容
    private String content;
    //访问量
    private Long viewCount;

    private Date createTime;
}
