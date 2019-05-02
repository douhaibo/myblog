package cn.ljtnono.myblog.service;

import cn.ljtnono.myblog.entity.BKBook;

import java.util.List;

/**
 *  处理书单业务的接口
 *  @author ljt
 *  @date 2018/12/11
 *  @version 1.0
*/
public interface BKBookService {

    /**
     * 获取首页书单列表
     * @return List<BKBook>
     */
    List<BKBook> getBlogBookList();

    /**
     * 获取aboutme页面的书单内容
     * @return List<BKBook>
     */
    List<BKBook> getAboutMeBookList();
}
