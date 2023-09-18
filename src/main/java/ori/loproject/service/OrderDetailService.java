package ori.loproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ori.loproject.domain.orderDetail.OrderDetail;
import ori.loproject.domain.orderDetail.OrderDetailRepository;
import ori.loproject.domain.store.Store;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public List<OrderDetail> findOrderDetail(Long code){
        return orderDetailRepository.findAllByOrderid(code);
    };

    public List<OrderDetail> findAllByOrderid(Long code){
        return orderDetailRepository.findAllByOrderid(code);
    }
}
