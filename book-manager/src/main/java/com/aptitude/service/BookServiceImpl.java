package com.aptitude.service;

import com.aptitude.mapper.BookMapper;
import com.aptitude.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class BookServiceImpl implements BookService{

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    private boolean flag = false;

    @Override
    @Transactional(readOnly = true)
    public Book getBookById (Integer id) {

        keySerialize();
        Book book = (Book) redisTemplate.opsForValue().get("bookId_" + id);

        if (book == null) {
            book = bookMapper.findById(id);
            redisTemplate.opsForValue().set("bookId_" + id, book);
        }

        return book;
    }

    @Override
    public List<Book> findAllBooks() {

        return bookMapper.findAll();
    }

    @Override
    public List<Book> getByKey (String key) {
        keySerialize();
        //  查询缓存
        List<Book> books = (List<Book>)redisTemplate.opsForValue().get("booksByKey_" + key);

        if (null == books) {
            //  缓存为空，查一遍数据库
            books = bookMapper.findByKey(key);
            //  如果非空，把查询出来的数据放入redis中
            if (books.size() > 0) {
                redisTemplate.opsForValue().set("booksBy" + key, books);
            }
        }

        return books;
    }

    @Override
    public Integer count() {
        keySerialize();

        Integer count = (Integer) redisTemplate.opsForValue().get("count");

        if (count == null) {
            count = bookMapper.count();
            redisTemplate.opsForValue().set("count", count);
        }

        return count;
    }

    @Override
    public List<Book> findByPage(Integer index) {
        keySerialize();
        List<Book> books = (List<Book>) redisTemplate.opsForValue().get("booksByPageIndex_" + index);

        if (books == null) {
            books = bookMapper.findByPage(index);

            redisTemplate.opsForValue().set("booksByPageIndex" + index, books);
        }

        return books;

    }

    @Override
    public void addBook (Book book) {

        bookMapper.add(book);
        cleanRedis();
    }

    @Override
    public void deleteBook (Integer id) {

        bookMapper.delete(id);
        cleanRedis();
    }

    @Override
    public void updateBook (Book book) {

        bookMapper.update(book);
        cleanRedis();
    }


    @Override
    public Book findBorrowersByBookId (Integer id) {
        return bookMapper.findBorrowers(id);
    }


    /*public Book getBookByName (String name) {
        return bookMapper.findByName(name);
    }

    public List<Book> getBookByPage (Integer index, Integer size) {
        return bookMapper.findByPage(index, size);
    }

    public Integer addBook (Book book) {
        return bookMapper.add(book);
    }

    public Integer removeBook (Integer id) {
        return bookMapper.delete(id);
    }

    public Integer updateBook (Book book) {
        return bookMapper.update(book);
    }

    public List<Book> findAllBooks () {
        return bookMapper.findAll();
    }
*/

    void keySerialize() {
        if (!flag){
            //  字符串序列化器
            RedisSerializer redisSerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(redisSerializer);
            flag = true;
        }
    }

    void cleanRedis() {
        Set<Object> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }
}
