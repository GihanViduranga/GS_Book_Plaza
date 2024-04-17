package lk.gsbp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private String CustomerId;
    private String Name;
    private String Address;
    private String Contact;
    private String Email;

}
