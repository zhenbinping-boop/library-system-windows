package com.library.controller;

import com.library.entity.ReaderType;
import com.library.common.Result;
import com.library.entity.Users;
import com.library.service.ReaderTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import com.library.mapper.OperationMapper;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/readertype")
@CrossOrigin(origins = "*")
public class ReaderTypeController {

    @Autowired
    private ReaderTypeService readerTypeService;
    @Autowired
    private OperationMapper operationMapper;
    @GetMapping("/list")
    public Result<List<ReaderType>> list() {
        return Result.success(readerTypeService.selectAll());
    }

    @GetMapping("/{tid}")
    public Result<ReaderType> getById(@PathVariable Integer tid) {
        return Result.success(readerTypeService.selectById(tid));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody ReaderType readerType, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        if (readerTypeService.insert(readerType)) {
            operationMapper.insertLog("添加", "读者类型", readerType.getTid().toString(), "添加读者类型", u.getUname());
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody ReaderType readerType, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        if (readerTypeService.update(readerType)) {
            operationMapper.insertLog("修改", "读者类型", readerType.getTid().toString(), "修改读者类型", u.getUname());
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    @DeleteMapping("/delete/{tid}")
    public Result<String> delete(@PathVariable Integer tid, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        if (readerTypeService.delete(tid)) {
            operationMapper.insertLog("删除", "读者类型", tid.toString(), "删除读者类型", u.getUname());
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}
