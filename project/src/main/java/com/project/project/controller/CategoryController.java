package com.project.project.controller;

import com.project.project.Model.MetadataFieldValueInsertModel;
import com.project.project.entities.Category;
import com.project.project.services.CategoryDaoService;
import com.project.project.services.CategoryMetadataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryDaoService categoryDaoService;

    @Autowired
    private CategoryMetadataFieldService categoryMetadataFieldService;

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

    @PostMapping("/metadata-fields/add")
    public String addMetaDataField(@RequestParam String fieldName) {
        return categoryMetadataFieldService.addNewMetadataField(fieldName);
    }

    @PostMapping("/metadata-fields/addValues/{categoryId}/{metaFieldId}")
    public String addMetaDataFieldValues(@RequestBody MetadataFieldValueInsertModel fieldValueDtos, @PathVariable Long categoryId, @PathVariable Long metaFieldId) {
        return categoryMetadataFieldService.addNewMetadataFieldValues(fieldValueDtos, categoryId, metaFieldId);
    }

}
