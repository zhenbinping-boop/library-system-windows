package com.library.controller;

import com.library.common.Result;
import com.library.entity.Operation;
import com.library.mapper.OperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/log")
public class OperationController {

    @Autowired
    private OperationMapper oMapper;

    @GetMapping("/list")
    public Result<List<Operation>> list() {
        return Result.success(oMapper.selectAll());
    }
}
