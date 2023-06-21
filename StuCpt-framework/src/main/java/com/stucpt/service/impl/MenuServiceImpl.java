package com.stucpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stucpt.constants.SystemConstants;
import com.stucpt.domain.ResponseResult;
import com.stucpt.domain.dto.MenuDto;
import com.stucpt.domain.entity.Menu;
import com.stucpt.domain.vo.MenuRoleIdsVo;
import com.stucpt.domain.vo.MenuVo;
import com.stucpt.domain.vo.RoleMenuVO;
import com.stucpt.enums.AppHttpCodeEnum;
import com.stucpt.mapper.MenuMapper;
import com.stucpt.service.MenuService;
import com.stucpt.utils.BeanCopyUtils;
import com.stucpt.utils.SecurityUtils;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-17 01:23:38
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuService menuService;

    /**
     *
     * @param id
     * @return
     */
    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限,否则返回其具有的权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> wrapper =  new LambdaQueryWrapper<>();
            //1.找出菜单类型为C 和 F的权限
            wrapper.in(Menu::getMenuType,SystemConstants.Menu,SystemConstants.Button);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            //只需要获取权限这列 用stream流来处理
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //其他角色就返回所具有的权限

        return getBaseMapper().selectPermsByUserId(id);
    }

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {

        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;

        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是  返回所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else {
            //否则 当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建菜单tree
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult getMenuList(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(menuName), Menu::getMenuName, menuName);
        queryWrapper.eq(Objects.nonNull(status),Menu::getStatus,status);
        queryWrapper.orderByAsc(Menu::getId,Menu::getOrderNum);
        List<Menu> list = list(queryWrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(list,MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return null;
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        Menu menu =getById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu,MenuVo.class);

        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if(menu.getParentId().equals(menu.getId())){
            return  ResponseResult.errorResult(AppHttpCodeEnum.PARENT_MENU_ERROR);
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long menuId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Menu::getParentId,menuId);
        long count = count(queryWrapper);
        if(count > 0){
            return  ResponseResult.errorResult(AppHttpCodeEnum.HAS_CHILD_MENU);
        }
        removeById(menuId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuTreeList() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus,SystemConstants.Menu_Status_Normal)
                .orderByDesc(Menu::getId,Menu::getOrderNum);
        List<Menu> menuLists = list(queryWrapper);
       List<RoleMenuVO>  roleMenuVOS =  menuLists.stream()
                .map(menu -> {
                    RoleMenuVO roleMenuVO = new RoleMenuVO();
                    roleMenuVO.setId(menu.getId());
                    roleMenuVO.setLabel(menu.getMenuName());
                    roleMenuVO.setParentId(menu.getParentId());
                    return roleMenuVO;
                })
                .collect(Collectors.toList());

        List<RoleMenuVO> menuTree = roleBuilderMenuTree(roleMenuVOS,0L);
        return ResponseResult.okResult(menuTree);
    }

    /**
     * 加载对应角色菜单列表树接口
     * @param id
     * @return
     */
    @Override
    public ResponseResult getRoleMenuTreeById(Long id) {
        MenuMapper menuMapper = getBaseMapper();
        List<Long> longs = menuMapper.selectMenuIdByUserId(id);
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus,SystemConstants.Menu_Status_Normal);
        queryWrapper.orderByDesc(Menu::getId,Menu::getOrderNum);
        List<Menu> menuList = list(queryWrapper);
        List<RoleMenuVO>  menus =  menuList.stream()
                .map(menu -> {
                    RoleMenuVO roleMenuVO = new RoleMenuVO();
                    roleMenuVO.setId(menu.getId());
                    roleMenuVO.setLabel(menu.getMenuName());
                    roleMenuVO.setParentId(menu.getParentId());
                    return roleMenuVO;
                })
                .collect(Collectors.toList());
        //构建菜单tree

        List<RoleMenuVO> menuTree = roleBuilderMenuTree(menus,0L);
        MenuRoleIdsVo menuRoleIdsVo = new MenuRoleIdsVo(menuTree,longs);
        return ResponseResult.okResult(menuRoleIdsVo);
    }


    private List<Menu> builderMenuTree(List<Menu> menus,long parentId) {

        //利用流的1.过滤来处理用户菜单数据  2.将原来menu转换成树
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus))) // 要加注链式编程注解
                .collect(Collectors.toList());
        return menuTree;
    }

    private List<RoleMenuVO> roleBuilderMenuTree(List<RoleMenuVO> roleMenuVOList,long parentId) {
        //利用流的1.过滤来处理用户菜单数据  2.将原来
        List<RoleMenuVO> menuTree = roleMenuVOList.stream()
                .filter(roleMenuVo -> roleMenuVo.getParentId().equals(parentId))
                .map(roleMenuVo -> roleMenuVo.setChildren(roleGetChildren(roleMenuVo,roleMenuVOList)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的子menu集合
     * @param menu
     * @param menus
     * @return
     *
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {

        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .collect(Collectors.toList());
        return childrenList;
    }

    /**
     * 获取存入参数的子menu集合
     * @param roleMenuVO
     * @param roleMenuVOS
     * @return
     *
     */
    private List<RoleMenuVO> roleGetChildren(RoleMenuVO roleMenuVO, List<RoleMenuVO> roleMenuVOS) {

        List<RoleMenuVO> childrenList = roleMenuVOS.stream()
                .filter(m -> m.getParentId().equals(roleMenuVO.getId()))
                .map(m->m.setChildren(roleGetChildren(m,roleMenuVOS)))
                .collect(Collectors.toList());
        return childrenList;
    }


    /**
     *获取角色的权限menuId给角色修改功能的菜单树
     * @param id
     * @return
     */
    private  List<Long> getRoleMenuId(Long id){
        return baseMapper.selectMenuIdByUserId(id);
    }



}

