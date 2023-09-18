package ori.loproject.domain.menu;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ori.loproject.domain.store.Store;
import ori.loproject.dto.MenuDto;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "menudb")
public class Menu {
    @Id
    private Long id;   //menuId
    @Column
    private String menuid;
    @ManyToOne
    @JoinColumn(name = "storeid")
    private Store storeid;
    @Column
    private String menuName;
    @Column
    private int menuPrice;
    @Column
    private String menuCategory;
    @Column
    private String menuImg;

    @Builder
    public Menu(Long id, String MenuID, Store storeid, String menuName, int menuPrice,
                String menuCategory, String menuImg) {
        this.id = id;
        this.menuid = MenuID;
        this.storeid = storeid;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuCategory = menuCategory;
        this.menuImg = menuImg;
    }

    public static Menu createMenu(MenuDto menuDto) {
        Menu menu = Menu.builder()
                .id(menuDto.getId())
                .MenuID(menuDto.getMenuid())
                .storeid(menuDto.getStoreid())
                .menuName(menuDto.getMenuName())
                .menuPrice(menuDto.getMenuPrice())
                .menuCategory(menuDto.getMenuCategory())
                .menuImg(menuDto.getMenuImg())
                .build();
        return menu;
    }

    public static Menu UpdateMenu(MenuDto menuDto) {
        Menu menu = Menu.builder()
                .id(menuDto.getId())
                .MenuID(menuDto.getMenuid())
                .storeid(menuDto.getStoreid())
                .menuPrice(menuDto.getMenuPrice())
                .menuName(menuDto.getMenuName())
                .menuImg(menuDto.getMenuImg())
                .build();
        return menu;
    }

    @Override
    public String toString() {
        return menuid;
    }
}