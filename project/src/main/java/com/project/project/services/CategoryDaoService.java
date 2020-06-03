package com.project.project.services;

import com.project.project.Exceptions.ResourceNotFoundException;
import com.project.project.Model.CategoryModel;
import com.project.project.entities.Category;
import com.project.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryDaoService {

    @Autowired
    private CategoryRepository categoryRepository;

    public String saveNewCategory(CategoryModel categoryModel){

        Optional<Category> category= categoryRepository.findByName(categoryModel.getName());
        if (category.isPresent())
            return "Category already exists";
        else {
            ModelMapper modelMapper = new ModelMapper();
            Category category1= modelMapper.map(categoryModel, Category.class);

            categoryRepository.save(category1);
            return "Category saved";
        }

    }

    public String saveNewSubCategory(String parentCategory, CategoryModel subCategories){

        Optional<Category> parent_Category=categoryRepository.findByName(parentCategory);

        if (parent_Category.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
//            Type listType=new TypeToken<List<Category>>(){}.getType();
            Category subCategories1= modelMapper.map(subCategories, Category.class);

            Category category=new Category();
            category=parent_Category.get();

            //category.addSubCategory(subCategories);

            Category finalCategory = category;
//            subCategories1.forEach(e->e.setParentCategory(finalCategory));
            subCategories1.setParentCategory(finalCategory);

            categoryRepository.save(subCategories1);
            return "Child category saved";
        }
        else
            throw new ResourceNotFoundException("Parent Category does not exists");

    }

    public List<Category> findAllCategory(){
        return (List<Category>) categoryRepository.findAll();
    }

    public Category findCategory(Long categoryId){
        Optional<Category> category= categoryRepository.findById(categoryId);
        if (category.isPresent())
        {
            Category category1= new Category();
            category1= category.get();
            return category1;
        }
        else
            throw new ResourceNotFoundException("Category ID does not exist");
    }

    public String updateCategory(CategoryModel categoryModel, String category){
        Optional<Category> category1=categoryRepository.findByName(category);

        if (category1.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            Category category2= modelMapper.map(categoryModel, Category.class);

            Category category3= category1.get();
            if (category2.getName()!=null)
                category3.setName(category2.getName());
            categoryRepository.save(category3);

            return "Category updated";
        }
        else
            throw new ResourceNotFoundException("Category name does not exist");
    }



}
