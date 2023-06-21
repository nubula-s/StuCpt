package com.stucpt.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.CategoryDto;
import com.stucpt.domain.dto.CategoryStatusDto;
import com.stucpt.domain.dto.QueryCategoryDto;
import com.stucpt.domain.entity.Category;
import com.stucpt.domain.vo.CategoryVo;
import com.stucpt.domain.vo.ExcelCategoryVo;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.service.CategoryService;
import com.stucpt.utils.BeanCopyUtils;
import com.stucpt.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/17/23:59
 * @Description:
 */

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){

         List<CategoryVo> list  = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @GetMapping("/list")
    public  ResponseResult listCategoryByPage(Integer pageNum, Integer pageSize, QueryCategoryDto categoryDto){
        return categoryService.listCategoryByPage(pageNum,pageSize,categoryDto);
    }


     @PostMapping
     public ResponseResult addCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
     }


     @GetMapping("/{id}")
     public ResponseResult getCategoryById(@PathVariable("id")Long id){

        return categoryService.getCategoryById(id);
     }

     @PutMapping
     public ResponseResult updateCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.updateCategory(categoryDto);
     }

     @DeleteMapping("/{id}")
     public ResponseResult deleteCategory(@PathVariable("id") Long id){
        return categoryService.deleteCategoryById(id);
     }

     @PutMapping("/changeStatus")
     public ResponseResult updateCategoryStatus(@RequestBody CategoryStatusDto categoryStatusDto){
        return categoryService.updateCategoryStatus(categoryStatusDto);
     }



    // 权限认证注解配合securityConfig的注解联动
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }




}
