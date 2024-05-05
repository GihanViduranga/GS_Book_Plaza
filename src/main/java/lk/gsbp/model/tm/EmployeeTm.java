package lk.gsbp.model.tm;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class EmployeeTm {
    private String EmployeeId;
    private String Name;
    private String Address;
    private String Contact;
    private String Date;
    private String Position;
    private String Salary;
}
