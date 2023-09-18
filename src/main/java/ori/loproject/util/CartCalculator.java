package ori.loproject.util;

import ori.loproject.dto.CartDto;

public class CartCalculator {
    public static int cartCalculator(CartDto cartDto) {
        int cartPrice = cartDto.getCartPrice() * cartDto.getCartAmount();
        return cartPrice;
    }
}