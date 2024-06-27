package com.ecommerce.shoppingcart.infrastructure.mappers;


import com.ecommerce.shoppingcart.domain.valueobjects.ProductDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.ecommerce.shoppingcart.infrastructure.entities.Product;

import java.util.Optional;

/**
 * Convert other types to Product Domain
 */
public class ProductDomainMapper {
    /**
     * Convert a product entity to product domain
     * @param product the product entity to convert
     * @return a product domain
     */
    public static ProductDomain fromEntity(Product product) {
        return Optional.ofNullable(product)
                .map(entity -> new ProductDomain(
                        new ProductIdDomain(entity.getId()),
                        entity.getCode(),
                        entity.getName(),
                        entity.getUnitPrice(),
                        entity.getStock(),
                        entity.isActive()
                )).orElse(null);
    }
}
