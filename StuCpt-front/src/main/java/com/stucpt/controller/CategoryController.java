package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.vo.CategoryVo;
import com.stucpt.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/13/16:40
 * @Description:
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
       return categoryService.getCategoryList();
    }



    @GetMapping("/listCategory")
    public ResponseResult listCategory(){


        return categoryService.listCategory();
    }

}
