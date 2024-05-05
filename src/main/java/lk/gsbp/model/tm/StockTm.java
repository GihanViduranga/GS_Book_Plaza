package lk.gsbp.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class StockTm {
    private String StockId;
    private String ItemName;
    private String CatogaryName;
    private String QTY;
}
