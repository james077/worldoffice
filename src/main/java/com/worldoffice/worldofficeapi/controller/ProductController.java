package com.worldoffice.worldofficeapi.controller;

import com.worldoffice.worldofficeapi.constants.ResourceMapping;
import com.worldoffice.worldofficeapi.dto.ProductDto;
import com.worldoffice.worldofficeapi.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(ResourceMapping.PRODUCT)
public class ProductController {

    private final IProductService productService;

    @GetMapping()
    public ResponseEntity<Page<ProductDto>> getProducts(@PageableDefault(size = 10, page = 0) Pageable pageable,
                                                        @RequestParam(required = false) String name, @RequestParam(required = false) Double min,
                                                        @RequestParam(required = false) Double max, @RequestParam(required = false) String brand) {
        return ResponseEntity.ok(productService.getProducts(name, brand,  min, max, pageable));
    }

}
