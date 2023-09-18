package ori.loproject.dto;

import lombok.*;

@NoArgsConstructor
@Data
public class OrderDto {
    private Long orderid;
    private Long storeid;
    private String storeName;
    private Long memberid;
    private String paymentMethod;
    private int orderTotalPrice;
    private String orderDate;
    private String orderRequest;
    private String orderReceive;

    @Builder
    public OrderDto(Long orderid, Long storeid, String storeName, Long memberid,
                 String paymentMethod, int orderTotalPrice, String orderDate,
                 String orderRequest) {
        this.orderid = orderid;
        this.storeid = storeid;
        this.storeName = storeName;
        this.memberid = memberid;
        this.paymentMethod = paymentMethod;
        this.orderTotalPrice = orderTotalPrice;
        this.orderDate = orderDate;
        this.orderRequest = orderRequest;
    }
}
