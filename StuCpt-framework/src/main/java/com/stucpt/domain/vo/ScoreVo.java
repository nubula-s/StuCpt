package com.stucpt.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.stucpt.domain.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/03/20:01
 * @Description:
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ScoreVo {


    private  Long id;
    private Long competitionId;
    private Long studentId;
    private Long collegeId;
    private Long majorId;

    private String name;
    private String userName;
    private String nickName;
    private String collegeName;
    private String majorName;
    private String sex;

    //分数
    private Integer score;
    //1是优秀奖，2是三等奖，3是二等奖，4是一等奖 默认是0
    private String prize;

    //是否通过
    private String status;
//    @JSONField(format = “yyyy-MM-dd HH:mm:ss”)
    private Date createTime;

}
