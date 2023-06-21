package com.stucpt.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/05/06/15:41
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionPublishVo {
    //竞赛id@TableId
    private Long id;
    //竞赛名称
    private String name;
    //限制人数
    private Integer limitNumber;
    //报名人数
    private Integer signPeople;
    //比赛是否过期
    private String isSign;
    //比赛是否完成
    private String isDum;
    //是否在评分时间范围内
    private String isGrade;
    private  String isPublish;

}
