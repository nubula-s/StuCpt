package com.stucpt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/05/05/21:20
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionToFrontVo {
    //竞赛id@TableId
    private Long id;

    //竞赛名称
    private String name;



    private Long categoryId;
    //限制人数
    private Integer limitNumber;
    //报名人数
    private Integer signPeople;

    private String isPublish;


    //主办方
    private  String sponsor;


}
