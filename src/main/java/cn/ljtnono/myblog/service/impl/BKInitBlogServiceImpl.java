package cn.ljtnono.myblog.service.impl;

import cn.ljtnono.myblog.entity.*;
import cn.ljtnono.myblog.mapper.BKInitBlogMapper;
import cn.ljtnono.myblog.entity.BKImg;
import cn.ljtnono.myblog.entity.BKLink;
import cn.ljtnono.myblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

import static cn.ljtnono.myblog.common.Counts.*;

/**
 *  初始化一些信息
 *  @author ljt
 *  @date 2018/11/29
 *  @version 1.0
*/
@Service
public class BKInitBlogServiceImpl implements BKInitBlogService {

    @Autowired
    private BKInitBlogMapper initBlogMapper;

    @Autowired
    private BKLinkService linkService;

    @Autowired
    private BKImgService imgService;

    @Autowired
    private BKTagService tagService;

    @Autowired
    private BKBlogService BKBlogService;

    @Autowired
    private BKBookService bookService;


    /**
     * 获取网页头部导航栏的链接信息
     * @return 链接列表
     */
    @Override
    public BKCategory getBlogCategory() {
        Integer[] linkIds = new Integer[]{20,21,23,24};
        List<BKLink> list = linkService.getLinksByIds(linkIds);
        BKCategory indexCategory = initBlogMapper.getBlogCategory(BKCategory.BLOG_CATEGORY_ID);
        indexCategory.setItems(list);
        return indexCategory;
    }


    /**
     * 获取网页页脚的一些内容
     * @return BKWebConfig
     */
    @Override
    public BKWebConfig getBlogWebConfig() {
        return initBlogMapper.getBlogWebConfig(BKWebConfig.BLOG_WEBCONFIG_DEFAULT);
    }


    @Override
    public Set<BKImg> getBlogAlbum() {
        //根据时间截取6条最新的
        return imgService.getBlogAlbum();
    }

    @Override
    public List<BKLink> getBlogFriendLinks() {
        return linkService.getBlogFriendLinks();
    }

    @Override
    public Set<BKTag> getCloudTags() {
        return tagService.getCloudTags();
    }
    /**
     * 获取技术文章的页面的分类标签
     * @return List<BKTag>
     */
    @Override
    public Set<BKTag> getArticleTags() {
        return tagService.getArticleTags();
    }

    @Override
    public List<BKBlog> getHotBlogList() {
        return BKBlogService.getHotBlogList(BLOG_HOT_COUNT);
    }

    @Override
    public List<BKBlog> getRecommendBlogList() {
        return BKBlogService.getRecommendBlogList(BLOG_RECOMMEND_COUNT);
    }

    /**
     * 获取猜你喜欢模块的列表
     *
     * @return 博客列表
     */
    @Override
    public List<BKBlog> getLikeBlogList() {
        return BKBlogService.getLikeBlogList();
    }

    /**
     * 获取技术文章页友情链接列表
     *
     * @return 友情链接列表
     */
    @Override
    public List<BKLink> getArticleFriendLinks() {
        return linkService.getArticlesFriendLinks();
    }

    /**
     * 获取我的书单列表
     * @return 书单列表的前5项
     */
    @Override
    public List<BKBook> getMyBookList() {
        return bookService.getBlogBookList();
    }

}
