package org.labs.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.application.api.model.AddItemReqBody;

public class AddItemReqBodyMother {
    public static AddItemReqBody random() {
        var faker = FakerMother.getFaker();
        AddItemReqBody addItemReqBody = new AddItemReqBody();
        addItemReqBody.setQuantity(faker.number().numberBetween(1, 10));
        return addItemReqBody;
    }
}
