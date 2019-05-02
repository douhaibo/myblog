package cn.ljtnono.myblog.mapper;

import cn.ljtnono.myblog.entity.BKUser;
import org.apache.ibatis.annotations.Select;
import static cn.ljtnono.myblog.common.Tables.USERTABLE;

/**
 *  用户
 *  @author ljt
 *  @date 2018/12/8
 *  @version 1.0
*/
public interface BKUserMapper {

    /**
     * 通过username 和 password 获取用户对象
     * @param username 携带的是用户的账户和用户的MD5密码信息
     * @return 返回的是用户信息
     */
    @Select("select * from " + USERTABLE + " where username = #{v}")
    BKUser getUserByUsername(String username);
}
