package cn.ljtnono.myblog.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 *  Md5 加密工具
 *  @author ljt
 *  @date 2018/12/9
 *  @version 1.0
*/
public class Md5Util {
    public static String getMD5(String source) {
        return getMD5(source.getBytes());
    }

    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte tmp[];
            synchronized (Md5Util.class) {
                md.update(source);
                tmp = md.digest();
            }
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            // 换后的结果转换为字符串
            s = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


    public static String getMD5LowerCase(String source) {
        return getMD5(source).toLowerCase();
    }

    /**
     * 温馨提示：记得在注册中密码存入数据库前也记得加密哦，提供一个utils方法
     *     进行shiro加密，返回加密后的结果
     * @param pass 要加密的铭文
     * @param saltSource 盐值
     * @return 密文
     */
    public static String md5(String pass,String saltSource){
        String hashAlgorithmName = "MD5";
        Object salt = ByteSource.Util.bytes(saltSource);
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, pass, salt, hashIterations);
        return result.toString();
    }


}
