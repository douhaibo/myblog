package cn.ljtnono.myblog.controller;

import cn.ljtnono.myblog.pojo.PageParam;
import cn.ljtnono.myblog.service.EssentialService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 *  controller的基本抽象
 *  @author ljt
 *  @date 2019/3/16
 *  @version 1.0
*/
public interface EssentialController {

    /**
     * 获取一个DO对象
     * @param t 获取的凭证，比如实体的某个字段  id 、 title之类
     * @param essentialService 执行查询的service类
     * @return 满足条件的实体对象
     */
    <T> T getOne(T t, EssentialService essentialService);

    /**
     * 更新一条数据
     * @param t 要更新的数据  以id为索引
     * @param essentialService 执行更新的service类
     * @return true 成功 false 失败
     */
    <T> boolean updateOne(T t, EssentialService essentialService);

    /**
     * 新增一条数据
     * @param t 新增的数据实体
     * @param saveToFastDFS  是否存储到FastDFS中去 目前只有img存储到里面去
     * @param essentialService 执行新增操作的service对象
     * @param file spring封装的文件实体
     * @return true 新增成功  false 新增失败
     */
    <T> boolean addOne(T t, boolean saveToFastDFS, MultipartFile file, EssentialService essentialService);


    /**
     * 删除一条数据
     * @param t 删除的实体  id作为索引
     * @param remove 是否从数据库中删除记录
     * @param essentialService 执行删除的service
     * @return true 删除成功  false 删除失败
     */
    <T> boolean deleteOne(T t, boolean remove, EssentialService essentialService);

    /**
     * 无条件查询所有记录
     * @param pageParam 分页参数
     * @param essentialService 执行的service
     * @return List<?> 结果集列表
     */
    List<?> getMultiple(PageParam pageParam, EssentialService essentialService);
}
