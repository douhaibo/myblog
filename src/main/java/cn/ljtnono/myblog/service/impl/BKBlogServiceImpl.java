package cn.ljtnono.myblog.service.impl;

import cn.ljtnono.myblog.entity.BKBlog;
import cn.ljtnono.myblog.exception.DataBaseOperationException;
import cn.ljtnono.myblog.exception.FieldIllegalException;
import cn.ljtnono.myblog.mapper.BKBlogMapper;
import cn.ljtnono.myblog.pojo.PageParam;
import cn.ljtnono.myblog.service.BKBlogService;
import cn.ljtnono.myblog.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static cn.ljtnono.myblog.common.Counts.*;


/**
 * 这个类用于获取首页内容
 *
 * @author ljt
 * @version 1.0
 * @date 2018/11/28
 */
@Service
public class BKBlogServiceImpl extends EssentialServiceImpl implements BKBlogService {

    private Logger logger = LoggerFactory.getLogger(BKBlogServiceImpl.class);

    @Autowired
    private BKBlogMapper bkBlogMapper;

    /**
     * redis缓存工具
     */
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据博客Id查询博客实体
     * @param blogId 博客id
     * @return 如果返回null 说明参数不正确  或者说没有这个博客信息
     */
    @Override
    public BKBlog getBlogById(Integer blogId) {
        if (blogId == null || blogId <= 0) {
            logger.error("blogId is " + blogId);
            throw new IllegalArgumentException("参数不正确！");
        }
        final BKBlog bkBlog = bkBlogMapper.getBlogById(blogId);
        if (bkBlog == null) {
            logger.info("BKBlog is null!");
            return null;
        }
        return bkBlog;
    }

    /**
     * 如果没有数据的话，返回一个没有数据的list集合
     * @param count 获取的条数
     * @return 网页首页的标签列表 含有
     */
    @Override
    public List<BKBlog> getHotBlogList(Integer count) {
        if (count == null || count <= 0) {
            logger.error("count is " + count);
            throw new IllegalArgumentException("参数错误！");
        }
        int len = count;
        final List<BKBlog> hotBKBlogList = bkBlogMapper.getHotBlogList(len);
        if (hotBKBlogList.size() == 0) {
            logger.info("数据库中没有博客数据！");
        }
        return bkBlogMapper.getHotBlogList(len);
    }

    /**
     * 获取首页blog列表
     * @return List<BKBlog>
     */
    @Override
    public List<BKBlog> getRecommendBlogList(Integer count) {
        int len;
        if (count == null || count < 0) {
            logger.error("count is " + count);
            throw new IllegalArgumentException("参数错误！");
        }
        len = count;
        final List<BKBlog> recommendBKBlogList = bkBlogMapper.getRecommendBlogList(len);
        if (recommendBKBlogList.size() == 0) {
            logger.info("数据库中没有博客数据！");
        }
        return recommendBKBlogList;
    }


    @Override
    public List<BKBlog> getLikeBlogList() {
        final List<BKBlog> likeBKBlogList = bkBlogMapper.getLikeBlogList();
        if (likeBKBlogList.size() == 0) {
            logger.info("数据库中没有博客数据！");
        }
        return likeBKBlogList;
    }

    /**
     * 获取所有的博客文章
     *
     * @param type 类型
     * @return 所有的博客文章
     */
    @Override
    public List<BKBlog> getBlogList(String type) {
        final List<BKBlog> bkBlogList;
        if (type.equals("all")) {
            bkBlogList = bkBlogMapper.getBlogList();
        } else {
            bkBlogList = bkBlogMapper.getBlogListByType(type);
        }
        if (bkBlogList.size() == 0) {
            logger.error("数据库中不存在任何博客文章！");
        }
        return bkBlogList;
    }



    /**
     * 获取博客的分页信息
     *
     * @param page  页数
     * @param limit 每页显示的条数
     * @param type  查询的博客类型
     * @return PageInfo<BKBlog>
     */
    @Override
    public PageInfo<BKBlog> getBlogPageList(Integer page, Integer limit, String type) {
        if (page == null || page < 0) {
            logger.error("pageNum is " + page);
            throw new IllegalArgumentException("页码错误！不能为" + page);
        }
        if (limit == null || limit < 0 || limit > BLOG_LIST_COUNT_MAX) {
            logger.error("limit is " + limit);
            throw new IllegalArgumentException("每页显示的条数不能为" + limit);
        }
        PageHelper.startPage(page, limit);
        List<BKBlog> bkBlogList = getBlogList(type);
        if (bkBlogList.size() == 0) {
            logger.error("数据库中没有博客数据！");
        }
        return new PageInfo<>(bkBlogList);
    }

    /**
     * 获取博客的全部列表,用于后台显示数据表格
     * @param page  页数
     * @param limit 每页多少条
     * @return List<BLog>
     */
    @Override
    public PageInfo<BKBlog> getBackBlogList(Integer page, Integer limit) {
        if (page == null || page <= 0) {
            page = 1;
        }
        PageHelper.startPage(page, limit);
        List<BKBlog> backBKBlogList = bkBlogMapper.getBackBlogList();
        return new PageInfo<>(backBKBlogList);
    }

    /**
     * 根据博客的id增加博客的评论数
     *
     * @param blogId 博客Id
     * @return >0 更新成功 =0 失败
     */
    @Override
    public boolean updateBlogCommentCountById(Integer blogId) {
        //可能出现blogId不存在的情况
        if (blogId == null) {
            logger.info("blogId is null！");
            throw new NullPointerException("参数为空！");
        }
        Integer result;
        try {
            result = bkBlogMapper.updateBlogCommentCountById(blogId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return result > 0;
    }

    /**
     * 根据博客的id刷新博客的浏览量
     *
     * @param blogId 博客Id
     * @return true 成功 false 失败
     */
    @Override
    public boolean updateBlogViewCountById(Integer blogId) {
        if (blogId == null) {
            logger.info("blogId is null！");
            throw new NullPointerException("参数为空！");
        }
        try {
            bkBlogMapper.updateBlogViewCountById(blogId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * 获取一个DO对象
     *
     * @param t 获取的凭证，比如实体的某个字段  id 、 title
     * @return 满足条件的实体对象
     */
    @Override
    public <T> T getOne(T t) {
        return super.getOne(t);
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
        super.addOne(t, saveToFastDFS, file);
        BKBlog blog;
        Integer addOneResult;
        try {
            blog = (BKBlog) t;
            blog.check();
            addOneResult = bkBlogMapper.addOne(blog);
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
     * @param t      需要删除的实体
     * @param remove 是否从数据库中删除记录
     * @return true 删除成功  false 删除失败
     */
    @Override
    public <T> boolean deleteOne(T t, boolean remove) {
        return super.deleteOne(t, remove);
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
            return bkBlogMapper.getMultiple();
        }
        return bkBlogMapper.getMultiple();
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
        return super.deleteMultiple(primaryKey, remove);
    }
}
