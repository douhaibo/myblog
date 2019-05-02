package cn.ljtnono.myblog.callback.impl;

import cn.ljtnono.myblog.callback.InitBlogCallBack;
import cn.ljtnono.myblog.entity.*;
import cn.ljtnono.myblog.entity.BKImg;
import cn.ljtnono.myblog.entity.BKLink;
import cn.ljtnono.myblog.service.BKInitBlogService;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Set;

import static cn.ljtnono.myblog.common.Strings.CATEGORY;
import static cn.ljtnono.myblog.entity.BKImg.BKIMG_DEFAULT;

/**
 *  初始化网页内容的回调函数
 *  @author ljt
 *  @date 2018/12/7
 *  @version 1.0
*/
public class InitBlogCallBackImpl implements InitBlogCallBack {


    private BKInitBlogService initBlogService;
    private ServletContext context;

    public InitBlogCallBackImpl(BKInitBlogService initBlogService, ServletContext context) {
        this.initBlogService = initBlogService;
        this.context = context;
    }
    /**
     * 初始化blog页面的数据
     */
    @Override
    public void initBlog() {
        Set<BKImg> blogAlbum = initBlogService.getBlogAlbum();
        List<BKLink> friendLinks = initBlogService.getBlogFriendLinks();
        Set<BKTag> cloudTags = initBlogService.getCloudTags();
        List<BKBlog> hotBKBlogList = initBlogService.getHotBlogList();
        List<BKBlog> recommendBKBlogList = initBlogService.getRecommendBlogList();
        context.setAttribute("recommendBKBlogList", recommendBKBlogList);
        context.setAttribute("album",blogAlbum);
        context.setAttribute("hotBKBlogList", hotBKBlogList);
        context.setAttribute("friendLinks",friendLinks);
        context.setAttribute("cloudTags",cloudTags);
        //设置默认图片
        context.setAttribute("defaultImage", BKIMG_DEFAULT);
    }

    /**
     * 初始化articles页面的内容
     */
    @Override
    public void initArticles() {
        Set<BKTag> articleTags = initBlogService.getArticleTags();
        List<BKLink> articleFriendLinks = initBlogService.getArticleFriendLinks();
        List<BKBlog> articleLikeBKBlogList = initBlogService.getLikeBlogList();
        List<BKBook> myBookList = initBlogService.getMyBookList();
        context.setAttribute("myBookList",myBookList);
        context.setAttribute("articleLikeBlogs", articleLikeBKBlogList);
        context.setAttribute("articleTags",articleTags);
        context.setAttribute("articleFriendLinks",articleFriendLinks);
    }

    /**
     * 初始化首部的内容
     */
    @Override
    public void initHeader() {
        BKCategory category = initBlogService.getBlogCategory();
        context.setAttribute(CATEGORY,category);
    }

    /**
     * 初始化网页底部的内容
     */
    @Override
    public void initFooter() {
        BKWebConfig webConfig = initBlogService.getBlogWebConfig();
        context.setAttribute("webConfig",webConfig);
    }


}
