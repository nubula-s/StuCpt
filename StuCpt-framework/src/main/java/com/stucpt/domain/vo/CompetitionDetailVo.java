package com.stucpt.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/25/17:01
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionDetailVo {
    //竞赛id@TableId
    private Long id;

    //竞赛名称
    private String name;
    //竞赛内容
    private String content;
    //所属分类id
    private Long categoryId;
    //限制人数
    private Integer limitNumber;
    //报名人数
    private Integer signPeople;
    //比赛是否过期
    private String isSign;
    //比赛是否完成
    private String isDum;

    private String isGrade;
    private String categoryName;

    private Date createTime;

    //是否允许评论 1是，0否
    private String isComment;
    //删除标志（0代表未删除，1代表已删除）
    private String delFlag;


}
