package com.worldoffice.worldofficeapi.delegate.impl;


import com.worldoffice.worldofficeapi.constants.ErrorMessages;
import com.worldoffice.worldofficeapi.constants.GeneralConstants;
import com.worldoffice.worldofficeapi.delegate.IOrderDelegate;
import com.worldoffice.worldofficeapi.dto.CustomerDto;
import com.worldoffice.worldofficeapi.dto.OrderDetailDto;
import com.worldoffice.worldofficeapi.dto.OrderDto;
import com.worldoffice.worldofficeapi.dto.ProductDto;
import com.worldoffice.worldofficeapi.exception.InvalidOrderException;
import com.worldoffice.worldofficeapi.service.ICustomerService;
import com.worldoffice.worldofficeapi.service.IOrderService;
import com.worldoffice.worldofficeapi.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderDelegate implements IOrderDelegate {

    private final IOrderService orderService;
    private final ICustomerService customerService;
    private final IProductService productService;

    @Override
    public ResponseEntity<Integer> generateOrder(OrderDto orderDto) {
        CustomerDto customerDto = customerService.getCustomerById(orderDto.getCustomer().getId());
        orderDto.setCustomer(customerDto);
        List<OrderDetailDto> orderDetailsDto = new ArrayList();
        orderDto.getOrderDetails().stream().forEach(orderDetail -> {
            setOrderDetailValues(orderDetail);
            validateStock(orderDetail);
            orderDetailsDto.add(orderDetail);
        });
        orderDto.setOrderDetails(orderDetailsDto);
        orderDto.calculateTotal();

        return ResponseEntity.ok(orderService.saveOrUpdate(orderDto));
    }

    @Override
    public ResponseEntity<String> addOrderDetail(OrderDetailDto orderDetailDto){
        OrderDto orderDto = orderService.getOrderById(orderDetailDto.getOrderId());
        boolean exist = false;
        for (OrderDetailDto od : orderDto.getOrderDetails()) {
            if (od.getProduct().getId().equals(orderDetailDto.getProduct().getId())) {
                od.setQuantity(od.getQuantity() + orderDetailDto.getQuantity());
                orderDetailDto = od;
                exist = true;
                break;
            }
        }
        if(!exist){
            setOrderDetailValues(orderDetailDto);
            orderDto.getOrderDetails().add(orderDetailDto);
        }
        validateStock(orderDetailDto);
        orderDto.calculateTotal();
        orderService.saveOrUpdate(orderDto);
        return ResponseEntity.ok(GeneralConstants.ADDED_PRODUCTS);
    }

    @Override
    public ResponseEntity<OrderDto> confirmOrder(int id){
        OrderDto orderDto = orderService.getOrderById(id);
        orderDto.getOrderDetails().stream().forEach(od -> {
            od.getProduct().setQuantity(od.getProduct().getQuantity()-od.getQuantity());
            productService.saveOrUpdate(od.getProduct());
        });
        orderDto.setConfirm(Boolean.TRUE);
        orderService.saveOrUpdate(orderDto);
        return ResponseEntity.ok(orderDto);
    }

    private void setOrderDetailValues(OrderDetailDto orderDetailDto){
        ProductDto productDto = productService.getProductById(orderDetailDto.getProduct().getId());
        orderDetailDto.setProduct(productDto);
        orderDetailDto.setPrice(productDto.getPrice());
        orderDetailDto.setDiscountPercentage(productDto.getDiscountPercentage());
    }

    private void validateStock(OrderDetailDto orderDetailDto){
        if(orderDetailDto.getProduct().getQuantity() < orderDetailDto.getQuantity())
            throw InvalidOrderException.builder()
                    .items(orderDetailDto.getQuantity())
                    .customError("Error:" + ErrorMessages.INSUFFICIENT_PRODUCTS+orderDetailDto.getProduct().getName()).build();
    }

}
