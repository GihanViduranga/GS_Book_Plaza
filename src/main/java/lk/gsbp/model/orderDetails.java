package lk.gsbp.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter

@ToString
public class orderDetails {
    private String ItemId;
    private String OrderId;
    private int qty;
    private double unitPrice;

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getItemId() {
        return ItemId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public int getQty() {
        return qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }


}
