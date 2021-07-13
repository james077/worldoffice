package com.worldoffice.worldofficeapi.mapper;


import com.worldoffice.worldofficeapi.domain.Order;
import com.worldoffice.worldofficeapi.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * @author James Martinez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IOrderMapper extends IMapperGeneric<Order, OrderDto> {

}
