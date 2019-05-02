package cn.ljtnono.myblog.provider;

import cn.ljtnono.myblog.entity.BKBlog;
import cn.ljtnono.myblog.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;


import java.util.Map;

import static cn.ljtnono.myblog.common.Tables.BLOGTABLE;

/**
 * 博客业务的动态SQL
 *
 * @author ljt
 * @version 1.0
 * @date 2018/12/14
 */
public class BKBlogProvider {

    /**
     * 获取一个博客实体
     * @param params 博客实体参数，一定要包含ID属性
     * @return sql语句
     */
    public String getOne(Map<String, Object> params) {
        BKBlog bkBlog = (BKBlog) params.get("blog");
        return new SQL(){
            {
                SELECT("*");
                FROM(BLOGTABLE);
                if (bkBlog.getId() != null && bkBlog.getId() > 0) {
                    WHERE("id=" + bkBlog.getId());
                }
                if (!StringUtils.isEmpty(bkBlog.getTitle())) {
                    AND();
                    WHERE("title='" + bkBlog.getTitle() + "'");
                }
                if (bkBlog.getMode() != null && (bkBlog.getMode() == 0 || bkBlog.getMode() == 1)) {
                    AND();
                    WHERE("mode=" + bkBlog.getMode());
                }
            }
        }.toString();
    }

    /**
     * 更新一个博客实体
     * @param params 博客实体参数，一定要包含ID属性
     * @return sql语句
     */
    public String updateOne(Map<String,Object> params) {
        BKBlog bkBlog = (BKBlog) params.get("blog");
        return new SQL(){
            {
                UPDATE(BLOGTABLE);
                if (bkBlog.getId() != null && bkBlog.getId() > 0) {
                    SET("id=" + bkBlog.getId() );
                }
                if (!StringUtils.isEmpty(bkBlog.getTitle())) {
                    SET("title='" + bkBlog.getTitle() + "'");
                }
                if (bkBlog.getMode() != null && (bkBlog.getMode() == 0 || bkBlog.getMode() == 1)) {
                    SET("mode=" + bkBlog.getMode());
                }
                if (bkBlog.getUpdateDateTime() != null) {
                    SET("updateDateTime='" + DateUtil.dateTimeFormat(bkBlog.getUpdateDateTime()) + "'");
                }
                if (bkBlog.getCommentCount() != null) {
                    SET("commentCount=" + bkBlog.getCommentCount());
                }
                if (bkBlog.getViewCount() != null) {
                    SET("viewCount=" + bkBlog.getViewCount());
                }
                if (!StringUtils.isEmpty(bkBlog.getSummary())) {
                    SET("summary='" + bkBlog.getSummary() + "'");
                }
                if (!StringUtils.isEmpty(bkBlog.getContentHtml())) {
                    SET("contentHtml='" + bkBlog.getContentHtml() + "'");
                }
                if (!StringUtils.isEmpty(bkBlog.getContentMarkDown())) {
                    SET("contentMarkDown='" + bkBlog.getContentMarkDown() + "'");
                }
                WHERE("id =" + bkBlog.getId());
            }
        }.toString();
    }

    /**
     * 请保证params 不能为空的
     * @param params 参数
     * @return sql语句
     */
    public String deleteMultiple(Map<String,Object> params) {
        return doDeleteMultiple(params,false);
    }

    /**
     *
     * @param params 参数
     * @return sql语句
     */
    public String removeMultiple(Map<String,Object> params) {
        return doDeleteMultiple(params,true);
    }

    /**
     * 删除多个
     * @param params 删除的id数组
     * @param remove 是否移除
     * @return SQL语句
     */
    private String doDeleteMultiple(Map<String,Object> params,boolean remove) {
        Integer[] p = (Integer[]) params.get("params");
        String s = BKProviderHelper.integerArrayToQueryString(p);
        if (remove) {
            return  " delete from " + BLOGTABLE + " where id in (" + s + " )";
        } else {
            return " update " + BLOGTABLE  + " set mode = 0 where id in (" + s + " )";
        }
    }
}
