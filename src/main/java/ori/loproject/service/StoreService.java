package ori.loproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ori.loproject.domain.store.Store;
import ori.loproject.domain.store.StoreRepository;
import ori.loproject.dto.StoreDto;

import java.util.List;
@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public Store findStoreInfo(Long storeid){
        return storeRepository.findByStoreid(storeid);
    };

    public List<Store> findStore(String category){
        return storeRepository.findByStoreCategory(category);
    };

    public List<Store> findTakeoutStore(String category, int takeoutState){
        return storeRepository.findByStoreCategoryAndTakeoutState(category, takeoutState);
    };

    public List<Store> searchStore(String keyword){
        return storeRepository.findBystoreNameContaining(keyword);
    }

    public List<Store> findByStoreCategory(String storeCategory){
        return storeRepository.findByStoreCategory(storeCategory);
    }

    public List<Store> findByStoreNameContaining(String keyword){
        return storeRepository.findBystoreNameContaining(keyword);
    }

    public Store findByStoreId(Long storeId) {
        return storeRepository.findByStoreid(storeId);
    }

    public Store findByMemberIdEmail(String Email){
        return storeRepository.findByMemberidEmail(Email);
    }

    @Transactional
    public void update(StoreDto dto){
        storeRepository.update(dto.getStoreid(), dto.getStoreIntroduction(), dto.getStoreBanner(), dto.getStoreTel());
    }
}
