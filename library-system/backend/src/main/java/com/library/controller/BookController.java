package com.library.controller;

import com.library.entity.Book;
import com.library.common.Result;
import com.library.entity.Users;
import com.library.mapper.OperationMapper;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private OperationMapper operationMapper;

    @GetMapping("/list")
    public Result<List<Book>> list() {
        return Result.success(bookService.selectAll());
    }

    @GetMapping("/{bisbn}")
    public Result<Book> getById(@PathVariable String bisbn) {
        return Result.success(bookService.selectById(bisbn));
    }

@GetMapping("/search")
public Result<List<Book>> search(@RequestParam(required = false) String bisbn,
                                 @RequestParam(required = false) String bname,
                                 @RequestParam(required = false) Integer typeid,
                                 @RequestParam(required = false) String author,
                                 @RequestParam(required = false) String publish) {
    bisbn = (bisbn != null && !bisbn.trim().isEmpty()) ? bisbn.trim() : null;
    bname = (bname != null && !bname.trim().isEmpty()) ? bname.trim() : null;
    author = (author != null && !author.trim().isEmpty()) ? author.trim() : null;
    publish = (publish != null && !publish.trim().isEmpty()) ? publish.trim() : null;
    
    return Result.success(bookService.search(bisbn, bname, typeid, author, publish));
}

    @PostMapping("/add")
    public Result<String> add(@RequestBody Book book, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        if (bookService.selectById(book.getBisbn()) != null) return Result.error("该ISBN已存在");
        if (bookService.insert(book)) {
            operationMapper.insertLog("insert","book",book.getBisbn(),"添加图书",u.getUname());
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody Book book, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        if (bookService.update(book)) {
            operationMapper.insertLog("update","book",book.getBisbn(),"图书信息更新",u.getUname());
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    @DeleteMapping("/delete/{bisbn}")
    public Result<String> delete(@PathVariable String bisbn, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        if (bookService.delete(bisbn)) {
            operationMapper.insertLog("delete","book",bisbn,"图书删除",u.getUname());
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}
