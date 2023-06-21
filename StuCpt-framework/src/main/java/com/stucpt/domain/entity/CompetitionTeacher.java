package com.stucpt.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (CompetitionTeacher)表实体类
 *
 * @author makejava
 * @since 2023-04-09 00:10:50
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cpt_competition_teacher")
public class CompetitionTeacher  {

    private static final long serialVersionUID = 625337492348897098L;

    private Long competitionId;
    
    private Long userId;



}

