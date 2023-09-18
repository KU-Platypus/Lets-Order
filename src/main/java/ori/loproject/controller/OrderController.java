package ori.loproject.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ori.loproject.domain.members.Member;
import ori.loproject.domain.order.Order;
import ori.loproject.domain.orderDetail.OrderDetail;
import ori.loproject.domain.store.Store;
import ori.loproject.dto.CartDto;
import ori.loproject.dto.CartListDto;
import ori.loproject.dto.OrderDetailDto;
import ori.loproject.dto.OrderDto;
import ori.loproject.service.MemberService;
import ori.loproject.service.OrderDetailService;
import ori.loproject.service.OrderService;
import ori.loproject.service.StoreService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final MemberService memberService;
    private final StoreService storeService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @GetMapping("/order")
    public String menuOrder(HttpSession session, Model model){
        Member member;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentUserName = authentication.getName();
        Long memberid;

        CartListDto cartListDto = (CartListDto) session.getAttribute("cartList");
        model.addAttribute("cartList", cartListDto);

        if(currentUserName.equals("anonymousUser")){
            model.addAttribute("memberInfo", 0);
            return "order/order";
        }

        member = memberService.findByEmail(currentUserName);
        memberid = member.getMemberid();

        model.addAttribute("memberInfo", memberid);
        return "order/order";
    }
    /**
     * 결제 페이지 요청시 주문 정보가 들어간 cartList와 member 정보를 전송하는 메소드
     * 만약 비회원이라면 멤버 id를 0으로 지정(db 입력시 null과 동일)
     * **/

    @ResponseBody
    @PostMapping("/orderInsert")
    public String orderAdd(OrderDto orderDto, HttpSession session) {

        CartListDto cartListDto = (CartListDto) session.getAttribute("cartList");
        Gson gson = new Gson();

        List<CartDto> cartDto = cartListDto.getCartDto();
        OrderDetailDto[] orderDetailArr = new OrderDetailDto[cartDto.size()];

        for(int i = 0;i < cartDto.size();i++){
            String orderDetailStr = gson.toJson(cartDto.get(i));
            orderDetailArr[i] = new OrderDetailDto(orderDto.getOrderid(), orderDetailStr);
        }

        Order order = Order.createOrder(orderDto);

        orderService.saveOrder(order);

        for(int i = 0;i < cartDto.size();i++){
            OrderDetail orderDetail = OrderDetail.createOrderDetail(orderDto.getOrderid(), orderDetailArr[i]);
            orderDetailService.saveOrderDetail(orderDetail);
        }
        return null;
    }
    /**
     * 세션에 있던 주문 내역을 orderDB에 넣는 메소드
     * 주문번호의 경우 중복 방지를 위해 멤버 아이디, 랜덤숫자  5자리, 현재 날짜를 사용
     * **/

    @GetMapping("/orderComplete")
    public String orderComplete(){
        return "order/order_complete";
    }
    /**
     * 결제 완료시 완료 화면으로 이동시켜 주는 메소드
     * **/

    @GetMapping("/orderList")
    public String showOrderList(Model model){
        Member member;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        if(currentUserName.equals("anonymousUser")){
            System.out.println("비회원 입니다.");
            return "redirect:/";
        }

        member = memberService.findByEmail(currentUserName);
        Long memberid = member.getMemberid();

        List<Order> orders = orderService.findAllByMemberid(memberid);
        List<Store> stores = new ArrayList<Store>();

        for(int i = 0;i < orders.size();i++){
            stores.add(storeService.findStoreInfo(orders.get(i).getStoreid()));
        }

        for(int i = 0;i < orders.size();i++){
            Order order = new Order(orders.get(i), stores.get(i).getStoreImg());
            orders.set(i, order);
        }

        model.addAttribute("orders", orders);

        return "order/order_list";
    }
    /**
     * 주문 내역 검색하는 메소드
     * **/

    //주문 상세 내역 보여줌
    @GetMapping("/orderViewDetail")
    public String showDetailOrder(@RequestParam("code") String code, Model model) {
        List<OrderDetail> orderDetail = orderDetailService.findOrderDetail(Long.parseLong(code));
        List<CartDto> cartDto = new ArrayList<>();
        Gson gson = new Gson();

        for(int i = 0;i < orderDetail.size();i++){
            OrderDetail str = orderDetail.get(i);
            cartDto.add(gson.fromJson(str.getMenuDetail(), CartDto.class));
        }
        model.addAttribute("detailCartDto", cartDto);

        return "order/order_detail";
    }
    /**
     * 주문 상세 내역 보여주는 메소드
     * **/
}