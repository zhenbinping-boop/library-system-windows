package com.library.entity;

public class Operation {
    private Integer id;
    private String type;
    private String tableName;
    private String recordId;
    private String content;
    private String operator;
    private String time;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}
