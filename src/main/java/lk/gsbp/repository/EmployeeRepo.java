package lk.gsbp.repository;


import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static List<Employee> getEmployees() throws SQLException {
        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employees = new ArrayList<>();

        while (resultSet.next()) {
            String EmployeeId = resultSet.getNString(1);
            String Name = resultSet.getNString(3);
            String Address = resultSet.getNString(4);
            String Contact = resultSet.getNString(5);
            String Date = resultSet.getNString(6);
            String Position = resultSet.getNString(7);
            String Salary = resultSet.getNString(8);

            Employee employee = new Employee(EmployeeId, Name, Address, Contact, Date, Position, Salary);

            employees.add(employee);
        }
        return employees;
    }
    public static List<String> getIds() throws SQLException {
        String sql = "SELECT EmployeeId FROM employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        List<String> Ids = new ArrayList<String>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String EmployeeId = resultSet.getNString(1);
            Ids.add(EmployeeId);
        }
        return Ids;
    }
    public static boolean update (Employee employee) throws SQLException {
        String sql = "UPDATE employee SET Name =?, Address =?, Contact =?, Date =?, Position =?, Salary =? WHERE EmployeeId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, employee.getName());
        pstm.setString(2, employee.getAddress());
        pstm.setString(3, employee.getContact());
        pstm.setString(4, employee.getDate());
        pstm.setString(5, employee.getPosition());
        pstm.setString(6, employee.getSalary());
        pstm.setObject(7, employee.getEmployeeId());

        return pstm.executeUpdate() > 0;
    }
    public static boolean update2 (String EmployeeId, String Name, String Address, String Contact, String Date, String Position, String Salary) throws SQLException {
        String sql = "UPDATE employee SET Name =?, Address =?, Contact =?, Date =?, Position =?, Salary =? WHERE EmployeeId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,Name);
        pstm.setObject(2,Address);
        pstm.setObject(3,Contact);
        pstm.setObject(4,Date);
        pstm.setObject(5,Position);
        pstm.setObject(6,Salary);
        pstm.setObject(7,EmployeeId);

        return pstm.executeUpdate() > 0;
    }
}
