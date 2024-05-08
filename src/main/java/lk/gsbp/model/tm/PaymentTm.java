package lk.gsbp.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentTm {
    private String PaymentId;
    private String PaymentMethod;
    private String Date;
    private String Payment;
}
