package com.worldoffice.worldofficeapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDto {

    private Integer id;
    @NotNull
    private CustomerDto customer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    @NotNull
    private String deliveryAddress;
    private Double total;
    private Boolean confirm;
    @NotNull
    private List<OrderDetailDto> orderDetails;

    public void calculateTotal(){
        this.setTotal(this.orderDetails.stream()
                .map(item ->  ((100-item.getDiscountPercentage()) * item.getPrice() * item.getQuantity())/100)
                .collect(Collectors.toList())
                .stream().reduce((double) 0, (a, b) -> a + b));
    }
}
