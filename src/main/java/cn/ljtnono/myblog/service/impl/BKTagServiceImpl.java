package cn.ljtnono.myblog.service.impl;

import cn.ljtnono.myblog.entity.BKLink;
import cn.ljtnono.myblog.entity.BKTag;
import cn.ljtnono.myblog.exception.DataBaseOperationException;
import cn.ljtnono.myblog.mapper.BKTagMapper;
import cn.ljtnono.myblog.pojo.PageParam;
import cn.ljtnono.myblog.service.BKTagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import static cn.ljtnono.myblog.common.Counts.ARTICLES_TAG_COUNT;
import static cn.ljtnono.myblog.common.Counts.CLOUDTAG_COUNT;
import static cn.ljtnono.myblog.common.Strings.TAG;


/**
 * 处理标签业务的实现类
 *
 * @author ljt
 * @version 1.0
 * @date 2018/12/11
 */
@Service
public class BKTagServiceImpl extends EssentialServiceImpl implements BKTagService  {


    @Autowired
    private BKTagMapper tagMapper;

    private final Logger logger = LoggerFactory.getLogger(BKTagServiceImpl.class);

    /**
     * 获取首页标签云的标签
     *
     * @return Set<BKTag>
     */
    @Override
    public Set<BKTag> getCloudTags() {
        return getTagSet(CLOUDTAG_COUNT,TAG);
    }

    /**
     * 获取技术文章页面的标签
     *
     * @return Set<BKTag>
     */
    @Override
    public Set<BKTag> getArticleTags() {
        return getTagSet(ARTICLES_TAG_COUNT, TAG);
    }

    /**
     * 获取TagSet
     *
     * @param count 需要查询的条数
     * @param className 模糊查询的条件  如 tag
     * @return BKTag 的set集合
     */
    @Override
    public Set<BKTag> getTagSet(Integer count, String className) {
        if (count == null || className == null) {
            logger.error("count:" + count + "className:" + className);
            throw new NullPointerException("参数为空！");
        }
        Set<BKTag> tagSet = tagMapper.getTagSet(count, className);
        if (tagSet.isEmpty()) {
            logger.info("没有数据！");
        }
        return tagSet;
    }

    /**
     *
     * @param page 页码
     * @param limit 每页查询的条数
     * @return PageInfo
     */
    @Override
    public PageInfo<BKTag> getPageTagSet(Integer page, Integer limit) {
        if (page == null || limit == null) {
            throw new NullPointerException("参数不能为null！");
        }
        if (page <= 0) {
            page = 1;
        }
        PageHelper.startPage(page,limit);
        List<BKTag> result = tagMapper.getPageTagSet();
        return new PageInfo<>(result);
    }


    /**
     * 获取一个DO对象
     *@throws RuntimeException 当传入的实体非法时抛出运行时异常
     * @param t 获取的凭证，比如实体的某个字段  id 、 title
     * @return 满足条件的实体对像，查询失败返回null
     */
    @Override
    public <T> T getOne(T t) {
        super.getOne(t);
        BKTag result;
        try {
            // TODO 使用缓存  重构
            BKTag bkTag = (BKTag) t;
            result = tagMapper.getOne(bkTag);
        } catch (TooManyResultsException e) {
            logger.error("查询出来多个结果");
            throw new DataBaseOperationException("请至少提供一个有效字段值作为查询依据");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataBaseOperationException("参数有误！请检查参数后再重试");
        }
        return (T) result;
    }

    /**
     * 更新一条数据
     *
     * @param t 要更新的数据  以id为查找
     * @return 满足条件的实体对象
     */
    @Override
    public <T> boolean updateOne(T t) {
        return super.updateOne(t);
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
        return super.addOne(t, saveToFastDFS, file);
    }

    /**
     * 删除一个实体
     *
     * @param t      需要删除的实体
     * @param remove 是否从数据库中删除记录
     * @return true 删除成功  false 删除失败
     */
    @Override
    public <T> boolean deleteOne(T t, boolean remove) {
        BKTag bkTag = (BKTag) t;
        Integer result;
        try {
            result = remove ? tagMapper.removeOne(bkTag) : tagMapper.deleteOne(bkTag);
        } catch (Exception e) {
            logger.error("删除失败！原因：" + e.getMessage());
            throw new DataBaseOperationException("删除失败！请重试");
        }
        return result > 0;
    }

    /**
     * 获取多条BKImg数据
     *
     * @param pageParam 分页参数
     * @return 如果分页参数小于1 都设置为1，如果分页参数正常，则正常分页查询，如果分页参数为null则查询所有
     */
    @Override
    public List<?> getMultiple(PageParam pageParam) {
        super.getMultiple(pageParam);
        if (pageParam != null) {
            pageParam.check();
            PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
            return tagMapper.getMultiple();
        }
        return tagMapper.getMultiple();
    }

    /**
     * 批量删除一个实体
     *
     * @param primaryKey 主键数组
     * @param remove     是否移除
     * @return true 删除成功  false 删除失败
     */
    @Override
    public boolean deleteMultiple(Object[] primaryKey, boolean remove) {
        super.deleteMultiple(primaryKey,remove);
        Integer[] ids = (Integer[]) primaryKey;
        Integer result;
        try {
            result = remove ? tagMapper.removeMultiple(ids) : tagMapper.deleteMultiple(ids);
        } catch (Exception e) {
            logger.error("删除失败！原因：" + e.getMessage());
            throw new DataBaseOperationException("删除失败！请重试");
        }
        return result > 0;
    }
}
