package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.CategoryDto;
import com.stucpt.domain.dto.CategoryStatusDto;
import com.stucpt.domain.dto.QueryCategoryDto;
import com.stucpt.domain.entity.Article;
import com.stucpt.domain.entity.Category;
import com.stucpt.domain.vo.CategoryListVo;
import com.stucpt.domain.vo.CategoryVo;
import com.stucpt.domain.vo.PageVo;
import com.stucpt.mapper.CategoryMapper;
import com.stucpt.service.ArticleService;
import com.stucpt.service.CategoryService;
import com.stucpt.utils.BeanCopyUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 16:25:01
 */
@Service("CategoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表 状态已为发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<Article>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList =  articleService.list(articleWrapper);
        //获取文章的分类id，并且去重

        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);

        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals( category.getStatus()))
                .collect(Collectors.toList());
        //分装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);



        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        //查询
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,SystemConstants.NORMAL);
        List<Category> list = list(queryWrapper);
        //封装
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list,CategoryVo.class);

        return categoryVos;
    }




    @Override
    public ResponseResult listCategoryByPage(Integer pageNum, Integer pageSize, QueryCategoryDto queryCategoryDto) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Strings.isNotEmpty(queryCategoryDto.getName()),Category::getName, queryCategoryDto.getName())
                .eq(queryCategoryDto.getStatus()!=null,Category::getStatus, queryCategoryDto.getStatus())
                .orderByDesc(Category::getId);
        Page<Category> page = new Page<>(pageNum, pageSize);
        Page<Category> categoryPage = page(page , wrapper);
        List<Category> categoryList = categoryPage.getRecords();
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(categoryList, CategoryListVo.class);
        PageVo pageVo = new PageVo(categoryListVos, categoryPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto,Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        Category category = getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category,CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto,Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategoryById(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateCategoryStatus(CategoryStatusDto categoryStatusDto) {
        Category category = getById(categoryStatusDto.getId());
        category.setStatus(categoryStatusDto.getStatus());
        updateById(category);
        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult listCategory() {

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,SystemConstants.NORMAL);
        List<Category> list = list(queryWrapper);
        //封装
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list,CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}

