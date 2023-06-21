package com.stucpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.CategoryDto;
import com.stucpt.domain.dto.CategoryStatusDto;
import com.stucpt.domain.dto.QueryCategoryDto;
import com.stucpt.domain.entity.Category;
import com.stucpt.domain.vo.CategoryVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-13 16:25:01
 */
@Service
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();


    ResponseResult listCategoryByPage(Integer pageNum, Integer pageSize, QueryCategoryDto categoryDto);

    ResponseResult addCategory(CategoryDto categoryDto);

    ResponseResult getCategoryById(Long id);

    ResponseResult updateCategory(CategoryDto categoryDto);

    ResponseResult deleteCategoryById(Long id);

    ResponseResult updateCategoryStatus(CategoryStatusDto categoryStatusDto);

    ResponseResult listCategory();
}

