package com.aptitude.controllers;


import com.aptitude.domain.Book;
import com.aptitude.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@Controller
public class BookController {

    @Value("${file.path}")
    private String filePath;

    @Autowired
    private BookService bookService;

    //  改为@GetMapping在添加图书后报405错误---》》》RM注解支持多种请求方法？
    //  不同情况下，对此接口的请求方法可能不同



    @RequestMapping("books.do")
    public String toBooks(Model model) {

        int count = bookService.count();
        model.addAttribute("count", count);

        return "books";
    }

    @RequestMapping("getByPage.do")
    @ResponseBody
    public List<Book> findByPage(String pageIndex, Model model) {
        Integer index = (Integer.parseInt(pageIndex) - 1) * 10;
        List<Book> books = bookService.findByPage(index);

        return books;
    }

    @RequestMapping("search.do")
    public String findByKey (String key, Model model) {
        System.out.println(key);
        List<Book> books = bookService.getByKey(key);
        int count = books.size();
        model.addAttribute("count", count);
        model.addAttribute("key", key);
        if (count > 0){
            model.addAttribute("books", books);
        }else {
            model.addAttribute("nullMessage", "未查询到相关图书");
        }
        return "search";
    }

    @GetMapping("toAdd.do")
    public String toAddBook() {
        return "add";
    }

    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    public String addBook(Book book, MultipartFile file, Model model) throws Exception {

        if (!file.isEmpty()) {

            String fileName = null;

            if ( (fileName = saveFile(file, model)) != null ) {
                book.setPictureName(fileName);
            } else {
                //  利用模板引擎返回数据
                model.addAttribute("book", book);
                return "add";
            }

        }

        bookService.addBook(book);
        model.addAttribute("tips", "新增图书成功");
        return "books.do";

    }



    @RequestMapping("delete.do")
    public String deleteBook (Integer id, String key, Model model) {

        if (key == null || key.equals("")) {
            bookService.deleteBook(id);
            model.addAttribute("tips", "删除成功");

            return "books.do";
        } else {
            bookService.deleteBook(id);
            model.addAttribute("tips", "删除成功");
            model.addAttribute("key", key);

            return "search.do";
        }


    }

    @RequestMapping("toEdit.do")
    public String toEditBook (Integer id, String key, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("key", key);
        return "edit";
    }

    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    public String toEditBook (Book book, String key, Model model, MultipartFile file) throws Exception {

        if (!file.isEmpty()) {
            String fileName = null;

            if ( (fileName = saveFile(file, model)) != null) {
                book.setPictureName(fileName);
            }else {
                model.addAttribute("book", book);
                return "edit";
            }

        }

        bookService.updateBook(book);
        model.addAttribute("tips", "更改图书信息成功");

        if (key == null || key.equals("")) {
            return "books.do";
        } else {
            model.addAttribute("key", key);
            return "search.do";
        }

    }

    @RequestMapping(value = "toBorrowers.do")
    public String toBorrowers (Integer id, String key, Model model) {
        Book book = bookService.findBorrowersByBookId(id);
        model.addAttribute("book", book);

        if (key == null || key.equals("")) {
            return "borrowers";

        }else {
            model.addAttribute("key", key);
            return "borrowers";
        }

    }


  /*  @RequestMapping("bookLis.do")
    public String getAllBook (Model model) {
        List<Book> bookList = bookService.findAllBooks();
        Integer bookCount = bookList.size();

        model.addAttribute("bookList", bookList);
        model.addAttribute("bookCount", bookCount);

        return "manage/bookList";
    }

    @GetMapping("search/{name}")
        public Book getBookByName (@PathVariable("name") String name) {
        return bookService.getBookByName(name);
    }

    @GetMapping("toBookList")
    public String toBookList () {
        return "manage/bookList";
    }

    //  这种请求URL的原理、注意事项是什么？不懂别乱用，血的教训——实现delete操作——至少花了6h
    //  最后还是用普通get请求形式解决的
    @GetMapping("get/{id}")
    @ResponseBody
    public Book getBookById(@PathVariable("id") Integer id) {

        return bookService.getBookById(id);
    }

*/

    private String saveFile(MultipartFile file, Model model) throws Exception {

        //  验证文件大小
        if (file.getSize() > 1048576) {
            model.addAttribute("errorMessage", "文件大小超出限制，请上传不大于1MB的图片文件");
            return null;
        }

        //  验证文件类型
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        System.out.println(extName);
        if (!extName.equalsIgnoreCase(".jpg") && !extName.equalsIgnoreCase(".png")
                && !extName.equalsIgnoreCase(".jpeg") && !extName.equalsIgnoreCase(".pneg")) {
            model.addAttribute("errorMessage", "上传失败，文件类型有误，请上传图片文件");
            return null;
        }

        String fileName = UUID.randomUUID().toString() + extName;
        File path = new File(filePath);
        if (!path.exists()) {
            path.mkdirs();
        }

        FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(new File(filePath + fileName)));

        return fileName;
    }
}
