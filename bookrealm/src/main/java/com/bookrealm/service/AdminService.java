package com.bookrealm.service;

import com.bookrealm.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Book> saveAll(List<Book> books);
    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book save(Book book);
}
