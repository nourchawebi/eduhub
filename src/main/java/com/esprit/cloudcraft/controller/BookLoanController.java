package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.dto.BookDto;
import com.esprit.cloudcraft.services.BookLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookLoan")
public class BookLoanController {

    @Autowired
    private BookLoanService bookLoanService ;

    @PostMapping("/addBookLan")
    @ResponseBody
    public Boolean addBookLoan(
           @RequestBody BookDto dto) {
        if (dto != null) {
            return bookLoanService.addBookLoan(dto.getUser(), dto.getBook());
        }
        return Boolean.FALSE;
    }
}
