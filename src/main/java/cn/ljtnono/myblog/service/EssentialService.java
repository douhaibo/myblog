package cn.ljtnono.myblog.service;

import cn.ljtnono.myblog.pojo.PageParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *  service的基类，提供service基础功能
 *  @author ljt
 *  @date 2019/3/16
 *  @version 1.0
*/
public interface EssentialService {

    /**
     * 获取一个DO对象
     * @param t 获取的凭证，比如实体的某个字段  id 、 title
     * @return 满足条件的实体对象
     */
    <T> T getOne(T t);

    /**
     * 更新一条数据
     * @param t 要更新的数据  以id为查找
     * @return 满足条件的实体对象
     */
    <T> boolean updateOne(T t);

    /**
     *  新增一条数据
     * @param t 新增的数据
     * @param saveToFastDFS 是否存储在fastdfs中去
     * @param file spring封装的文件实体
     * @return true 成功 false 失败
     */
    <T> boolean addOne(T t, boolean saveToFastDFS, MultipartFile file);

    /**
     *  删除一个实体
     * @param t 需要删除的实体
     * @param remove 是否从数据库中删除记录
     * @return true 删除成功  false 删除失败
     */
    <T> boolean deleteOne(T t, boolean remove);

    /**
     * 无条件获取全部记录
     * @param pageParam        分页参数
     * @return List<T>        结果集列表
     */
    List<?> getMultiple(PageParam pageParam);

    /**
     * 批量删除一个实体
     * @param primaryKey 主键数组
     * @param remove 是否移除
     * @return true 删除成功  false 删除失败
     */
    boolean deleteMultiple(Object[] primaryKey,boolean remove) ;
}
