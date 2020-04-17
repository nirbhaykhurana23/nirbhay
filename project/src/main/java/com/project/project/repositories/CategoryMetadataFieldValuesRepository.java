package com.project.project.repositories;

import com.project.project.entities.CategoryMetadataFieldValues;
import com.project.project.utils.CategoryMetadataFieldValuesID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMetadataFieldValuesRepository extends CrudRepository<CategoryMetadataFieldValues, CategoryMetadataFieldValuesID> {

   /* @Query(value = "select f.id, f.name from category_metadata_field_values v " +
            "inner join " +
            "category_metadata_field f where v.category_metadata_field_id = f.id " +
            "and v.category_id= :categoryId", nativeQuery = true)
    List<Object[]> findMetadataFieldsByCategoryId(Long categoryId);*/

}
