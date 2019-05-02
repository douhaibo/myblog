package cn.ljtnono.myblog.exception;

import java.io.Serializable;

/**
 *  shiro 认证错误
 *  @author ljt
 *  @date 2018/12/9
 *  @version 1.0
*/
public class MyAuthenticationException extends RuntimeException implements Serializable {


    private static final long serialVersionUID = -6152795108337532556L;

    public MyAuthenticationException() {
        super();
    }

    public MyAuthenticationException(String message) {
        super(message);
    }

    public MyAuthenticationException(Throwable throwable) {
        super(throwable);
    }

    public MyAuthenticationException(String message,Throwable throwable) {
        super(message,throwable);
    }

}
