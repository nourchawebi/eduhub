package com.esprit.cloudcraft.dto;

import com.esprit.cloudcraft.entities.Category;
import com.esprit.cloudcraft.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRequest(Long id,
                          @NotNull(message = "100")
                          @NotEmpty(message = "100")
                          String title,
                          @NotNull(message = "101")
                          @NotEmpty(message = "101")
                          String author,
                          @NotNull(message = "102")
                          @NotEmpty(message = "102")
                          String description
                         )
{
}
