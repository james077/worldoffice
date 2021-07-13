package com.worldoffice.worldofficeapi.service;

import com.worldoffice.worldofficeapi.dto.CustomerDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICustomerService {

    CustomerDto getCustomerById(Integer id);

    ResponseEntity<List<CustomerDto>> getCustomers();

}
