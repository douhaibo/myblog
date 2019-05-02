package cn.ljtnono.myblog.service;

import cn.ljtnono.myblog.entity.BKBlog;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 *  @author ljt
 *  @date 2018/11/21
 *  @version 1.0
 *  @see cn.ljtnono.myblog.service.impl.BKBlogServiceImpl
 *
*/
public interface BKBlogService extends EssentialService{

    /**
     * 根据blogId 查询博客
     * @param blogId 博客id
     * @return BKBlog
     */
    BKBlog getBlogById(Integer blogId);

    /**
     * 获取根据浏览量排序的博客列表
     * @param count 获取的条数
     * @return 网页首页的标签列表 含有
     */
    List<BKBlog> getHotBlogList(Integer count);

    /**
     * 获取首页blog列表
     * @param count 需要查询的条数
     * @return List<BKBlog>
     */
    List<BKBlog> getRecommendBlogList(Integer count);

    /**
     * 获取猜你喜欢的blog列表
     * @return List<BKBlog>
     */
    List<BKBlog> getLikeBlogList();

    /**
     * 获取博客列表
     * @param type 类型
     * @return 博客列表
     */
    List<BKBlog> getBlogList(String type);

    /**
     * 获取博客的分页信息
     * @param page 页数
     * @param limit 每页显示的条数
     * @param type 查询的博客类型
     * @return PageInfo<BKBlog>
     */
    PageInfo<BKBlog> getBlogPageList(Integer page, Integer limit, String type);

    /**
     * 获取博客
     * @param page
     * @param limit
     * @return
     */
    PageInfo<BKBlog> getBackBlogList(Integer page, Integer limit);

    /**
     * 根据博客的id增加博客的评论数
     * @param blogId 博客Id
     * @return true更新成功 false 失败
     */
    boolean updateBlogCommentCountById(Integer blogId);

    /**
     * 根据博客的id刷新博客的浏览量
     * @param blogId 博客Id
     * @return true 成功 false 失败
     */
    boolean updateBlogViewCountById(Integer blogId);

}
