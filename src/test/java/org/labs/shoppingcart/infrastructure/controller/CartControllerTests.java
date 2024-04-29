package org.labs.shoppingcart.infrastructure.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labs.shoppingcart.infrastructure.mother.AddItemReqBodyMother;
import org.labs.shoppingcart.infrastructure.mother.AddProductReqBodyMother;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CartController.class)
class CartControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private final String productCartUrl = "/api/v1/product/%s/cart";


    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) throws NoSuchFieldException, IllegalAccessException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addItem_ShouldCreateItem_WhenProductExists() throws Exception {
        // Given a product in the cart
        var productId = UUID.randomUUID().toString();
        var cartController = new CartController();
        var addProductReqBody = AddProductReqBodyMother.random();
        cartController.addProduct(productId, addProductReqBody);

        // When it adds an item from existing product to the cart
        var mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
        var addItemReqBody = AddItemReqBodyMother.random();
        var content = new ObjectMapper().writeValueAsString(addItemReqBody);
        var result = mockMvc.perform(patch(String.format(productCartUrl, productId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // Then the item should be created
        result.andExpect(status().isCreated());

        // And the item should be in the cart
        var items = Optional.ofNullable(cartController.getItems().getBody()).orElse(new ArrayList<>());
        var actualItem = items.stream().filter(item -> item.getCode().equals(productId)).findFirst();
        assertFalse(items.isEmpty());
        assertTrue(actualItem.isPresent());
        assertEquals(addProductReqBody.getPrice(), actualItem.get().getPrice());
        assertEquals(addItemReqBody.getQuantity(), actualItem.get().getQuantity());
    }

    @Test
    void addItem_ShouldFailed_WhenProductDoesNotExist() throws Exception {
        // Given a cart without any product
        var productCode = UUID.randomUUID().toString();
        var content = new ObjectMapper().writeValueAsString(AddItemReqBodyMother.random());

        // When it tries to add an item to the cart
        var result = mockMvc.perform(patch(String.format(productCartUrl, productCode))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // Then the item should not be created
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.url").exists())
                .andExpect(jsonPath("$.reqMethod").exists());

    }

    @Test
    void addProduct_ShouldCreateProduct_WhenProductDoesNotExist() throws Exception {
        // Given a cart without any product
        String productCode = UUID.randomUUID().toString();
        String content = new ObjectMapper().writeValueAsString(AddProductReqBodyMother.random());

        // When it adds a product to the cart
        var mockMvc = MockMvcBuilders.standaloneSetup(new CartController()).build();
        var result = mockMvc.perform(post(String.format(productCartUrl, productCode))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // Then the product should be created
        result.andExpect(status().isCreated());
    }


    @Test
    void addProduct_ShouldCreateProduct_WhenProductExist() throws Exception {
        // Given a product in the cart
        var productId = UUID.randomUUID().toString();
        var existedAddProductReqBody = AddProductReqBodyMother.random();
        var cartController = new CartController();
        cartController.addProduct(productId, existedAddProductReqBody);

        // When it adds the existing product to the cart
        var mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
        var addProductReqBody = AddProductReqBodyMother.random();
        var content = new ObjectMapper().writeValueAsString(addProductReqBody);
        var result = mockMvc.perform(post(String.format(productCartUrl, productId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // Then the product should be created
        result.andExpect(status().isCreated());

        // And the item should be in the cart
        var items = Optional.ofNullable(cartController.getItems().getBody()).orElse(new ArrayList<>());
        var actualItem = items.stream().filter(item -> item.getCode().equals(productId)).findFirst();
        assertFalse(items.isEmpty());
        assertTrue(actualItem.isPresent());
        assertEquals(addProductReqBody.getPrice(), actualItem.get().getPrice());
        assertEquals(addProductReqBody.getQuantity(), actualItem.get().getQuantity());
    }


}
