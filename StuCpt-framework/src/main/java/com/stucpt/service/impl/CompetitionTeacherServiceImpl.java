package com.stucpt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.domain.entity.CompetitionTeacher;
import com.stucpt.mapper.CompetitionTeacherMapper;
import com.stucpt.service.CompetitionTeacherService;
import org.springframework.stereotype.Service;

/**
 * (CompetitionTeacher)表服务实现类
 *
 * @author makejava
 * @since 2023-04-09 00:10:51
 */
@Service("competitionTeacherService")
public class CompetitionTeacherServiceImpl extends ServiceImpl<CompetitionTeacherMapper, CompetitionTeacher> implements CompetitionTeacherService {

}

