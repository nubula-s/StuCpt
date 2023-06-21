package com.stucpt.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (CompetitionPublish)表实体类
 *
 * @author makejava
 * @since 2023-05-06 15:16:55
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cpt_competition_publish")
public class CompetitionPublish  {
    //竞赛表id@TableId
    private Long competitionId;

    //是否允许发布
    private String isPublish;



}

