package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory (Category category);
    List<Category> getAllCategory();
    Category getCategoryByID(Long id);
    Category UpdateCategory (Category category);
    void deleteCategory (Category category);
}
