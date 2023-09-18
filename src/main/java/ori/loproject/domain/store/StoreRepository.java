package ori.loproject.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findByStoreid(Long storeid);

    List<Store> findByStoreCategoryAndTakeoutState(String category, int takeoutState);

    List<Store> findByStoreCategory(String category);

    List<Store> findBystoreNameContaining(String keyword);

    // 메뉴Id의 Email 찾기
    Store findByMemberidEmail(String Email);

    @Modifying
    @Query(value = "UPDATE storedb s SET s.store_introduction = :storeIntroduction," +
            "s.store_banner = :storeBanner, s.store_tel = :storeTel WHERE s.storeid = :storeid", nativeQuery = true)
    void update(@Param("storeid") Long storeid, @Param("storeIntroduction") String storeIntroduction,
                @Param("storeBanner") String storeBanner, @Param("storeTel") String storeTel);
}
