package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.dto.*;
import com.esprit.cloudcraft.entities.Book;

import com.esprit.cloudcraft.entities.userEntities.User;

import com.esprit.cloudcraft.services.BookLoanService;
import com.esprit.cloudcraft.services.BookService;
import com.esprit.cloudcraft.services.FileStorageService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin("*")
public class BookController {
    @jakarta.annotation.Resource
    private BookService bookService;

    @jakarta.annotation.Resource
    private FileStorageService fileStorageService;
    @jakarta.annotation.Resource
    private BookLoanService bookLoanService ;


    @PostMapping(value = "/addBook/{idCategory}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addBook(@RequestParam String author,@RequestParam String title,@RequestParam String description,@RequestParam MultipartFile picture ,
                                          @PathVariable Long idCategory, Principal connectedUser) {
        System.out.println(picture);
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Long idUser = user.getId();
        if (idUser !=null && idCategory!=null)
        {
            Book newBook=Book.builder()
                    .title(title)
                    .description(description)
                    .author(author)
                    .build();
            bookService.addBook(newBook,idUser,idCategory,picture);
            return ResponseEntity.ok().build();

        }
        else {
            return ResponseEntity.notFound().build();
        }

    }
    @PostMapping(value = "/updateBook",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateBook(@RequestParam String author,@RequestParam String title,@RequestParam String description,
                                             @RequestParam Long idBook) {

        if (author != null && title != null && description != null ) {
            return ResponseEntity.ok(bookService.saveBook(idBook,title, author,description));
        }
        else
        {
            return ResponseEntity.notFound().build() ;
        }
    }
    @PostMapping("/borrowBook/{idBook}")
    public ResponseEntity<Object> addBookLoan(@PathVariable Long idBook, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Book book = bookService.getBookByID(idBook);
        if (user!=null && book != null) {
            return ResponseEntity.ok(bookLoanService.addBookLoan(user, book));
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/returnBook/{idBook}")
    public ResponseEntity<Object> returnBook(@PathVariable Long idBook) {
        if (idBook!=null) {
            return ResponseEntity.ok(bookLoanService.returnBookLoan(idBook));
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
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
    @GetMapping("/findBooksByid/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<PageResponse<BookResponse>> findAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page ,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Principal connectedUser
    )
    {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Long idUser = user.getId();
        return ResponseEntity.ok(bookService.findAll(page, size, idUser));
    }

    @GetMapping("/findBooksByUser")
    public ResponseEntity<PageResponse<BookResponse>>findUserBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page ,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Principal connectedUser)

    {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (user!=null)
        {
            return ResponseEntity.ok(bookService.findBookByUser(page, size,user));
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findBookLoansByUser")
    public ResponseEntity<PageResponse<BookBorrowResponse>>findUserBookLoans(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page ,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Principal connectedUser)

    {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (user!=null)
        {
            return ResponseEntity.ok(bookLoanService.findBookLoansByUser(page, size,user));
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName)    {
        // Construct the file path
        Path filePath =fileStorageService.getImagePath(fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        // Check if the file exists
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Set content type as application/pdf
        HttpHeaders headers = new HttpHeaders();
        String contentType;
        if (fileName.toLowerCase().endsWith(".pdf")) {
            contentType = MediaType.APPLICATION_PDF_VALUE;
        } else if (fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            contentType = MediaType.IMAGE_JPEG_VALUE; // You can use IMAGE_PNG_VALUE for PNG files
        } else {
            // Default to binary data if the file type is unknown
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        // Return the file/image as ResponseEntity with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }



}
