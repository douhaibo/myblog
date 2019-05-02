package cn.ljtnono.myblog.provider;

import java.util.stream.IntStream;

/**
 *  动态SQL帮助类
 *  @author ljt
 *  @date 2018/12/11
 *  @version 1.0
*/
public class BKProviderHelper {


    /**
     * 防止实例化
     */
    private BKProviderHelper(){}

    /**
     * 将Integer数组分成 1,2,3的以逗号隔开的形式
     * @param params Integer数组
     * @throws NullPointerException 当输入参数为null时抛出空指针异常
     * @return 1,2,3形式
     */
    public static String integerArrayToQueryString(Integer[] params) {
        return arrayToQueryString(params);
    }

    /**
     * 将Integer数组分成 1,2,3的以逗号隔开的形式
     * @param params Integer数组
     * @throws NullPointerException 当输入参数为null时抛出空指针异常
     * @return 1,2,3形式
     */
    public static String stringArrayToQueryString(String[] params) {
        StringBuilder stringBuilder;
        for (int i = 0; i < params.length; i++) {
            stringBuilder = new StringBuilder();
            params[i] = stringBuilder.append("'")
                    .append(params[i])
                    .append("'")
                    .toString();
        }
        return arrayToQueryString(params);
    }

    /**
     *  将数组转换为in查询的形式
     * @param params 需要转换的数组
     * @return 字符串
     */
    private static String arrayToQueryString(Object[] params) {
        if (params == null || params.length == 0) {
            throw new NullPointerException("参数错误！");
        }
        StringBuilder result = new StringBuilder();
        IntStream.range(0, params.length).forEach(i -> {
            if (i != params.length - 1) {
                result.append(params[i]).append(",");
            } else {
                result.append(params[i]);
            }
        });
        return result.toString();
    }

}
