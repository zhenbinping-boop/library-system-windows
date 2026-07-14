package com.library.entity;

/**
 * 用户实体类
 * 对应数据库表：users
 */
public class Users {
    private Integer uid;        // 用户编号
    private String uname;       // 用户名
    private String upassword;   // 密码
    private Integer utype;      // 用户类型：0=管理员, 1=馆员, 2=普通用户

    // 无参构造方法（必须）
    public Users() {}

    // 全参构造方法
    public Users(Integer uid, String uname, String upassword, Integer utype) {
        this.uid = uid;
        this.uname = uname;
        this.upassword = upassword;
        this.utype = utype;
    }

    // Getter和Setter方法
    public Integer getUid() { return uid; }
    public void setUid(Integer uid) { this.uid = uid; }

    public String getUname() { return uname; }
    public void setUname(String uname) { this.uname = uname; }

    public String getUpassword() { return upassword; }
    public void setUpassword(String upassword) { this.upassword = upassword; }

    public Integer getUtype() { return utype; }
    public void setUtype(Integer utype) { this.utype = utype; }
}
