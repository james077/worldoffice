package com.worldoffice.worldofficeapi.repository;

import com.worldoffice.worldofficeapi.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where (:name is null or p.name like %:name%) " +
            "and  (:brand is null or p.brand = :brand) " +
            "and (:minPrice is null or :maxPrice is null or p.price BETWEEN :minPrice and :maxPrice)")
    Page<Product> find(@Param("name") String name, @Param("brand") String brand,
                       @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, Pageable pageable);
}
