package com.stucpt.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Notice)表实体类
 *
 * @author makejava
 * @since 2023-04-09 00:33:30
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cpt_notice")
public class Notice  {
    @TableId
    private Long id;

    //标题
    private String title;
    //公告内容
    private String content;
    //公告摘要
    private String summary;
    //是否置顶（0否，1是）
    private String isTop;
    //是否轮播0否 1是
    private String isCarousel;
    //缩略图
    private String thumbnail;

    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    @TableField(fill = FieldFill.INSERT)  //可以自己自动填充
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

    public Notice(Long id, long viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }
}

