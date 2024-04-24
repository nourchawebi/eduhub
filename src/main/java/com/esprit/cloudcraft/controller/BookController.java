package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.dto.*;
import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.Category;
import com.esprit.cloudcraft.services.BookService;
import com.esprit.cloudcraft.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
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

    @PostMapping(value = "/addBook/{idUsuer}/{idCategory}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addBook(@RequestParam String author,@RequestParam String title,@RequestParam String description,@RequestParam MultipartFile picture , @PathVariable Long idUsuer, @PathVariable Long idCategory) {
        System.out.println(picture);
        if (idUsuer !=null && idUsuer!=null)
        {
            Book newBook=Book.builder()
                    .title(title)
                    .description(description)
                    .author(author)
                    .build();
            bookService.addBook(newBook,idUsuer,idCategory,picture);
            return ResponseEntity.ok().build();

        }
        else {
            return ResponseEntity.notFound().build();
        }

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
    @GetMapping("/findBooksByid/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<PageResponse<BookResponse>> findAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page ,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
            )
    {
        return ResponseEntity.ok(bookService.findAll(page, size));
    }

}
