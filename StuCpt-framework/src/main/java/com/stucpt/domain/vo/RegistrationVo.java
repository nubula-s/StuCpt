package com.stucpt.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/04/10/19:24
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)   //实现链式编程注解
public class RegistrationVo {
    @TableId
    private Long id;


    private Long competitionId;

    private Long studentId;
    private Long collegeId;

    //审核状态
    private String isApproved;

    private Long majorId;

    private String name;
    private String userName;
    private String nickName;
    private String collegeName;
    private String majorName;
    private String sex;
    private String isDum;

    private Date createTime;


}
