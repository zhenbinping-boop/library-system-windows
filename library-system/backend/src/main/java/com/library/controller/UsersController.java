package com.library.controller;

import com.library.common.Result;
import com.library.entity.Users;
import com.library.service.ReaderService;
import com.library.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import com.library.mapper.OperationMapper;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.sql.Date;
import java.util.Map;

import com.library.entity.Reader;
import com.library.mapper.ReaderMapper;
/**
 * 用户Controller
 * @RestController：返回JSON数据
 * @RequestMapping("/api/user")：接口前缀为 /user
 * @CrossOrigin：允许跨域访问（前端调用后端）
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UsersController {
    @Autowired
    private ReaderService readerService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private ReaderMapper readerMapper;
    @Autowired
    private OperationMapper operationMapper;

    // 登录接口 POST /user/login
    @PostMapping("/login")
    public Result<Users> login(@RequestBody Users user, HttpSession session) {
        Users u = usersService.login(user.getUname(), user.getUpassword());
        if (u != null) {
            session.setAttribute("user", u);
            return Result.success(u);
        }
        return Result.error("用户名或密码错误");
    }
    @PostMapping("/register")
    public Result<String> register(@RequestBody Map<String,Object> params) {
        String rid = (String) params.get("rid");
        String upassword = (String) params.get("upassword");

        Users user = new Users();
        user.setUname(rid);
        user.setUpassword(upassword);
        user.setUtype(2);

        if (usersService.insert(user)) {
            Reader reader = new Reader();
            reader.setRid(rid);
            reader.setRname((String) params.get("rname"));
            reader.setAge((Integer) params.get("age"));
            reader.setTele((String) params.get("tele"));
            reader.setMajor((String) params.get("major"));
            reader.setRtype((Integer) params.get("rtype"));
            String indate = (String) params.get("indate");
            if (indate != null && !indate.isEmpty()) {
                reader.setIndate(Date.valueOf(indate));
            } else {
                reader.setIndate(new Date(System.currentTimeMillis()));
            }
            readerMapper.insert(reader);
            return Result.success("注册成功");
        }
        return Result.error("注册失败");
    }

    // 获取当前登录用户信息 GET /user/info
    @GetMapping("/info")
    public Result<Users> info(HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u != null) return Result.success(u);
        return Result.error(401, "未登录");
    }

    // 退出登录 POST /user/logout
    @PostMapping("/logout")
    public Result<String> logout(HttpSession session) {
        session.invalidate();  // 销毁Session
        return Result.success("退出成功");
    }

    // 查询所有用户（仅管理员）GET /user/list
    @GetMapping("/list")
    public Result<List<Users>> list(HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() != 0) return Result.error(403, "无权限");
        return Result.success(usersService.selectAll());
    }

    // 新增用户（仅管理员）POST /user/add
    @PostMapping("/add")
    public Result<String> add(@RequestBody Users user, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() != 0) return Result.error(403, "无权限");
        if (usersService.insert(user)){
            operationMapper.insertLog("添加", "用户", String.valueOf(user.getUid()), "添加用户：" + user.getUname(), u.getUname());
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    // 修改密码 POST /user/updatePwd
    @PostMapping("/updatePwd")
    public Result<String> updatePwd(@RequestParam Integer uid, @RequestParam String upassword, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null) return Result.error(401, "未登录");
        // 只能改自己的密码，或管理员可改所有
        if (u.getUtype() != 0 && !u.getUid().equals(uid)) return Result.error(403, "无权限");
        if (usersService.updatePassword(uid, upassword)) {
            operationMapper.insertLog("修改", "用户", String.valueOf(uid), "修改用户密码：" + uid, u.getUname());
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    // 删除用户（仅管理员）DELETE /user/delete/{uid}
    @DeleteMapping("/delete/{uid}")
    public Result<String> delete(@PathVariable Integer uid, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() != 0) return Result.error(403, "无权限");
        if (usersService.delete(uid)) {
            operationMapper.insertLog("删除", "用户", String.valueOf(uid), "删除用户：" + uid, u.getUname());
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}
