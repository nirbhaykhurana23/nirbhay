package com.project.project.repositories;

import com.project.project.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(value = "select * from product where category_id IN (select id from category where name =:category)" , nativeQuery = true)
    List<Product> findAllProducts(@Param("category") String category);

    @Query(value = "select * from product where seller_user_id=:sellerid",nativeQuery = true)
    List<Product> findSellerAssociatedProducts(@Param("sellerid") Integer sellerid);

}
