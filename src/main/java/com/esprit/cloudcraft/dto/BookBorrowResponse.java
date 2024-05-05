
package com.esprit.cloudcraft.dto;

import lombok.*;

import java.util.Date;

@Builder

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookBorrowResponse {
    private Long id ;
    private Date loanDate;
    private Date dueDate;
    private boolean returned ;
    private Long idBook;
    private String title;
    private String author;
    private String description;
    private String coverPicture ;
    private String owner;
    private String category ;

}
