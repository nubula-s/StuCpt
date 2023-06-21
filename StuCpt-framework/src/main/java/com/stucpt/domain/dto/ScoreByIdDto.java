package com.stucpt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/05/04/0:16
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreByIdDto {

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

    private Date createTime;

}
