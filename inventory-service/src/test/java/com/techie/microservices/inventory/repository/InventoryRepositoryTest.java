package com.techie.microservices.inventory.repository;

import com.techie.microservices.inventory.constants.InventoryConstants;
import com.techie.microservices.inventory.model.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource("classpath:application_test.properties")
public class InventoryRepositoryTest {

@Autowired
private InventoryRepository inventoryRepository;
private Inventory inventory=new Inventory();


@BeforeEach
void setup(){
    inventory.setSkuCode(InventoryConstants.SKU_CODE);
    inventory.setQuantity(InventoryConstants.QUANTITY);
    inventoryRepository.save(inventory);

}
    @Test
    void testExistsBySkuCodeAndQuantityIsGreaterThanEqual() {

    boolean result = inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(InventoryConstants.SKU_CODE, InventoryConstants.QUANTITY);
    assertTrue(result);

    }

    @Test
    void testExistsBySkuCodeAndQuantityIsGreaterThanNotEqual() {

    boolean result = inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(InventoryConstants.SKU_CODE, 15);
    assertFalse(result);
    }
}