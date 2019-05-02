package cn.ljtnono.myblog.provider;

import cn.ljtnono.myblog.entity.BKLink;
import cn.ljtnono.myblog.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static cn.ljtnono.myblog.common.Tables.LINKTABLE;

/**
 *  处理link业务的动态SQL类
 *  @author ljt
 *  @date 2018/12/10
 *  @version 1.0
*/
public class BKLinkProvider {

    /**
     * 根据Id数组获取页面链接
     * @param params Id数组参数
     * @return SQL语句
     */
    public String getLinkListByIdArray(Map<String,Object> params) {
        Integer[] linkIds = (Integer[]) params.get("linkIds");
        String result = BKProviderHelper.integerArrayToQueryString(linkIds);
        return "select * from " + LINKTABLE + " where id in (" + result +")";
    }

    /**
     *  获取一个link实体
     * @param params 实体参数
     * @return sql语句
     */
    public String getOne(Map<String,Object> params) {
        BKLink bkLink = (BKLink) params.get("link");
        return new SQL(){
            {
                SELECT("*");
                FROM(LINKTABLE);
                if (bkLink.getId() != null && bkLink.getId() > 0) {
                    WHERE("id=" + bkLink.getId() );
                }
                if (!StringUtils.isEmpty(bkLink.getTitle())) {
                    AND();
                    WHERE("title='" + bkLink.getTitle() + "'");
                }
                if (!StringUtils.isEmpty(bkLink.getHref())) {
                    AND();
                    WHERE("href='" + bkLink.getHref() + "'");
                }
                if (bkLink.getMode() != null && (bkLink.getMode() == 0 || bkLink.getMode() == 1)) {
                    AND();
                    WHERE("mode=" + bkLink.getMode());
                }
                if (bkLink.getType() != null && (bkLink.getType() == 1 || bkLink.getType() == 2)) {
                    AND();
                    WHERE("type=" + bkLink.getType());
                }
                if (!StringUtils.isEmpty(bkLink.getClassNames())) {
                    AND();
                    WHERE("classNames='" + bkLink.getClassNames() + "'");
                }
            }
        }.toString();

    }

    /**
     *  修改一个link实体信息
     * @param params 实体参数
     * @return sql语句
     */
    public String updateOne(Map<String,Object> params) {
        BKLink bkLink = (BKLink) params.get("link");
        return new SQL(){
            {
                UPDATE(LINKTABLE);
                if (bkLink.getId() != null && bkLink.getId() > 0) {
                    SET("id=" + bkLink.getId() );
                }
                if (!StringUtils.isEmpty(bkLink.getTitle())) {
                    SET("title='" + bkLink.getTitle() + "'");
                }
                if (!StringUtils.isEmpty(bkLink.getHref())) {
                    SET("href='" + bkLink.getHref() + "'");
                }
                if (bkLink.getMode() != null && (bkLink.getMode() == 0 || bkLink.getMode() == 1)) {
                    SET("mode=" + bkLink.getMode());
                }
                if (bkLink.getUpdateDateTime() != null) {
                    SET("updateDateTime='" + DateUtil.dateTimeFormat(bkLink.getUpdateDateTime()) + "'");
                }
                if (bkLink.getType() == 1 || bkLink.getType() == 2) {
                    SET("type=" + bkLink.getType());
                }
                WHERE("id =" + bkLink.getId());
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
            return  " delete from " + LINKTABLE + " where id in (" + s + " )";
        } else {
            return " update " + LINKTABLE  + " set mode = 0 where id in (" + s + " )";
        }
    }
}
