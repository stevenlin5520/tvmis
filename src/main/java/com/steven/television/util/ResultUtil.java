package com.steven.television.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author steven
 * @desc
 * @date 2020/9/25 15:45
 */
public class ResultUtil extends HashMap {

    public static Map<String,Object> toResult(boolean state,String msg,Object obj){
        Map<String, Object> map = new HashMap<String, Object>(4);
        map.put("state",state);
        map.put("msg",msg);
        map.put("result",obj);
        return map;
    }

    public static Map<String,Object> toSuccessResult(Object obj){
        Map<String, Object> map = new HashMap<String, Object>(4);
        map.put("state",true);
        map.put("msg","操作成功");
        map.put("result",obj);
        return map;
    }

    public static Map<String,Object> toFailResult(Object obj){
        Map<String, Object> map = new HashMap<String, Object>(4);
        map.put("state",false);
        map.put("msg","操作失败");
        map.put("result",obj);
        return map;
    }

}
