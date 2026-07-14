package com.library.entity;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * 图书实体类
 * 对应数据库表：book
 */
public class Book {
    private String bisbn;       // ISBN编号（主键）
    private Integer typeid;     // 图书类型编号
    private String bname;       // 图书名称
    private String author;      // 作者
    private String publish;     // 出版社
    private Date pdate;         // 出版日期
    private Integer ptimes;     // 印刷次数
    private BigDecimal bprice;  // 单价
    private String isChildren;  // 是否少儿读物：是/否
    private Integer num;        // 馆藏数量
    private String typename;    // 关联字段：类型名称

    public Book() {}

    public String getBisbn() { return bisbn; }
    public void setBisbn(String bisbn) { this.bisbn = bisbn; }

    public Integer getTypeid() { return typeid; }
    public void setTypeid(Integer typeid) { this.typeid = typeid; }

    public String getBname() { return bname; }
    public void setBname(String bname) { this.bname = bname; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublish() { return publish; }
    public void setPublish(String publish) { this.publish = publish; }

    public Date getPdate() { return pdate; }
    public void setPdate(Date pdate) { this.pdate = pdate; }

    public Integer getPtimes() { return ptimes; }
    public void setPtimes(Integer ptimes) { this.ptimes = ptimes; }

    public BigDecimal getBprice() { return bprice; }
    public void setBprice(BigDecimal bprice) { this.bprice = bprice; }

    public String getIsChildren() { return isChildren; }
    public void setIsChildren(String isChildren) { this.isChildren = isChildren; }

    public Integer getNum() { return num; }
    public void setNum(Integer num) { this.num = num; }

    public String getTypename() { return typename; }
    public void setTypename(String typename) { this.typename = typename; }
}
