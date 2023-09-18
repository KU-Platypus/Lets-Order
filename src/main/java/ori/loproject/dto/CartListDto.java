package ori.loproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CartListDto {
    private int listTotal;
    private Long storeid;
    private String storeName;

    List<CartDto> cartDto;
}
