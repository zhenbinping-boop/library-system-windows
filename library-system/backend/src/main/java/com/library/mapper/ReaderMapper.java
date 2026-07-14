package com.library.mapper;

import com.library.entity.Reader;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ReaderMapper {

    @Select("SELECT r.*, t.typename, t.maxbnum, t.limitdays FROM reader r LEFT JOIN readertype t ON r.rtype = t.tid")
    @Results({
        @Result(property = "rid", column = "rid"),
        @Result(property = "rtype", column = "rtype"),
        @Result(property = "rname", column = "rname"),
        @Result(property = "age", column = "age"),
        @Result(property = "gender", column = "gender"),
        @Result(property = "tele", column = "tele"),
        @Result(property = "major", column = "major"),
        @Result(property = "indate", column = "indate"),
        @Result(property = "typename", column = "typename"),
        @Result(property = "maxbnum", column = "maxbnum"),
        @Result(property = "limitdays", column = "limitdays")
    })
    List<Reader> selectAll();

    @Select("SELECT r.*, t.typename, t.maxbnum, t.limitdays FROM reader r LEFT JOIN readertype t ON r.rtype = t.tid WHERE r.rid = #{rid}")
    @Results({
        @Result(property = "rid", column = "rid"),
        @Result(property = "rtype", column = "rtype"),
        @Result(property = "rname", column = "rname"),
        @Result(property = "age", column = "age"),
        @Result(property = "gender", column = "gender"),
        @Result(property = "tele", column = "tele"),
        @Result(property = "major", column = "major"),
        @Result(property = "indate", column = "indate"),
        @Result(property = "typename", column = "typename"),
        @Result(property = "maxbnum", column = "maxbnum"),
        @Result(property = "limitdays", column = "limitdays")
    })
    Reader selectById(String rid);

    @Select("<script>" +
            "SELECT r.*, t.typename, t.maxbnum, t.limitdays FROM reader r LEFT JOIN readertype t ON r.rtype = t.tid " +
            "WHERE 1=1 " +
            "<if test='rid != null'> AND r.rid = #{rid} </if>" +
            "<if test='rname != null'> AND r.rname LIKE CONCAT('%',#{rname},'%') </if>" +
            "<if test='rtype != null'> AND r.rtype = #{rtype} </if>" +
            "<if test='major != null'> AND r.major LIKE CONCAT('%',#{major},'%') </if>" +
            "</script>")
    @Results({
        @Result(property = "rid", column = "rid"),
        @Result(property = "rtype", column = "rtype"),
        @Result(property = "rname", column = "rname"),
        @Result(property = "age", column = "age"),
        @Result(property = "gender", column = "gender"),
        @Result(property = "tele", column = "tele"),
        @Result(property = "major", column = "major"),
        @Result(property = "indate", column = "indate"),
        @Result(property = "typename", column = "typename"),
        @Result(property = "maxbnum", column = "maxbnum"),
        @Result(property = "limitdays", column = "limitdays")
    })
    List<Reader> search(@Param("rid") String rid, @Param("rname") String rname,
                        @Param("rtype") Integer rtype, @Param("major") String major);

    @Insert("INSERT INTO reader(rid, rtype, rname, age, gender, tele, major, indate) " +
            "VALUES(#{rid}, #{rtype}, #{rname}, #{age}, #{gender}, #{tele}, #{major}, #{indate})")
    int insert(Reader reader);

    @Update("UPDATE reader SET rtype=#{rtype}, rname=#{rname}, age=#{age}, gender=#{gender}, " +
            "tele=#{tele}, major=#{major}, indate=#{indate} WHERE rid=#{rid}")
    int update(Reader reader);

    @Delete("DELETE FROM reader WHERE rid = #{rid}")
    int delete(String rid);
}
