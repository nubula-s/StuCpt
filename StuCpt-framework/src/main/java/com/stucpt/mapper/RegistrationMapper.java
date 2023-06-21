package com.stucpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stucpt.domain.entity.Registration;
import com.stucpt.domain.entity.Student;
import com.stucpt.domain.vo.RegistrationVo;
import com.stucpt.domain.vo.ScoreVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (Registration)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-10 13:39:53
 */
public interface RegistrationMapper extends BaseMapper<Registration> {


    void setScoreStatus(@Param("registrationId") Long registrationId, @Param("isApproved") String isApproved);

    Student getStudent(@Param("studentId") Long studentId);

    List<RegistrationVo> getRegistrationList(@Param("name") String name, @Param("nickName") String nickName, @Param("isApproved") String isApproved);

    List<RegistrationVo> getRegistrationListById(@Param("userId") Long userId);

    void deleteRegistrationByUser(@Param("id") Long id);

    List<RegistrationVo> getStudentByUserId(@Param("userId") Long userId);


    void reduceSignPeople(@Param("competitionId") Long competitionId);

    void deleteScore(@Param("registrationId") Long registrationId);

}
