package com.stucpt.domain.dto;

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
 * @Date: 2023/04/03/15:11
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMajorDto {

    //专业名称
    private String name;

    //专业负责人
    private String director;

    private Long collegeId;


}
