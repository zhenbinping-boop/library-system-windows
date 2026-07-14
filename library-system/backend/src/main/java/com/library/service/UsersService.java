package com.library.service;

import com.library.entity.Users;
import com.library.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 用户Service
 * @Service注解：告诉Spring Boot这是一个Service类
 */
@Service
public class UsersService {

    @Autowired  // 自动注入Mapper
    private UsersMapper usersMapper;

    // 登录
    public Users login(String uname, String upassword) {
        return usersMapper.check(uname, upassword);
    }

    // 查询所有用户
    public List<Users> selectAll() {
        return usersMapper.selectAll();
    }

    // 新增用户
    public boolean insert(Users user) {
        return usersMapper.insert(user) > 0;
    }

    // 修改密码
    public boolean updatePassword(Integer uid, String upassword) {
        return usersMapper.updatePassword(uid, upassword) > 0;
    }

    // 删除用户
    public boolean delete(Integer uid) {
        return usersMapper.delete(uid) > 0;
    }

    // 根据ID查询
    public Users selectById(Integer uid) {
        return usersMapper.selectById(uid);
    }
}
