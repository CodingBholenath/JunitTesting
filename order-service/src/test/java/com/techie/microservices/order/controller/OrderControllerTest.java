package com.techie.microservices.order.controller;

import com.techie.microservices.order.constants.OrderConstants;
import com.techie.microservices.order.dto.OrderRequest;
import com.techie.microservices.order.service.OrderService;
import org.apache.kafka.common.protocol.types.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.module.ResolutionException;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @MockBean
    private OrderService orderService;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService)).build();
    }

    @Test
    void test_PlaceOrder() throws Exception {
        OrderRequest.UserDetails userDetails = new OrderRequest.UserDetails(OrderConstants.EMAIL, OrderConstants.FIRST_NAME, OrderConstants.LAST_NAME);
        OrderRequest orderRequest = new OrderRequest(OrderConstants.ORDER_ID, OrderConstants.ORDER_NUMBER, OrderConstants.SKU_CODE, OrderConstants.PRICE, OrderConstants.QUANTITY, userDetails);
        doNothing().when(orderService).placeOrder(orderRequest);
        mockMvc.perform(MockMvcRequestBuilders.post(OrderConstants.URL)
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"id\": 100,\n" +
                                "  \"orderNumber\": \"ORDER123\",\n" +
                                "  \"skuCode\": \"SKU123\",\n" +
                                "  \"price\": 10,\n" +
                                "  \"quantity\": 10,\n" +
                                "  \"userDetails\": {\n" +
                                "    \"email\": \"email\",\n" +
                                "    \"firstName\": \"John\",\n" +
                                "    \"lastName\": \"Doe\"\n" +
                                "  }\n" +
                                "}")).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Order Placed Successfully"));
    }

    @Test
    void fallbackMethod() {
        OrderRequest.UserDetails userDetails = new OrderRequest.UserDetails(OrderConstants.EMAIL, OrderConstants.FIRST_NAME, OrderConstants.LAST_NAME);
        OrderRequest orderRequest = new OrderRequest(OrderConstants.ORDER_ID, OrderConstants.ORDER_NUMBER, OrderConstants.SKU_CODE, OrderConstants.PRICE, OrderConstants.QUANTITY, userDetails);
        RuntimeException runtimeException = new RuntimeException("Oops! Something went wrong ,Please order after some time");


        CompletableFuture<String> result = orderController.fallbackMethod(orderRequest, runtimeException);

        result.thenAccept(result1 -> assertEquals("Oops! Something went wrong, please order after some time!", result1));
    }
}