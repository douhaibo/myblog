package cn.ljtnono.myblog.controller;

import cn.ljtnono.myblog.entity.BKImg;
import cn.ljtnono.myblog.pojo.JsonResult;
import cn.ljtnono.myblog.pojo.PageParam;
import cn.ljtnono.myblog.service.BKImgService;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * 处理img请求的controller
 *
 * @author ljt
 * @version 1.0
 * @date 2018/12/12
 */
@Controller
@RequestMapping("/img")
public class BKImgController extends SimpleEssentialController {

    @Autowired
    private BKImgService imgService;

    /**
     *  editormd 删除文件的接口
     * @param file 上传的图片文件，这里是springmvc自行封装的文件对象
     * @return 返回 JSONObject 对象
     * 失败，返回success字段值为0
     * 成功，返回success字段的值为1
     * message字段带有异常信息
     */
    @RequestMapping(value = "/editormdUploadImg", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject editormdUploadImg(@RequestParam(value = "editormd-image-file") MultipartFile file) {
        // TODO 待写blog的相关类的时候进行重构，这里还有些问题
        return imgService.uploadImg(file);
    }


    /**
     * 增加一个图片实体
     * @param file 上传的图片
     * @return JsonResult 消息
     */
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadImg(MultipartFile file) {
        return imgService.uploadImg(file);
    }
    /**
     * 分页获取Img数据
     * @param pageParam 分页参数
     * @return 如果参数为null 则默认设置都为1 即返回一条数据
     * 如果分页参数小于1 默认返回前10条数据，如果分页参数正常，则正常分页查询
     */
    @RequestMapping("/getPageImgList")
    @ResponseBody
    public JsonResult getPageImgList(PageParam pageParam, HttpServletResponse response) {
        JsonResult jsonResult;
        try {
            List<BKImg> getMultipleResult = (List<BKImg>) imgService.getMultiple(pageParam);
            PageInfo pageInfo = new PageInfo<>(getMultipleResult);
            jsonResult = JsonResult.success(pageInfo.getList(), (int) pageInfo.getTotal());
        } catch (Exception e) {
            jsonResult = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return jsonResult;
    }

    /**
     *  根据图片的id来删除一个图片
     *  如果remove参数为false 则只是 set mode = 0
     *  如果remove 参数为true 则从数据库中删除一条数据
     *  图片实体参数的id值必须不为空
     * @param bkImg  要删除的图片
     * @param remove 是否从数据库中删除记录
     * @return JsonResult 包含请求信息的JsonResult对象
     */
    @RequestMapping("/deleteImgById")
    @ResponseBody
    public JsonResult deleteImgById(BKImg bkImg, boolean remove, HttpServletResponse response) {
        JsonResult result;
        if (StringUtils.isEmpty(bkImg.getId())) {
            result = JsonResult.fail(response.getStatus(), "图片的id不能为" + bkImg.getId());
            return result;
        }
        try {
            imgService.deleteOne(bkImg, remove);
            result = JsonResult.successForMessage("删除成功！", response.getStatus());
        } catch (Exception e) {
            result = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return result;
    }

    /**
     * 根据id数组删除多个
     * 如果remove参数为false 则只是 set mode = 0
     * 如果remove 参数为true 则从数据库中删除一条数据
     * @param params   id数组
     * @param remove   是否移除
     * @param response 响应对象
     * @return JsonResult 包含请求信息的JsonResult对象
     */
    @ResponseBody
    @RequestMapping("/deleteImgsByIdArray")
    public JsonResult deleteImgsByIdArray(@RequestParam(value = "params[]") String[] params, @RequestParam(value = "remove", defaultValue = "false") boolean remove, HttpServletResponse response) {
        JsonResult result;
        if (params == null || params.length == 0) {
            return JsonResult.fail(response.getStatus(), "删除失败！参数不能为空数组" );
        }
        try {
            imgService.deleteMultiple(params,remove);
            result = JsonResult.successForMessage("删除成功！", response.getStatus());
        } catch (Exception e) {
            result = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return result;
    }


    /**
     * 修改图片信息
     * @param img      修改的图片的信息
     * @param response 响应对象
     * @return JsonResult 包含请求信息的JsonResult对象
     */
    @ResponseBody
    @RequestMapping(value = "/updateImg", method = RequestMethod.POST)
    public JsonResult updateImg(@RequestBody BKImg img, HttpServletResponse response) {
        JsonResult result;
        try {
            updateOne(img, imgService);
            result =  JsonResult.successForMessage("修改成功！", response.getStatus());
        } catch (Exception e) {
            result = JsonResult.fail(response.getStatus(), e.getMessage());
        }
        return result;
    }


    /**
     * 根据id 或者title 字段获取图片
     *  这里title 或者id 必须有一个不为空
     * @param img      图片对象
     * @param response 响应对象
     * @return JsonResult 对象
     */
    @ResponseBody
    @RequestMapping(value = "/searchImg")
    public JsonResult searchImg(BKImg img, HttpServletResponse response) {
        JsonResult result;
        if (StringUtils.isEmpty(img.getId()) && StringUtils.isEmpty(img.getTitle())) {
            result = JsonResult.fail(response.getStatus(), "id或者title不能都为空");
        } else {
            try {
                BKImg one = getOne(img, imgService);
                List<BKImg> imgList = new ArrayList<>();
                imgList.add(one);
                result = JsonResult.success(imgList, imgList.size());
            } catch (Exception e) {
                result = JsonResult.fail(response.getStatus(), e.getMessage());
            }
        }
        return result;
    }

}
