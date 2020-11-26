package com.steven.television.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author steven
 * @author steven
 * @desc
 * @date 2020/9/25 15:30
 */
public class ConvertUtil<T> {

    public static String objectToJson(Object obj){
        return JSONObject.toJSONString(obj);
    }

    public static Map<String,Object> entityToMap(Object obj){
        return JSONObject.parseObject(objectToJson(obj),Map.class);
    }

}
