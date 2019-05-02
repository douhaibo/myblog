package cn.ljtnono.myblog.shiro;

import cn.ljtnono.myblog.entity.BKUser;
import cn.ljtnono.myblog.exception.MyAuthenticationException;
import cn.ljtnono.myblog.mapper.BKAuthorizationMapper;
import cn.ljtnono.myblog.service.BKUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;

/**
 *  处理username和password realm关系
 *  @author ljt
 *  @date 2018/12/7
 *  @version 1.0
*/
public class UserRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(UserRealm.class);

    /**
     * 公盐
     */
    private static final String SALT_PUBLIC = "JT Geek";

    @Autowired
    private BKUserService service;

    @Autowired
    private BKAuthorizationMapper bkAuthorizationMapper;

    @Override
    public String getName() {
        return "UserRealm";
    }

    /**
     * 授权操作
     * @param principals 收钱
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        BKUser user = (BKUser) principals.getPrimaryPrincipal();
        if (user == null) {
            logger.error("用户过期！请重新登录");
            throw new MyAuthenticationException("用户已经过期！请重新登录");
        }
        Set<String> roles = bkAuthorizationMapper.getRoles(user.getUsername());
        //执行授权操作
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (roles != null) {
            info.addRoles(roles);
        }
        return info;
    }

    /**
     * 认证
     * @param authenticationToken 获取token
     * @return AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        BKUser user = service.getUserByUsername(username);
        if (user == null) {
            logger.error("未知用户！");
            throw new UnknownAccountException("没有这个用户！");
        }
        //这里以公盐 + 私盐的形式
        MySimpleByteSource salt = new MySimpleByteSource(SALT_PUBLIC + username);
        return new SimpleAuthenticationInfo(user,user.getPassword(),salt,getName());
    }

}
