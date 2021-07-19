package repository;

import util.Converter;
import model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.EmployeeService;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class EmployeeRepository implements Repository<Employee> {

    /**
     * The list of the employees
     */
    private static List<Employee> employees = new ArrayList<>();

    public int insert(DataSource dataSource, Employee employee) throws SQLException {
        String sql = "insert into employee(fname,lname,birthday,position_id,department_id) values(?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            employees = getAll(dataSource);
            if (employees.contains(employee)) {
                return 1;
            }
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setDate(3, Date.valueOf(employee.getBirthday()));
            preparedStatement.setInt(4, employee.getPositionId());
            preparedStatement.setInt(5, employee.getDepartmentId());

            if (preparedStatement.executeUpdate() > 0)
                return 0; //ok

            return 1; //not ok
        }
    }

    public int delete(DataSource dataSource, Employee employee) throws SQLException {
        String sql = "delete from employee where fname=? and lname=? and birthday=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setDate(3, Date.valueOf(employee.getBirthday()));
            if (preparedStatement.executeUpdate() > 0)
                return 0;
            return 1;
        }
    }

    public static List<Employee> findByLastName(DataSource dataSource, String lastName) throws SQLException {
        String sql = "SELECT * FROM employee where lname=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            employees.clear();
            preparedStatement.setString(1, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employees.add(Converter.resultSetToEmployee(resultSet));
            }

            return employees;
        }
    }

    public List<Employee> getAll(DataSource dataSource) throws SQLException {
        String sql = "SELECT * FROM employee";
        try (Statement statement = dataSource.getConnection().createStatement()) {
            employees.clear();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                employees.add(Converter.resultSetToEmployee(resultSet));
            }
            return employees;
        }
    }
}
