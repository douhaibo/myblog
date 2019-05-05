package cn.ljtnono.myblog.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *  @author ljt
 *  @date 2019/5/5
 *  @version 1.1
*/
@Controller
public class BKFormController {

	/**
	 * 前台动态跳转页面
	 * @param pageName  要跳转的页面的名字
	 * @return 返回动态页面
	 */
	@RequestMapping(value="/{pageName}")
	public String dynamicPage(@PathVariable String pageName){
		return pageName;
	}

	/**
	 * 访问首页动态跳转到blog页面
	 * @return blog页面
	 */
	@RequestMapping("/")
	public String blog() {
		return "redirect:/blog";
	}

	/**
	 * 后台系统页面分发
	 * @param module 请求页面属于哪个模块
	 * @param currentPage  请求的页面
	 * @param mv 页面模型对象
	 * @return /admin/index 页面
	 */
	@RequestMapping("/admin/{module}/{currentPage}")
	public ModelAndView adminIndex(@PathVariable("module") String module, @PathVariable("currentPage")String currentPage, ModelAndView mv) {
		mv.addObject("module", module);
		mv.addObject("currentPage", currentPage);
		mv.setViewName("/admin/index");
		return mv;
	}

	/**
	 * 跳转到登陆后台的登陆界面
	 * @return 后台登陆界面
	 */
	@RequestMapping("/admin")
	public String adminLogin() {
		return "/admin/login";
	}

	/**
	 * 跳转到博客编辑界面
	 * @return 博客编辑界面
	 */
	@RequestMapping("/admin/blog/edit")
	public String editBlog() {
		return "/admin/blog/edit";
	}

}

