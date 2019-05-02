package cn.ljtnono.myblog.service;

import cn.ljtnono.myblog.entity.*;
import cn.ljtnono.myblog.entity.BKImg;
import cn.ljtnono.myblog.entity.BKLink;
import java.util.List;
import java.util.Set;

/**
 *  网站一些初始化工作
 *  @author ljt
 *  @date 2018/12/4
 *  @version 1.0
 *  @see cn.ljtnono.myblog.service.impl.BKInitBlogServiceImpl
*/
public interface BKInitBlogService {

    /**
     * 获取首页导航栏
     * @return 网页首页导航栏
     */
    BKCategory getBlogCategory();

    /**
     *
     * 获取网站配置信息
     * @return 网站首页配置信息
     */
    BKWebConfig getBlogWebConfig();


    /**
     * 获取首页相册列表
     * @return 相册列表
     */
    Set<BKImg> getBlogAlbum();

    /**
     * 获取首页友情链接列表
     * @return 友情链接列表
     */
    List<BKLink> getBlogFriendLinks();

    /**
     * 获取首页标签云列表
     * @return 标签云列表
     */
    Set<BKTag> getCloudTags();

    /**
     * 获取排名前5的博文的基本内容
     * @return 网页首页的标签列表 含有
     */
    List<BKBlog> getHotBlogList();

    /**
     * 获取站长推荐的博文列表
     * @return 站长推荐的博文列表
     */
    List<BKBlog> getRecommendBlogList();


    /**
     * 获取技术文章的页面的分类标签
     * @return List<BKTag>
     */
    Set<BKTag> getArticleTags();


    /**
     * 获取技术文章页友情链接列表
     * @return 友情链接列表
     */
    List<BKLink> getArticleFriendLinks();

    /**
     * 获取猜你喜欢模块的列表
     * @return 博客列表
     */
    List<BKBlog> getLikeBlogList();

    /**
     * 获取我的书单列表
     * @return 书单列表的前5项
     */
    List<BKBook> getMyBookList();
}
