package com.techie.microservices.order.service;

import com.techie.microservices.order.client.InventoryClient;
import com.techie.microservices.order.constants.OrderConstants;
import com.techie.microservices.order.dto.OrderRequest;
import com.techie.microservices.order.event.OrderPlacedEvent;
import com.techie.microservices.order.model.Order;
import com.techie.microservices.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private InventoryClient inventoryClient;
    @Mock
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    private static OrderRequest orderRequest;

    @BeforeAll
    static void setUp() {
        orderRequest = mock(OrderRequest.class);
        OrderRequest.UserDetails userDetails = mock(OrderRequest.UserDetails.class);
        when(userDetails.email()).thenReturn(OrderConstants.EMAIL);
        when(userDetails.firstName()).thenReturn(OrderConstants.FIRST_NAME);
        when(userDetails.lastName()).thenReturn(OrderConstants.LAST_NAME);
//when(orderRequest.orderNumber()).thenReturn(OrderConstants.ORDER_NUMBER);
        when(orderRequest.skuCode()).thenReturn(OrderConstants.SKU_CODE);
        when(orderRequest.quantity()).thenReturn(OrderConstants.QUANTITY);
        when(orderRequest.price()).thenReturn(OrderConstants.PRICE);
        when(orderRequest.userDetails()).thenReturn(userDetails);
    }

    //void setUp(){
// orderRequest=mock(OrderRequest.class);
//        OrderRequest.UserDetails userDetails=mock(OrderRequest.UserDetails.class);
//        when(userDetails.email()).thenReturn(OrderConstants.EMAIL);
//        when(userDetails.firstName()).thenReturn(OrderConstants.FIRST_NAME);
//        when(userDetails.lastName()).thenReturn(OrderConstants.LAST_NAME);
////when(orderRequest.orderNumber()).thenReturn(OrderConstants.ORDER_NUMBER);
//        when(orderRequest.skuCode()).thenReturn(OrderConstants.SKU_CODE);
//        when(orderRequest.quantity()).thenReturn(OrderConstants.QUANTITY);
//        when(orderRequest.price()).thenReturn(OrderConstants.PRICE);
//        when(orderRequest.userDetails()).thenReturn(userDetails);
//}
    @Test
    void placeOrder_WhenProductIsInStock() {

        when(inventoryClient.isInStock(OrderConstants.SKU_CODE, OrderConstants.QUANTITY)).thenReturn(true);
        orderService.placeOrder(orderRequest);

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(kafkaTemplate, times(1)).send(eq("order-placed"), any(OrderPlacedEvent.class));
    }

    @Test
    void placedOrder_WhenProductIsNotInStock() {
        when(inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity())).thenReturn(false);
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> orderService.placeOrder(orderRequest));
        assertEquals("Product with SkuCode " + orderRequest.skuCode() + " is not in stock", runtimeException.getMessage());

    }
}