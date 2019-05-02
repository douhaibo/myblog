package cn.ljtnono.myblog.exception;

import java.io.Serializable;

/**
 *  数据库操作异常
 *  @author ljt
 *  @date 2019/3/28
 *  @version 1.0
*/
public class DataBaseOperationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -2962946724513012953L;

    public DataBaseOperationException() {
        super();
    }

    public DataBaseOperationException(String message) {
        super(message);
    }
}
