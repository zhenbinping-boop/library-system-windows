package com.library.service;

import com.library.entity.BookType;
import com.library.mapper.BookTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookTypeService {

    @Autowired
    private BookTypeMapper bookTypeMapper;

    public List<BookType> selectAll() {
        return bookTypeMapper.selectAll();
    }

    public BookType selectById(Integer typeid) {
        return bookTypeMapper.selectById(typeid);
    }

    public boolean insert(BookType bookType) {
        return bookTypeMapper.insert(bookType) > 0;
    }

    public boolean update(BookType bookType) {
        return bookTypeMapper.update(bookType) > 0;
    }

    public boolean delete(Integer typeid) {
        return bookTypeMapper.delete(typeid) > 0;
    }
}
