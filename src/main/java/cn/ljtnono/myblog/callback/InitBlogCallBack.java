package cn.ljtnono.myblog.callback;

/**
 *  处理初始化数据的回调函数
 *  @author ljt
 *  @date 2018/12/7
 *  @version 1.0
*/
public interface InitBlogCallBack {

    /**
     * 初始化blog页面的数据
     */
    void initBlog();

    /**
     * 初始化articles页面的内容
     */
    void initArticles();

    /**
     * 初始化首部的内容
     */
    void initHeader();

    /**
     * 初始化网页底部的内容
     */
    void initFooter();

}
