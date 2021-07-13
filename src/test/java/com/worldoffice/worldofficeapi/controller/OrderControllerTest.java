package com.worldoffice.worldofficeapi.controller;

import com.worldoffice.worldofficeapi.constants.GeneralConstants;
import com.worldoffice.worldofficeapi.constants.ResourceMapping;
import com.worldoffice.worldofficeapi.delegate.impl.OrderDelegate;
import com.worldoffice.worldofficeapi.dto.CustomerDto;
import com.worldoffice.worldofficeapi.dto.OrderDto;
import com.worldoffice.worldofficeapi.service.impl.OrderService;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters=false)
@ContextConfiguration(classes ={OrderController.class})
@WebMvcTest
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderDelegate orderDelegate;
    
    @Autowired
    private MockMvc mvc;

    private OrderDto orderDto;
    private ObjectWriter ow;

    @BeforeEach
    public void init(){
        CustomerDto customerDto = CustomerDto.builder()
                .name("Maria").build();
        orderDto = OrderDto.builder()
                .customer(customerDto).total(1000000d).build();

        org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.AUTO_DETECT_SETTERS, false);
        ow = mapper.writer().withDefaultPrettyPrinter();
    }


    @Test
    public void saveOrder_OK() throws Exception {
        //Arrange
        when(orderDelegate.generateOrder(orderDto)).thenReturn(ResponseEntity.ok(1));
        String requestJson = ow.writeValueAsString(orderDto);

        //Act and Assert
        mvc.perform(post(ResourceMapping.ORDER + ResourceMapping.SAVE)
                .content(requestJson)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getOrderById_OK() throws Exception {
        //Arrange
        orderDto.setId(1);
        when(orderService.getOrderById(orderDto.getId())).thenReturn(orderDto);

        //Act and Assert
        mvc.perform(get(ResourceMapping.ORDER + "/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void confirmOrder_OK() throws Exception {
        //Arrange
        orderDto.setId(1);
        when(orderDelegate.confirmOrder(orderDto.getId())).thenReturn(ResponseEntity.ok(orderDto));

        //Act and Assert
        mvc.perform(get(ResourceMapping.ORDER + ResourceMapping.CONFIRM + "/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}
