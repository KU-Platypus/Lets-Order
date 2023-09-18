package ori.loproject.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import ori.loproject.domain.orderDetail.OrderDetail;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByMemberidOrderByOrderDateDesc(Long memberid);

    List<Order> findByMemberidAndStoreid(Long memberid, Long storeid);

    List<Order> findAllByMemberidAndStoreidAndOrderReceive(Long memberid, Long storeid, String checknum);

    List<Order> findAllByStoreidOrderByOrderDateDesc(String storeid);

    List<Order> findByOrderid(Long orderId);
}
