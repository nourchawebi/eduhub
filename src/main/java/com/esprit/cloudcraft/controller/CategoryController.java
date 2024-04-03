package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.Category;
import com.esprit.cloudcraft.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
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
    public List<Category> GetAllCategories () {
        if (!this.categoryService.getAllCategory().isEmpty()) {
            return this.categoryService.getAllCategory();
        }
        else
            return null;
    }

    @DeleteMapping("/deleteCategory/{id}")
    @ResponseBody
    public Object deleteCategory(@PathVariable Long id) {
        if (categoryService.getCategoryByID(id) != null) {
            Category category = categoryService.getCategoryByID(id);
            categoryService.deleteCategory(category);
            return "category deleted with success";
        }
        else
        {
            return null;
        }
    }

    @PostMapping("/addCategory")
    @ResponseBody
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PutMapping("/updateCategory/{id}")
    @ResponseBody
    public Category updateCategory(@PathVariable Long id, @RequestBody Category categoryupdated) {
        Category category = categoryService.getCategoryByID(id);
        if (category != null) {
            category.setName(categoryupdated.getName());
            category.setDescription(categoryupdated.getName());
            return categoryService.UpdateCategory(category);
        }
        else
        {
            return null ;
        }
    }
}
