package com.library.mapper;

import com.library.entity.BookType;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BookTypeMapper {

    @Select("SELECT * FROM booktype")
    List<BookType> selectAll();

    @Select("SELECT * FROM booktype WHERE typeid = #{typeid}")
    BookType selectById(Integer typeid);

    @Insert("INSERT INTO booktype(typename) VALUES(#{typename})")
    @Options(useGeneratedKeys = true, keyProperty = "typeid")
    int insert(BookType bookType);

    @Update("UPDATE booktype SET typename = #{typename} WHERE typeid = #{typeid}")
    int update(BookType bookType);

    @Delete("DELETE FROM booktype WHERE typeid = #{typeid}")
    int delete(Integer typeid);
}
