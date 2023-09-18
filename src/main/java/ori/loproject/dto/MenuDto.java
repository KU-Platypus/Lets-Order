package ori.loproject.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ori.loproject.domain.store.Store;

@NoArgsConstructor
@Data
public class MenuDto {
    private Long id;
    private String menuid;
    private Store storeid;
    private String menuName;
    private int menuPrice;
    private String menuCategory;
    private String menuImg;

    @Builder
    public MenuDto(Long id, String menuid, Store storeid, String menuName, int menuPrice,
                   String menuCategory, String menuImg) {
        this.id = id;
        this.menuid = menuid;
        this.storeid = storeid;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuCategory = menuCategory;
        this.menuImg = menuImg;
    }
}