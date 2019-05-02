package cn.ljtnono.myblog.service.impl;

import cn.ljtnono.myblog.entity.BKTimeLine;
import cn.ljtnono.myblog.mapper.BKTimeLineMapper;
import cn.ljtnono.myblog.service.BKTimeLineService;
import cn.ljtnono.myblog.utils.RedisUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  时间轴的业务
 *  @author ljt
 *  @date 2019/1/19
 *  @version 1.0
*/
@Service
public class BKTimeLineServiceImpl implements BKTimeLineService {

    @Autowired
    private BKTimeLineMapper bkTimeLineMapper;

    @Autowired
    private RedisUtil redisUtil;

    private final Logger logger = LoggerFactory.getLogger(BKTimeLineServiceImpl.class);

    @Override
    public List<BKTimeLine> getBKTimeLineAll() {
        try {
            Map<Object, Object> hmget = redisUtil.hmget(BKTimeLine.REDIS_PREFIX);
            List<BKTimeLine> result = new ArrayList<>();
            if (hmget != null && hmget.size() > 0) {
                hmget.forEach((o, o2) -> {
                    result.add((BKTimeLine) JSONObject.toBean(JSONObject.fromObject(o2),BKTimeLine.class));
                });
                return result;
            }
        } catch (Exception e) {
            logger.info("没有走缓存，原因：" + e.getMessage());
        }
        List<BKTimeLine> bkTimeLineAll = bkTimeLineMapper.getBKTimeLineAll();
        if (bkTimeLineAll != null && bkTimeLineAll.size() > 0) {
            try {
                bkTimeLineAll.forEach(bkTimeLine -> {
                    redisUtil.hset(BKTimeLine.REDIS_PREFIX,bkTimeLine.getId() + "",bkTimeLine,24 * 60 * 60);
                });
            } catch (Exception e) {
                logger.info("缓存失败，原因：" + e.getMessage());
            }
        }
        return bkTimeLineAll;
    }
}
