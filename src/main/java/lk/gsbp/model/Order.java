package lk.gsbp.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Order {
    private String OrderId;
    private String Date;
    private String CustomerId;
    private double NetTotal;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public double getNetTotal() {
        return NetTotal;
    }

    public void setNetTotal(double netTotal) {
        NetTotal = netTotal;
    }
}
