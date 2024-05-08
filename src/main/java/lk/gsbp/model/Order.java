package lk.gsbp.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter

@ToString

public class Order {
    private String OrderId;
    private String Date;
    private String CustomerId;

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public String getDate() {
        return Date;
    }

    public String getCustomerId() {
        return CustomerId;
    }
}
