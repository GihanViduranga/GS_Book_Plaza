package lk.gsbp.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DeliveryTm {
    private String DeliveryId;
    private String DeliverName;
    private String Date;
    private String Address;
    private String Stetus;
}
