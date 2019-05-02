package cn.ljtnono.myblog.service;

import cn.ljtnono.myblog.entity.BKTag;
import cn.ljtnono.myblog.pojo.PageParam;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

/**
 *  处理标签业务的接口
 *  @author ljt
 *  @date 2018/12/11
 *  @version 1.0
*/
public interface BKTagService extends EssentialService{

    /**
     * 获取首页标签云的标签
     * @return Set<BKTag>
     */
    Set<BKTag> getCloudTags();

    /**
     * 获取技术文章页面的标签
     * @return Set<BKTag>
     */
    Set<BKTag> getArticleTags();

    /**
     * 获取TagSet
     * @param count 查询的条数
     * @param className 根据tag里面包含的className 来进行模糊查询
     * @return tag的set集合
     */
    Set<BKTag> getTagSet(Integer count, String className);

    /**
     * 分页查询标签的信息
     * @param page 页码
     * @param limit 每页查询的条数
     * @return list
     */
    PageInfo<BKTag> getPageTagSet(Integer page, Integer limit);


}
