package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.dto.BookDto;
import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.Category;
import com.esprit.cloudcraft.services.BookService;
import com.esprit.cloudcraft.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getBookById/{id}")
    @ResponseBody
    public Book findBookById (@PathVariable Long id) {
        return this.bookService.getBookByID(id);
    }

    @GetMapping("/getAllBooks")
    @ResponseBody
    public List<Book> GetAllBooks () {
        if (!this.bookService.getAllBooks().isEmpty()) {
            return this.bookService.getAllBooks();
        }
        else
            return null;
    }

    @GetMapping("/getBooksByCategory/{id}")
    @ResponseBody
    public List<Book> getAllBooksByCategory (@PathVariable Long id)
    {
        Category category = categoryService.getCategoryByID(id);
        if (!this.bookService.findBookByCategory(category).isEmpty()) {
            return this.bookService.findBookByCategory(category);
        }
        else
            return null;
    }

    @DeleteMapping("/deleteBook/{id}")
    @ResponseBody
    public Object deleteBook(@PathVariable Long id) {
        if (bookService.getBookByID(id) != null) {
            Book book = bookService.getBookByID(id);
            bookService.deleteBook(book);
            return "book deleted with success";
        }
        else
        {
            return null;
        }
    }

    @PostMapping("/addBook")
    @ResponseBody
    public Boolean addBook(@RequestBody BookDto dto) {
        if (dto!= null){
            return bookService.addBook(dto.getUser(), dto.getBook());
        }
        return Boolean.FALSE;

    }

    @PutMapping("/updateBook/{id}")
    @ResponseBody
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookupdated) {
        Book book = bookService.getBookByID(id);
        if (book != null) {
            book.setPicture(bookupdated.getPicture());
            book.setAuthor(bookupdated.getAuthor());
            book.setTitle(bookupdated.getTitle());
            book.setDescription(bookupdated.getDescription());
            return bookService.UpdateBook(book);
        }
        else
        {
            return null ;
        }
    }
}
