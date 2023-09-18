package ori.loproject.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ori.loproject.domain.menu.Menu;
import ori.loproject.domain.order.Order;
import ori.loproject.domain.orderDetail.OrderDetail;
import ori.loproject.domain.store.Store;
import ori.loproject.dto.CartDto;
import ori.loproject.dto.MenuDto;
import ori.loproject.dto.StoreDto;
import ori.loproject.service.OrderService;
import ori.loproject.service.OrderDetailService;
import ori.loproject.service.MenuService;
import ori.loproject.service.StoreService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ManageController {

    // 이미지 업로드 경로
    @Value("${img.upload.path}")
    private String uploadPath;

    @Autowired
    private StoreService storeService;

    @Autowired
    private MenuService menuService;

    // 관리자 기본 페이지
    @GetMapping
    public String admin(Model model) {
        return "admin/menu_admin";
    }

    //메뉴 추가 페이지로 이동시킴
    @GetMapping("/menuAdd")
    public String menuAdda() {
        return "admin/menu_Add";
    }

    //메뉴 추가 (실제 동작하는 부분)
    @PostMapping("/Add")
    public RedirectView menuAdd(HttpServletRequest request, @RequestParam("image") MultipartFile image) {
        // 이름과 가격 지정
        String MenuName = request.getParameter("menuname");
        int Price = Integer.parseInt(request.getParameter("Price"));
        String menuID, nameCode = null; // 메뉴 이름, 아이디
        int num = 1;              // 메뉴 번호
        Long id = 0L;
        String imgPath = null;    // 이미지 주소

        //이미지 저장시 경로에 폴더가 없다면 자동으로 생성해줌
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 현재 로그인한 유저의 ID를 호출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserName = authentication.getName();

        //이메일을 기준으로 가게이름을 찾음
        Store store = storeService.findByMemberIdEmail(UserName);

        // Store Id 를 기준으로 가게 정보 불러옴
        String storeName = store.getStoreName();

        //가게 이름을 기준으로 가장 마지막 메뉴를 찾음
        Menu orignMenu = menuService.findByLastMenu(storeName);
        Menu lastMenuId = menuService.findByLastMenuID();
        if (orignMenu == null) { //이전에 등록된 메뉴가 없다면
            nameCode = store.getStoreName() + "0";
            id = lastMenuId.getId() + 1;
        } else { // 이전에 등록된 메뉴가 있다면
            menuID = orignMenu.getMenuid();

            // 메뉴의 마지막 번호


            if(num < 10){
                num = Integer.parseInt(menuID.substring(menuID.length() - 1));
                num += 1;
                // 메뉴 ID
                nameCode = menuID.substring(0, menuID.length() - 1);
            } else{
                num = Integer.parseInt(menuID.substring(menuID.length() - 2));
                num += 1;
                // 메뉴 ID
                nameCode = menuID.substring(0, menuID.length() - 2);
            }

            //마지막 id값 생성
            id = lastMenuId.getId() + 1;
        }

        //원본 이미지 이름을 가져옴
        String imgName = image.getOriginalFilename();

        if (imgName == null || imgName.isEmpty()) {
            // 이미지가 전송되지 않았을 경우 건너뜀
        }
        else {
            // 이미지의 확장자만 분리
            String imgExt = imgName.substring(imgName.lastIndexOf("."));

            //이미지를 저장할 경로 및 이름을 지정함
            String saveImgPath = uploadPath + storeName + "_" + nameCode + num + imgExt;
            imgPath = "/item/" + storeName + "_" + nameCode + num + imgExt;

            // 실제로 이미지를 저장하고 안되면 예외처리
            try {
                image.transferTo(new File(saveImgPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String test = nameCode + num;
        System.out.println("nameCode + num / nameCode : "+test);

        // dto 생성 및 값 할당
        MenuDto menuDto = MenuDto.builder()
                .id(id)
                .menuid(test)
                .menuName(MenuName)
                .menuPrice(Price)
                .storeid(store)
                .menuImg(imgPath)
                .build();

        // MenuDB에 저장
        Menu menu = Menu.createMenu(menuDto);
        menuService.addMenu(menu);
        return new RedirectView("/admin/menuDel");
    }

    // 메뉴 삭제
    @PostMapping("/menuDel")
    public RedirectView menuDel(@RequestParam("DelMenuId") String[] menuId) {
        for (String menuid : menuId) {
            Long id = Long.valueOf(menuid);
            //이미지 경로 불러옴
            String menuImg = menuService.findMenuImgById(id);

            //이미지 제거
            if (menuImg != null) {
                Path path = Paths.get(uploadPath + menuImg.substring(5));
                try {
                    Files.delete(path);
                } catch (NoSuchFileException e) {
                    // 파일이 없는 경우
                    System.out.println("경로에 파일이 없습니다.");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            //DB에서 목록 제거
            menuService.delMenu(id);
        }
        return new RedirectView("/admin/menuDel");
    }

    // 위의 메뉴 삭제랑 연결되는 페이지에 값 전달용
    @GetMapping("/menuDel")
    public String menuDel(Model model) {
        // 현재 로그인한 유저의 ID를 호출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserName = authentication.getName();
        //이메일을 기준으로 가게이름을 찾음
        Store store = storeService.findByMemberIdEmail(UserName);

        List<Menu> menus = menuService.findAllByStoreId(store.getStoreid());
        model.addAttribute("menus", menus);
        return "admin/menu_Del";
    }

    // 메뉴 수정 연산
    @PostMapping("/menuEdit")
    public RedirectView menuEdit(HttpServletRequest request, @RequestParam("image") MultipartFile image) {
        // dto 생성 및 값 할당
        MenuDto menuDto = new MenuDto();
        System.out.println(request.getParameter("menuid"));
        System.out.println(request.getParameter("menuName"));
        System.out.println(request.getParameter("menuPrice"));
        System.out.println(request.getParameter("menuImgChanged"));

        // 수정된 이름과 가격 그리고 이미지 수정여부 저장
        Long Id = Long.valueOf(request.getParameter("menuid"));
        String MenuName = request.getParameter("menuName");
        int Price = Integer.parseInt(request.getParameter("menuPrice"));
        String imgChange = request.getParameter("menuImgChanged");

        // 현재 로그인한 유저의 ID를 호출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserName = authentication.getName();

        //이메일을 기준으로 가게이름을 찾음
        Store store = storeService.findByMemberIdEmail(UserName);

        // Store Id 를 기준으로 가게 정보 불러옴
        String storeName = store.getStoreName();

        //ID를 기준으로 memuId를 찾음
        String menuID = menuService.findMenuIdById(Id);

        //가게 이름을 기준으로 가장 마지막 메뉴를 찾음
        Menu menuName = menuService.findByLastMenu(storeName);

        if (imgChange.equals("true")) {
            //이미지 삭제를 위해 해당 DB를 통째로 날림
            menuService.delMenu(Id);

            //이미지 저장시 경로에 폴더가 없다면 자동으로 생성해줌
            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            //원본 이미지 이름을 가져옴
            String imgName = image.getOriginalFilename();

            // 메뉴의 마지막 번호
            int num = Integer.parseInt(menuID.substring(menuID.length() - 2));
            num += 1;

            // 메뉴 ID
            String id = menuID.substring(0, menuID.length() - 2);

            // 이미지의 확장자만 분리
            String imgExt = imgName.substring(imgName.lastIndexOf("."));

            //이미지를 저장할 경로 및 이름을 지정함
            String saveImgPath = uploadPath + storeName + "_" + id + num + imgExt;
            String imgPath = "/item/" + storeName + "_" + id + num + imgExt;

            // 실제로 이미지를 저장하고 안되면 예외처리
            try {
                image.transferTo(new File(saveImgPath));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //이미지 저장
            menuDto.setMenuImg(imgPath);
        } else { //이미지를 변경하지 않았다면
            //기존에 저장된 이미지 경로 불러옴
            String menuImg = menuService.findMenuImgById(Id);
            menuDto.setMenuImg(menuImg);
        }
        menuDto.setId(Id);
        menuDto.setMenuid(menuID);
        menuDto.setMenuName(MenuName);
        menuDto.setMenuPrice(Price);
        menuDto.setStoreid(store);


        // MenuDB에 저장
        Menu menu = Menu.UpdateMenu(menuDto);
        menuService.addMenu(menu);
        return new RedirectView("/admin/menuDel");
    }

    // 메뉴 수정
    @GetMapping("/menuEdit")
    public String menuEdit(@RequestParam("menuid") Long menuid, Model model) {
        // menuid에 해당하는 메뉴 정보 조회
        Menu menu = menuService.findAllByMenuID(menuid);
        model.addAttribute("menu", menu);
        return "admin/menu_Edit";
    }


    /** ======================주문 영역=============================== **/

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    // 주문 리스트
    @GetMapping("/orderList")
    public String orderList(Model model, HttpServletRequest request){
        // 현재 로그인한 유저의 ID를 호출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        // 관리자의 storeId를 반환
        Store store = storeService.findByMemberIdEmail(currentUserName);

        // 관리자의 storeId를 HTTP 세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute("storeId", store.getStoreid());

        List<Order> orders = orderService.findAllByStoreid(String.valueOf(store.getStoreid()));

        model.addAttribute("orders", orders);

        return "admin/admin_order_list";
    }

    //주문 상세 내역 보여줌
    @GetMapping("/OrderViewDetail")
    public String showDetailOrder(@RequestParam("code") String code, Model model) {
        List<OrderDetail> orderDetail = orderDetailService.findAllByOrderid(Long.parseLong(code));
        List<Order> orders = orderService.findByOrderId(Long.parseLong(code));
        List<CartDto> cartDto = new ArrayList<>();
        Gson gson = new Gson();

        Order order = orders.get(0);

        OrderDetail str = orderDetail.get(0);
        CartDto cart = gson.fromJson(str.getMenuDetail(), CartDto.class);
        cart.setOrderRequest(order.getOrderRequest());
        cartDto.add(cart);

        model.addAttribute("detailCartDto", cartDto);

        return "admin/admin_order_detail";
    }

    // 주문 접수/취소 상태저장
    @ResponseBody
    @PostMapping("/orderReceiveSave")
    public String orderReceiveSave(@RequestBody Map<String, Object> data){
        long orderId = ((Long) data.get("orderId")).longValue();
        String key = (String) data.get("key");

        // orderId 기준으로 테이블 정보를 가져옴
        List<Order> orders = orderService.findByOrderId(orderId);
        Order order = orders.get(0);
        order.setOrderReceive(key);
        orderService.saveOrder(order);
        System.out.println("업데이트 됨");

        return null;
    }

    /** ====================== 가게 관리 영역 ============================== **/
    @GetMapping("/storeManagement")
    public String store_Management(Model model){
        // 현재 로그인한 유저의 ID를 호출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        // 관리자의 storeId를 반환
        Store store = storeService.findByMemberIdEmail(currentUserName);

        model.addAttribute("store", store);
        return "admin/store_Management";
    }

    @PostMapping("/storeManagement")
    public RedirectView store_Management(HttpServletRequest request, @RequestParam("imgFile") MultipartFile imgFile){
        StoreDto storeDto = new StoreDto();

        String storeIntroduction = request.getParameter("storeIntroduction");
        String storeTell = request.getParameter("storeTell");
        Long storeId = Long.valueOf(request.getParameter("storeId"));
        String imgName = imgFile.getOriginalFilename();

        // Store Id 를 기준으로 가게 정보 불러옴
        Store stores = storeService.findByStoreId(storeId);

        if (imgName == null || imgName.equals("")) {
            System.out.println("이미지 변경 안함");
            storeDto.setStoreBanner(stores.getStoreBanner());
        }else {
            System.out.println("stores.getStoreBanner():"+stores.getStoreBanner());
            if (stores.getStoreBanner() != null){
                //기존 이미지 삭제 작업
                String delPath = uploadPath.replaceAll("/item/","");
                File file = new File(delPath + stores.getStoreBanner());
                boolean deleted = file.delete();
                if(deleted){
                    System.out.println("원본이미지 삭제");
                }
                else{
                    System.out.println("삭제 실패");
                }
            }

            //이미지 저장시 경로에 폴더가 없다면 자동으로 생성해줌
            File dir = new File(uploadPath + "banner/");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 가게 코드 가져옴
            String storeName = stores.getStoreName();

            // 이미지의 확장자만 분리
            String imgExt = imgName.substring(imgName.lastIndexOf("."));

            //이미지를 저장할 경로 및 이름을 지정함
            String saveImgPath = uploadPath + "banner/" + storeName + "_banner" + imgExt;
            String imgPath = "/item/banner/" + storeName + "_banner" + imgExt;

            System.out.println("imgPath:"+imgPath);

            // 실제로 이미지를 저장하고 안되면 예외처리
            try {
                imgFile.transferTo(new File(saveImgPath));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //이미지 저장
            storeDto.setStoreBanner(imgPath);
        } //이미지를 변경하지 않았다면 그대로 넘어감

        storeDto.setStoreid(storeId);
        storeDto.setStoreIntroduction(storeIntroduction);
        storeDto.setStoreTel(storeTell);

        // MenuDB에 저장
        storeService.update(storeDto);

        return new RedirectView("/admin/storeManagement");
    }
}
