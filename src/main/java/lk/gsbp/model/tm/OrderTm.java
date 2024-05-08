package lk.gsbp.model.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class OrderTm {
    private String ItemID;
    private String ItemName;
    private String UnitPrice;
    private String QTY;
    private String TotalPrice;
    private String Action;
}
