package com.library.mapper;

import com.library.entity.Users;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 用户Mapper接口
 * @Mapper注解：告诉Spring Boot这是一个MyBatis的Mapper接口
 */
@Mapper
public interface UsersMapper {

    // 登录验证：根据用户名和密码查询用户
    @Select("SELECT * FROM users WHERE uname = #{uname} AND upassword = #{upassword}")
    Users check(@Param("uname") String uname, @Param("upassword") String upassword);

    // 查询所有用户
    @Select("SELECT * FROM users")
    List<Users> selectAll();

    // 新增用户
    @Insert("INSERT INTO users(uname, upassword, utype) VALUES(#{uname}, #{upassword}, #{utype})")
    int insert(Users user);

    // 修改密码
    @Update("UPDATE users SET upassword = #{upassword} WHERE uid = #{uid}")
    int updatePassword(@Param("uid") Integer uid, @Param("upassword") String upassword);

    // 删除用户
    @Delete("DELETE FROM users WHERE uid = #{uid}")
    int delete(Integer uid);

    // 根据ID查询用户
    @Select("SELECT * FROM users WHERE uid = #{uid}")
    Users selectById(Integer uid);
}
