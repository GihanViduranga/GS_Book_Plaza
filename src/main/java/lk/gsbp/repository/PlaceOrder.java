package lk.gsbp.repository;

import lk.gsbp.model.Order;
import lk.gsbp.model.orderDetails;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class PlaceOrder {
    private Order order;
    private List<orderDetails> odList;


}
