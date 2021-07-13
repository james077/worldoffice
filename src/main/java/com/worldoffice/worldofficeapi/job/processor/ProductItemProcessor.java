package com.worldoffice.worldofficeapi.job.processor;

import com.worldoffice.worldofficeapi.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.google.common.base.Strings;
import java.util.HashSet;
import java.util.Set;

public class ProductItemProcessor implements ItemProcessor<ProductDto, ProductDto>{

  
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductItemProcessor.class);
  private Set<ProductDto> productDtos = new HashSet<>();
  
  @Override
  public ProductDto process(ProductDto productDto){
    LOGGER.info("Processing  the product {}", productDto);
    if(isValid(productDto))
      return productDto;

    return null;
  }

  private boolean isValid(ProductDto productDto){
    if(productDtos.contains(productDto) || containNullFields(productDto))
      return false;

    productDtos.add(productDto);
    return true;
  }

  private boolean containNullFields(ProductDto productDto){
    if(Strings.isNullOrEmpty(productDto.getName()) || Strings.isNullOrEmpty(productDto.getBrand()) ||
        Strings.isNullOrEmpty(productDto.getState()) || productDto.getPrice() == null ||
        productDto.getQuantity() == null || productDto.getDiscountPercentage() == null) {
      return true;
    }
    return false;
  }

}
