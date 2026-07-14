package com.library.entity;

/**
 * 图书类型实体类
 * 对应数据库表：booktype
 */
public class BookType {
    private Integer typeid;     // 类型编号
    private String typename;    // 类型名称

    public BookType() {}

    public BookType(Integer typeid, String typename) {
        this.typeid = typeid;
        this.typename = typename;
    }

    public Integer getTypeid() { return typeid; }
    public void setTypeid(Integer typeid) { this.typeid = typeid; }

    public String getTypename() { return typename; }
    public void setTypename(String typename) { this.typename = typename; }
}
