package com.project.project.controller;

import com.project.project.entities.Category;
import com.project.project.services.CategoryDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryDaoService categoryDaoService;

    @PostMapping("/save-category")
    public void saveCategory(@RequestBody Category category){
        Category category1= categoryDaoService.saveNewCategory(category);
    }

    @PostMapping("/save-category/{parentCategory}")
    public void saveSubCategory(@PathVariable String parentCategory, @RequestBody List<Category> subCategory){
        List<Category> category1= categoryDaoService.saveNewSubCategory(parentCategory, subCategory);
    }

    @GetMapping("/find-all-categories")
    public List<Category> findAllCategories(){
        return categoryDaoService.findAllCategory();
    }

}
