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
 * @Date: 2023/03/25/14:22
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotCompetitionVo {
    //竞赛id@TableId
    private Long id;

    //竞赛名称
    private String name;

    //报名人数
    private Integer signPeople;
    //比赛是否过期
    private String isSign;
    //比赛是否完成
    private String isDum;

    //报名开始时间
    private Date signTime;
    //报名截至时间
    private Date signEnd;
    //比赛开始时间
    private Date startTime;
    //比赛结束时间
    private Date endTime;


}
