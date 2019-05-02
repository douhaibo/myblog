package cn.ljtnono.myblog.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 *  Spring 上下文工具
 *  @author ljt
 *  @date 2018/12/15
 *  @version 1.0
*/

public class SpringUtil implements ApplicationContextAware {


    /**
     * 当前IOC
     */
    private static ApplicationContext applicationContext;

    /**
     * 设置当前上下文环境，此方法由spring自动装配
     */
    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        applicationContext = arg0;
    }

    /**
     * 从当前IOC获取bean
     *
     * @param id
     * bean的id
     * @return 获取的bean
     */
    public static Object getObject(String id) {
        Object object = null;
        object = applicationContext.getBean(id);
        return object;
    }

    /**
     * 根据class从IOC容器中获取bean
     * @param type class实例
     * @param <T> 返回泛型
     * @return Bean
     */
    public  static <T> T getBean(Class<T> type) {
        if (type == null) {
            return null;
        }
        return applicationContext.getBean(type);
    }

}