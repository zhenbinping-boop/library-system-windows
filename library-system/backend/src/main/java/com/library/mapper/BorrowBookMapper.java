package com.library.mapper;

import com.library.entity.BorrowBook;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BorrowBookMapper {

    // 查询所有未归还的借阅记录
    @Select("SELECT bb.*, r.rname, b.bname, b.author FROM borrowbook bb " +
            "LEFT JOIN reader r ON bb.rid = r.rid " +
            "LEFT JOIN book b ON bb.bisbn = b.bisbn " +
            "WHERE bb.is_returned = 0")
    @Results({
        @Result(property = "rid", column = "rid"),
        @Result(property = "bisbn", column = "bisbn"),
        @Result(property = "bdate", column = "bdate"),
        @Result(property = "rdate", column = "rdate"),
        @Result(property = "fine", column = "fine"),
        @Result(property = "rname", column = "rname"),
        @Result(property = "bname", column = "bname"),
        @Result(property = "author", column = "author"),
        @Result(property = "isReturned", column = "is_returned")
    })
    List<BorrowBook> selectAllActive();

    // 查询所有借阅记录（包括已归还）
    @Select("SELECT bb.*, r.rname, b.bname, b.author FROM borrowbook bb " +
            "LEFT JOIN reader r ON bb.rid = r.rid " +
            "LEFT JOIN book b ON bb.bisbn = b.bisbn")
    @Results({
        @Result(property = "rid", column = "rid"),
        @Result(property = "bisbn", column = "bisbn"),
        @Result(property = "bdate", column = "bdate"),
        @Result(property = "rdate", column = "rdate"),
        @Result(property = "fine", column = "fine"),
        @Result(property = "rname", column = "rname"),
        @Result(property = "bname", column = "bname"),
        @Result(property = "author", column = "author"),
        @Result(property = "isReturned", column = "is_returned")
    })
    List<BorrowBook> selectAll();

    // 查询某读者的未归还借阅记录
    @Select("SELECT bb.*, r.rname, b.bname, b.author FROM borrowbook bb " +
            "LEFT JOIN reader r ON bb.rid = r.rid " +
            "LEFT JOIN book b ON bb.bisbn = b.bisbn " +
            "WHERE bb.rid = #{rid} AND bb.is_returned = 0")
    @Results({
        @Result(property = "rid", column = "rid"),
        @Result(property = "bisbn", column = "bisbn"),
        @Result(property = "bdate", column = "bdate"),
        @Result(property = "rdate", column = "rdate"),
        @Result(property = "fine", column = "fine"),
        @Result(property = "rname", column = "rname"),
        @Result(property = "bname", column = "bname"),
        @Result(property = "author", column = "author"),
        @Result(property = "isReturned", column = "is_returned")
    })
    List<BorrowBook> selectByReaderId(String rid);

    // 统计某读者的未归还数量
    @Select("SELECT COUNT(*) FROM borrowbook WHERE rid = #{rid} AND is_returned = 0")
    int countActiveByReader(String rid);

    // 新增借阅记录
    @Insert("INSERT INTO borrowbook(rid, bisbn, bdate, is_returned) VALUES(#{rid}, #{bisbn}, #{bdate}, 0)")
    int insert(BorrowBook borrowBook);

    // 归还图书（更新归还日期、罚金、状态）
    @Update("UPDATE borrowbook SET rdate = #{rdate}, fine = #{fine}, is_returned = 1 " +
            "WHERE rid = #{rid} AND bisbn = #{bisbn} AND is_returned = 0")
    int returnBook(@Param("rid") String rid, @Param("bisbn") String bisbn,
                   @Param("rdate") java.sql.Date rdate, @Param("fine") java.math.BigDecimal fine);
}
