package com.aptitude;

import com.aptitude.domain.Book;
import com.aptitude.domain.User;
import com.aptitude.service.BookService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookManagerApplicationTests {

    @Autowired
    private BookService bookService;

    @Test
    void contextLoads() {
        Book book = bookService.findBorrowersByBookId(39);
        System.out.println(book);
        for (User u:book.getUserList()
             ) {
            System.out.println(u);
        }
    }

}
