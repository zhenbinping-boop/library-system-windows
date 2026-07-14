package com.library.mapper;

import com.library.entity.Operation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OperationMapper {

    @Insert("INSERT INTO operation (type, table_name, record_id, content, operator) " +
            "VALUES (#{type}, #{tableName}, #{recordId}, #{content}, #{operator})")
    int insertLog(@Param("type") String type,
                  @Param("tableName") String tableName,
                  @Param("recordId") String recordId,
                  @Param("content") String content,
                  @Param("operator") String operator);
    @Select("SELECT id, type, table_name AS tableName, record_id AS recordId, content, operator, " +
            "DATE_FORMAT(time, '%Y-%m-%d %H:%i:%s') AS time FROM operation ORDER BY time DESC")
    List<Operation> selectAll();
}
