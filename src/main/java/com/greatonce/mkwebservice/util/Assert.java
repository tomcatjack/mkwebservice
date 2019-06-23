package com.greatonce.mkwebservice.util;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 断言类
 * @author buer
 * @version 2017-08-21 18:24 1.0
 */
public final class Assert {
    /**
     * 空UUID
     */
    public static final UUID EMPTY_UUID = UUID.fromString("0-0-0-0-0");
    /**
     * 数字表达式
     */
    public static final Pattern NUMBER_PATTERN = Pattern.compile("^(-?\\d+)?$");
    /**
     * 小数表达式
     */
    public static final Pattern DECIMAL_PATTERN = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
    /**
     * 字母表达式
     */
    public static final Pattern LETTER_PATTERN = Pattern.compile("^[A-Za-z]+$");
    /**
     * 邮件表达式
     */
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.+-]+@[\\w.-]+\\.[\\w.-]+$");
    /**
     * 手机表达式
     */
    public static final Pattern MOBILE_PATTERN = Pattern.compile("^1([3-9][0-9]{9})$");
    //region 是否判断

    /**
     * 是否为null
     * @param object 对象
     */
    public static boolean isNull(Object object){
        return null == object;

    }

    /**
     * 是否为空
     * @param chars 字符串
     */
    public static boolean isEmpty(CharSequence chars){
        return isNull(chars) || chars.length() == 0;
    }

    /**
     * 是否为空
     * @param array 数组
     * @param <T>   类型
     */
    public static <T> boolean isEmpty(T[] array){
        return isNull(array) || array.length == 0;
    }

    /**
     * 是否空uuid
     * @param uuid uuid
     */
    public static boolean isEmpty(UUID uuid){
        return isNull(uuid) || uuid.equals(EMPTY_UUID);
    }

    /**
     * Map是否为空
     * @param map map
     */
    public static boolean isEmpty(Map map){
        return isNull(map) || map.isEmpty();
    }

    /**
     * 是否整数
     * @param numberChars 字符串
     */
    public static boolean isNumber(CharSequence numberChars){
        if(isEmpty(numberChars)){
            return false;
        } else{
            Matcher matcher = NUMBER_PATTERN.matcher(numberChars);
            return matcher.matches();
        }
    }

    /**
     * 是否小数
     * @param decimalChars 字符串
     */
    public static boolean isDecimal(CharSequence decimalChars){
        if(isEmpty(decimalChars)){
            return false;
        } else{
            Matcher matcher = DECIMAL_PATTERN.matcher(decimalChars);
            return matcher.matches();
        }
    }

    /**
     * 是否字母
     * @param letterChars 字符串
     */
    public static boolean isLetter(CharSequence letterChars){
        if(isEmpty(letterChars)){
            return false;
        } else{
            Matcher matcher = LETTER_PATTERN.matcher(letterChars);
            return matcher.matches();
        }
    }

    /**
     * 是否邮箱地址
     * @param emailChars 邮箱地址字符串
     */
    public static boolean isEMail(CharSequence emailChars){
        if(isEmpty(emailChars)){
            return false;
        } else{
            Matcher matcher = EMAIL_PATTERN.matcher(emailChars);
            return matcher.matches();
        }
    }

    /**
     * 是否手机
     * @param mobileChars 手机号码
     */
    public static boolean isMobile(CharSequence mobileChars){
        if(isEmpty(mobileChars)){
            return false;
        } else{
            Matcher matcher = MOBILE_PATTERN.matcher(mobileChars);
            return matcher.matches();
        }
    }

    /**
     * 是否为空
     * @param collection 集合
     */
    public static boolean isEmpty(Collection collection){
        return isNull(collection) || collection.isEmpty();
    }

    /**
     * 判断两个字符串是否一样，都未NULL也返回true
     * @param str1 字符串1
     * @param str2 字符串2
     */
    public static boolean isSame(String str1, String str2){
        if(!Assert.isEmpty(str1)){
            return str1.equalsIgnoreCase(str2);
        }
        if(!Assert.isEmpty(str2)){
            return str2.equalsIgnoreCase(str1);
        }
        return true;
    }

    /**
     * 是否为真
     * @param value 值
     */
    public static boolean isTrue(Boolean value){
        return value != null && value;
    }

    /**
     * 是否为假
     * @param value 值
     */
    public static boolean isFalse(Boolean value){
        return value != null && !value;
    }
    //endregion
    //强制要求判断

    /**
     * 判断必需为真
     * @param expression 逻辑表达式
     * @param message    异常消息
     */
    public static void notFalse(Boolean expression, String message){
        if(!isFalse(expression)){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断必需为假
     * @param expression 逻辑表达式
     * @param message    异常消息
     */
    public static void notTrue(Boolean expression, String message){
        if(!isTrue(expression)){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断对象不能为空。
     * @param object  对象
     * @param message 异常消息
     */
    public static void notNull(Object object, String message){
        if(isNull(object)){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断字符串不能为空
     * @param chars   判断的字符串对象
     * @param message 异常消息
     */
    public static void notEmpty(CharSequence chars, String message){
        if(isEmpty(chars)){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断数组不能为空
     * @param <T>     数组类型
     * @param array   数组
     * @param message 异常消息
     */
    public static <T> void notEmpty(T[] array, String message){
        if(isEmpty(array)){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断容器不能为空
     * @param collection 集合
     * @param message    异常消息
     */
    public static void notEmpty(Collection collection, String message){
        if(isEmpty(collection)){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断键值对对象不能为空
     * @param map     map
     * @param message 异常消息
     */
    public static void notEmpty(Map map, String message){
        if(isEmpty(map)){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断UUID对象不能为空
     * @param uuid    uuid
     * @param message 异常消息
     */
    public static void notEmpty(UUID uuid, String message){
        if(isEmpty(uuid)){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断是否为整数
     * @param numberChars 整数
     * @param message     异常消息
     */
    public static void notNumber(CharSequence numberChars, String message){
        if(!isNumber(numberChars)){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断是否为浮点数
     * @param decimalChars 浮点数
     * @param message      异常消息
     */
    public static void notDecimal(CharSequence decimalChars, String message){
        if(!isDecimal(decimalChars)){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断是否为字母
     * @param letterChars 字母
     * @param message     异常消息
     */
    public static void notLetter(CharSequence letterChars, String message){
        if(!isLetter(letterChars)){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断是否为电子邮箱地址
     * @param emailChars 电子邮箱地址
     * @param message    异常消息
     */
    public static void notEMail(CharSequence emailChars, String message){
        if(!isEMail(emailChars)){
            throw new IllegalArgumentException(message);
        }
    }
    //endregion
}