package com.project.project.repositories;

import com.project.project.entities.CategoryMetadataFieldValues;
import com.project.project.utils.CategoryMetadataFieldValuesID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMetadataFieldValuesRepository extends CrudRepository<CategoryMetadataFieldValues, CategoryMetadataFieldValuesID> {

}
