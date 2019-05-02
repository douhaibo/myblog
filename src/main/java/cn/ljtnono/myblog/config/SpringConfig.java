package cn.ljtnono.myblog.config;

import cn.ljtnono.myblog.utils.SpringUtil;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 *  配置了各种bean
 *  @author ljt
 *  @date 2018/12/17
 *  @version 1.0
 */
@Configuration
@ComponentScan(value = {"cn.ljtnono.myblog"},excludeFilters = {
        @ComponentScan.Filter(Controller.class),
        @ComponentScan.Filter(Aspect.class)
})
public class SpringConfig {

    /**
     * 配置一个SpringUtil 用来在任何环境下能够获取ioc容器中的Bean
     * @return SpringUtil
     */
    @Bean
    @Lazy
    public ApplicationContextAware setApplicationContextAware() {
        return new SpringUtil();
    }

    /**
     * 设置文件上传下载组件
     * 这里name必须设置为multipartResolver
     * @return CommonsMultipartResolver组件
     */
    @Bean(name = "multipartResolver")
    @Lazy
    public CommonsMultipartResolver setCommonsMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(500000000);
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);
        return resolver;
    }
}
