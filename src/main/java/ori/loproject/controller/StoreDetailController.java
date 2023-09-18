package ori.loproject.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ori.loproject.domain.members.Member;
import ori.loproject.domain.menu.Menu;
import ori.loproject.domain.order.Order;
import ori.loproject.domain.orderDetail.OrderDetail;
import ori.loproject.domain.store.Store;
import ori.loproject.dto.CartDto;
import ori.loproject.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StoreDetailController {
    @Autowired
    private MenuService menuService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrderDetailService orderDetailService;

    Long storeid;

    @GetMapping("/menu")
    public String menus(@RequestParam("id") Long storeid, Model model) {
        this.storeid = storeid;
        Store store = storeService.findStoreInfo(storeid);
        List<Menu> menus = menuService.findAllMenus(storeid);

        model.addAttribute("menus", menus);
        model.addAttribute("store", store);
        return "store/storeDetail";
    }

    @ResponseBody
    @RequestMapping("/checkVisit")
    public String checkVisit() {
        Member member;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        System.out.println(currentUserName);

        if(currentUserName.equals("anonymousUser")){
            return "2";
        }

        member = memberService.findByEmail(currentUserName);
        Long memberid = member.getMemberid();

        System.out.println("123123"+memberid);

        List<Order> orders = orderService.findByMemberidAndStoreid(memberid, storeid);

        if(orders.isEmpty()){
            return "0";
        }

        return "1";
    }

    @GetMapping("/checkorder")
    public String checkOrder(Model model) {
        Member member;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        if(currentUserName.equals("anonymousUser")){
            System.out.println("비회원 입니다.");
        }

        member = memberService.findByEmail(currentUserName);
        Long memberid = member.getMemberid();

        List<Order> orders = orderService.findAllOrderList(memberid, storeid, "1");
        List<OrderDetail> orderDetail;
        List<CartDto> cartDto = new ArrayList<>();
        Gson gson = new Gson();
        Order order1;

        for(int i = 0;i < orders.size();i++){
            order1 = orders.get(i);
            System.out.println(order1.getOrderid());

            orderDetail = orderDetailService.findOrderDetail(order1.getOrderid());

            for(int j = 0;j < orderDetail.size();j++){
                OrderDetail str = orderDetail.get(j);
                cartDto.add(gson.fromJson(str.getMenuDetail(), CartDto.class));
            }
        }

        model.addAttribute("detailCartDto", cartDto);

        return "order/order_detail";
    }
    /**
     * 과거 주문 내역 보여줌
     * **/
}