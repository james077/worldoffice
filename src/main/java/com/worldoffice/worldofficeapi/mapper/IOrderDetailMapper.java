package com.worldoffice.worldofficeapi.mapper;


import com.worldoffice.worldofficeapi.domain.OrderDetail;
import com.worldoffice.worldofficeapi.dto.OrderDetailDto;
import org.mapstruct.*;

/**
 * @author James Martinez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IOrderDetailMapper extends IMapperGeneric<OrderDetail, OrderDetailDto> {

    @Mappings({
            @Mapping(source = "order.id", target = "orderId"),
    })
    OrderDetailDto entityToDto(OrderDetail orderDetail);
}
