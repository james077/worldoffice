package com.worldoffice.worldofficeapi.service;

import com.worldoffice.worldofficeapi.constants.ErrorMessages;
import com.worldoffice.worldofficeapi.domain.Order;
import com.worldoffice.worldofficeapi.domain.Product;
import com.worldoffice.worldofficeapi.dto.*;
import com.worldoffice.worldofficeapi.exception.InvalidOrderException;
import com.worldoffice.worldofficeapi.exception.NoContentException;
import com.worldoffice.worldofficeapi.mapper.IOrderMapper;
import com.worldoffice.worldofficeapi.mapper.IProductMapper;
import com.worldoffice.worldofficeapi.repository.ICustomerRepository;
import com.worldoffice.worldofficeapi.repository.IOrderRepository;
import com.worldoffice.worldofficeapi.repository.IProductRepository;
import com.worldoffice.worldofficeapi.service.impl.OrderService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private IOrderRepository orderRepository;

	@Mock
	private ICustomerRepository customerRepository;

	@Mock
	private IOrderMapper orderMapper;

	@Mock
	private IProductRepository productRepository;

	@Mock
	private IProductMapper productMapper;

	private OrderDto orderDto;

	@BeforeEach
	public void init(){
		CustomerDto customerDto = CustomerDto.builder()
				.id(1).name("Maria")
				.build();
		List<OrderDetailDto> orderDetails = new ArrayList<>();
		orderDetails.add(OrderDetailDto.builder()
			.product(ProductDto.builder().id(1).name("product").price(100000d).build())
			.quantity(1).build()
		);
		orderDto = OrderDto.builder().id(1)
				.customer(customerDto).total(1000000d).orderDetails(orderDetails)
				.build();
	}

	@Test
    public void saveOrUpdate_whenNewOrder_shouldSaveInfo() {
		//Arrange
        Integer id = 1;
        Order order = new Order();
        order.setId(id);
        Product product = new Product();
        when(orderMapper.dtoToEntity(any(OrderDto.class))).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

		//Act
        int newId = orderService.saveOrUpdate(orderDto);

        //Assert
        Assert.assertEquals(id.intValue(), newId);
    }

	@Test
	public void getOrderById_whenExistOrder() {
		//Arrange
		when(orderRepository.findById(anyInt())).thenReturn(Optional.of(new Order()));
		when(orderMapper.entityToDto(any(Order.class))).thenReturn(orderDto);
		//Act
		OrderDto response = orderService.getOrderById(1);
		//Assert
		Assert.assertNotNull(response);
		assertThat(orderDto.getId()).isEqualTo(1);
	}

	@Test
	public void getOrderById_whenNotExistOrder() {
		//Arrange
		when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

		//Act and Assert
		NoContentException thrown = assertThrows(
				NoContentException.class,
				() ->orderService.getOrderById(1)
		);
		Assert.assertEquals(thrown.getId(),1);
	}

}
