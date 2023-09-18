package ori.loproject.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StoreDto {
    private Long storeid;
    private String storeName;
    private String storeCategory;
    private String storeImg;
    private String storeAddress;
    private int takeoutState;
    private String openTime;
    private String closeTime;
    private String storeIntroduction;
    private String storeBanner;
    private String storeTel;

    @Builder
    public StoreDto(String storeName, String storeCategory, String storeImg,
                 String storeAddress, int takeoutState, String openTime,
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
}
