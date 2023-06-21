package com.stucpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stucpt.domain.entity.Competition;
import com.stucpt.domain.entity.Student;
import com.stucpt.domain.vo.CompetitionPublishVo;
import com.stucpt.domain.vo.CompetitionToFrontVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (Competition)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-25 13:59:34
 */
@Mapper
public interface CompetitionMapper extends BaseMapper<Competition> {

    Student getStudent(@Param("userId") Long userId);

    void attendCompetition(@Param("competitionId") Long competitionId, @Param("studentId") Long studentId, @Param("statusNormal") String statusNormal);


    List<CompetitionToFrontVo> getCompetitionListByName(@Param("competitionName") String competitionName);

    void updateCompetitionPublish(@Param("competitionId") Long competitionId, @Param("isPublish") String isPublish);

    List<CompetitionPublishVo> getCompetitionPublishList(@Param("name") String name, @Param("isPublish") String isPublish);
}
