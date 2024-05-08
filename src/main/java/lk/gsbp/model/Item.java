package lk.gsbp.model;

import lombok.*;

import java.util.PrimitiveIterator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Item {
    private String ItemsId;
    private String ItemName;
    private String QTY;
    private String UnitPrice;
    private String StockId;
//select itemId from Item where ItemName = " Atles CR 200";


    public String getItemsId() {
        return ItemsId;
    }

    public void setItemsId(String itemsId) {
        ItemsId = itemsId;
    }
}
