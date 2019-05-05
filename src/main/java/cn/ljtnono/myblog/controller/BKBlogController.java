package cn.ljtnono.myblog.controller;

import cn.ljtnono.myblog.entity.*;
import cn.ljtnono.myblog.mapper.BKImgMapper;
import cn.ljtnono.myblog.pojo.JsonResult;
import cn.ljtnono.myblog.pojo.PageParam;
import cn.ljtnono.myblog.service.BKBlogService;
import cn.ljtnono.myblog.validate.blog.AddBlogGroup;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.List;

import static cn.ljtnono.myblog.common.Counts.ARTICLES_BLOG_COUNT;

/**
 *  处理Blog相关的请求
 *  @author ljt
 *  @date 2018/11/22
 *  @version 1.0
*/
@Controller
@RequestMapping("/blog")
public class BKBlogController extends SimpleEssentialController{

    @Autowired
    private BKBlogService bkBlogService;

    @Autowired
    private BKImgMapper bkImgService;

    private Logger logger = LoggerFactory.getLogger(BKBlogController.class);

    @RequestMapping("/article_detail")
    public ModelAndView articleDetail(@RequestParam(value = "num",required = false)Integer blogId) {
        //检查blogId是否正确
        if (blogId == null || blogId <= 0) {
            logger.error("博客参数错误！");
            throw new IllegalArgumentException("参数错误！");
        }
        ModelAndView mv = new ModelAndView();
        BKBlog BKBlog = bkBlogService.getBlogById(blogId);
        bkBlogService.updateBlogViewCountById(blogId);
        mv.addObject("blog", BKBlog);
        mv.setViewName("article_detail");
        return mv;
    }

    /**
     *
     * @param page 页数
     * @param type 类型
     * @return
     */
    @RequestMapping("/articles")
    public ModelAndView getBlogPageList(Integer page,String type) {
        if (page == null || page <= 0) {
            logger.error("页数参数错误！");
            throw new IllegalArgumentException("参数错误！");
        }
        //如果类型参数为空的话，默认是查询所有博客列表
        if (TextUtils.isEmpty(type)) {
            type = "all";
        }
        ModelAndView mv = new ModelAndView();
        PageInfo<BKBlog> pageInfo = bkBlogService.getBlogPageList(page,ARTICLES_BLOG_COUNT,type);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("type",type);
        mv.setViewName("articles");
        return mv;
    }


    /**
     * 分页获取全部博客列表
     * @return JSON格式的全部博客列表
     */
    @ResponseBody
    @RequestMapping("/getBlogList")
    @SuppressWarnings("unchecked")
    public JsonResult getBlogList(@RequestParam(value = "page",defaultValue = "1") Integer page, Integer limit, HttpServletResponse response) {
        try {
            PageInfo<BKBlog> blogs = bkBlogService.getBackBlogList(page,limit);
            return JsonResult.success(blogs.getList(), (int) blogs.getTotal());
        } catch (Exception e) {
            logger.error("查询出错，原因：" + e.getMessage());
            return JsonResult.fail(response.getStatus(),e.getMessage());
        }
    }

    /**
     * 畅言的回推地址
     * @param data 数据
     */
    @RequestMapping(value = "/cyht",method = RequestMethod.POST)
    public void updateBlogChangYanCallBack(String data) {
        JSONObject cy = JSONObject.fromObject(data);
        String sourceid = cy.getString("sourceid");
        int blogId = Integer.parseInt(sourceid);
        bkBlogService.updateBlogCommentCountById(blogId);
    }

    /**
     * 发表博客
     * @param bkBlog 博客参数
     * @param response 响应
     * @return JsonResult对象
     */
    @ResponseBody
    @RequestMapping(value = "/addBlog", method = RequestMethod.POST)
    public JsonResult addBlog(@Validated(value = {AddBlogGroup.class}) @RequestBody BKBlog bkBlog, BindingResult bindingResult, HttpServletResponse response) {
        JsonResult jsonResult;
        if (bindingResult.hasErrors()) {
            return firstErrorMessageToJsonResult(bindingResult,response);
        }
        try {
            // 生成摘要信息
            bkBlog.generateSummary();
            // 获取图片id
            String id = bkImgService.getImgIdBySrc(bkBlog.getCoverImg().getSrc());
            bkBlog.getCoverImg().setId(id);
            bkBlog.setCreateDateTime(new Date());
            bkBlog.setUpdateDateTime(new Date());
            bkBlogService.addOne(bkBlog, false, null);
            jsonResult = JsonResult.successForMessage("发表成功！",response.getStatus());
        }catch (Exception e) {
            jsonResult = JsonResult.fail(response.getStatus(), "发表失败！");
        }
        return jsonResult;
    }

    /**
     * 分页获取 博客列表
     * @param pageParam 分页参数对象
     * @param response http响应对象
     * @return JsonResult对象
     */
    @ResponseBody
    @RequestMapping("/getPageBlogList")
    public JsonResult getPageBlogList(PageParam pageParam,  HttpServletResponse response) {
        JsonResult jsonResult;
        try {
            List<BKBlog> getMultipleResult = (List<BKBlog>) bkBlogService.getMultiple(pageParam);
            PageInfo<BKBlog> pageInfo = new PageInfo<>(getMultipleResult);
            jsonResult = JsonResult.success(pageInfo.getList(), (int) pageInfo.getTotal());
        } catch (Exception e) {
            jsonResult = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return jsonResult;
    }
}
