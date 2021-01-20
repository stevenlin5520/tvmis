package com.steven.television.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author steven
 * @desc
 * @date 2020/11/15 0:24
 */
public class Page<T> implements Serializable {
    //当前页
    private int page;
    //每页的数量
    private int limit;

    //当前页面第一个元素在数据库中的行号
    private int startRow;
    //当前页面最后一个元素在数据库中的行号
    private int endRow;
    //总页数
    private int pages;

    //前一页
    private int prev;
    //下一页
    private int next;
    //总页数
    private int total;
    //数据列表
    private List<T> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = (page-1)*limit;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = page*limit;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = page==1 ? 1 : page-1;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = page >= pages ? pages : page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void returnData(List<T> list,int total){
        this.setTotal(total);
        this.setList(list);
        int pages = total/limit;
        this.pages=total%limit > 0 ? pages+1 : pages;
    }

    /**
     * 传入其他参数
     */
    private Map data;

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
