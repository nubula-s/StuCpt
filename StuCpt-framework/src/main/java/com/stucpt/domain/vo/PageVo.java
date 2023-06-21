package com.stucpt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/13/19:55
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {

    /*
    分页VO
    * */
    private List rows;
    private Long total;

}
