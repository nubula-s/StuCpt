package com.stucpt.mapper;
import com.stucpt.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/09/11:30
 * @Description:
 */
@Mapper
public interface ArticleMapper  extends BaseMapper<Article>{

}
