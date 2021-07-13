package com.worldoffice.worldofficeapi.service;

import com.worldoffice.worldofficeapi.dto.OrderDetailDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderDetailservice {

    ResponseEntity<Integer> saveOrUpdate(OrderDetailDto orderDetailDto);

    ResponseEntity<OrderDetailDto>getOrderDetailById(Integer id);

    ResponseEntity<List<OrderDetailDto>> getOrderDetails();

}
