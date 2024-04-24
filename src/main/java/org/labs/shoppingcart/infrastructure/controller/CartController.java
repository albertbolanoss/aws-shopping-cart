package org.labs.shoppingcart.infrastructure.controller;

import com.perficient.shoppingcart.application.api.controller.CartApi;
import com.perficient.shoppingcart.application.api.model.AddItemReqBody;
import com.perficient.shoppingcart.application.api.model.AddProductReqBody;
import com.perficient.shoppingcart.application.api.model.GetItemResBody;
import com.perficient.shoppingcart.application.api.model.ProductItemReqBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@SessionScope
public class CartController implements CartApi {
    private final AbstractMap<String, ProductItemReqBody> productItems = new ConcurrentHashMap<>();

    public ResponseEntity<Void> addItem(String productCode, AddItemReqBody addItemReqBody) {
        var addProductReqBody = productItems.get(productCode);
        productItems.put(productCode, new ProductItemReqBody()
                .code(productCode)
                .quantity(addProductReqBody.getQuantity())
                .price(addProductReqBody.getPrice()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> addProduct(String productCode, AddProductReqBody addProductReqBody) {
        productItems.put(productCode, new ProductItemReqBody()
                .code(productCode)
                .quantity(addProductReqBody.getQuantity())
                .price(addProductReqBody.getPrice()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<GetItemResBody>> getItems() {
        List<GetItemResBody> getItemResBodies = new ArrayList<>();
        for (Map.Entry<String, ProductItemReqBody> entry : productItems.entrySet()) {
            String productCode = entry.getKey();
            ProductItemReqBody productItemReqBody = entry.getValue();
            var getItemResBody = new GetItemResBody()
                    .code(productCode)
                    .quantity(productItemReqBody.getQuantity())
                    .price(productItemReqBody.getPrice())
                    .subtotal(productItemReqBody.getPrice().multiply(BigDecimal.valueOf(productItemReqBody.getQuantity())));
            getItemResBodies.add(getItemResBody);
        }

        return ResponseEntity.ok(getItemResBodies);
    }
}
