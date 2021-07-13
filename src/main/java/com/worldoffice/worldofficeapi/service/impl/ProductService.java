package com.worldoffice.worldofficeapi.service.impl;


import com.worldoffice.worldofficeapi.domain.Order;
import com.worldoffice.worldofficeapi.domain.Product;
import com.worldoffice.worldofficeapi.dto.OrderDto;
import com.worldoffice.worldofficeapi.dto.ProductDto;
import com.worldoffice.worldofficeapi.exception.NoContentException;
import com.worldoffice.worldofficeapi.mapper.IProductMapper;
import com.worldoffice.worldofficeapi.repository.IProductRepository;
import com.worldoffice.worldofficeapi.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IProductMapper productMapper;


    @Override
    public Page<ProductDto> getProducts(String name, String brand, Double minPrice, Double maxPrice, Pageable pageable) {
        log.info("Getting product...");
        return productRepository.find(name, brand, minPrice, maxPrice, pageable)
               .map(product -> productMapper.entityToDto(product));
    }

    @Override
    public int saveOrUpdate(ProductDto productDto ) {
        log.info("Saving product...");
        Product product = productMapper.dtoToEntity(productDto);
        productRepository.save(product);
        return product.getId();
    }

    @Override
    public ProductDto getProductById(Integer id) {
        log.info("Getting product with id :" +id);
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent())
            return productMapper.entityToDto(product.get());

        throw NoContentException.builder().id(id).build();
    }

}
