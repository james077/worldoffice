package com.worldoffice.worldofficeapi.service.impl;

import com.worldoffice.worldofficeapi.domain.Customer;
import com.worldoffice.worldofficeapi.dto.CustomerDto;
import com.worldoffice.worldofficeapi.exception.NoContentException;
import com.worldoffice.worldofficeapi.mapper.ICustomerMapper;
import com.worldoffice.worldofficeapi.repository.ICustomerRepository;
import com.worldoffice.worldofficeapi.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final ICustomerRepository customerRepository;
    private final ICustomerMapper customerMapper;

    @Override
    public CustomerDto getCustomerById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent())
            return customerMapper.entityToDto(customer.get());

        throw NoContentException.builder().id(id).build();
    }

    @Override
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<Customer> customers = customerRepository.findAll();

        if(customers == null)
            return ResponseEntity.noContent().build();

        List<CustomerDto> customersDto = customers.stream()
                .map(customer-> customerMapper.entityToDto(customer))
                .collect(Collectors.toList());

        return ResponseEntity.ok(customersDto);
    }

}
