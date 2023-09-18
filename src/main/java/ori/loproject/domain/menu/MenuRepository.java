package ori.loproject.domain.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    // StoreId_storeName
    Menu findAllByid(Long ID);

    //가게이름
    List<Menu> findAllByStoreid_storeName(String StoreId);

    // ID 값으로 메뉴ID를 찾음
    @Query("SELECT menuid FROM Menu WHERE id = :id")
    String findMenuidById(@Param("id") Long id);

    // ID 값으로 이미지 경로를 찾음
    @Query("SELECT menuImg FROM Menu WHERE id = :id")
    String findMenuImgById(@Param("id") Long id);

    //가게 이름을 중복없이 불러옴
    @Query(value = "SELECT DISTINCT m.storeid.storeName FROM Menu m")
    List<String> findAllByDistinctStoreid();

    @Query("SELECT m.storeid.storeName FROM Menu m WHERE m.storeid.id = :storeid")
    String findstoreNameByStoreid(@Param("storeid") Long storeId);

    // 해당 가게의 가장 마지막 메뉴를 반환
    Menu findTopByStoreid_storeNameOrderByIdDesc(String storeName);

    // 해당 가게의 가장 마지막 메뉴를 반환
    Menu findTopByOrderByIdDesc();

    List<Menu> findAllByStoreidStoreid(Long storeid);
}
