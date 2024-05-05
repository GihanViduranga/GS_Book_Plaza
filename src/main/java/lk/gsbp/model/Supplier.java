package lk.gsbp.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Supplier {
    private String SupplierId;
    private String SuppName;
    private String Contact;
    private String Product;
    private String DeliveryTerms;
    private String SupplierRating;
}
