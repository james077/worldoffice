package com.worldoffice.worldofficeapi.controller;


import com.worldoffice.worldofficeapi.constants.ResourceMapping;
import com.worldoffice.worldofficeapi.dto.ProductDto;
import com.worldoffice.worldofficeapi.service.impl.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters=false)
@ContextConfiguration(classes ={ProductController.class})
@WebMvcTest
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void getProducts_OK() throws Exception {
        //Arrange
        List<ProductDto> products = new ArrayList();
        ProductDto productDto = ProductDto.builder().name("RAM").price(200000d).build();
        products.add(productDto);
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.asc("name"),Sort.Order.desc("id")));
        Page<ProductDto> pageProducts = new PageImpl<>(products, pageable,100);
        when(productService.getProducts("one", null, null, null,pageable)).thenReturn(pageProducts);

        //Act and Assert
        mvc.perform(get(ResourceMapping.PRODUCT+"/?name=one")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}

