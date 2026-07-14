package com.library.mapper;

import com.library.entity.ReaderType;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ReaderTypeMapper {

    @Select("SELECT * FROM readertype")
    List<ReaderType> selectAll();

    @Select("SELECT * FROM readertype WHERE tid = #{tid}")
    ReaderType selectById(Integer tid);

    @Insert("INSERT INTO readertype(typename, maxbnum, limitdays) VALUES(#{typename}, #{maxbnum}, #{limitdays})")
    @Options(useGeneratedKeys = true, keyProperty = "tid")
    int insert(ReaderType readerType);

    @Update("UPDATE readertype SET typename=#{typename}, maxbnum=#{maxbnum}, limitdays=#{limitdays} WHERE tid=#{tid}")
    int update(ReaderType readerType);

    @Delete("DELETE FROM readertype WHERE tid = #{tid}")
    int delete(Integer tid);
}
