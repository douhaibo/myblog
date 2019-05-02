package cn.ljtnono.myblog.service.impl;

import cn.ljtnono.myblog.entity.BKSkill;
import cn.ljtnono.myblog.mapper.BKSkillMapper;
import cn.ljtnono.myblog.service.BKSkillService;
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

import static cn.ljtnono.myblog.entity.BKSkill.BKSKILL_REDIS_HASH_KEY;

@Service
public class BKSkillServiceImpl implements BKSkillService {

    @Autowired
    private BKSkillMapper bkSkillMapper;

    private Logger logger = LoggerFactory.getLogger(BKSkillServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<BKSkill> getBKSkillAll() {
        try {
            Map<Object, Object> hmget = redisUtil.hmget(BKSKILL_REDIS_HASH_KEY);
            List<BKSkill> result = new ArrayList<>();
            if (hmget != null && hmget.size() > 0) {
                logger.info("获取缓存实例");
                hmget.forEach((o, o2) -> {
                    result.add((BKSkill) JSONObject.toBean(JSONObject.fromObject(o2),BKSkill.class));
                });
                return result;
            }
        } catch (Exception e) {
            logger.info("没有走缓存！");
            System.out.println(e.getMessage());
        }
        List<BKSkill> all = bkSkillMapper.getBKSkillAll();
        if (all.size() > 0) {
            try {
                all.forEach(bkSkill -> {
                    redisUtil.hset(BKSKILL_REDIS_HASH_KEY,BKSkill.class.getSimpleName() + bkSkill.getId(),bkSkill,24 * 60 * 60);
                });
                logger.info("缓存成功！hashkey = " + BKSKILL_REDIS_HASH_KEY);
            } catch (Exception e) {
                logger.error("缓存失败！原因：" + e.getMessage());
            }
        }
        return all;
    }
}
