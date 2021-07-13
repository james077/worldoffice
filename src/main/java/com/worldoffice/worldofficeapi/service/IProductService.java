package com.worldoffice.worldofficeapi.service;

import com.worldoffice.worldofficeapi.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IProductService {

    Page<ProductDto> getProducts(String name, String brand, Double minPrice,Double maxPrice, Pageable pageable);

    int saveOrUpdate(ProductDto productDto );

    ProductDto getProductById(Integer id);

}
