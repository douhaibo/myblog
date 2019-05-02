package cn.ljtnono.myblog.fastdfs;

import java.io.Serializable;

/**
 *  fastdfs 异常的父类
 *  @author ljt
 *  @date 2019/3/29
 *  @version 1.0
*/
public class FastDFSException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 5069717293599804931L;

    public FastDFSException() {
        super();
    }

    public FastDFSException(String message) {
        super(message);
    }
}
