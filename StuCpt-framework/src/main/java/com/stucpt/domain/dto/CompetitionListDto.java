package com.stucpt.domain.dto;

import com.stucpt.validator.CheckTimeInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/25/23:08
 * @Description:
 * 查询页面的查询内容返回给后台封装成Dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionListDto {

    //竞赛名称
    private String name;
    //竞赛内容
    private String content;
    //所属分类id
    private Integer categoryId;
    //类型（0是个人赛，1是团队赛）
    private String type;

    //状态（0已发布，1草稿）
    private String status;
    //限制人数
    private Integer limitNumber;
    //报名人数
    private Integer signPeople;
    //是否能报名
    private String isSign;
    //比赛是否已经结束
    private String isDum;
    //是否在评分时间范围内
    private String isGrade;

    //报名开始时间
    @CheckTimeInterval(beginTime = "signTime",endTime = "signEnd")
    private Date signTime;
    //报名截至时间
    private Date signEnd;
    //比赛开始时间
    @CheckTimeInterval(beginTime = "startTime",endTime = "endTime")
    private Date startTime;
    //比赛结束时间
    private Date endTime;
    //评分时间范围
    private Date gradeStart;

    private Date gradeEnd;


}
