package cn.ljtnono.myblog.entity;

import cn.ljtnono.myblog.exception.FieldIllegalException;

/**
 *  检查参数合法性
 *  @author ljt
 *  @date 2019/3/16
 *  @version 1.0
*/
public interface CheckAble {

    /**
     * 检查整个实体的每个字段是否合法
     * @return true 没有问题
     * @throws FieldIllegalException 记录哪个字段出现问题
     */
    boolean check() throws FieldIllegalException;

}
