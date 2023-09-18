package ori.loproject.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ori.loproject.dto.OrderDto;

@JsonIgnoreProperties({"type"})
@NoArgsConstructor
@Getter
@Entity
@Table(name = "orderdb")
public class Order {
    @Id
    private Long orderid;

    @Column
    private Long storeid;

    @Column
    private String storeName;

    @Column
    private Long memberid;

    @Column
    private int orderTotalPrice;

    @Column
    private String orderDate;

    @Column
    private String orderRequest;

    @Column
    private String paymentMethod;

    @Column // 주문 확인
    private String orderReceive;

    private String storeImg;

    @Builder
    public Order(Long orderid, Long storeid, String storeName, Long memberid,
                 String paymentMethod, int orderTotalPrice, String orderDate,
                 String orderRequest, String orderReceive) {
        this.orderid = orderid;
        this.storeid = storeid;
        this.storeName = storeName;
        this.memberid = memberid;
        this.paymentMethod = paymentMethod;
        this.orderTotalPrice = orderTotalPrice;
        this.orderDate = orderDate;
        this.orderRequest = orderRequest;
        this.orderReceive = orderReceive;
    }

    public Order(Order order, String storeImg) {
        this.orderid = order.orderid;
        this.storeid = order.storeid;
        this.storeName = order.storeName;
        this.memberid = order.memberid;
        this.paymentMethod = order.paymentMethod;
        this.orderTotalPrice = order.orderTotalPrice;
        this.orderDate = order.orderDate;
        this.orderRequest = order.orderRequest;
        this.orderReceive = order.orderReceive;
        this.storeImg = storeImg;
    }

    public static Order createOrder(OrderDto orderDto) {
        Order order = Order.builder()
                .orderid(orderDto.getOrderid())
                .storeid(orderDto.getStoreid())
                .storeName(orderDto.getStoreName())
                .memberid(orderDto.getMemberid())
                .paymentMethod(orderDto.getPaymentMethod())
                .orderTotalPrice(orderDto.getOrderTotalPrice())
                .orderDate(orderDto.getOrderDate())
                .orderRequest(orderDto.getOrderRequest())
                .orderReceive(orderDto.getOrderReceive())
                .build();
        return order;
    }

    public void setOrderReceive(String orderReceive){
        this.orderReceive = orderReceive;
    }
}
