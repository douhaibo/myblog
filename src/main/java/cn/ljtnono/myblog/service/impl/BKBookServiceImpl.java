package cn.ljtnono.myblog.service.impl;

import cn.ljtnono.myblog.entity.BKBook;
import cn.ljtnono.myblog.mapper.BKBookMapper;
import cn.ljtnono.myblog.service.BKBookService;
import cn.ljtnono.myblog.service.BKImgService;
import cn.ljtnono.myblog.utils.RedisUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.ljtnono.myblog.entity.BKBook.MAX_DESC_LENGTH;

/**
 *  处理书单业务的实现类
 *  @author ljt
 *  @date 2018/12/11
 *  @version 1.0
*/
@Service
public class BKBookServiceImpl implements BKBookService {

    @Autowired
    private BKBookMapper bookMapper;

    @Autowired
    private BKImgService imgService;

    private Logger logger = LoggerFactory.getLogger(BKBookServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取首页书单列表
     *
     * @return List<BKBook>
     */
    @Override
    public List<BKBook> getBlogBookList() {
        List<BKBook> myBookList = bookMapper.getMyBookList();
        initBlogBookList(myBookList);
        return myBookList;
    }

    /**
     * 获取aboutme页面的书单内容
     * @return List<BKBook>
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<BKBook> getAboutMeBookList() {
        //首先从缓存中找
        try {
            Map<Object, Object> books = redisUtil.hmget(BKBook.REDIS_PREFIX);
            if (books != null && books.size() > 0) {
                List<BKBook> result = new ArrayList<>();
                books.forEach((o, o2) -> {
                    result.add((BKBook) JSONObject.toBean(JSONObject.fromObject(o2), BKBook.class));
                });
                initBlogBookList(result);
                return result;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.info("没有走缓存！");
        }
        List<BKBook> bookListAll = bookMapper.getBookListAll();
        initBlogBookList(bookListAll);
        if (bookListAll.size() > 0) {
            try {
                bookListAll.forEach(book -> {
                    redisUtil.hset(BKBook.REDIS_PREFIX,book.getId() + "",JSONObject.fromObject(book),60 * 60 * 24);
                });
            } catch (Exception e) {
                logger.info("缓存失败！原因：" + e.getMessage());
            }
        }
        return bookListAll;
    }

    /**
     * 对Book列表进行一些处理，
     * @param myBookList 需要处理的book列表
     */
    private void initBlogBookList(List<BKBook> myBookList) {
        if (myBookList.size() == 0) {
            logger.info("bookList为空！");
            return;
        }
        myBookList.forEach(book -> {
            if (book.getDesc().length() >= MAX_DESC_LENGTH) {
                //控制书单的简介的文字不超过最大值
                book.setDesc(book.getDesc().substring(0,MAX_DESC_LENGTH));
            }
        });
    }
}
