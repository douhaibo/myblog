package cn.ljtnono.myblog.controller;

import cn.ljtnono.myblog.entity.BKTimeLine;
import cn.ljtnono.myblog.pojo.JsonResult;
import cn.ljtnono.myblog.service.BKTimeLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  时间轴
 *  @author ljt
 *  @date 2019/1/19
 *  @version 1.0
*/
@Controller
@RequestMapping("/timeLine")
public class BKTimeLineController extends SimpleEssentialController{

    @Autowired
    private BKTimeLineService bkTimeLineService;

    private Logger logger = LoggerFactory.getLogger(BKTimeLineController.class);


    @RequestMapping("/getBKTimeLineAll")
    @ResponseBody
    public JsonResult getBKTimeLineAll(HttpServletResponse response) {
        JsonResult result;
        try {
            List<BKTimeLine> bkTimeLineAll = bkTimeLineService.getBKTimeLineAll();
            if (bkTimeLineAll != null && bkTimeLineAll.size() > 0) {
                result = JsonResult.success(bkTimeLineAll,bkTimeLineAll.size());
            } else {
                result = JsonResult.fail(response.getStatus(),"没有数据！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = JsonResult.fail(response.getStatus(),e.getMessage());
        }
        return result;
    }

}
