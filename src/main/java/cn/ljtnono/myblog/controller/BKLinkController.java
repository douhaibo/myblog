package cn.ljtnono.myblog.controller;

import cn.ljtnono.myblog.entity.BKLink;
import cn.ljtnono.myblog.pojo.JsonResult;
import cn.ljtnono.myblog.pojo.PageParam;
import cn.ljtnono.myblog.service.BKLinkService;
import cn.ljtnono.myblog.validate.link.AddLinkGroup;
import cn.ljtnono.myblog.validate.link.DeleteLinkByIdGroup;
import cn.ljtnono.myblog.validate.link.UpdateLinkGroup;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ljt
 * @version 1.0
 * @date 2019/3/21
 */
@Controller
public class BKLinkController extends SimpleEssentialController {

    @Autowired
    private BKLinkService bkLinkService;

    /**
     * 这里只是根据id和地址还有标题查询的
     * 需要检查这三个参数的合法性
     * @param link     装了要查询的字段条件
     * @param response 响应对象
     * @return JsonResult 对象 错误返回错误信息  正确的返回正确的信息
     */
    @RequestMapping("/searchLink")
    @ResponseBody
    public JsonResult searchLink(BKLink link, HttpServletResponse response) {
        JsonResult result;
        BKLink getOneResult;
        try {
            getOneResult = getOne(link, bkLinkService);
            if (getOneResult != null) {
                List<BKLink> bkLinkList = new ArrayList<>();
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
     * 分页获取link数据
     *
     * @param pageParam 分页参数
     * @return JsonResult对象
     */
    @ResponseBody
    @RequestMapping("/getPageLinkList")
    public JsonResult getPageLinkList(PageParam pageParam, HttpServletResponse response) {
        JsonResult jsonResult;
        try {
            List<BKLink> multiple = (List<BKLink>) bkLinkService.getMultiple(pageParam);
            PageInfo<BKLink> pageInfo = new PageInfo<>(multiple);
            jsonResult = JsonResult.success(pageInfo.getList(), (int) pageInfo.getTotal());
        } catch (Exception e) {
            jsonResult = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return jsonResult;
    }


    /**
     * 根据id删除一个连接
     * @param bkLink   要删除的链接实体，其中包含着id属性，而且id属性必须要是合法的
     * @param response http响应对象\
     * @param remove   是否从数据库中删除
     * @return JsonResult 对象
     */
    @ResponseBody
    @RequestMapping("/deleteLinkById")
    public JsonResult deleteLinkById(@Validated(DeleteLinkByIdGroup.class) BKLink bkLink, BindingResult bindingResult, boolean remove, HttpServletResponse response) {
        JsonResult result;
        if (bindingResult.hasErrors()) {
            return firstErrorMessageToJsonResult(bindingResult,response);
        }
        try {
            bkLinkService.deleteOne(bkLink, remove);
            result = JsonResult.successForMessage("删除成功！",response.getStatus());
        } catch (Exception e) {
            result = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return result;
    }

    /**
     * 获取本站的友情链接
     *
     * @param response http响应对象
     * @return JsonResult 对象
     */
    @ResponseBody
    @RequestMapping("/getFriendLinkList")
    public JsonResult getFriendLinkList(HttpServletResponse response) {
        JsonResult result;
        try {
            List<BKLink> friendLinkListResult = bkLinkService.getFriendLinkList();
            if (friendLinkListResult.size() > 0) {
                result = JsonResult.success(friendLinkListResult, friendLinkListResult.size());
            } else {
                result = JsonResult.fail(response.getStatus(), "没有数据！");
            }
        } catch (Exception e) {
            result = JsonResult.fail(response.getStatus(), "获取失败！请重试");
        }
        return result;
    }

    /**
     * 修改一个连接的信息
     *
     * @param bkLink   要修改的链接
     * @param response 响应对象
     * @return 修改的结果
     */
    @ResponseBody
    @RequestMapping(value = "/updateLink", method = RequestMethod.POST)
    public JsonResult updateLink(@RequestBody @Validated(UpdateLinkGroup.class) BKLink bkLink, BindingResult bindingResult, HttpServletResponse response) {
        JsonResult result;
        if (bindingResult.hasErrors()) {
            return firstErrorMessageToJsonResult(bindingResult,response);
        }
        try {
            bkLinkService.updateOne(bkLink);
            result = JsonResult.successForMessage("修改成功！",response.getStatus());
        } catch (Exception e) {
            result = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return result;
    }

    /**
     * 根据id数组删除多个链接
     *
     * @param params   id数组
     * @param remove   是否彻底删除
     * @param response 响应对象
     * @return JsonResult对象
     */
    @ResponseBody
    @RequestMapping("/deleteLinksByIdArray")
    public JsonResult deleteLinksByIdArray(@RequestParam("params[]") Integer[] params, @RequestParam(value = "remove", defaultValue = "false") boolean remove, HttpServletResponse response) {
        JsonResult result;
        if (params == null || params.length == 0) {
            result = JsonResult.fail(response.getStatus(), "参数不能为空数组");
            return result;
        }
        try {
            bkLinkService.deleteMultiple(params, remove);
            result = JsonResult.successForMessage("删除成功！",response.getStatus());
        } catch (Exception e) {
            result = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return result;
    }

    /**
     * 添加一个link到数据库中去
     * @param bkLink   要添加的实体对象
     * @param response 响应对象
     * @param bindingResult 验证出错信息
     * @return JsonResult 成功或失败都返回提示信息
     */
    @ResponseBody
    @RequestMapping(value = "/addLink", method = RequestMethod.POST)
    public JsonResult addLink(@Validated(value = AddLinkGroup.class) BKLink bkLink, BindingResult bindingResult, HttpServletResponse response) {
        JsonResult result;
        if (bindingResult.hasErrors()) {
            return firstErrorMessageToJsonResult(bindingResult,response);
        }
        try {
            bkLinkService.addOne(bkLink, false, null);
            result = JsonResult.successForMessage("添加成功！",response.getStatus());
        } catch (Exception e) {
            result = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return result;
    }

}
