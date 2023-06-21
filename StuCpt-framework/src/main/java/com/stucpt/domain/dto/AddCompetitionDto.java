package com.stucpt.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stucpt.validator.CheckTimeInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/25/14:16
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCompetitionDto {
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
    //比赛是否过期
    private String isSign;
    //比赛是否完成
    private String isDum;

    private String isGrade;
    //状态（0已发布，1草稿）
    private String status;
    //限制人数
    private Integer limitNumber;

    //主办方
    private  String sponsor;


    private List<Long> teacherIds;

    //报名开始时间

    //JsonFormat转化格式，将json传过来的格式进行规范再到数据库，这里也可以进行全局配置
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CheckTimeInterval(beginTime = "signTime",endTime = "signEnd")
    private Date signTime;
    //报名截至时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signEnd;
    //比赛开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CheckTimeInterval(beginTime = "startTime",endTime = "endTime")
    private Date startTime;
    //比赛结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;


    //评分时间范围
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gradeStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gradeEnd;



    private Integer delFlag;



}
