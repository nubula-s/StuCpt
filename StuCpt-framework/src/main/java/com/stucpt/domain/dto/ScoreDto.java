package com.stucpt.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/03/19:20
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cpt_score")
public class ScoreDto {

  private Long id;
    //分数
    private Integer score;
    //1是优秀奖，2是三等奖，3是二等奖，4是一等奖 默认是0
    private String prize;
    //创建者


}
