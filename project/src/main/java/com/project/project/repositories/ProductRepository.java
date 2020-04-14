package com.project.project.repositories;

import com.project.project.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query(value = "select pv.id, p.name, p.brand, p.description, pv.price from category c, product p, product_variation pv where c.name=:category and c.id=p.category_id and p.id=pv.product_id", nativeQuery = true)
    List<Object[]> findAllProducts(@Param("category") String category);

}
