package ori.loproject.domain.orderDetail;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ori.loproject.dto.OrderDetailDto;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "orderDetailDB")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long orderid;

    @Column
    private String menuDetail;

    @Builder
    public OrderDetail(Long orderid, String menuDetail) {
        this.orderid = orderid;
        this.menuDetail = menuDetail;
    }

    public static OrderDetail createOrderDetail(Long orderid, OrderDetailDto orderDetailDto) {
        OrderDetail orderDetail = OrderDetail.builder()
                .orderid(orderDetailDto.getOrderid())
                .menuDetail(orderDetailDto.getMenuDetail())
                .build();
        return orderDetail;
    }
}
