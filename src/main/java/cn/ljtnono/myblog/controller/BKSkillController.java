package cn.ljtnono.myblog.controller;

import cn.ljtnono.myblog.entity.BKSkill;
import cn.ljtnono.myblog.pojo.JsonResult;
import cn.ljtnono.myblog.pojo.PageParam;
import cn.ljtnono.myblog.service.BKSkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  获取所有的技能列表
 *  @author ljt
 *  @date 2019/1/17
 *  @version 1.0
*/
@Controller
@RequestMapping("/skill")
public class BKSkillController extends SimpleEssentialController{

    @Autowired
    private BKSkillService bkSkillService;

    private Logger logger = LoggerFactory.getLogger(BKSkillController.class);

    @RequestMapping("/getBKSkillAll")
    @ResponseBody
    public JsonResult getBKSkillAll(HttpServletResponse response) {
        JsonResult jsonResult;
        try {
            List<BKSkill> bkSkillAll = bkSkillService.getBKSkillAll();
            jsonResult = JsonResult.success(bkSkillAll,bkSkillAll.size());
        } catch (Exception e) {
            jsonResult = JsonResult.fail(response.getStatus(),e.getMessage());
            logger.error("获取技能数据失败！原因：" + e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping("/getPageSkillList")
    @ResponseBody
    public JsonResult getPageSkillList(PageParam pageParam, HttpServletResponse response) {
        // TODO 待写
        return null;
    }

}
