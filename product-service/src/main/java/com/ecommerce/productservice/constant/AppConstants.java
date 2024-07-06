package com.ecommerce.productservice.constant;

public interface AppConstants {
    interface Endpoints {
        String PRODUCT = "/product";
        String CART = "/cart";
        String CHECKOUT = "/checkout";
    }

    interface DB {
        String PREFIX = "ecs_";
        String PRODUCT_TABLE = PREFIX + "product";
        String CART_TABLE = PREFIX + "cart";
    }

    interface ErrorMessage {
        String PRODUCT_NOT_FOUND = "Product not found!";
        String PRODUCT_NAME_NOT_EMPTY = "productName should not be empty!";
        String ID_NOT_EMPTY = "id cannot be empty!";
    }
}
