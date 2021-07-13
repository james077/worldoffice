package com.worldoffice.worldofficeapi.delegate;

import com.worldoffice.worldofficeapi.dto.OrderDetailDto;
import com.worldoffice.worldofficeapi.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface IOrderDelegate {

    ResponseEntity<Integer> generateOrder(OrderDto OrderDto);

    ResponseEntity<String> addOrderDetail(OrderDetailDto orderDetailDto);

    ResponseEntity<OrderDto> confirmOrder(int id);
}
