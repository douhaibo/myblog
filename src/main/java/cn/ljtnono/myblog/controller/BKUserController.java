package cn.ljtnono.myblog.controller;

import cn.ljtnono.myblog.entity.BKUser;
import cn.ljtnono.myblog.exception.MyAuthenticationException;
import cn.ljtnono.myblog.exception.ParamNotPresentException;
import cn.ljtnono.myblog.service.BKUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 *  处理用户请求的控制器
 *  主要是登陆注销
 *  @author ljt
 *  @date 2018/12/7
 *  @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class BKUserController extends SimpleEssentialController{

    private Logger logger = LoggerFactory.getLogger(BKUserController.class);


    @Autowired
    private BKUserService userService;

    @RequestMapping(value = "/login")
    public ModelAndView login(ModelAndView mv,@RequestParam(value = "user",required = false) String username,
                              @RequestParam(value = "psw",required = false) String password) {

        if (username == null || password == null) {
            throw new ParamNotPresentException("请检查用户名和密码参数！");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            subject.login(token);
            BKUser user = (BKUser) subject.getPrincipal();
            subject.getSession(true).setAttribute("user", user);
            mv.addObject("module","system");
            mv.addObject("currentPage","softInfo");
            mv.setViewName("/admin/index");
        } catch (UnknownAccountException uae) {
            logger.error("未知用户！");
            throw new MyAuthenticationException("没有此用户！");
        } catch (IncorrectCredentialsException ice) {
            logger.error("密码错误！");
            throw new MyAuthenticationException("密码错误！");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.error("认证错误！");
            throw new MyAuthenticationException(ae.getMessage());
        }
        return mv;
    }

    /**
     * 用户注销
     */
    @RequestMapping("/exit")
    public String exit() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //所以只能使用自动跳转控制器
        return "redirect:/back_login";
    }

    /**
     *
     * @param newPassword 修改用户的用户名
     * @return JSON 数据串
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    @RequiresPermissions("/back/updatePassword")
    public String updatePassword(String newPassword,Integer type) {
        BKUser user = (BKUser) SecurityUtils.getSubject();
        if (user == null) {
            logger.error("当前用户不存在！");
            throw new NullPointerException("用户身份已过期！请重新登录");
        }
        return userService.updatePassword(newPassword,type);
    }
}
