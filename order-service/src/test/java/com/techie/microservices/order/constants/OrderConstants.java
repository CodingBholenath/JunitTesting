package com.techie.microservices.order.constants;

import com.techie.microservices.order.dto.OrderRequest;

import java.math.BigDecimal;

public class OrderConstants {

    public final static String URL = "/api/order";
    public final static String ORDER_NUMBER = "ORDER123";
    public final static long ORDER_ID = 100L;
    public final static BigDecimal PRICE = BigDecimal.valueOf(10);
    public final static String SKU_CODE = "SKU123";
    public final static Integer QUANTITY = 10;
    public final static Long ID = 123L;
    //USER DETAILS
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";

}
