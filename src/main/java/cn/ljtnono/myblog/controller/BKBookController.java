package cn.ljtnono.myblog.controller;

import cn.ljtnono.myblog.entity.BKBook;
import cn.ljtnono.myblog.pojo.JsonResult;
import cn.ljtnono.myblog.service.BKBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  book控制器
 *  @author ljt
 *  @date 2019/1/17
 *  @version 1.0
*/
@Controller
public class BKBookController extends SimpleEssentialController{

    @Autowired
    private BKBookService bookService;

    private final Logger logger = LoggerFactory.getLogger(BKBookController.class);

    @RequestMapping("/getAboutMeBookList")
    @ResponseBody
    public JsonResult getAboutMeBookList(HttpServletResponse response) {
        JsonResult result;
        try {
            List<BKBook> aboutMeBookList = bookService.getAboutMeBookList();
            if (aboutMeBookList != null && aboutMeBookList.size() > 0) {
                result = JsonResult.success(aboutMeBookList,aboutMeBookList.size());
            } else {
                JsonResult.Builder bookBuilder = JsonResult.newBuilder();
                result = bookBuilder.status(response.getStatus())
                        .totalCount(0)
                        .request("success")
                        .message("数据为空！")
                        .build();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = JsonResult.fail(response.getStatus(),"读取数据失败！");
        }
        return result;
    }
}
