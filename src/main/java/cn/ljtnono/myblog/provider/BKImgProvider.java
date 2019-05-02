package cn.ljtnono.myblog.provider;


import cn.ljtnono.myblog.entity.BKImg;
import cn.ljtnono.myblog.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;
import static cn.ljtnono.myblog.common.Tables.IMGTABLE;

/**
 *  img的动态sql帮助类
 *  @author ljt
 *  @date 2018/12/11
 *  @version 1.0
*/
public class BKImgProvider {

    /**
     * 根据imgId数组获取相关的img实体列表
     * @param params imgId数组
     * @return sql
     */
    public String getImgSetByIdArray(Map<String,Object> params) {
        Integer[] linkIds = (Integer[]) params.get("imgIds");
        String result = BKProviderHelper.integerArrayToQueryString(linkIds);
        return "select * from " + IMGTABLE + " where id in (" + result + ")";
    }

    /**
     * 根据图片的Url数组获取图片的IdList
     * @param params 参数
     * @return sql语句
     */
    public String getImageIdListBySrcArray(Map<String,Object> params) {
        String[] imageUrlArray = (String[]) params.get("imageSrcArray");
        StringBuilder result = new StringBuilder();
        for (String imageUrl : imageUrlArray) {
            result.append("'").append(imageUrl).append("'").append(",");
        }
        //删除最后一个逗号
        result.delete(result.length() - 1,result.length());
        System.out.println(result.toString());
        return "select id from " + IMGTABLE + " where src in (" + result.toString() + ")";
    }
    /**
     *
     * @param params 参数
     * @return sql语句
     */
    public String getOne(Map<String,Object> params) {
        BKImg bkImg = (BKImg) params.get("img");
        return new SQL(){
            {
                SELECT("*");
                FROM(IMGTABLE);
                if (!StringUtils.isEmpty(bkImg.getId())) {
                    WHERE("id='" + bkImg.getId() + "'");
                }
                if (!StringUtils.isEmpty(bkImg.getTitle())) {
                    AND();
                    WHERE("title='" + bkImg.getTitle() + "'");
                }
                if (!StringUtils.isEmpty(bkImg.getSrc())) {
                    AND();
                    WHERE("src='" + bkImg.getSrc() + "'");
                }
                if (bkImg.getMode() != null && (bkImg.getMode() == 0 || bkImg.getMode() == 1)) {
                    AND();
                    WHERE("mode=" + bkImg.getMode());
                }
            }
        }.toString();
    }

    /**
     *
     * @param params 参数
     * @return sql语句
     */
    public String updateOne(Map<String,Object> params) {
        BKImg bkImg = (BKImg) params.get("img");
        return new SQL(){
            {
                UPDATE(IMGTABLE);
                if (!StringUtils.isEmpty(bkImg.getTitle())) {
                    SET("title='" + bkImg.getTitle() + "'");
                }
                if (!StringUtils.isEmpty(bkImg.getSrc())) {
                    SET("src='" + bkImg.getSrc() + "'");
                }
                if (bkImg.getMode() != null && (bkImg.getMode() == 0 || bkImg.getMode() == 1)) {
                    SET("mode=" + bkImg.getMode());
                }
                if (bkImg.getUpdateDateTime() != null) {
                    SET("updateDateTime='" + DateUtil.dateTimeFormat(bkImg.getUpdateDateTime()) + "'");
                }
                if (!StringUtils.isEmpty(bkImg.getId())) {
                    WHERE("id='" + bkImg.getId() + "'");
                }
            }
        }.toString();
    }

    /**
     *  一次删除多个 只是设置mode为0
     * @param params 主键数组
     * @return sql
     *
     */
    public String deleteMultiple(Map<String,Object> params) {
        return doDeleteMultiple(params,false);
    }

    /**
     *  一次删除多个 删除记录
     * @param params 主键数组
     * @return sql
     *
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
        String[] p = (String[]) params.get("params");
        String s = BKProviderHelper.stringArrayToQueryString(p);
        if (remove) {
            return "delete from " + IMGTABLE + " where id in (" + s + " )";
        } else {
            return "update " + IMGTABLE + " set mode = 0 where id in (" + s + " )";
        }
    }

}
