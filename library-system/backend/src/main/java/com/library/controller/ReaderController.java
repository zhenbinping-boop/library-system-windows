package com.library.controller;

import com.library.entity.Reader;
import com.library.common.Result;
import com.library.entity.Users;
import com.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import com.library.mapper.OperationMapper;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/reader")
@CrossOrigin(origins = "*")
public class ReaderController {

    @Autowired
    private ReaderService readerService;
    @Autowired
    private OperationMapper operationMapper;
    @GetMapping("/list")
    public Result<List<Reader>> list() {
        return Result.success(readerService.selectAll());
    }

    @GetMapping("/{rid}")
    public Result<Reader> getById(@PathVariable String rid) {
        return Result.success(readerService.selectById(rid));
    }

    @GetMapping("/search")
    public Result<List<Reader>> search(@RequestParam(required = false) String rid,
                                       @RequestParam(required = false) String rname,
                                       @RequestParam(required = false) Integer rtype,
                                       @RequestParam(required = false) String major) {
        return Result.success(readerService.search(rid, rname, rtype, major));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Reader reader, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        if (readerService.selectById(reader.getRid()) != null) return Result.error("该读者编号已存在");
        if (readerService.insert(reader)) {
            operationMapper.insertLog("添加", "读者", reader.getRid(), "添加新读者：" + reader.getRname(), u.getUname());
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody Reader reader, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        if (readerService.update(reader)) {
            operationMapper.insertLog("修改", "读者", reader.getRid(), "修改读者信息：" + reader.getRname(), u.getUname());
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    @DeleteMapping("/delete/{rid}")
    public Result<String> delete(@PathVariable String rid, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        if (readerService.delete(rid)) {
            operationMapper.insertLog("删除", "读者", rid, "删除读者：" + rid, u.getUname());
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}
