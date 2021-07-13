package com.worldoffice.worldofficeapi.controller;


import com.worldoffice.worldofficeapi.constants.ResourceMapping;
import com.worldoffice.worldofficeapi.delegate.IOrderDelegate;
import com.worldoffice.worldofficeapi.dto.OrderDetailDto;
import com.worldoffice.worldofficeapi.dto.OrderDto;
import com.worldoffice.worldofficeapi.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping(ResourceMapping.ORDER)
public class OrderController {

    private final IOrderService orderService;
    private final IOrderDelegate orderDelegate;

    @GetMapping(value ="/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable int id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable int id) {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }

    @PostMapping(value = ResourceMapping.SAVE)
    public ResponseEntity<Integer> saveOrder(@RequestBody OrderDto orderDto) {
        return orderDelegate.generateOrder(orderDto);
    }

    @PostMapping(value =ResourceMapping.ADD)
    public ResponseEntity<String> addDetailOrder(@RequestBody @Valid OrderDetailDto orderDetailDto) {
        return orderDelegate.addOrderDetail(orderDetailDto);
    }

    @GetMapping(value =ResourceMapping.CONFIRM + "/{id}")
    public ResponseEntity<OrderDto> confirmOrder(@PathVariable int id) {
        return orderDelegate.confirmOrder(id);
    }

}
