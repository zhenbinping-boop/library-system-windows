package com.library.service;

import com.library.entity.Reader;
import com.library.mapper.ReaderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReaderService {

    @Autowired
    private ReaderMapper readerMapper;

    public List<Reader> selectAll() {
        return readerMapper.selectAll();
    }

    public Reader selectById(String rid) {
        return readerMapper.selectById(rid);
    }

    public List<Reader> search(String rid, String rname, Integer rtype, String major) {
        return readerMapper.search(rid, rname, rtype, major);
    }

    public boolean insert(Reader reader) {
        return readerMapper.insert(reader) > 0;
    }

    public boolean update(Reader reader) {
        return readerMapper.update(reader) > 0;
    }

    public boolean delete(String rid) {
        return readerMapper.delete(rid) > 0;
    }
}
