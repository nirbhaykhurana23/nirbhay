package com.project.project.services;

import com.project.project.entities.Category;
import com.project.project.entities.User;
import com.project.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryDaoService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveNewCategory(Category category){
        categoryRepository.save(category);
        return category;
    }

    public List<Category> saveNewSubCategory(String parentCategory, List<Category> subCategory){
        Optional<Category> parent_Category=categoryRepository.findByName(parentCategory);

        Category category=new Category();
        category=parent_Category.get();

        //category.addSubCategory(subCategory);

        Category finalCategory = category;
        subCategory.forEach(e->e.setParentCategory(finalCategory));

        categoryRepository.saveAll(subCategory);
        return subCategory;
    }

    public List<Category> findAllCategory(){
        return (List<Category>) categoryRepository.findAll();
    }

}
