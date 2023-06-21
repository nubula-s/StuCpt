package com.stucpt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/09/0:47
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeListVo {

    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //访问量
    private Long viewCount;
    //缩略图
    private String thumbnail;

    private Date createTime;


}
