package cn.ljtnono.myblog.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.Map;
import java.util.Set;

import static cn.ljtnono.myblog.common.Tables.*;

/**
 *  用于查询用户的角色 以及权限
 *  @author ljt
 *  @date 2018/9/15
 *  @version 1.0
*/
public interface BKAuthorizationMapper {


    /**
     * 登录时获取用户角色信息
     * @param username 用户的用户名
     * @return 角色数组
     */
    @Select("select " + ROLETABLE + ".name" + " from " + USER_ROLE_TABLE +
            " right join " + ROLETABLE + " on " + ROLETABLE + ".id = " +
            USER_ROLE_TABLE + ".rid right join " + USERTABLE + " on " +
            USERTABLE + ".id = " + USER_ROLE_TABLE + ".uid" + " where " + USERTABLE + ".username = #{v}")
    Set<String> getRoles(String username);

    /**
     * 登陆时获取用户权限信息
     * @param map map
     * @return 权限集合
     */
    Set<String> getPermissions(Map<String, Object> map);
}
