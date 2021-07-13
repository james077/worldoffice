package com.worldoffice.worldofficeapi.service.impl;

import com.worldoffice.worldofficeapi.domain.Order;
import com.worldoffice.worldofficeapi.dto.OrderDto;
import com.worldoffice.worldofficeapi.exception.NoContentException;
import com.worldoffice.worldofficeapi.mapper.IOrderMapper;
import com.worldoffice.worldofficeapi.repository.IOrderRepository;
import com.worldoffice.worldofficeapi.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IOrderMapper orderMapper;

    @Override
    public int saveOrUpdate(OrderDto orderDto) {
        log.info("Saving order...");
        Order order = orderMapper.dtoToEntity(orderDto);
        order.setAllJoins();
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public OrderDto getOrderById(Integer id) {
        log.info("Getting order with id "+id);
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent())
            return orderMapper.entityToDto(order.get());

        throw NoContentException.builder().id(id).build();
    }

    @Override
    public Boolean deleteOrder(Integer orderId){
        log.info("Deleting order...");
        if(orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return Boolean.TRUE;
        }
        throw NoContentException.builder().id(orderId).build();
    }

}
