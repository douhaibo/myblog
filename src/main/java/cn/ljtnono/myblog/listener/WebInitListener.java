package cn.ljtnono.myblog.listener;

import cn.ljtnono.myblog.callback.InitBlogCallBack;
import cn.ljtnono.myblog.callback.impl.InitBlogCallBackImpl;
import cn.ljtnono.myblog.service.BKInitBlogService;
import cn.ljtnono.myblog.timer.UpdateContextTimer;
import cn.ljtnono.myblog.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;
import java.util.Timer;
/**
 *  初始化各种数据
 *  @author ljt
 *  @date 2018/11/29
 *  @version 1.0
*/

public class WebInitListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(WebInitListener.class);

    private UpdateContextTimer updateContextTimer;

    /**
     * 打印日志的函数
     * @param msg 打印的内容
     */
    private void printInitLog(String msg)  {
        logger.info(getClass().getSimpleName() + " " +DateUtil.dateTimeFormat(new Date()) + " 信息 " + "[" + msg + "]");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        BKInitBlogService initBlogService = ac.getBean(BKInitBlogService.class);
        ServletContext context = sce.getServletContext();
        InitBlogCallBack initBlogCallBack = new InitBlogCallBackImpl(initBlogService,context);
        printInitLog("开始初始化页面数据");
        initBlogCallBack.initHeader();
        initBlogCallBack.initFooter();
        initBlogCallBack.initBlog();
        initBlogCallBack.initArticles();
        printInitLog("页面数据全部初始化完成");
        updateContextTimer = new UpdateContextTimer(initBlogCallBack);
        //每隔一天刷新一次
        new Timer().schedule(updateContextTimer,DateUtil.getSomeTime(24,0,0).getTime() - System.currentTimeMillis(),1000*60*60*24);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
