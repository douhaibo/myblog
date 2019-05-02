package cn.ljtnono.myblog.exception;

import java.io.Serializable;

/**
 *  参数不存在异常
 *  @author ljt
 *  @date 2018/12/9
 *  @version 1.0
*/
public class ParamNotPresentException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 7494639079992444819L;

    public ParamNotPresentException() {
        super();
    }

    public ParamNotPresentException(String message) {
        super(message);
    }

    public ParamNotPresentException(Throwable throwable) {
        super(throwable);
    }

    public ParamNotPresentException(String message,Throwable throwable) {
        super(message,throwable);
    }
}
