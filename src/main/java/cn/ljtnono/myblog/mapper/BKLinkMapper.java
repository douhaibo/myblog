package cn.ljtnono.myblog.mapper;

import cn.ljtnono.myblog.entity.BKLink;
import cn.ljtnono.myblog.provider.BKLinkProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import static cn.ljtnono.myblog.common.Tables.LINKTABLE;

/**
 *  link数据操作接口
 *  @author ljt
 *  @date 2018/12/9
 *  @version 1.0
*/
public interface BKLinkMapper {

    /**
     * 根据linkId获取一个Link实体
     * @param linkId linkId
     * @return 该id的Link实体 如果没有返回null
     */
    @Select("select * from " + LINKTABLE + " where id = #{value}")
    @Results(id = LINKTABLE)
    BKLink getLinkById(Integer linkId);

    /**
     * 根据linkId 数组获取相应的实体类
     * @param linkIdArray linkId 数组
     * @return Set<BKLink>
     */
    @SelectProvider(type = BKLinkProvider.class,method = "getLinkListByIdArray")
    List<BKLink> getLinkListByIdArray(@Param("linkIds") Integer[] linkIdArray);

    /**
     * 获取技术文章页面的友情链接
     * @return List<BKLink>
     */
    @Select("select * from " + LINKTABLE + " where classNames like '%article_friendlink%' limit 8")
    @ResultMap(LINKTABLE)
    List<BKLink> getArticleFriendLinks();

    /**
     * 获取首页友情链接
     * @return List<BKLink>
     */
    @Select("select * from " + LINKTABLE + " where classNames like '%blog_friendlink%' limit 11")
    @ResultMap(LINKTABLE)
    List<BKLink> getBlogFriendLinks();


    /**
     * 获取一个link对象
     * @param link 字段条件
     * @return BkLink 对象
     */
    @SelectProvider(type = BKLinkProvider.class,method = "getOne")
    BKLink getOne(@Param("link") BKLink link);

    /**
     * 更新一个连接的信息
     * @param link 要更新的链接的信息
     * @return 影响的行数
     */
    @UpdateProvider(type = BKLinkProvider.class,method = "updateOne")
    Integer updateOne(@Param("link") BKLink link);

    /**
     * 无条件获取数据库中的所有记录
     * @return  所有记录
     */
    @Select("select * from " + LINKTABLE )
    List<BKLink> getMultiple();

    /**
     * 删除一个对象 只是将mode设置为0
     * @param bkLink 要删除的链接
     * @return 影响的行数
     */
    @Update("update " + LINKTABLE + " set mode = 0 where id = #{id}")
    Integer deleteOne(BKLink bkLink);

    /**
     * 删除一个link  从数据表中删除此条记录
     * @param bkLink 要删除的链接实体  其中包含正确的id属性
     * @return 影响的行数
     */
    @Delete("delete from " + LINKTABLE + " where id = #{id}")
    Integer removeOne(BKLink bkLink);

    /**
     * 获取所有的友情链接列表
     * @return 所有友情链接列表
     */
    @Select("select * from " + LINKTABLE + " where classNames like '%friendlink%' " )
    List<BKLink> getFriendLinkList();

    /**
     * 只是set mode = 0
     * @param params 参数数组
     * @return 影响的行数
     */
    @UpdateProvider(type = BKLinkProvider.class,method = "deleteMultiple")
    Integer deleteMultiple(@Param("params") Integer[] params);

    /**
     * 从数据库中删除记录
     * @param params 参数素组
     * @return 影响的行数
     */
    @DeleteProvider(type = BKLinkProvider.class,method = "removeMultiple")
    Integer removeMultiple(@Param("params") Integer[] params);

    /**
     * 添加一个link记录
     * @param link 要添加的链接记录
     * @return 影响的行数
     */
    @Insert("insert into " + LINKTABLE + " (title,type,classNames,href,createDateTime,updateDateTime,mode) values(#{title},#{type},#{classNames},#{href},now(),now(),1)")
    Integer addOne(BKLink link);
}
