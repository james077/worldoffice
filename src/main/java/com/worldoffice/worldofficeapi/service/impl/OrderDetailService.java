package com.worldoffice.worldofficeapi.service.impl;

import com.worldoffice.worldofficeapi.domain.OrderDetail;
import com.worldoffice.worldofficeapi.dto.OrderDetailDto;
import com.worldoffice.worldofficeapi.mapper.IOrderDetailMapper;
import com.worldoffice.worldofficeapi.repository.IOrderDetailRepository;
import com.worldoffice.worldofficeapi.service.IOrderDetailservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailservice {

    private final IOrderDetailRepository orderDetailRepository;
    private final IOrderDetailMapper orderDetailMapper;

    @Override
    public ResponseEntity<Integer> saveOrUpdate(OrderDetailDto OrderDetailDto) {
        OrderDetail orderDetail = orderDetailRepository
                .save(orderDetailMapper.dtoToEntity(OrderDetailDto));
        if (orderDetail.getId() == null)
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(orderDetail.getId());
    }

    @Override
    public ResponseEntity<OrderDetailDto> getOrderDetailById(Integer id) {
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);
        if (!orderDetail.isPresent())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(orderDetailMapper.entityToDto(orderDetail.get()));
    }

    @Override
    public ResponseEntity<List<OrderDetailDto>> getOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        if(orderDetails == null)
            return ResponseEntity.noContent().build();

        List<OrderDetailDto> orderDetailsDto = orderDetails.stream()
                .map(orderDetail-> orderDetailMapper.entityToDto(orderDetail))
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderDetailsDto);
    }

}
