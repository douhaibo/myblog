package cn.ljtnono.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 	自动跳转页面
 *  @author ljt
 *  @date 2018/8/9
 *  @version 1.0
*/
@Controller
public class BKFormController {

	@RequestMapping(value="/{formName}")
	public String loginForm(@PathVariable String formName){
		return formName;
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
	public ModelAndView backIndex(@PathVariable("module") String module, @PathVariable("currentPage")String currentPage, ModelAndView mv) {
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
	public String backLogin() {
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

