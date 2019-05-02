package cn.ljtnono.myblog.service.impl;

import cn.ljtnono.myblog.entity.BKUser;
import cn.ljtnono.myblog.mapper.BKUserMapper;
import cn.ljtnono.myblog.service.BKUserService;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  用户接口的实现类
 *  @author ljt
 *  @date 2018/12/8
 *  @version 1.0
*/
@Service
public class BKUserServiceImpl implements BKUserService {

    @Autowired
    private BKUserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(BKUserServiceImpl.class);

    /**
     * 根据username查询相应的user实体类
     * @param username 用户名和面
     * @return 相应的User实体
     */
    @Override
    public BKUser getUserByUsername(String username) {
        if (TextUtils.isEmpty(username)) {
            logger.error("username is " + username);
            throw new IllegalArgumentException("参数错误！");
        }
        return userMapper.getUserByUsername(username);
    }

    /**
     * 根据根据不同的方式来修改面
     *
     * @param newPassword 新的密码
     * @param type        1、根据旧密码修改新秘密 2、根据邮箱地址修改新密码 3、根据电话修改密码
     * @return json 对象
     */
    @Override
    public String updatePassword(String newPassword, Integer type) {
        return null;
    }

    public Integer updatePasswordByOldPassword() {
        return null;
    }
}
