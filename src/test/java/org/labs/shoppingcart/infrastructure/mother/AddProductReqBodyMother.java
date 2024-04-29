package org.labs.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.application.api.model.AddProductReqBody;

import java.math.BigDecimal;

public class AddProductReqBodyMother {
    public static AddProductReqBody random() {
        var faker = FakerMother.getFaker();
        AddProductReqBody addProductReqBody = new AddProductReqBody();
        addProductReqBody.setQuantity(faker.number().numberBetween(1, 100));
        addProductReqBody.setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500)));
        return addProductReqBody;
    }
}
