package com.techie.microservices.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import
        com.techie.microservices.product.constants.ProductConstants;
import com.techie.microservices.product.dto.ProductRequest;
import com.techie.microservices.product.dto.ProductResponse;
import com.techie.microservices.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private ProductRequest productRequest;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService)).build();
        productRequest = new ProductRequest(ProductConstants.ID, ProductConstants.NAME, ProductConstants.DESCRIPTION, ProductConstants.SKU_CODE, ProductConstants.PRICE);
        productResponse = new ProductResponse(ProductConstants.ID, ProductConstants.NAME, ProductConstants.DESCRIPTION, ProductConstants.SKU_CODE, ProductConstants.PRICE);
        objectMapper = new ObjectMapper();
    }

    @Test
    void createProduct_ShouldReturnCreatedStatus() throws Exception {

        Mockito.when(productService.createProduct(productRequest)).thenReturn(productResponse);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(ProductConstants.URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(productResponse)));

    }

    @Test
    void testTo_getAllProducts_List() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(List.of(productResponse));

        mockMvc.perform(MockMvcRequestBuilders.get(ProductConstants.URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(productResponse))));

    }
}