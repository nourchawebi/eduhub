package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.Category;
import com.esprit.cloudcraft.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryById/{id}")
    @ResponseBody
    public Category findCategoryById (@PathVariable Long id) {
        return this.categoryService.getCategoryByID(id);
    }

    @GetMapping("/getAllCategories")
    @ResponseBody
    public List<Category> getAllCategories () {
        if (!this.categoryService.getAllCategory().isEmpty()) {
            return this.categoryService.getAllCategory();
        }
        else
            return null;
    }

    @DeleteMapping("/deleteCategory/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        Category category = categoryService.getCategoryByID(id);
        if (category != null) {
            categoryService.deleteCategory(category);
            return ResponseEntity.ok().body("Category deleted with success");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addCategory")
    @ResponseBody
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PutMapping("/updateCategory")
    @ResponseBody
    public ResponseEntity<Object> updateCategory(@RequestBody Category category) {

        if (category != null) {
            categoryService.UpdateCategory(category);
            return ResponseEntity.ok().body("Category updated with success");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
