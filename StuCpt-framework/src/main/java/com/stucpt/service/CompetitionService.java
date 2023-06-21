package com.stucpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.domain.entity.Competition;
import com.stucpt.domain.vo.PageVo;


/**
 * (Competition)表服务接口
 *
 * @author makejava
 * @since 2023-03-25 13:59:34
 */

public interface CompetitionService extends IService<Competition> {

    ResponseResult hotCompetitionList();

    ResponseResult competitionListByCategoryId(Integer pageNum, Integer pageSize,Long categoryId);

    ResponseResult competitionList(Integer pageNum, Integer pageSize);

    ResponseResult updateSignPeople(Long id);

    ResponseResult getCompetitionDetail(Long id);

    ResponseResult add(AddCompetitionDto competitionDto);

    ResponseResult<PageVo> pageCompetitionList(Integer pageNum, Integer pageSize, QueryCompetitionDto queryCompetitionDto);

    ResponseResult updateCompetition(AddCompetitionDto competitionDto);

    ResponseResult getCompetitionById(Long id);

    ResponseResult deleteCompetitionById(Long id);

    ResponseResult attendCompetition(AttendCompetitionDto attendCompetitionDto);


    ResponseResult competitionListByName(Integer pageNum, Integer pageSize, String competitionName);

    ResponseResult updateCompetitionStatus(CompetitionStatusDto competitionStatusDto);

    ResponseResult updateCompetitionPublish(CompetitionPublishDto competitionPublishDto);

    ResponseResult getCompetitionPublishList(Integer pageNum, Integer pageSize, QueryCompetitionPublishDto queryCompetitionPublishDto);
}

