package com.ecommerce.cart.domain.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * To calculate the payment total service with cash
 */
@Service("CASH")
public class PaymentSummaryCashService implements PaymentSummaryService {
    private final BigDecimal FACTOR = new BigDecimal("1.2");
    /**
     * Calculate the total with fee using cash
     * @param subtotal the subtotal without fee
     * @return the total with type big decimal
     */
    @Override
    public BigDecimal calculateTotalWithFee(BigDecimal subtotal) {
        return subtotal.multiply(FACTOR);
    }
}
