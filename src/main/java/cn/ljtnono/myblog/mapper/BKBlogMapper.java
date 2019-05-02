package cn.ljtnono.myblog.mapper;

import cn.ljtnono.myblog.entity.BKBlog;
import cn.ljtnono.myblog.provider.BKBlogProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static cn.ljtnono.myblog.common.Tables.*;

/**
 * 处理首页对应内容
 * @author ljt
 * @version 1.0
 * @date 2018/11/29
 */
public interface BKBlogMapper {

    /**
     * 根据Id获取博客信息
     * @param blogId 博客的id
     * @return BKBlog
     */
    @Select("select * from " + BLOGTABLE + " where id = #{value}")
    @Results(id = BLOGTABLE,value = {
            @Result(property = "tag",column = "tagId",one = @One(select = "cn.ljtnono.myblog.mapper.BKTagMapper.getBKTagById")),
            @Result(property = "coverImg", column = "imageId",one=@One(select = "cn.ljtnono.myblog.mapper.BKImgMapper.getImgById"))
    })
    BKBlog getBlogById(Integer blogId);


    /**
     * 查询首页中hot部分 按照平评论数降序排列 默认5
     * @param count 获取的条数
     * @return List<BKBlog>
     */
    @Select("select * from " + BLOGTABLE + " order by  commentCount desc limit #{v}")
    @ResultMap(BLOGTABLE)
    List<BKBlog> getHotBlogList(Integer count);


    /**
     * 查询首页站长推荐的文章
     *
     * @param count 查询的条数
     * @return List<BKBlog>
     */
    @Select("select * from " + BLOGTABLE + " limit #{v}")
    @ResultMap(BLOGTABLE)
    List<BKBlog> getRecommendBlogList(Integer count);


    /**
     * 获取猜你喜欢模块的博客文章
     * 根据浏览量的排名降序 这里写死了是三个
     * @return 博客列表
     */
    @Select("select * from " + BLOGTABLE + " order by viewCount desc limit 3")
    @ResultMap(BLOGTABLE)
    List<BKBlog> getLikeBlogList();


    /**
     * 获取blog列表
     * @return 博客列表
     */
    @Select("select * from " + BLOGTABLE)
    @ResultMap(BLOGTABLE)
    List<BKBlog> getBlogList();

    /**
     * 获取blog列表
     *
     * @param type 类型
     * @return 博客列表
     */
    @Select("select " + BLOGTABLE + ".id," + BLOGTABLE + ".summary," + BLOGTABLE + ".title," +
            BLOGTABLE + ".viewCount," + BLOGTABLE + ".commentCount," + BLOGTABLE + ".imageId," +
            TAGTABLE + ".id as t_id," + TAGTABLE + ".title as t_title," +
            LINKTABLE + ".href as l_href," + LINKTABLE + ".title as l_title" +
            " from " + BLOGTABLE +
            " left join " + TAGTABLE + " on " + TAGTABLE + ".id = " + BLOGTABLE + ".tagId " +
            " left join " + LINKTABLE + " on " + TAGTABLE + ".link = " + LINKTABLE + ".id " +
            " where " + TAGTABLE + ".title = #{v}"
    )
    @Results({
            @Result(property = "tag.id", column = "t_id"),
            @Result(property = "tag.title", column = "t_title"),
            @Result(property = "tag.link.href", column = "l_href"),
            @Result(property = "tag.link.title", column = "l_title")
    })
    List<BKBlog> getBlogListByType(String type);


    /**
     * 获取所有的博客列表信息,用于后台数据表格
     * @return
     */
    @Select("select * from " + BLOGTABLE)
    @ResultMap(BLOGTABLE)
    List<BKBlog> getBackBlogList();

    /**
     * 根据博客Id更新博客的评论数
     * @param blogId 博客id
     * @return 影响的行数
     */
    @Update("update " + BLOGTABLE + " set commentCount = commentCount + 1 where id = #{v}")
    Integer updateBlogCommentCountById(Integer blogId);

    /**
     * 刷新博客的浏览量
     * @param blogId 博客的id
     * @return 影响的行数
     */
    @Update("update " + BLOGTABLE + " set viewCount = viewCount + 1 where id = #{v}")
    Integer updateBlogViewCountById(Integer blogId);


    /**
     * 获取一个blog对象
     * @param blog 字段条件
     * @return BKBlog 对象
     */
    @SelectProvider(type = BKBlogProvider.class,method = "getOne")
    @ResultMap(BLOGTABLE)
    BKBlog getOne(@Param("blog") BKBlog blog);

    /**
     * 更新一个连接的信息
     * @param blog 要更新的链接的信息
     * @return 影响的行数
     */
    @UpdateProvider(type = BKBlogProvider.class,method = "updateOne")
    Integer updateOne(@Param("blog") BKBlog blog);

    /**
     * 设置mode为0
     * @param blog 博客实体，其中带有id
     * @return 影响的行数
     */
    @Update("update " + BLOGTABLE  + " set mode = 0 where id = #{id}")
    Integer deleteOne(BKBlog blog);

    /**
     * 删除一个实体记录
     * @param blog 要删除的实体的记录 具有id属性
     * @return 影响的行数
     */
    @Delete("delete from " + BLOGTABLE  + " where id = #{id}")
    Integer removeOne(BKBlog blog);


    /**
     * 无条件获取数据库中的所有记录
     * @return  所有记录
     */
    @Select("select * from " + BLOGTABLE)
    @ResultMap(BLOGTABLE)
    List<BKBlog> getMultiple();

    /**
     * set mode = 0
     * @param params id数组
     * @return 影响的行数
     */
    @UpdateProvider(type = BKBlogProvider.class, method = "deleteMultiple")
    Integer deleteMultiple(@Param("params") Integer[] params);

    /**
     * 从数据库中删除记录
     * @param params 参数素组
     * @return 影响的行数
     */
    @DeleteProvider(type = BKBlogProvider.class,method = "removeMultiple")
    Integer removeMultiple(@Param("params") Integer[] params);

    /**
     * 添加一个博客实体
     * @param blog 要添加的博客实体
     * @return 影响的行数
     */
    @Insert("insert into " + BLOGTABLE + "(title,contentHtml,contentMarkDown,summary,tagId,createDateTime,updateDateTime,commentCount,viewCount,imageId,mode) values(#{title},#{contentHtml},#{contentMarkDown},#{summary},#{tag.id},#{createDateTime},#{updateDateTime},0,0,#{coverImg.id},#{mode})")
    Integer addOne(BKBlog blog);
}
