package lk.gsbp.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Customer {
    private String CustomerId;
    private String Name;
    private String Address;
    private String Contact;
    private String Email;
}
