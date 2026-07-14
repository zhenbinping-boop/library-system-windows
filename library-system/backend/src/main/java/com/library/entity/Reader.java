package com.library.entity;

import java.sql.Date;

/**
 * 读者实体类
 * 对应数据库表：reader
 */
public class Reader {
    private String rid;         // 读者编号（主键）
    private Integer rtype;      // 读者类型编号
    private String rname;       // 姓名
    private Integer age;        // 年龄
    private String gender;      // 性别
    private String tele;        // 电话
    private String major;       // 所在系部
    private Date indate;        // 注册日期
    private String typename;    // 关联字段：类型名称
    private Integer maxbnum;    // 关联字段：最大借阅量
    private Integer limitdays;  // 关联字段：最长借阅天数

    public Reader() {}

    public String getRid() { return rid; }
    public void setRid(String rid) { this.rid = rid; }

    public Integer getRtype() { return rtype; }
    public void setRtype(Integer rtype) { this.rtype = rtype; }

    public String getRname() { return rname; }
    public void setRname(String rname) { this.rname = rname; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getTele() { return tele; }
    public void setTele(String tele) { this.tele = tele; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public Date getIndate() { return indate; }
    public void setIndate(Date indate) { this.indate = indate; }

    public String getTypename() { return typename; }
    public void setTypename(String typename) { this.typename = typename; }

    public Integer getMaxbnum() { return maxbnum; }
    public void setMaxbnum(Integer maxbnum) { this.maxbnum = maxbnum; }

    public Integer getLimitdays() { return limitdays; }
    public void setLimitdays(Integer limitdays) { this.limitdays = limitdays; }
}
