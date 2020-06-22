package com.aptitude.mapper;

import  java.util.List;

import com.aptitude.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BookMapper {

    Book findById(int id);

    List<Book> findByKey(String key);

    void add(Book book);

    List<Book> findAll();

    Integer count();

    List<Book> findByPage(Integer index);

    void delete (Integer id);

    void update(Book book);

    Book findBorrowers (Integer id);

    /*Book findByName (String name);

    //@Param注解在2个或以上的参数时是必须的，单参数可缺省
    List<Book> findByPage(@Param("index") int index, @Param("size") int size);

    int delete(int id);


    List<Book> findAll();

    int count();

    Book findBorrowersList(int id);*/
}
