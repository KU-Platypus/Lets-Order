package ori.loproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ori.loproject.domain.menu.Menu;
import ori.loproject.domain.menu.MenuRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public List<Menu> findAllMenus(Long storeid) {
        return menuRepository.findAllByStoreidStoreid(storeid);
    }

    // 메뉴별로 분류
    public Menu findAllByMenuID(Long ID){
        return menuRepository.findAllByid(ID);
    }

    //ID로 MemberId를 찾음
    public String findMenuIdById(Long Id){
        return menuRepository.findMenuidById(Id);
    }

    //ID로 이미지 주소를 찾음
    public String findMenuImgById(Long Id){
        return menuRepository.findMenuImgById(Id);
    }

    // 해당 가게의 가장 마지막 메뉴를 반환
    public Menu findByLastMenu(String storeName){
        return menuRepository.findTopByStoreid_storeNameOrderByIdDesc(storeName);
    }

    public Menu findByLastMenuID(){
        return menuRepository.findTopByOrderByIdDesc();
    }

    // 저장
    public Menu addMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    // 삭제
    public void delMenu(Long id) {
        menuRepository.deleteById(id);
    }

    // 가게이름으로 가게아이디 찾기?
    public List<Menu> findAllByStoreId(Long storeId) {
        return menuRepository.findAllByStoreidStoreid(storeId);
    }
}
