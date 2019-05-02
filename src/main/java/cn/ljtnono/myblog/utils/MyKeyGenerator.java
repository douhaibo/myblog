package cn.ljtnono.myblog.utils;


import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 *  cache key spring注解生成策略
 *  @author ljt
 *  @date 2018/10/12
 *  @version 1.0
*/
public class MyKeyGenerator implements KeyGenerator {


    private static final String CACHEPREFIX = "myblog";

    @Override
    public Object generate(Object o, Method method, Object... objects) {
        StringBuilder sb = new StringBuilder();
        final String targetName = o.getClass().getName();
        final String methodName = method.getName();
        sb.append(CACHEPREFIX);
        sb.append(targetName);
        sb.append("&");
        sb.append(methodName);
        sb.append("%");
        for (Object object : objects) {
            if (isBaseType(object)) {
                sb.append(object);
            } else {
                sb.append(object.getClass().getName());
            }
            sb.append("$");
        }
        return sb.toString();
    }

    /**
     * 判断是否是java基本类型
     * 通过java class 来比较是否相等
     * @param object   参数
     * @return true  是  false  不是
     */
    private  boolean isBaseType(Object object) {
        Class className = object.getClass();
        return className.equals(Integer.class) ||
                className.equals(Byte.class) ||
                className.equals(Long.class) ||
                className.equals(Double.class) ||
                className.equals(Float.class) ||
                className.equals(Character.class) ||
                className.equals(Short.class) ||
                className.equals(Boolean.class);
    }
}
