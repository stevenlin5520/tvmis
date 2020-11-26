package com.steven.television.util;

/**
 * @author steven
 * @desc
 * @date 2020/11/11 1:50
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param value
     * @return
     */
    public static boolean isBlank(String value){
        if(value == null || value.length() == 0){
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否不为空
     * @param value
     * @return
     */
    public static boolean isNotBlank(String value){
        return !isBlank(value);
    }

    public static boolean isEquale(String value1, String value2){
        if(isNotBlank(value1)){
            return value1.equalsIgnoreCase(value2);
        }
        if(isBlank(value2)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isNotEquale(String value1, String value2){
        return !isEquale(value1, value2);
    }
}
