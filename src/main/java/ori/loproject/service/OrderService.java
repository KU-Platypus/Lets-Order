package ori.loproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ori.loproject.domain.order.Order;
import ori.loproject.domain.order.OrderRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> findAllByMemberid(Long memberid) {
        return orderRepository.findAllByMemberidOrderByOrderDateDesc(memberid);
    }

    public List<Order> findByMemberidAndStoreid(Long memberid, Long storeid) {
        return orderRepository.findByMemberidAndStoreid(memberid, storeid);
    }

    public List<Order> findAllOrderList(Long memberid, Long storeid, String checknum) {
        return orderRepository.findAllByMemberidAndStoreidAndOrderReceive(memberid, storeid, checknum);
    }

    public List<Order> findAllByStoreid(String storeid){
        return orderRepository.findAllByStoreidOrderByOrderDateDesc(storeid);
    }

    public List<Order> findByOrderId(Long orderId){
        return orderRepository.findByOrderid(orderId);
    }
}
