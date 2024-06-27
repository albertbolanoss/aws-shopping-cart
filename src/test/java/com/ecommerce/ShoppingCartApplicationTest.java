package com.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ShoppingCartApplicationTest {
    private ShoppingCartApplication shoppingCartApplication;

    @BeforeEach
    void init() {
        shoppingCartApplication = new ShoppingCartApplication();
    }

    @Test
    void shallowEtagHeaderFilter() {
        var shallowEtagHeaderFilter = shoppingCartApplication.shallowEtagHeaderFilter();

        assertNotNull(shallowEtagHeaderFilter);
    }
}
