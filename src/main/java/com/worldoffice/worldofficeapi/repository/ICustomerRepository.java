package com.worldoffice.worldofficeapi.repository;

import com.worldoffice.worldofficeapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author James Martinez
 */
@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Integer> {

}