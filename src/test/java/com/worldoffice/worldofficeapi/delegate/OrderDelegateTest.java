package com.worldoffice.worldofficeapi.delegate;

import com.worldoffice.worldofficeapi.constants.ErrorMessages;
import com.worldoffice.worldofficeapi.delegate.impl.OrderDelegate;
import com.worldoffice.worldofficeapi.dto.CustomerDto;
import com.worldoffice.worldofficeapi.dto.OrderDetailDto;
import com.worldoffice.worldofficeapi.dto.OrderDto;
import com.worldoffice.worldofficeapi.dto.ProductDto;
import com.worldoffice.worldofficeapi.exception.InvalidOrderException;
import com.worldoffice.worldofficeapi.service.ICustomerService;
import com.worldoffice.worldofficeapi.service.IOrderService;
import com.worldoffice.worldofficeapi.service.IProductService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderDelegateTest {

    @InjectMocks
    private OrderDelegate orderDelegate;

    @Mock
    private IOrderService orderService;

    @Mock
    private ICustomerService customerService;

    @Mock
    private IProductService productService;

    private OrderDto orderDto;
    private CustomerDto customerDto;
    private ProductDto productDto;

    @BeforeEach
    public void init(){
        customerDto = CustomerDto.builder()
                .id(1).name("Maria")
                .build();
        List<OrderDetailDto> orderDetails = new ArrayList<>();
        orderDetails.add(OrderDetailDto.builder()
                .product(ProductDto.builder().id(1).name("product").price(100000d).build())
                .quantity(1).build()
        );
        orderDto = OrderDto.builder()
                .customer(customerDto).total(1000000d).orderDetails(orderDetails)
                .build();
        productDto = ProductDto.builder()
                .name("product1").price(100000d)
                .quantity(10).discountPercentage(5f)
                .build();
    }

    @Test
    public void generateOrder_whenDataIsCorrect() {
        //Arrange
        when(customerService.getCustomerById(anyInt())).thenReturn(customerDto);
        when(orderService.saveOrUpdate(orderDto)).thenReturn(1);
        when(productService.getProductById(anyInt())).thenReturn(productDto);
        //Act
        ResponseEntity<Integer> response = orderDelegate.generateOrder(orderDto);
        //Assert
        Assert.assertNotNull(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void generateOrder_whenNotProducts() {
        //Arrange
        List<OrderDetailDto> orderDetails = new ArrayList<>();
        orderDetails.add(OrderDetailDto.builder()
                .product(ProductDto.builder().id(1).name("product").price(100000d).build())
                .quantity(6).build()
        );
        orderDto.setOrderDetails(orderDetails);
        productDto.setQuantity(1);
        when(productService.getProductById(anyInt())).thenReturn(productDto);

        //Act and Assert
        InvalidOrderException thrown = assertThrows(
                InvalidOrderException.class,
                () ->orderDelegate.generateOrder(orderDto)
        );
        Assert.assertEquals(thrown.getCustomError(),
                "Error:" + ErrorMessages.INSUFFICIENT_PRODUCTS+productDto.getName());
    }

}
