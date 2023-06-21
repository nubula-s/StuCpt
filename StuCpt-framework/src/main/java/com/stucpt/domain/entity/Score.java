package com.stucpt.domain.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Score)表实体类
 *
 * @author makejava
 * @since 2023-04-03 14:50:08
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cpt_score")
public class Score  {

//    @TableId
    @TableId
    private Long id;

    private Long competitionId;
//    @TableId
    private Long studentId;

    
    private Long collegeId;
    //分数
    private Integer score;
    //1是优秀奖，2是三等奖，3是二等奖，4是一等奖 默认是0
    private String prize;

    private String status;

    private Long registrationId;



    //创建者
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

    //删除标志（0代表存在 1代表删除）
    private Integer delFlag;

}

