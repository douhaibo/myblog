package cn.ljtnono.myblog.service;

import cn.ljtnono.myblog.entity.BKLink;

import java.util.List;

/**
 *  处理链接的业务接口
 *  @author ljt
 *  @date 2018/12/9
 *  @version 1.0
*/
public interface BKLinkService extends EssentialService{


    /**
     * 根据id数组获取link数组
     * @param linkIds id数组
     * @return 该id数组的link列表
     */
    List<BKLink> getLinksByIds(Integer[] linkIds);

    /**
     * 获取技术文章的友情链接
     * @return List<BKLink>
     */
    List<BKLink> getArticlesFriendLinks();

    /**
     * 获取首页友情链接
     * @return List<BKLink>
     */
    List<BKLink> getBlogFriendLinks();


    /**
     * 获取所有的友情链接 不区分具体哪个页面
     * @return 所有友情链接列表
     */
    List<BKLink> getFriendLinkList();
}
