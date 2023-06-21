package com.stucpt.controller;

import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.MenuDto;
import com.stucpt.domain.entity.Menu;
import com.stucpt.service.MenuService;
import com.stucpt.utils.BeanCopyUtils;
import com.stucpt.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/18/23:33
 * @Description:
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult getMenuList(String status,String menuName){
        return  menuService.getMenuList(status,menuName);
    }

    @PostMapping
    public  ResponseResult addMenu(@RequestBody MenuDto menuDto){
        Menu menu =  BeanCopyUtils.copyBean(menuDto,Menu.class);
        return  menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenuById(@PathVariable("id")Long id){
        return menuService.getMenuById(id);
    }

    @PutMapping
    public  ResponseResult updateMenu(@RequestBody MenuDto menuDto){
        Menu menu = BeanCopyUtils.copyBean(menuDto, Menu.class);
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long menuId){
        return menuService.deleteMenu(menuId);
    }

    /**
     * 角色设置里的权限菜单分配
     * @return
     */
    @GetMapping("/treeselect")
    public ResponseResult getMenuTreeList(){
        return menuService.getMenuTreeList();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult getRoleMenuTreeById(@PathVariable("id") Long id){
        return menuService.getRoleMenuTreeById(id);
    }


}
