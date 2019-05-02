package cn.ljtnono.myblog.utils;

import cn.ljtnono.myblog.entity.BKPermission;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 *  封装了一些JSON的常用方法
 *  @author ljt
 *  @date 2018/12/2
 *  @version 1.0
*/
public class JSONUtil {

    /**
     * 不允许你实例化
     */
    private JSONUtil() {
        super();
    }

    /**
     *
     * 只能使用使用简单的类型bean类型转换，如果是成员变量包含其他对象的不能使用该类
     * 如果是字段名setXxxYxx  这种的也不能使用该函数
     * @param json 需要转换的json字符串
     * @param bean 转换的class类
     * @return bean的实例
     * @throws Exception 找不到类 等异常
     */
    public static <T> T JSONStringToBean(String json, Class<T> bean) throws Exception {
        // 如果是接口的话，那么报错
        T newInstance;
        if (bean.isInterface()) {
            throw new RuntimeException("can not deal with a interface");
        }
        // 如果是基本类型，报错
        if (json == null || json.length() == 0) {
            throw new RuntimeException("the json is null or empty");
        } else {
            Map<String, Object> map = JSONToMap(json);
            newInstance = bean.newInstance();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String propertyName = entry.getKey();
                Object value = entry.getValue();
                // 这里只适合setName 这种 如果由多个单词组成就不行了
                String setMethodName = getSetMethodName(propertyName);
                // 获取set函数的参数列表类型 这里只能获取到的是String类型的参数，需要进一步处理
                Class<?> type = getPropertyType(bean, propertyName);
                value = convertValType(value, type);
                // 这里要保证函数是
                bean.getMethod(setMethodName, type).invoke(newInstance, value);
            }
        }
        return newInstance;
    }

    /**
     * 获取字段的set函数名字
     * @param propertyName 字段
     * @return set函数名
     */
    private static String getSetMethodName(String propertyName) {
        return "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    /**
     * 获取字段的类型
     * @param bean 字段所属类
     * @param propertyName 字段
     * @return 字段类型.class
     * @throws NoSuchFieldException  没有这个字段
     */
    private static <T> Class<?> getPropertyType(Class<T> bean, String propertyName) throws NoSuchFieldException {
        Class<?> type;
        if (hasPropertyDeclared(bean, propertyName)) {
            type = bean.getDeclaredField(propertyName).getType();
        } else {
            type = bean.getField(propertyName).getType();
        }
        return type;
    }

    /**
     * 将bean 转换成JSON 字符串
     * @param bean 需要转换的bean
     * @param <T> 泛型
     * @return 转换过后的json字符串
     */
    public static <T> String beanToJSONString(Class<T> bean) {
        return null;
    }

    /**
     * 判断某个类的属性是否是可获取的，即 public private protected 但是不包括父类继承的对象
     * 根据这个判断使用getDeclaredFields  还是getFields 来设置属性值
     * @param bean bean类型
     * @param propertyName 需要检测的字段
     * @return true 可以获取 false 不能获取
     */
    private static <T> boolean hasPropertyDeclared(Class<T> bean, String propertyName) {
        Field[] declaredFields = bean.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals(propertyName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取一个Bean的所有字段。包括父类的public字段
     * @param bean 需要获取的类
     * @return Field[] 数组
     */
	private static Field[] getBeanFieldsAll(Class bean) {
		//如果该bean存在父类
		Field[] fields = bean.getDeclaredFields();
		Field[] result = null;
		if (bean.getSuperclass() != Object.class) {
			Field[] father = bean.getSuperclass().getFields();
			//数组拼接   没有必要去重，直接把重复的字段重复赋值就OK
			result = new Field[fields.length + father.length];
			System.arraycopy(father,0,result,0,father.length);
			System.arraycopy(fields, 0, result, father.length, fields.length);
		}
		if (result != null) {
			return result;
		}
		return fields;
	}

    /**
     * 将json字符串转换成map
     * @param json json字符串
     * @return 键值对
     */
    private static Map<String, Object> JSONToMap(String json) {
        // 1.处理JSONString
        Map<String, Object> map = new HashMap<>();
        String[] entity = json.replace("{", "").replace("}", "").split(",");
        for (String s : entity) {
            String[] split = s.split(":");
            map.put(split[0], split[1]);
        }
        return map;
    }

    /**
     * 根据 参数类型将Object 转换成相应类型的值
     * 目前不支持Date类型
     * @param value 要转换的值
     * @param fieldTypeClass 要转换的类型
     * @return Object
     */
    private static Object convertValType(Object value, Class<?> fieldTypeClass) {
        Object retVal = null;
        if (Long.class.getName().equals(fieldTypeClass.getName())
                || long.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Long.parseLong(value.toString());
        } else if (Integer.class.getName().equals(fieldTypeClass.getName())
                || int.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Integer.parseInt(value.toString());
        } else if (Float.class.getName().equals(fieldTypeClass.getName())
                || float.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Float.parseFloat(value.toString());
        } else if (Double.class.getName().equals(fieldTypeClass.getName())
                || double.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Double.parseDouble(value.toString());
        } else if (String.class.getName().equals(fieldTypeClass.getName())) {
            retVal = value.toString();
        }
        return retVal;
    }



    public static void main(String[] args){
        String json = "{id:12,name:123,res:\"hello\"}";
        try {
            BKPermission permission = JSONStringToBean(json, BKPermission.class);
            System.out.println(permission.getRes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
