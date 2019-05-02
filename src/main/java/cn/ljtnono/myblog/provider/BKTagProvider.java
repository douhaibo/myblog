package cn.ljtnono.myblog.provider;

import cn.ljtnono.myblog.entity.BKTag;
import cn.ljtnono.myblog.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static cn.ljtnono.myblog.common.Tables.TAGTABLE;
/**
 * 标签SQL拼接类
 *  @author ljt
 *  @date 2019/4/18
 *  @version 1.0
*/
public class BKTagProvider {


    /**
     * 获取一个实体
     * @param param 要获取的实体的参数
     * @return 单个实体
     */
    public String getOne(Map<String,Object> param) {
        BKTag tag = (BKTag) param.get("tag");
        return new SQL(){
            {
                SELECT("*");
                FROM(TAGTABLE);
                if (tag.getId() != null && tag.getId() > 0) {
                    WHERE("id=" + tag.getId());
                }
                if (!StringUtils.isEmpty(tag.getTitle()) && tag.getTitle().length() > 0 && tag.getTitle().length() <= 10) {
                    AND();
                    WHERE("title='" + tag.getTitle() + "'");
                }
                if (tag.getMode() != null && (tag.getMode() == 0 || tag.getMode() == 1)) {
                    AND();
                    WHERE("mode=" + tag.getMode());
                }
            }
        }.toString();
    }

    /**
     * 更新一个标签的实体,一定要有ID属性
     * @param param 标签参数
     * @return 拼接的SQL语句
     */
    public String updateOne(Map<String,Object> param) {
        BKTag tag = (BKTag) param.get("tag");
        return new SQL(){
            {
                UPDATE(TAGTABLE);
                if (tag.getId() != null && tag.getId() > 0) {
                    SET("id=" + tag.getId() );
                }
                if (!StringUtils.isEmpty(tag.getTitle())) {
                    SET("title='" + tag.getTitle() + "'");
                }
                if (tag.getMode() != null && (tag.getMode() == 0 || tag.getMode() == 1)) {
                    SET("mode=" + tag.getMode());
                }
                if (tag.getUpdateDateTime() != null) {
                    SET("updateDateTime='" + DateUtil.dateTimeFormat(tag.getUpdateDateTime()) + "'");
                }
                WHERE("id =" + tag.getId());
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
            return  " delete from " + TAGTABLE + " where id in (" + s + " )";
        } else {
            return " update " + TAGTABLE  + " set mode = 0 where id in (" + s + " )";
        }
    }

}
