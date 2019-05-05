package cn.ljtnono.myblog.controller;

import cn.ljtnono.myblog.pojo.JsonResult;
import cn.ljtnono.myblog.entity.BKTag;
import cn.ljtnono.myblog.pojo.PageParam;
import cn.ljtnono.myblog.service.BKTagService;
import cn.ljtnono.myblog.validate.tag.DeleteTagByIdGroup;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 *  处理tag请求的controller
 *  @author ljt
 *  @date 2018/12/12
 *  @version 1.0
*/
@Controller
@RequestMapping("/tag")
public class BKTagController extends SimpleEssentialController{

    @Autowired
    private BKTagService tagService;

    private final Logger logger = LoggerFactory.getLogger(BKTagController.class);

    /**
     * 这里只是根据id和地址还有标题查询的
     * 需要检查这三个参数的合法性
     * @param tag     装了要查询的字段条件
     * @param response 响应对象
     * @return JsonResult 对象 错误返回错误信息  正确的返回正确的信息
     */
    @RequestMapping("/searchTag")
    @ResponseBody
    public JsonResult searchTag(BKTag tag, HttpServletResponse response) {
        JsonResult result;
        BKTag getOneResult;
        try {
            getOneResult = getOne(tag, tagService);
            if (getOneResult != null) {
                List<BKTag> bkLinkList = new ArrayList<>();
                bkLinkList.add(getOneResult);
                result = JsonResult.success(bkLinkList, bkLinkList.size());
            } else {
                result = JsonResult.fail(response.getStatus(), "没有数据！");
            }
        } catch (Exception e) {
            result = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return result;
    }


    /**
     * 获取所有标签信息
     * @return 所有标签信息的Json数据
     */
    @RequestMapping("/getPageTags")
    @ResponseBody
    public JsonResult getPageTags(@RequestParam(value = "page",defaultValue = "1") Integer page, Integer limit, HttpServletResponse response) {
        try {
            PageInfo<BKTag> pageInfo = tagService.getPageTagSet(page,limit);
            return JsonResult.success(pageInfo.getList(), (int) pageInfo.getTotal());
        } catch (Exception e) {
            logger.error("查询出错，原因：" + e.getMessage());
            return JsonResult.fail(response.getStatus(),e.getMessage());
        }
    }

    /**
     * 获取所有的标签
     * @return 所有标签信息的Json数据
     */
    @RequestMapping("/getPageTagList")
    @ResponseBody
    public JsonResult getPageTagList(PageParam pageParam, HttpServletResponse response) {
        JsonResult jsonResult;
        try {
            List<BKTag> getMultipleResult = (List<BKTag>) tagService.getMultiple(pageParam);
            PageInfo<BKTag> pageInfo = new PageInfo<>(getMultipleResult);
            jsonResult = JsonResult.success(pageInfo.getList(), (int) pageInfo.getTotal());
        } catch (Exception e) {
            jsonResult = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return jsonResult;
    }

    /**
     * 获取每个标签具有的文章数
     * @param response 响应对象
     * @return JsonResult 结果
     */
    @RequestMapping("/getTagArticleCount")
    @ResponseBody
    public JsonResult getTagArticleCount(HttpServletResponse response) {
        // TODO 等待blog部分完成再写
        return null;
    }

    /**
     * 删除一个标签请求
     * @param bkTag 要删除的标签
     * @param bindingResult 错误结果
     * @param remove 是否移除 true表示移除
     * @param response 响应
     * @return JsonResult 对象
     */
    @RequestMapping("/deleteTagById")
    @ResponseBody
    public JsonResult deleteTagById(@Validated({DeleteTagByIdGroup.class}) BKTag bkTag, BindingResult bindingResult, boolean remove, HttpServletResponse response) {
        JsonResult jsonResult;
        if (bindingResult.hasErrors()) {
            return firstErrorMessageToJsonResult(bindingResult,response);
        }
        try {
            tagService.deleteOne(bkTag, remove);
            jsonResult = JsonResult.successForMessage("删除成功！",response.getStatus());
        } catch (Exception e) {
            jsonResult = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return jsonResult;
    }

    /**
     *
     * 根据id数组删除多个标签
     * @param params   id数组
     * @param remove   是否彻底删除
     * @param response 响应对象
     * @return JsonResult对象
     */
    @ResponseBody
    @RequestMapping("/deleteTagsByIdArray")
    public JsonResult deleteTagsByIdArray(@RequestParam("params[]") Integer[] params, @RequestParam(value = "remove", defaultValue = "false")boolean remove, HttpServletResponse response) {
        JsonResult result;
        if (params == null || params.length == 0) {
            result = JsonResult.fail(response.getStatus(), "参数不能为空数组");
            return result;
        }
        try {
            tagService.deleteMultiple(params, remove);
            result = JsonResult.successForMessage("删除成功！",response.getStatus());
        } catch (Exception e) {
            result = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return result;
    }
}
