package ori.loproject.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ori.loproject.dto.CartDto;
import ori.loproject.dto.CartListDto;
import ori.loproject.util.CartCalculator;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @ResponseBody
    @PostMapping("/addCart")
    public CartListDto addCart(CartDto cartDto, Long storeid, String storeName, HttpSession session) {
        CartListDto cartListDto = (CartListDto) session.getAttribute("cartList");

        int cartTotal = CartCalculator.cartCalculator(cartDto);

        if(cartListDto == null) {
            List<CartDto> newCart = new ArrayList<>();
            cartDto.setCartTotal(cartTotal);
            newCart.add(cartDto);
            cartListDto = new CartListDto(cartTotal, storeid, storeName, newCart);
        }
        else {
            List<CartDto> prevCart = cartListDto.getCartDto();
            int prevlistTotal = cartListDto.getListTotal();

            cartListDto.setListTotal(prevlistTotal + cartTotal);

            if(prevCart.contains(cartDto)) {
                System.out.println("!! same cart exist !!");
                int cartIndex = prevCart.indexOf(cartDto);
                int amount = cartDto.getCartAmount();

                CartDto newCart = prevCart.get(cartIndex);

                int sumAmount = newCart.getCartAmount() + amount;
                int sumPrice = newCart.getCartTotal() + cartTotal;

                newCart.setCartAmount(sumAmount);
                newCart.setCartTotal(sumPrice);
                prevCart.set(cartIndex, newCart);
            }
            else {
                cartDto.setCartTotal(cartTotal);
                prevCart.add(cartDto);
            }
        }
        session.setAttribute("cartList", cartListDto);
        System.out.println("cartList = " + cartListDto);

        return cartListDto;
    }
    /**
     * 장바구니 추가 버튼(이거 먹을래요!) 클릭시 동작하는 메소드
     * 메뉴의 cartDto와 store의 id, name을 받아옴.
     * cartListDto가 null이면 새로운 cart는 생성
     * 이미 있다면 중복 검사 후 같은 메뉴면 수량과 가격만 변화 시킴.
     * totalPrice 계산후 cartListDto 형식에 맞게 저장.
     * **/

    @RequestMapping("/cartList")
    public String menuCart(HttpSession session, Model model) {
        CartListDto cartListDto = (CartListDto)session.getAttribute("cartList");
        
        if (cartListDto != null) {
            model.addAttribute("cartListDto", cartListDto);
        }else{
            model.addAttribute("null");
        }
        return "menu/menu_cart";
    }
    /**
     * 장바구니 보기 클릭시 해당 페이지로 이동 시켜주는 메소드
     * cartListDto가 null이 아니면 주문이 있는 걸로 판단, 세션에 있는 값 넘겨줌.
     * null이면 빈 것으로 판단, null값 넘겨줌.
     * **/

    @ResponseBody
    @RequestMapping("/requestCartList")
    public CartListDto cartListDto(HttpSession session) {
        CartListDto cartListDto = (CartListDto) session.getAttribute("cartList");
        if (cartListDto != null) {
            return cartListDto;
        }
        return null;
    }
    /**
     * 장바구니 목록을 출력하기 위한 요청을 처리하는 메소드
     * requestCartList 요청이 들어오면 세션에 저장된 cartListDto를 반환해줌.
     * 단, cartListDto가 null이 아닐 경우, 즉 주문이 무조건 하나라도 들어있는 경우 반환함.
     * **/

    @ResponseBody
    @DeleteMapping("/cartAllDelete")
    public void cartAllDelete(HttpSession session) {
        session.removeAttribute("cartList");
    }
    /**
     * 장바구니에서 전체삭제 요청시 cartList 세션에 있는 값 모두 삭제.
     * **/
}