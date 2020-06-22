package com.aptitude.service;

import com.aptitude.domain.Book;

import java.util.List;

public interface BookService {

    Book getBookById (Integer id);

    List<Book> getByKey (String key);

    void addBook (Book book);

    List<Book> findAllBooks();

    Integer count();

    List<Book> findByPage(Integer index);

    void deleteBook (Integer id);

    void updateBook (Book book);

    Book findBorrowersByBookId (Integer id);

}
