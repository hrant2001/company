package util;

import model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class Converter {
    public static Employee resultSetToEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();

        employee.setEmployeeId(resultSet.getInt("employee_id"));
        employee.setFirstName(resultSet.getString("fname"));
        employee.setLastName(resultSet.getString("lname"));
        employee.setBirthday(resultSet.getDate("birthday").toLocalDate());

        return employee;
    }
}
