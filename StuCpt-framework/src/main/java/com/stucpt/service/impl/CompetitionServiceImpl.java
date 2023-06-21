package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.*;
import com.stucpt.domain.entity.*;
/*
import com.stucpt.domain.entity.CompetitionTag;
*/
import com.stucpt.domain.entity.Competition;
import com.stucpt.domain.vo.*;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.exception.SystemException;
import com.stucpt.mapper.CompetitionMapper;
//import com.stucpt.service.CompetitionTagService;
import com.stucpt.service.*;
import com.stucpt.utils.BeanCopyUtils;
import com.stucpt.utils.RedisCache;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (Competition)表服务实现类
 *
 * @author makejava
 * @since 2023-03-25 13:59:34
 */
@Service("competitionService")
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition> implements CompetitionService {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ScoreService scoreService;


    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private CompetitionTeacherService competitionTeacherService;

    @Autowired
    private CompetitionPublishService competitionPublishService;

    @Override
    public ResponseResult hotCompetitionList() {

        LambdaQueryWrapper<Competition> queryWrapper = new LambdaQueryWrapper<>();
        //接口需求，必须是状态正常
        queryWrapper.eq(Competition::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        //且按照浏览量排序
        queryWrapper.orderByAsc(Competition::getIsSign);
        queryWrapper.orderByAsc(Competition::getIsDum);
        queryWrapper.orderByDesc(Competition::getId);
        // 最多查询10篇
        Page<Competition> page = new Page(1, 8);
        page(page, queryWrapper);

        List<Competition> competitions = page.getRecords();

        List<HotCompetitionVo> vos = BeanCopyUtils.copyBeanList(competitions, HotCompetitionVo.class);
        for (Competition competition : competitions) {
            HotCompetitionVo vo = new HotCompetitionVo();
            BeanUtils.copyProperties(competition, vo);
            vos.add(vo);
        }

        return ResponseResult.okResult(vos);
    }

    /*

    分类文章分页功能
    * */
    @Override
    public ResponseResult competitionListByCategoryId(Integer pageNum, Integer pageSize, Long categoryId) {

        LambdaQueryWrapper<Competition> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Competition::getCategoryId, categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Competition::getStatus, SystemConstants.COMPETITION_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Competition::getIsTop);
        lambdaQueryWrapper.orderByDesc(Competition::getId);

        //分页查询
        Page<Competition> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        List<Competition> competitions = page.getRecords();


        //competitionId去查询competitionName进行设置
        competitions.stream()
                .map(competition ->
                        competition.setCategoryName(categoryService.getById(competition.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装查询结果
        List<CompetitionListVo> competitionListVos = BeanCopyUtils.copyBeanList(page.getRecords(), CompetitionListVo.class);
        PageVo pageVo = new PageVo(competitionListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult competitionList(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Competition> queryWrapper = new LambdaQueryWrapper<>();
        // 状态是正式发布的
        queryWrapper.eq(Competition::getStatus, SystemConstants.COMPETITION_STATUS_NORMAL);
        // 通过isDum验证竞赛是否过期
        queryWrapper.eq(Competition::getIsDum, SystemConstants.COMPETITION_STATUS_NORMAL);
        // 对isTop进行降序
        queryWrapper.orderByDesc(Competition::getIsTop, Competition::getStartTime);

        //分页查询
        Page<Competition> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<Competition> competitions = page.getRecords();


        //competitionId去查询competitionName进行设置
        competitions.stream()
                .map(competition ->
                        competition.setCategoryName(categoryService.getById(competition.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装查询结果
        List<CompetitionListVo> competitionListVos = BeanCopyUtils.copyBeanList(page.getRecords(), CompetitionListVo.class);
        PageVo pageVo = new PageVo(competitionListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    /*
     * id查询
     * */
    @Override
    public ResponseResult getCompetitionDetail(Long id) {

        //根据id查询文章
        Competition competition = getById(id);
        //从redis中获取viewcountid = {Long@11078} 8
        Integer signPeople = redisCache.getCacheMapValue("competition:signPeople", id.toString());
        competition.setSignPeople(signPeople.intValue());
        //转换成VO
        CompetitionDetailVo competitionDetailVo = BeanCopyUtils.copyBean(competition, CompetitionDetailVo.class);

        //根据分类id查询分类名并且可以实现跳转
        Long categoryId = competitionDetailVo.getCategoryId();
        //获取分类的对象和名字
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            competitionDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(competitionDetailVo);
    }

    @Override
    public ResponseResult updateSignPeople(Long id) {
        //更新redis中对应id比赛的报名人数
        redisCache.incrementCacheMapValue("competition:signPeople", id.toString(), 1);
        return ResponseResult.okResult();

    }


    @Override
    @Transactional  //事务回滚
    public ResponseResult add(AddCompetitionDto addcompetitionDto) {
        //添加博客
        Competition competition = BeanCopyUtils.copyBean(addcompetitionDto, Competition.class);


        //在competitionServiceImpl下调用save保存到数据库
        save(competition);

        //对competition中的teacherIds进行流处理
        List<CompetitionTeacher> competitionTeachers = addcompetitionDto.getTeacherIds().stream()
                .map(userId -> new CompetitionTeacher(competition.getId(), userId))
                .collect(Collectors.toList());
        //添加
        competitionTeacherService.saveBatch(competitionTeachers);

        CompetitionPublish competitionPublish = new CompetitionPublish();
        competitionPublish.setCompetitionId(competition.getId());
        competitionPublishService.save(competitionPublish);

        return ResponseResult.okResult();

    }

    /**
     * 后台竞赛分页+模糊查询列表
     *
     * @param pageNum
     * @param pageSize
     * @param queryCompetitionDto
     * @return
     */
    @Override
    public ResponseResult<PageVo> pageCompetitionList(Integer pageNum, Integer pageSize, QueryCompetitionDto queryCompetitionDto) {
        //1.分页查询
        LambdaQueryWrapper<Competition> queryWrapper = new LambdaQueryWrapper<>();
        //判断getname是否值
        queryWrapper.like(StringUtils.hasText(queryCompetitionDto.getName()), Competition::getName, queryCompetitionDto.getName());
        queryWrapper.eq(StringUtils.hasText(queryCompetitionDto.getStatus()), Competition::getStatus, queryCompetitionDto.getStatus());
        //queryWrapper.eq(StringUtils.hasText(competitionListDto.getIsSign()),Competition::getIsSign,competitionListDto.getIsSign());
        queryWrapper.eq(StringUtils.hasText(queryCompetitionDto.getIsDum()), Competition::getIsDum, queryCompetitionDto.getIsDum());
        queryWrapper.orderByDesc(Competition::getId);

        //分页查询
        Page<Competition> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());


        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getCompetitionById(Long id) {
        Competition competition = getById(id);
        AddCompetitionDto addCompetitionDto = BeanCopyUtils.copyBean(competition, AddCompetitionDto.class);
        LambdaQueryWrapper<CompetitionTeacher> queryWrapper = new LambdaQueryWrapper<CompetitionTeacher>();
        queryWrapper.select(CompetitionTeacher::getUserId);
        queryWrapper.eq(CompetitionTeacher::getCompetitionId, competition.getId());
        List<Long> teachers = competitionTeacherService.list(queryWrapper)
                .stream()
                .map(CompetitionTeacher::getUserId)
                .collect(Collectors.toList());
        addCompetitionDto.setTeacherIds(teachers);
        return ResponseResult.okResult(addCompetitionDto);
    }

    @Override
    public ResponseResult updateCompetition(AddCompetitionDto addCompetitionDto) {
        //1.转化
        Competition competition = BeanCopyUtils.copyBean(addCompetitionDto, Competition.class);
        //2.更新competition的内容
        updateById(competition);

        LambdaQueryWrapper<CompetitionTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompetitionTeacher::getCompetitionId, competition.getId());
        //3.移除原来的老师
        competitionTeacherService.remove(queryWrapper);
        //对addCompetitionDto中的tag进行流处理，将竞赛id和teacherId传到CompetitionTeacher中
        List<Long> teachers = addCompetitionDto.getTeacherIds();
        List<CompetitionTeacher> competitionTeachers = teachers.stream()
                .map(userId -> new CompetitionTeacher(competition.getId(), userId))
                .collect(Collectors.toList());
        //
        competitionTeacherService.saveBatch(competitionTeachers);
        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult deleteCompetitionById(Long id) {
        removeById(id);
        competitionPublishService.removeById(id);
        return ResponseResult.okResult();

    }


    /**
     * 参加比赛功能
     *
     * @param attendCompetitionDto
     * @return 获取到比赛id和参加者的id来查询相应的信息并且把status默认为0即为待通过
     */
    @Override
    public ResponseResult attendCompetition(AttendCompetitionDto attendCompetitionDto) {

        Student student = baseMapper.getStudent(attendCompetitionDto.getUserId());
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getId, student.getId());
        if (studentService.count(queryWrapper) == 0) {
            throw new SystemException(AppHttpCodeEnum.STUDENT_NOT_EXIST);
        }
        AddRegistrationDto addRegistrationDto = new AddRegistrationDto();
        addRegistrationDto.setCollegeId(student.getCollegeId());
        addRegistrationDto.setStudentId(student.getId());
        addRegistrationDto.setCompetitionId(attendCompetitionDto.getCompetitionId());
        Registration registration = BeanCopyUtils.copyBean(addRegistrationDto, Registration.class);
        registrationService.save(registration);
        Long id = attendCompetitionDto.getCompetitionId();
        redisCache.incrementCacheMapValue("competition:SignPeople", id.toString(), 1);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult competitionListByName(Integer pageNum, Integer pageSize, String competitionName) {

        List<CompetitionToFrontVo> competitionToFrontVos = baseMapper.getCompetitionListByName(competitionName);

        Page<CompetitionToFrontVo> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        PageVo pageVo = new PageVo(competitionToFrontVos, page.getTotal());
        //competitionId去查询competitionName进行设置
//        competitions.stream()
//                .map(competition ->
//                        competition.setCategoryName(categoryService.getById(competition.getCategoryId()).getName()))
//                .collect(Collectors.toList());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult updateCompetitionStatus(CompetitionStatusDto competitionStatusDto) {

        Competition competition = getById(competitionStatusDto.getCompetitionId());
        competition.setStatus(competitionStatusDto.getStatus());
        updateById(competition);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateCompetitionPublish(CompetitionPublishDto competitionPublishDto) {

            baseMapper.updateCompetitionPublish(competitionPublishDto.getCompetitionId(),competitionPublishDto.getIsPublish());
            return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCompetitionPublishList(Integer pageNum, Integer pageSize, QueryCompetitionPublishDto queryCompetitionPublishDto) {
        List<CompetitionPublishVo> competitionPublishVos =  baseMapper.getCompetitionPublishList(queryCompetitionPublishDto.getName(),queryCompetitionPublishDto.getIsPublish());

        Page<CompetitionPublishVo> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        PageVo pageVo = new PageVo(competitionPublishVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

}


