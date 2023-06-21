package com.stucpt.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.stucpt.validator.CheckTimeInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (Competition)表实体类
 *
 * @author makejava
 * @since 2023-03-25 13:59:34
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cpt_competition")
@Accessors(chain = true)
public class Competition  {
    //竞赛id@TableId
    private Long id;

    //竞赛名称
    private String name;
    //竞赛内容
    private String content;
    //所属分类id
    private Long categoryId;

    //缩略图
    private String thumbnail;

    //类型（0是个人赛，1是团队赛）
    private String type;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //限制人数
    private Integer limitNumber;
    //报名人数
    private Integer signPeople;
    //比赛是否过期
    private String isSign;
    //比赛是否完成
    private String isDum;
    //构造一个 并且提示数据库中没有该列
    @TableField(exist = false)
    private String categoryName;

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

    private String isGrade;
    //评分时间范围
    private Date gradeStart;

    private Date gradeEnd;

    //主办方
    private  String sponsor;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更新者
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;


    public Competition(Long id, Integer signPeople) {
        this.id = id;
        this.signPeople = signPeople;
    }
}

