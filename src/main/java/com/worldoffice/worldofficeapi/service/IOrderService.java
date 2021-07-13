package com.worldoffice.worldofficeapi.service;

import com.worldoffice.worldofficeapi.dto.OrderDto;
import org.springframework.http.ResponseEntity;


public interface IOrderService {

    int saveOrUpdate(OrderDto orderDto);

    OrderDto getOrderById(Integer id);

    Boolean deleteOrder(Integer orderId);

}
