package com.library.entity;

/**
 * 读者类型实体类
 * 对应数据库表：readertype
 */
public class ReaderType {
    private Integer tid;        // 类型编号
    private String typename;    // 类型名称
    private Integer maxbnum;    // 最大借阅量
    private Integer limitdays;  // 最长借阅天数

    public ReaderType() {}

    public Integer getTid() { return tid; }
    public void setTid(Integer tid) { this.tid = tid; }

    public String getTypename() { return typename; }
    public void setTypename(String typename) { this.typename = typename; }

    public Integer getMaxbnum() { return maxbnum; }
    public void setMaxbnum(Integer maxbnum) { this.maxbnum = maxbnum; }

    public Integer getLimitdays() { return limitdays; }
    public void setLimitdays(Integer limitdays) { this.limitdays = limitdays; }
}
