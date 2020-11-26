package com.steven.television.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @desc 参数处理工具类
 * @author steven
 * @date 2020/11/10 9:47
 */
public class ParamsUtil {

    /**
     * 判断参数为空
     * @param params
     * @return 有为空的则返回false
     */
    public static boolean validateBlank(Object... params){
        for (Object param : params) {
            if(param instanceof String && (param == null || ((String) param).length() == 0)){
                return false;
            }
            if((param instanceof Integer || param instanceof Long || param instanceof BigDecimal) && param==null){
                return false;
            }
            if(param instanceof Map && (param == null || ((Map) param).isEmpty())){
                return false;
            }
            if((param instanceof List || param instanceof Set) && (param == null || ((List)param).size() == 0)){
                return false;
            }
            if(param instanceof Object && param == null){
                return false;
            }
        }
        return true;
    }


    public static Map<String,Object> returnValidate(Object... params){
        boolean validateBlank = validateBlank(params);
        if(validateBlank){
            return ResultUtil.toResult(true,"校验成功",null);
        }
        return ResultUtil.toResult(false,"参数有误",null);
    }
}
