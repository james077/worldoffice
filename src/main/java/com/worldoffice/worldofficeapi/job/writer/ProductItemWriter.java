package com.worldoffice.worldofficeapi.job.writer;

import java.util.List;

import com.worldoffice.worldofficeapi.domain.Product;
import com.worldoffice.worldofficeapi.dto.ProductDto;
import com.worldoffice.worldofficeapi.repository.IProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductItemWriter implements ItemWriter<ProductDto> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductItemWriter.class);
  
  @Autowired
  IProductRepository productTempRepository;

  @Override
  public void write(List<? extends ProductDto> items) throws Exception {
    LOGGER.info("Saving products {}", items);
    for(ProductDto item: items) {
      Product mt = Product
          .builder()
              .name(item.getName())
              .brand(item.getBrand())
              .price(item.getPrice())
              .quantity(item.getQuantity())
              .state(item.getState())
              .discountPercentage(item.getDiscountPercentage())
          .build();
      productTempRepository.save(mt);
    }
  }

}
