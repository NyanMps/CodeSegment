package com.etoak.book.commons.page;

import java.io.Serializable;
import java.util.List;

/**
 * 存放关于分页的数据
 * <p>
 * Created by 冰封承諾Andy on 2018/5/21.
 */
public class Page<T> implements Serializable {
    // 当前页
    private int pageNumber;
    private int total;
    private int pageCount;
    private int pageSize = 3;

    private int pre;
    private int next;
    // 起始位置
    private int start;

    private List<T> rows;

    public Page() {
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount() {
        // 计算总页数
        return (total + pageSize - 1) / pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPre() {
        if (pageNumber > 1)
            return pageNumber - 1;
        return 1;
    }

    public int getNext() {
        if (pageNumber < getPageCount())
            return pageNumber + 1;
        return getPageCount();
    }

    public int getStart() {
        return (pageNumber - 1) * pageSize;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", total=" + total +
                ", pageCount=" + getPageCount() +
                ", pageSize=" + pageSize +
                ", pre=" + getPre() +
                ", next=" + getNext() +
                ", start=" + getStart() +
                ", rows=" + rows +
                '}';
    }
}
