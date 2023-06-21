package com.stucpt.utils;

import com.stucpt.domain.entity.Article;
import com.stucpt.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/13/12:56
 * @Description:
 */
public class BeanCopyUtils {
    private BeanCopyUtils(){

    }
    public  static <V> V copyBean(Object source,Class<V> clazz){
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source,result);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //返回对象
        return result;

    }

    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz){

       //利用泛型和字节流处理
        return
                list.stream()
                .map(o -> copyBean(o,clazz))
                .collect(Collectors.toList());
    }

  /*  public static void main(String[] args) {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("ss");

        HotArticleVo hotArticleVo = copyBean(article, HotArticleVo.class);
        System.out.println(hotArticleVo);
    }*/
}
