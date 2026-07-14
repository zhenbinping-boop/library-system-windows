package com.library.service;

import com.library.entity.Book;
import com.library.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    public List<Book> selectAll() {
        return bookMapper.selectAll();
    }

    public Book selectById(String bisbn) {
        return bookMapper.selectById(bisbn);
    }

    public List<Book> search(String bisbn, String bname, Integer typeid, String author, String publish) {
        return bookMapper.search(bisbn, bname, typeid, author, publish);
    }

    public boolean insert(Book book) {
        return bookMapper.insert(book) > 0;
    }

    public boolean update(Book book) {
        return bookMapper.update(book) > 0;
    }

    public boolean delete(String bisbn) {
        return bookMapper.delete(bisbn) > 0;
    }

    public boolean decreaseNum(String bisbn) {
        return bookMapper.decreaseNum(bisbn) > 0;
    }

    public boolean increaseNum(String bisbn) {
        return bookMapper.increaseNum(bisbn) > 0;
    }
}
