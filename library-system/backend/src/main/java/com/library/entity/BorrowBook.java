package com.library.entity;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * 借阅记录实体类
 * 对应数据库表：borrowbook
 */
public class BorrowBook {
    private String rid;         // 读者编号
    private String bisbn;       // 图书ISBN
    private Date bdate;         // 借阅日期
    private Date rdate;         // 归还日期
    private BigDecimal fine;    // 罚金
    private String rname;       // 关联字段：读者姓名
    private String bname;       // 关联字段：书名
    private String author;      // 关联字段：作者
    private Integer isReturned; // 是否归还：0=未归还, 1=已归还

    // ========== 逾期状态字段（不存入数据库，仅用于前端显示）==========
    private transient Long overdueDays;    // 逾期天数
    private transient BigDecimal overdueFine; // 逾期罚金
    private transient String overdueStatus; // 逾期状态文本

    public BorrowBook() {}

    // ========== Getter / Setter ==========
    public String getRid() { return rid; }
    public void setRid(String rid) { this.rid = rid; }

    public String getBisbn() { return bisbn; }
    public void setBisbn(String bisbn) { this.bisbn = bisbn; }

    public Date getBdate() { return bdate; }
    public void setBdate(Date bdate) { this.bdate = bdate; }

    public Date getRdate() { return rdate; }
    public void setRdate(Date rdate) { this.rdate = rdate; }

    public BigDecimal getFine() { return fine; }
    public void setFine(BigDecimal fine) { this.fine = fine; }

    public String getRname() { return rname; }
    public void setRname(String rname) { this.rname = rname; }

    public String getBname() { return bname; }
    public void setBname(String bname) { this.bname = bname; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getIsReturned() { return isReturned; }
    public void setIsReturned(Integer isReturned) { this.isReturned = isReturned; }

    // ========== 逾期字段 Getter / Setter ==========
    public Long getOverdueDays() { return overdueDays; }
    public void setOverdueDays(Long overdueDays) { this.overdueDays = overdueDays; }

    public BigDecimal getOverdueFine() { return overdueFine; }
    public void setOverdueFine(BigDecimal overdueFine) { this.overdueFine = overdueFine; }

    public String getOverdueStatus() { return overdueStatus; }
    public void setOverdueStatus(String overdueStatus) { this.overdueStatus = overdueStatus; }
}