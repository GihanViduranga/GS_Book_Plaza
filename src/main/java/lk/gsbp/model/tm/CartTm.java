package lk.gsbp.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CartTm {
    private String ItemId;
    private String ItemName;
    private int UnitPrice;
    private int QTY;
    private double TotalAmount;
    private JFXButton Action;


}
