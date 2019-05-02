package cn.ljtnono.myblog.mapper;

import cn.ljtnono.myblog.entity.*;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import static cn.ljtnono.myblog.common.Tables.*;

/**
 *  初始化博客
 *  @author ljt
 *  @date 2018/12/3
 *  @version 1.0
*/
public interface BKInitBlogMapper {

    /**
     * 获取首页导航栏
     * @param categoryId 导航栏的id
     * @return 导航栏的实体
     */
    @Select("select * from " + CATEGORYTABLE + "  where id = #{v}")
    @Results(id=CATEGORYTABLE)
    BKCategory getBlogCategory(Integer categoryId);

    /**
     * 获取web的配置
     * @param configId 配置的id
     * @return BKWebConfig 实体
     */
    @Select("select * from " + WEBCONFIGTABLE + " where id = #{v}")
    @Results(id=WEBCONFIGTABLE)
    BKWebConfig getBlogWebConfig(Integer configId);

}
