package com.worldoffice.worldofficeapi.service;

import com.worldoffice.worldofficeapi.domain.OrderDetail;
import com.worldoffice.worldofficeapi.dto.OrderDetailDto;
import com.worldoffice.worldofficeapi.mapper.IOrderDetailMapper;
import com.worldoffice.worldofficeapi.repository.IOrderDetailRepository;
import com.worldoffice.worldofficeapi.service.impl.OrderDetailService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderDetailServiceTest {

	@InjectMocks
	private OrderDetailService orderDetailservice;

	@Mock
	private IOrderDetailRepository orderDetailRepository;

	@Mock
	private IOrderDetailMapper orderDetailMapper;


	@Test
    public void saveOrUpdate_whenNewOrderDetail_shouldSaveInfo() {
		//Arrange
        Integer id = 1;
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(id);
        when(orderDetailMapper.dtoToEntity(any(OrderDetailDto.class))).thenReturn(orderDetail);
        when(orderDetailRepository.save(orderDetail)).thenReturn(orderDetail);
		//Act
        ResponseEntity<Integer> newId = orderDetailservice.saveOrUpdate(new OrderDetailDto());
        //Assert
        Assert.assertEquals(id, newId.getBody());
        assertThat(newId.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void saveOrUpdate_whenNotSaveInfo() {
		//Arrange
        OrderDetail orderDetail = new OrderDetail();
        when(orderDetailMapper.dtoToEntity(any(OrderDetailDto.class))).thenReturn(orderDetail);
        when(orderDetailRepository.save(orderDetail)).thenReturn(orderDetail);
		//Act
        ResponseEntity<Integer> response = orderDetailservice.saveOrUpdate(new OrderDetailDto());
		//Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

	@Test
	public void getOrderDetailById_whenExistOrderDetail() {
		//Arrange
		when(orderDetailRepository.findById(anyInt())).thenReturn(Optional.of(new OrderDetail()));
		when(orderDetailMapper.entityToDto(any(OrderDetail.class))).thenReturn(new OrderDetailDto());
		//Act
		ResponseEntity<OrderDetailDto> response = orderDetailservice.getOrderDetailById(1);
		//Assert
		Assert.assertNotNull(response.getBody());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void getOrderDetail_whenNotExistOrderDetail() {
		//Arrange
		when(orderDetailRepository.findById(anyInt())).thenReturn(Optional.empty());
		//Act
		ResponseEntity<OrderDetailDto> response = orderDetailservice.getOrderDetailById(1);
		//Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	public void getOrderDetails_whenExistOrderDetails() {
		//Arrange
		List<OrderDetail> orderDetails  = new ArrayList<>();
		orderDetails.add(new OrderDetail());
		when(orderDetailRepository.findAll()).thenReturn(orderDetails);
		when(orderDetailMapper.entityToDto(any(OrderDetail.class))).thenReturn(new OrderDetailDto());
		//Act
		ResponseEntity<List<OrderDetailDto>> response = orderDetailservice.getOrderDetails();
		//Assert
		Assert.assertNotNull(response.getBody());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void getOrderDetails_whenNotExistOrderDetails() {
		//Arrange
		when(orderDetailRepository.findAll()).thenReturn(null);
		//Act
		ResponseEntity<List<OrderDetailDto>> response = orderDetailservice.getOrderDetails();
		//Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
}
