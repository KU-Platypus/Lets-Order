package ori.loproject.domain.store;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ori.loproject.domain.members.Member;
import ori.loproject.dto.StoreDto;
@NoArgsConstructor
@Getter
@Entity
@Table(name = "storedb")
public class Store {
    @Id
    private Long storeid;
    @Column
    private String storeName;
    @Column
    private String storeCategory;
    @Column
    private String storeImg;
    @Column
    private String storeAddress;
    @Column
    private int takeoutState;
    @Column
    private String openTime;
    @Column
    private String closeTime;
    @Column
    private String storeIntroduction;
    @Column
    private String storeBanner;
    @Column
    private String storeTel;

    @OneToOne
    @JoinColumn(name = "member_Id")
    private Member memberid;


    @Builder
    public Store(String storeName, String storeCategory, String storeImg,
                 String storeAddress, int takeoutState, String openTime ,
                 String closeTime, String storeIntroduction, String storeBanner, String storeTel) {
        this.storeName = storeName;
        this.storeCategory = storeCategory;;
        this.storeImg = storeImg;
        this.storeAddress = storeAddress;
        this.takeoutState = takeoutState;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.storeIntroduction = storeIntroduction;
        this.storeBanner = storeBanner;
        this.storeTel = storeTel;
    }

    public static Store createStore(StoreDto storeDto) {
        Store store = Store.builder()
                .storeName(storeDto.getStoreName())
                .storeCategory(storeDto.getStoreCategory())
                .storeImg(storeDto.getStoreImg())
                .storeAddress(storeDto.getStoreAddress())
                .takeoutState(storeDto.getTakeoutState())
                .openTime(storeDto.getOpenTime())
                .closeTime(storeDto.getCloseTime())
                .storeIntroduction(storeDto.getStoreIntroduction())
                .storeBanner(storeDto.getStoreBanner())
                .build();
        return store;
    }
}
