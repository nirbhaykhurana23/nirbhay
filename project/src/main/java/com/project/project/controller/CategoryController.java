package com.project.project.controller;

import com.project.project.Model.CategoryModel;
import com.project.project.Model.MetadataFieldValueInsertModel;
import com.project.project.entities.Category;
import com.project.project.entities.CategoryMetadataField;
import com.project.project.services.CategoryDaoService;
import com.project.project.services.CategoryMetadataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryDaoService categoryDaoService;

    @Autowired
    private CategoryMetadataFieldService categoryMetadataFieldService;

    @PostMapping("/save-category")
    public String saveCategory(@Valid @RequestBody CategoryModel categoryModel){
        return categoryDaoService.saveNewCategory(categoryModel);
    }

    @PostMapping("/save-category/{parentCategory}")
    public String saveSubCategory(@Valid @PathVariable String parentCategory, @RequestBody CategoryModel subCategories){
        return categoryDaoService.saveNewSubCategory(parentCategory, subCategories);
    }

    @GetMapping("/find-all-categories")
    public List<Category> findAllCategories(){
        return categoryDaoService.findAllCategory();
    }

    @GetMapping("/find-category/{category_id}")
    public Category findCategory(@PathVariable Long category_id){
        return categoryDaoService.findCategory(category_id);
    }

    @PutMapping ("/updateCategory/{category}")
    public String updateCategory(@RequestBody CategoryModel categoryModel, @PathVariable String category){
        return categoryDaoService.updateCategory(categoryModel, category);
    }

    @PostMapping("/metadata-fields/add")
    public String addMetaDataField(@RequestParam String fieldName) {
        return categoryMetadataFieldService.addNewMetadataField(fieldName);
    }

    @GetMapping("/find-all-metadata-fields")
    public List<CategoryMetadataField> findAllMetadataFields(){
        return categoryMetadataFieldService.findAllMetadataFields();
    }

    @PostMapping("/metadata-fields/addValues/{categoryId}/{metaFieldId}")
    public String addMetaDataFieldValues(@RequestBody MetadataFieldValueInsertModel fieldValueDtos, @PathVariable Long categoryId, @PathVariable Long metaFieldId) {
        return categoryMetadataFieldService.addNewMetadataFieldValues(fieldValueDtos, categoryId, metaFieldId);
    }

}
