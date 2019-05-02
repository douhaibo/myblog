package cn.ljtnono.myblog.service;

import cn.ljtnono.myblog.entity.BKUser;

/**
 *  用户业务接口
 *  @author ljt
 *  @date 2018/12/8
 *  @version 1.0
*/
public interface BKUserService {

    /**
     * 根据用户名和密码获取数据库中的用户对象
     * @param username 用户名和面
     * @return 完整的用户信息
     */
    BKUser getUserByUsername(String username);

    /**
     * 根据根据不同的方式来修改面
     * @param newPassword 新的密码
     * @param type 1、根据旧密码修改新秘密 2、根据邮箱地址修改新密码 3、根据电话修改密码
     * @return json 对象
     */
    String updatePassword(String newPassword, Integer type);
}
