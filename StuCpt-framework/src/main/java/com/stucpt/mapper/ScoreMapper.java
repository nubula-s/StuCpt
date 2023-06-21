package com.stucpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stucpt.domain.dto.ScoreByIdDto;
import com.stucpt.domain.entity.Score;
import com.stucpt.domain.vo.ScoreVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (Score)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-03 14:50:08
 */
@Mapper
public interface ScoreMapper extends BaseMapper<Score> {


    List<ScoreVo> getScoreList(@Param("name") String name, @Param("nickName") String nickName, @Param("status") String status);

    ScoreByIdDto getScoreById(@Param("id") Long id);

    List<ScoreVo> getScoreListToGrade(@Param("userIds") Long userIds, @Param("name") String name, @Param("nickName") String nickName);

    List<ScoreVo> getScoreListToFront(@Param("id") Long id);

}
