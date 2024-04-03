package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.Category;
import com.esprit.cloudcraft.repository.CategoryDao;
import com.esprit.cloudcraft.services.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryServiceImp implements CategoryService {
    @Resource
    private CategoryDao categoryDao;

    @Override
    public Category addCategory(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryDao.findAll();
    }

    @Override
    public Category getCategoryByID(Long id) {

        if(id!= null)
        {
            final Optional<Category> optionalCategory= categoryDao.findById(id);
            if (optionalCategory.isPresent())
            {
                return optionalCategory.get();
            }
        }
        return null;
    }

    @Override
    public Category UpdateCategory(Category category) {
        return categoryDao.saveAndFlush(category);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryDao.delete(category);

    }
}
