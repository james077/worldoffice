package com.worldoffice.worldofficeapi.mapper;

import com.worldoffice.worldofficeapi.domain.Customer;
import com.worldoffice.worldofficeapi.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * @author James Martinez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ICustomerMapper extends IMapperGeneric<Customer, CustomerDto> {

}
