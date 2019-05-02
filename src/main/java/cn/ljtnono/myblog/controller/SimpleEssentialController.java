package cn.ljtnono.myblog.controller;

import cn.ljtnono.myblog.pojo.JsonResult;
import cn.ljtnono.myblog.pojo.PageParam;
import cn.ljtnono.myblog.service.EssentialService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  EssentialController 的基本实现
 *  @author ljt
 *  @date 2019/3/17
 *  @version 1.0
*/
public class SimpleEssentialController implements EssentialController {
    /**
     * 获取一个DO对象
     * @param t 获取的凭证，比如实体的某个字段  id 、 title之类
     * @return 满足条件的实体对象
     */
    @Override
    public <T> T getOne(T t, EssentialService essentialService) {
        return essentialService.getOne(t);
    }

    /**
     * 更新一条数据
     *
     * @param t           要更新的数据  以id为查找
     * @param essentialService 执行更新的service类
     * @return 满足条件的实体对象
     */
    @Override
    public <T>  boolean updateOne(T t, EssentialService essentialService) {
        return essentialService.updateOne(t);
    }

    /**
     * 新增一条数据
     *
     * @param t             新增的数据实体
     * @param saveToFastDFS 是否存储到FastDFS中去 目前只有img存储到里面去
     * @param essentialService   执行新增操作的service对象
     * @return true 新增成功  false 新增失败
     */
    @Override
    public <T> boolean addOne(T t, boolean saveToFastDFS, MultipartFile file, EssentialService essentialService) {
        return essentialService.addOne(t,saveToFastDFS,file);
    }

    /**
     * 删除一条数据
     *
     * @param t                 删除的实体  id作为索引
     * @param remove 是否从数据库删除记录
     * @param essentialService       执行删除的service
     * @return 删除的对象
     */
    @Override
    public <T> boolean deleteOne(T t, boolean remove, EssentialService essentialService) {
        return essentialService.deleteOne(t, remove);
    }

    /**
     * 无条件查询所有记录
     *
     * @param pageParam        分页参数
     * @param essentialService 执行的service
     * @return List<?> 结果集列表
     */
    @Override
    public List<?> getMultiple(PageParam pageParam, EssentialService essentialService) {
        return essentialService.getMultiple(pageParam);
    }

    /**
     * 获取第一条错误信息的JsonResult
     * @param bindingResult 错误信息对象
     * @param response 响应对象
     * @return JsonResult 对象
     */
    protected JsonResult firstErrorMessageToJsonResult(BindingResult bindingResult, HttpServletResponse response) {
        FieldError fieldError = bindingResult.getFieldError();
        return JsonResult.fail(response.getStatus(),fieldError.getDefaultMessage());
    }

}
