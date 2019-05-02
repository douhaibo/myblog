package cn.ljtnono.myblog.mapper;

import cn.ljtnono.myblog.entity.BKTag;
import cn.ljtnono.myblog.provider.BKTagProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

import static cn.ljtnono.myblog.common.Tables.LINKTABLE;
import static cn.ljtnono.myblog.common.Tables.TAGTABLE;

/**
 *  标签的数据接口
 *  @author ljt
 *  @date 2018/12/11
 *  @version 1.0
*/
public interface BKTagMapper {


    /**
     * 根据tag里面包含的className字段来进行模糊查询
     * @param count 查询的条数
     * @param className 模糊查询的条件
     * @return tag的set集合
     */
    @Select("select * from " + TAGTABLE +
        " left join " + LINKTABLE + " on " + TAGTABLE + ".link = " + LINKTABLE + ".id" +
            " where " + LINKTABLE + ".classNames like CONCAT('%',#{arg1},'%') limit #{arg0}"
    )
    @ResultMap(TAGTABLE)
    Set<BKTag> getTagSet(Integer count, String className);

    /**
     * 分页查询tag
     * @return list
     */
    @Select("select * from " + TAGTABLE)
    @ResultMap(TAGTABLE)
    List<BKTag> getPageTagSet();

    /**
     * 根据id 获取某一个标签的全部属性
     * @param bkTagId 标签的id
     * @return BKTag
     */
    @Select("select * from " + TAGTABLE + " where id = #{value}")
    @Results(id = TAGTABLE,value = {
            @Result(property = "link",column = "link",one = @One(select = "cn.ljtnono.myblog.mapper.BKLinkMapper.getLinkById"))
    })
    BKTag getBKTagById(Integer bkTagId);


    /**
     * 获取单个标签实体
     * @param bkTag 根据属性来获取
     * @return 单个实体
     */
    @SelectProvider(type = BKTagProvider.class, method = "getOne")
    @ResultMap(TAGTABLE)
    BKTag getOne(@Param("tag") BKTag bkTag);

    /**
     * 更新一个标签
     * @param tag 要更新标签
     * @return 影响的行数
     */
    @UpdateProvider(type = BKTagProvider.class,method = "updateOne")
    Integer updateOne(@Param("tag") BKTag tag);


    /**
     * 获取所有的标签列表
     * @return 获取所有的标签列表
     */
    @Select("select * from " + TAGTABLE)
    @ResultMap(TAGTABLE)
    List<BKTag> getMultiple();

    /**
     * 移除一个标签惠济路
     * @param bkTag 要移除的标签，必须要得有id
     * @return 影响的行数
     */
    @Delete("delete from " + TAGTABLE + " where id = #{id}" )
    Integer removeOne(BKTag bkTag);

    /**
     * 删除一个对象 只是将mode设置为0
     * @param bkTag 要删除的标签实体
     * @return 影响的行数
     */
    @Update("update " + TAGTABLE + " set mode = 0 where id = #{id}")
    Integer deleteOne(BKTag bkTag);

    /**
     * 根据id数组批量删除
     * @param params 参数素组
     * @return 影响的行数
     */
    @DeleteProvider(type = BKTagProvider.class,method = "removeMultiple")
    Integer removeMultiple(@Param("params") Integer[] params);

    /**
     *根据id数组批量设置mode为0
     * @param params 参数素组
     * @return 影响的行数
     */
    @UpdateProvider(type = BKTagProvider.class,method = "deleteMultiple")
    Integer deleteMultiple(@Param("params") Integer[] params);
}
