package com.library.controller;

import com.library.entity.BookType;
import com.library.common.Result;
import com.library.entity.Users;
import com.library.service.BookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;
import com.library.mapper.OperationMapper;
@RestController
@RequestMapping("/api/booktype")
@CrossOrigin(origins = "*")
public class BookTypeController {

    @Autowired
    private BookTypeService bookTypeService;
    @Autowired
    private OperationMapper operationMapper;
    @GetMapping("/list")
    public Result<List<BookType>> list() {
        return Result.success(bookTypeService.selectAll());
    }

    @GetMapping("/{typeid}")
    public Result<BookType> getById(@PathVariable Integer typeid) {
        return Result.success(bookTypeService.selectById(typeid));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody BookType bookType, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        if (bookTypeService.insert(bookType)){
            operationMapper.insertLog("insert","booktype",bookType.getTypename(),"添加图书分类",u.getUname());
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody BookType bookType, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        if (bookTypeService.update(bookType)){
            operationMapper.insertLog("update","booktype",bookType.getTypename(),"图书类型更新",u.getUname());
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    @DeleteMapping("/delete/{typeid}")
    public Result<String> delete(@PathVariable Integer typeid, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        BookType bookType = bookTypeService.selectById(typeid);
        if (bookTypeService.delete(typeid)){
            operationMapper.insertLog("delete","booktype",bookType.getTypename(),"删除图书分类",u.getUname());
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}
