package cn.ljtnono.myblog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 *  配置Spring的统一异常处理类
 *  @author ljt
 *  @date 2018/12/15
 *  @version 1.0
*/
@ControllerAdvice
public class SpringExceptionHandler{

    /**
     * 统一异常处理方式
     * @param e 出现的异常类
     * @return 跳转的视图
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView globalExceptionHandler(Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("ex",e);
        mv.setViewName("error");
        return mv;
    }

}
