package cn.ljtnono.myblog.service.impl;

import cn.ljtnono.myblog.entity.BKLink;
import cn.ljtnono.myblog.exception.DataBaseOperationException;
import cn.ljtnono.myblog.exception.FieldIllegalException;
import cn.ljtnono.myblog.mapper.BKLinkMapper;
import cn.ljtnono.myblog.pojo.PageParam;
import cn.ljtnono.myblog.service.BKLinkService;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *  link业务实现类
 *  @author ljt
 *  @date 2018/12/9
 *  @version 1.0
*/
@Service
public class BKLinkServiceImpl extends EssentialServiceImpl implements BKLinkService {

    @Autowired
    private BKLinkMapper linkMapper;

    private Logger logger = LoggerFactory.getLogger(BKLinkServiceImpl.class);

    /**
     * 根据id数组获取link数组
     *
     * @param linkIdArray id数组
     * @return 该id数组的link 集合
     */
    @Override
    public List<BKLink> getLinksByIds(Integer[] linkIdArray) {
        if (linkIdArray == null) {
            logger.error("linkId is null");
            throw new NullPointerException("参数为null！");
        }
        for (Integer linkId : linkIdArray) {
            if (linkId == null || linkId <= 0) {
                throw new IllegalArgumentException("参数异常！");
            }
        }
        return linkMapper.getLinkListByIdArray(linkIdArray);
    }

    /**
     * 获取技术文章的友情链接
     * @return List<BKLink>
     */
    @Override
    public List<BKLink> getArticlesFriendLinks() {
        List<BKLink> links = linkMapper.getArticleFriendLinks();
        if (links.size() == 0) {
            logger.info("没有数据！");
            return links;
        }
        initLinkList(links);
        return links;
    }

    /**
     * 获取首页友情链接
     * @return List<BKLink>
     */
    @Override
    public List<BKLink> getBlogFriendLinks() {
        List<BKLink> friendLinks = linkMapper.getBlogFriendLinks();
        if (friendLinks.size() == 0) {
            logger.info("没有数据！");
            return friendLinks;
        }
        initLinkList(friendLinks);
        return friendLinks;
    }

    /**
     * 获取所有的友情链接 不区分具体哪个页面
     * @return 所有友情链接列表
     */
    @Override
    public List<BKLink> getFriendLinkList() {
        return linkMapper.getFriendLinkList();
    }

    /**
     * 对于linkList做一些必要的设置
     * @param links List 参数
     */
    private void initLinkList(List<BKLink> links) {
        links.sort((o1, o2) -> {
            if (o1.getUpdateDateTime().getTime() != o2.getUpdateDateTime().getTime()) {
                return (int) (o2.getUpdateDateTime().getTime() - o1.getUpdateDateTime().getTime());
            } else {
                return o2.getId() - o1.getId();
            }
        });
        System.out.println("list:" + links);
    }

    /**
     * 获取一个DO对象
     * @throws RuntimeException 当传入的实体非法时抛出运行时异常
     * @param t 获取的凭证，比如实体的某个字段  id 、 title
     * @return 满足条件的实体对像，查询失败返回null
     */
    @Override
    public <T> T getOne(T t) {
        super.getOne(t);
        BKLink result;
        try {
            // TODO 使用缓存  重构
            BKLink bkLink = (BKLink) t;
            result = linkMapper.getOne(bkLink);
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
     * @param t 要更新的数据  以id为查找
     * @return true 修改成功
     * @throws DataBaseOperationException 产生数据库操作异常
     */
    @Override
    public <T> boolean updateOne(T t) {
        super.updateOne(t);
        // TODO 验证是不是所有字段都没有值 重构
        BKLink bkLink = (BKLink) t;
        try {
            return linkMapper.updateOne(bkLink) > 0;
        } catch (Exception e) {
            logger.error("修改失败！原因：" + e.getMessage());
            throw new DataBaseOperationException("修改失败！请重试");
        }
    }

    /**
     * 新增一条数据
     *
     * @param t             新增的数据
     * @param saveToFastDFS 是否存储在fastdfs中去
     * @param file          spring封装的文件实体
     * @throws DataBaseOperationException 产生数据库操作异常
     * @throws FieldIllegalException 字段检测异常
     * @return true 成功
     */
    @Override
    public <T> boolean addOne(T t, boolean saveToFastDFS, MultipartFile file) {
        super.addOne(t,saveToFastDFS,file);
        BKLink link = (BKLink) t;
        Integer addOneResult;
        try {
            link.check();
            addOneResult = linkMapper.addOne(link);
        } catch (FieldIllegalException e) {
            if (logger.isErrorEnabled()) {
                logger.error("参数错误：" + e.getMessage());
            }
            throw e;
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("添加错误！原因：" + e.getMessage());
            }
            throw new DataBaseOperationException("添加错误！");
        }
        return addOneResult > 0;
    }

    /**
     * 删除一个实体
     *
     * @param t                 需要删除的实体
     * @param remove 是否从fastdfs中删除
     * @return true 删除成功
     * @throws DataBaseOperationException 出现数据库操作异常时抛出
     */
    @Override
    public <T> boolean deleteOne(T t, boolean remove) {
        BKLink bkLink = (BKLink) t;
        Integer result;
        try {
            result = remove ? linkMapper.removeOne(bkLink) : linkMapper.deleteOne(bkLink);
        } catch (Exception e) {
            logger.error("删除失败！原因：" + e.getMessage());
            throw new DataBaseOperationException("删除失败！请重试");
        }
        return result > 0;
    }

    /**
     * 获取多条数据
     * @param pageParam 分页参数
     * @return 如果分页参数小于1 默认返回前10条数据，如果分页参数正常，则正常分页查询，如果分页参数为null则查询所有
     */
    @Override
    public List<?> getMultiple(PageParam pageParam) {
        super.getMultiple(pageParam);
        if (pageParam != null) {
            pageParam.check();
            PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
            return linkMapper.getMultiple();
        }
        return linkMapper.getMultiple();
    }

    /**
     * 批量删除一个实体
     *
     * @param primaryKey  主键数组
     * @return true 删除成功
     * @throws DataBaseOperationException 产生数据库操作异常
     * @param remove  是否移除
     */
    @Override
    public boolean deleteMultiple(Object[] primaryKey,boolean remove){
        super.deleteMultiple(primaryKey,remove);
        Integer[] ids = (Integer[]) primaryKey;
        Integer result;
        try {
            result = remove ? linkMapper.removeMultiple(ids) : linkMapper.deleteMultiple(ids);
        } catch (Exception e) {
            logger.error("删除失败！原因：" + e.getMessage());
            throw new DataBaseOperationException("删除失败！请重试");
        }
        return result > 0;
    }
}
