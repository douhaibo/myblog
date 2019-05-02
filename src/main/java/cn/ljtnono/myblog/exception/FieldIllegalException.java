package cn.ljtnono.myblog.exception;

import java.io.Serializable;

/**
 *  字段不合法异常
 *  @author ljt
 *  @date 2019/3/16
 *  @version 1.0
*/
public class FieldIllegalException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -8845008253983426649L;

    public FieldIllegalException() {
        super();
    }

    public FieldIllegalException(String message) {
        super(message);
    }

}
