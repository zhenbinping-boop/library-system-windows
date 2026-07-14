package com.library.service;

import com.library.entity.ReaderType;
import com.library.mapper.ReaderTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReaderTypeService {

    @Autowired
    private ReaderTypeMapper readerTypeMapper;

    public List<ReaderType> selectAll() {
        return readerTypeMapper.selectAll();
    }

    public ReaderType selectById(Integer tid) {
        return readerTypeMapper.selectById(tid);
    }

    public boolean insert(ReaderType readerType) {
        return readerTypeMapper.insert(readerType) > 0;
    }

    public boolean update(ReaderType readerType) {
        return readerTypeMapper.update(readerType) > 0;
    }

    public boolean delete(Integer tid) {
        return readerTypeMapper.delete(tid) > 0;
    }
}
