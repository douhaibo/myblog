package cn.ljtnono.myblog.service.impl;

import cn.ljtnono.myblog.pojo.PageParam;
import cn.ljtnono.myblog.service.EssentialService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *  baseService 的初步实现，封装共同的操作
 *  @author ljt
 *  @date 2019/3/18
 *  @version 1.0
*/
public class EssentialServiceImpl implements EssentialService {

    /**
     *  检查参数的合法性
     * @param t 需要检查的参数
     * @throws NullPointerException 参数t为null
     */
    private  <T> void checkParam(T t) {
        if (t == null) {
            throw new NullPointerException("参数不能为：" + null);
        }
    }

    /**
     * 获取一个DO对象
     *
     * @param t 获取的凭证，比如实体的某个字段  id 、 title
     * @return 满足条件的实体对象
     */
    @Override
    public <T> T getOne(T t) {
        checkParam(t);
        return null;
    }

    /**
     * 更新一条数据
     *
     * @param t 要更新的数据  以id为查找
     * @return 满足条件的实体对象
     */
    @Override
    public <T> boolean updateOne(T t){
        checkParam(t);
        return false;
    }

    /**
     * 新增一条数据
     *
     * @param t             新增的数据
     * @param saveToFastDFS 是否存储在fastdfs中去
     * @param file          spring封装的文件实体
     * @return true 成功 false 失败
     */
    @Override
    public <T> boolean addOne(T t, boolean saveToFastDFS, MultipartFile file) {
        checkParam(t);
        return false;
    }

    /**
     * 删除一个实体
     *
     * @param t                 需要删除的实体
     * @param remove 是否从数据库中删除记录
     * @return true 删除成功  false 删除失败
     */
    @Override
    public <T> boolean deleteOne(T t, boolean remove) {
        checkParam(t);
        return false;
    }

    /**
     * 获取多条BKImg数据
     * @param pageParam 分页参数
     * @return 如果分页参数小于1 都设置为1，如果分页参数正常，则正常分页查询，如果分页参数为null则查询所有
     */
    @Override
    public List<?> getMultiple(PageParam pageParam) {
        return null;
    }

    /**
     * 批量删除一个实体
     *
     * @param primaryKey  主键数组
     * @param remove 是否移除
     * @return true 删除成功  false 删除失败
     */
    @Override
    public boolean deleteMultiple(Object[] primaryKey,boolean remove){
        // TODO 参数验证
        if (primaryKey == null || primaryKey.length == 0) {
            throw new IllegalArgumentException("参数错误！请重试");
        }
        return false;
    }
}
