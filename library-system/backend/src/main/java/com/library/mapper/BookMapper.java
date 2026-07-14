package com.library.mapper;

import com.library.entity.Book;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BookMapper {

    // 查询所有图书（关联图书类型名称）
    @Select("SELECT b.*, t.typename FROM book b LEFT JOIN booktype t ON b.typeid = t.typeid")
    @Results({
        @Result(property = "bisbn", column = "bisbn"),
        @Result(property = "typeid", column = "typeid"),
        @Result(property = "bname", column = "bname"),
        @Result(property = "author", column = "author"),
        @Result(property = "publish", column = "publish"),
        @Result(property = "pdate", column = "pdate"),
        @Result(property = "ptimes", column = "ptimes"),
        @Result(property = "bprice", column = "bprice"),
        @Result(property = "isChildren", column = "is_children"),
        @Result(property = "num", column = "num"),
        @Result(property = "typename", column = "typename")
    })
    List<Book> selectAll();

    // 根据ISBN查询图书
    @Select("SELECT b.*, t.typename FROM book b LEFT JOIN booktype t ON b.typeid = t.typeid WHERE b.bisbn = #{bisbn}")
    @Results({
        @Result(property = "bisbn", column = "bisbn"),
        @Result(property = "typeid", column = "typeid"),
        @Result(property = "bname", column = "bname"),
        @Result(property = "author", column = "author"),
        @Result(property = "publish", column = "publish"),
        @Result(property = "pdate", column = "pdate"),
        @Result(property = "ptimes", column = "ptimes"),
        @Result(property = "bprice", column = "bprice"),
        @Result(property = "isChildren", column = "is_children"),
        @Result(property = "num", column = "num"),
        @Result(property = "typename", column = "typename")
    })
    Book selectById(String bisbn);

    // 动态条件查询（支持ISBN精确匹配、书名模糊匹配等）
    @Select("<script>" +
            "SELECT b.*, t.typename FROM book b LEFT JOIN booktype t ON b.typeid = t.typeid " +
            "WHERE 1=1 " +
            "<if test='bisbn != null'> AND b.bisbn = #{bisbn} </if>" +
            "<if test='bname != null'> AND b.bname LIKE CONCAT('%',#{bname},'%') </if>" +
            "<if test='typeid != null'> AND b.typeid = #{typeid} </if>" +
            "<if test='author != null'> AND b.author LIKE CONCAT('%',#{author},'%') </if>" +
            "<if test='publish != null'> AND b.publish LIKE CONCAT('%',#{publish},'%') </if>" +
            "</script>")
    @Results({
        @Result(property = "bisbn", column = "bisbn"),
        @Result(property = "typeid", column = "typeid"),
        @Result(property = "bname", column = "bname"),
        @Result(property = "author", column = "author"),
        @Result(property = "publish", column = "publish"),
        @Result(property = "pdate", column = "pdate"),
        @Result(property = "ptimes", column = "ptimes"),
        @Result(property = "bprice", column = "bprice"),
        @Result(property = "isChildren", column = "is_children"),
        @Result(property = "num", column = "num"),
        @Result(property = "typename", column = "typename")
    })
    List<Book> search(@Param("bisbn") String bisbn, @Param("bname") String bname,
                      @Param("typeid") Integer typeid, @Param("author") String author,
                      @Param("publish") String publish);

    // 新增图书
    @Insert("INSERT INTO book(bisbn, typeid, bname, author, publish, pdate, ptimes, bprice, is_children, num) " +
            "VALUES(#{bisbn}, #{typeid}, #{bname}, #{author}, #{publish}, #{pdate}, #{ptimes}, #{bprice}, #{isChildren}, #{num})")
    int insert(Book book);

    // 修改图书
    @Update("UPDATE book SET typeid=#{typeid}, bname=#{bname}, author=#{author}, publish=#{publish}, " +
            "pdate=#{pdate}, ptimes=#{ptimes}, bprice=#{bprice}, is_children=#{isChildren}, num=#{num} WHERE bisbn=#{bisbn}")
    int update(Book book);

    // 删除图书
    @Delete("DELETE FROM book WHERE bisbn = #{bisbn}")
    int delete(String bisbn);

    // 减少库存（借阅时调用）
    @Update("UPDATE book SET num = num - 1 WHERE bisbn = #{bisbn} AND num > 0")
    int decreaseNum(String bisbn);

    // 增加库存（归还时调用）
    @Update("UPDATE book SET num = num + 1 WHERE bisbn = #{bisbn}")
    int increaseNum(String bisbn);
}
