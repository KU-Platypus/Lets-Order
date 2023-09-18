package ori.loproject.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Objects;

@Data
@ToString
public class CartDto {
    private String cartName;
    private int cartPrice;
    private int cartAmount;
    private int cartTotal;
    private String orderRequest;

    @Override
    public int hashCode() {
        final int prime = 50;
        int result = 1;
        result = prime * result + Objects.hash(cartName, cartPrice);
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
        CartDto other = (CartDto) obj;
        return Objects.equals(cartName, other.cartName) && cartPrice == other.cartPrice;
    }
}
