package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/18/21:19
 * @Description:
 * 查询页面的查询内容返回给后台封装成Dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDto {

    //标题
    private String title;
    //文章摘要
    private String summary;

    private String status;
}
