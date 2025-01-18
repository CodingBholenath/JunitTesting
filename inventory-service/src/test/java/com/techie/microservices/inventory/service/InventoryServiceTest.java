package com.techie.microservices.inventory.service;

import com.techie.microservices.inventory.constants.InventoryConstants;
import com.techie.microservices.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class InventoryServiceTest {
@ExtendWith(MockitoExtension.class)
@InjectMocks
private InventoryService inventoryService;
@Mock
private InventoryRepository inventoryRepository;
    @Test
    void testInStock_Success() {
when(inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(InventoryConstants.SKU_CODE, InventoryConstants.QUANTITY)).thenReturn(true);
        boolean isInStock = inventoryService.isInStock(InventoryConstants.SKU_CODE, InventoryConstants.QUANTITY);
        assertTrue(isInStock);
    }


    @Test
    void testIsInStock_OutOfStock(){

        when(inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(InventoryConstants.SKU_CODE, InventoryConstants.QUANTITY)).thenReturn(false);
        boolean isInStock = inventoryService.isInStock(InventoryConstants.SKU_CODE, InventoryConstants.QUANTITY);
        assertFalse(isInStock);
    }


}