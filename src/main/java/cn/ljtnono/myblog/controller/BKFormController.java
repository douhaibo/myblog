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
	 * 后台管理动态页面
	 * @param currentPageName 页面的后缀
	 * @param mv 视图
	 * @return mv
	 */
	@RequestMapping("/back_{currentPageName}")
	public ModelAndView backIndex(@PathVariable String currentPageName, ModelAndView mv) {
		mv.addObject("currentPageName","back_" + currentPageName);
		mv.setViewName("back_index");
		return mv;
	}

	@RequestMapping("/admin/login")
	public String backLogin() {
		return "/admin/login";
	}

}

