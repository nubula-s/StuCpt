package com.stucpt.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/25/16:49
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionListVo {
    //竞赛id@TableId
    private Long id;

    //竞赛名称
    private String name;
    //竞赛内容
    private String content;

    private String thumbnail;

    //所属分类id
    private Long categoryId;
    //类型（0是个人赛，1是团队赛）
    private String type;
    //是否置顶（0否，1是）
    private String isTop;
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
    private String categoryName;

    //报名开始时间
    private Date signTime;
    //报名截至时间
    private Date signEnd;
    //比赛开始时间
    private Date startTime;
    //比赛结束时间
    private Date endTime;


    //评分时间范围
    private Date gradeStart;

    private Date gradeEnd;


    //主办方
    private  String sponsor;



}
