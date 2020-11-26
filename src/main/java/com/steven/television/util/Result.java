package com.steven.television.util;

/**
 * @author steven
 * @desc
 * @date 2020/11/11 1:45
 */
public class Result {
    private boolean state;
    private String msg;
    private Object result;

    public Result(boolean state, String msg, Object result) {
        this.state = state;
        this.msg = msg;
        this.result = result;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static Result success(Object result){
        return new Result(true,"操作成功",result);
    }

    public static Result failed(Object result){
        return new Result(false,"操作失败",result);
    }

    public static Result toResult(boolean state,Object result){
        if(state){
            return new Result(true,"操作成功",result);
        }
        return new Result(false,"操作失败",result);
    }
}
