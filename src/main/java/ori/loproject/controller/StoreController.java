package ori.loproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ori.loproject.domain.store.Store;
import ori.loproject.service.StoreService;

import java.util.List;

@Controller
public class StoreController {
    @Autowired
    private StoreService storeService;

    
    @GetMapping("/store/{category}")
    public String storeListPrint(@PathVariable String category, Model model){
        List<Store> storeList = storeService.findStore(category);
        model.addAttribute("storeList", storeList);
        model.addAttribute("categoryCode", category);
        return "store/store";
    }
    /**
     * 주소로 카테고리 코드를 받아와 해당 매장들을 검색하는 메소드
     * 검색한 매장들을 model에 담아 return 페이지로 전송.
     * */

    @GetMapping("/search")
    public String moveToSearch() {
        return "store/store_search";
    }
    /**
     * 검색 메뉴 클릭시 매장 검색 페이지로 이동
     * */

    @GetMapping("/takeoutStore/{category}")
    public String takeoutStoreListPrint(@PathVariable String category, Model model){
        List<Store> storeList = storeService.findTakeoutStore(category, 1);
        model.addAttribute("storeList", storeList);
        model.addAttribute("categoryCode", category);
        return "store/takeout_store";
    }
    /**
     * 포장만되는 스토어 검색하는 메소드
     * */

    @GetMapping("/allStore/{category}")
    public String allStoreListPrint(@PathVariable String category, Model model){
        List<Store> storeList = storeService.findStore(category);
        model.addAttribute("storeList", storeList);
        model.addAttribute("categoryCode", category);
        return "store/store";
    }
    /**
     * 전체 매장 검색
     * */

    @PostMapping("/form")
    public String searchStrore(@RequestParam("keyword") String keyword, Model model) {
        List<Store> searchList = storeService.searchStore(keyword);
        model.addAttribute("searchList", searchList);
        System.out.println("폼들어옴========");
        return "store/store_search";
    }
    /**
     * form으로 들어온 값이 이름에 포함된 매장들을 검색하는 메소드
     * 검색한 매장들을 model에 담아 return 페이지로 전송.
     * */
}