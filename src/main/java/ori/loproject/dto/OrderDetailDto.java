package ori.loproject.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderDetailDto {
    private Long orderid;
    private String menuDetail;


    @Builder
    public OrderDetailDto(Long orderid, String menuDetail) {
        this.orderid = orderid;
        this.menuDetail = menuDetail;
    }
}
