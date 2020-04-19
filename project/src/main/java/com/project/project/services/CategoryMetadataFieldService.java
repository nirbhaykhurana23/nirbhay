package com.project.project.services;

import com.project.project.Exceptions.BadRequestException;
import com.project.project.Exceptions.ResourceNotFoundException;
import com.project.project.Model.CategoryMetadataFieldModel;
import com.project.project.Model.MetadataFieldValueInsertModel;
import com.project.project.entities.Category;
import com.project.project.entities.CategoryMetadataField;
import com.project.project.entities.CategoryMetadataFieldValues;
import com.project.project.repositories.CategoryMetadataFieldRepository;
import com.project.project.repositories.CategoryMetadataFieldValuesRepository;
import com.project.project.repositories.CategoryRepository;
import com.project.project.utils.StringToSetParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryMetadataFieldService {

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public String addNewMetadataField(String fieldName){
        CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findByName(fieldName);
        if (categoryMetadataField!=null){
            throw new BadRequestException("Metadata field already exists");
        }
        else{
            CategoryMetadataField categoryMetadataField1= new CategoryMetadataField();
            categoryMetadataField1.setName(fieldName);
            categoryMetadataFieldRepository.save(categoryMetadataField1);
            return "Category metadata field created";
        }
    }

    public List<CategoryMetadataField> findAllMetadataFields(){
        return categoryMetadataFieldRepository.findAll();
    }

    public String addNewMetadataFieldValues(MetadataFieldValueInsertModel fieldValueDtos, Long categoryId, Long metaFieldId){

        Optional<Category> category= categoryRepository.findById(categoryId);
        Optional<CategoryMetadataField> categoryMetadataField= categoryMetadataFieldRepository.findById(metaFieldId);
        if (!category.isPresent())
            throw new ResourceNotFoundException("Category does not exists");
        else if (!categoryMetadataField.isPresent())
            throw new ResourceNotFoundException("Metadata field does not exists");
        else{
            Category category1= new Category();
            category1= category.get();

            CategoryMetadataField categoryMetadataField1= new CategoryMetadataField();
            categoryMetadataField1= categoryMetadataField.get();

            CategoryMetadataFieldValues categoryFieldValues = new CategoryMetadataFieldValues();

            for(CategoryMetadataFieldModel fieldValuePair : fieldValueDtos.getFieldValues()){

                String values = StringToSetParser.toCommaSeparatedString(fieldValuePair.getValues());

                categoryFieldValues.setValue(values);
                categoryFieldValues.setCategory(category1);
                categoryFieldValues.setCategoryMetadataField(categoryMetadataField1);

                categoryMetadataFieldValuesRepository.save(categoryFieldValues);
            }
            return "Metadata field values added successfully";
        }

    }

}
