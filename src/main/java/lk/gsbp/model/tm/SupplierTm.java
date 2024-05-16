package lk.gsbp.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class SupplierTm {
    private String SupplierId;
    private String SupplierName;
    private String Contact;
    private String Product;
    private int QTY;
    private int ItemPrice;
    private JFXButton Remove;
}
