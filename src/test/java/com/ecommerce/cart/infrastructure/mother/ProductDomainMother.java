package com.ecommerce.cart.infrastructure.mother;

import com.ecommerce.shared.infrastructure.mother.FakerMother;
import com.ecommerce.cart.domain.valueobjects.CartItemDomain;
import com.ecommerce.cart.domain.valueobjects.ProductDomain;

import java.math.BigDecimal;

/**
 * Generate random Product domain
 */
public class ProductDomainMother {
    /**
     * Generate a random product domain
     * @return a product domain
     */
    public static ProductDomain random() {
        return new ProductDomain(
                ProductIdDomainMother.random(),
                FakerMother.getFaker().random().hex(),
                FakerMother.getFaker().commerce().productName(),
                BigDecimal.valueOf(FakerMother.getFaker().number().randomDouble(6, 100L, 5000L)),
                FakerMother.getFaker().number().numberBetween(1,100),
                Boolean.TRUE
        );
    }

    /**
     * Generate a random product domain
     * @return a product domain
     */
    public static ProductDomain noStockAvailable() {
        return new ProductDomain(
                ProductIdDomainMother.random(),
                FakerMother.getFaker().random().hex(),
                FakerMother.getFaker().commerce().productName(),
                BigDecimal.valueOf(FakerMother.getFaker().number().randomDouble(6, 100L, 5000L)),
                0,
                Boolean.TRUE
        );
    }

    /**
     * Generate a random fixed product domain
     * @return a product domain
     */
    public static ProductDomain fromCartItem(CartItemDomain cartItemDomain) {
        return new ProductDomain(
                cartItemDomain.getProductIdDomain(),
                FakerMother.getFaker().random().hex(),
                FakerMother.getFaker().commerce().productName(),
                cartItemDomain.getUnitPrice(),
                cartItemDomain.getQuantity(),
                Boolean.TRUE
        );
    }
}
