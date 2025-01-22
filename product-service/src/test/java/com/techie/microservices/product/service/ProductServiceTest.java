package com.techie.microservices.product.service;

import com.techie.microservices.product.constants.ProductConstants;
import com.techie.microservices.product.dto.ProductRequest;
import com.techie.microservices.product.dto.ProductResponse;
import com.techie.microservices.product.model.Product;
import com.techie.microservices.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
@InjectMocks
private  ProductService productService;
@Mock
private ProductRepository productRepository;
@Autowired
private MockMvc mockMvc;

private ProductRequest productRequest;
private Product product;
@BeforeEach

void setUp(){

    productRequest= new ProductRequest(ProductConstants.ID, ProductConstants.NAME, ProductConstants.DESCRIPTION, ProductConstants.SKU_CODE, ProductConstants.PRICE);
}
    @Test
   public  void test_To_createProduct_Successfully() {
    product=new Product(ProductConstants.ID, ProductConstants.NAME, ProductConstants.DESCRIPTION, ProductConstants.SKU_CODE, ProductConstants.PRICE);
    Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);

 ProductResponse productResponse=productService.createProduct(productRequest);

//        assertEquals(ProductConstants.ID, productResponse.id());
        assertEquals(ProductConstants.NAME, productResponse.name());
        assertEquals(ProductConstants.DESCRIPTION, productResponse.description());
        assertEquals(ProductConstants.SKU_CODE, productResponse.skuCode());
        assertEquals(ProductConstants.PRICE, productResponse.price());
    }

@Test
public void testCreateProductWhen(){
    Mockito.when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Not saved"));
    assertThrows(RuntimeException.class,()->productService.createProduct(productRequest));
}

    @Test
    void getAllProducts() {
    product =new Product(ProductConstants.ID, ProductConstants.NAME, ProductConstants.DESCRIPTION, ProductConstants.SKU_CODE, ProductConstants.PRICE);
   Mockito.when(productRepository.findAll()).thenReturn(List.of(product));

    List<ProductResponse> productResponses=productService.getAllProducts();

    assertEquals(1, productResponses.size());
    assertEquals(ProductConstants.ID, productResponses.get(0).id());
    }
}