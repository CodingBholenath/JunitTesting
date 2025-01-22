package com.techie.microservices.inventory.controller;

import com.techie.microservices.inventory.constants.InventoryConstants;
import com.techie.microservices.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InventoryController.class)
class InventoryControllerTest {
    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testIsInStock_EnoughQuantity_ReturnsTrue() throws Exception {

        Mockito.when(inventoryService.isInStock(InventoryConstants.SKU_CODE, InventoryConstants.QUANTITY)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get(InventoryConstants.URL)
                        .param(InventoryConstants.SKUCODE, InventoryConstants.SKU_CODE)
                        .param(InventoryConstants.QUANT, String.valueOf(InventoryConstants.QUANTITY)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));


    }

    @Test
    public void testIsInStock_NotEnoughQuantity_ReturnsFalse() throws Exception {
        Mockito.when(inventoryService.isInStock(InventoryConstants.SKU_CODE, InventoryConstants.QUANTITY)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.get(InventoryConstants.URL)
                        .param(InventoryConstants.SKUCODE, InventoryConstants.SKU_CODE)
                        .param(InventoryConstants.QUANT, String.valueOf(InventoryConstants.QUANTITY)))
                .andExpect(MockMvcResultMatchers.content().string("false"));
//
    }
}