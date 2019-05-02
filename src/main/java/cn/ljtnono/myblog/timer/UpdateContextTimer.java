package cn.ljtnono.myblog.timer;

import cn.ljtnono.myblog.callback.InitBlogCallBack;
import cn.ljtnono.myblog.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.TimerTask;

/**
 *  每天0点定时刷新
 *  @author ljt
 *  @date 2019/1/9
 *  @version 1.0
*/
public class UpdateContextTimer extends TimerTask {

    private final InitBlogCallBack initBlogCallBack;

    private final Logger logger = LoggerFactory.getLogger(UpdateContextTimer.class);

    public UpdateContextTimer(InitBlogCallBack initBlogCallBack) {
        this.initBlogCallBack = initBlogCallBack;
    }
    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        //获取到回调函数
        logger.info("现在是：" + DateUtil.normalFormat(new Date()) + "执行定时刷新任务！");
        initBlogCallBack.initFooter();
        initBlogCallBack.initHeader();
        initBlogCallBack.initArticles();
        initBlogCallBack.initBlog();
    }

}
