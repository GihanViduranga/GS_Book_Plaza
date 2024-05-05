package lk.gsbp.model;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class Employee {
    private String EmployeeId;
    private String Name;
    private String Address;
    private String Contact;
    private String date;
    private String Position;
    private String Salary;
}
