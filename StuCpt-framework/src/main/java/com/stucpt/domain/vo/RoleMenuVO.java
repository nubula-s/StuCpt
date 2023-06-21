package com.stucpt.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.stucpt.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/20/22:54
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)   //实现链式编程注解
public class RoleMenuVO {
    private Long id;

    //菜单名称
    private String label;
    //父菜单ID
    private Long parentId;

    private List<RoleMenuVO> children;
}
