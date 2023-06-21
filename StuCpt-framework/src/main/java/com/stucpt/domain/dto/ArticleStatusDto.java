package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/09/22:36
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleStatusDto {
    private Long articleId;
    private String status;
}
