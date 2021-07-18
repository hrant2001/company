package repository;

import util.Converter;
import util.DBCPDataSource;
import model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.EmployeeService;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class EmployeeRepository implements Repository<Employee>{

    /**
     * The resource bundle
     */
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", new Locale(System.getProperty("lang")));

    /**
     * Slf4j logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    /**
     * The list of the employees
     */
    private static List<Employee> employees = new ArrayList<>();

    private static PreparedStatement preparedStatement;
    private static Statement statement;
    private static Connection connection;

    public void insert(DataSource dataSource, Employee employee) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
        }

        employees = getAll(dataSource);
        if (employees.contains(employee)) {
            LOGGER.warn("The employee " + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getBirthday() + " is already in the list");
            System.out.println("\n" + resourceBundle.getString("emp") + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getBirthday() + " " + resourceBundle.getString("in-list") + "\n");
            return;
        }

        String sql = "insert into employee(fname,lname,birthday,position_id,department_id) values(?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setDate(3, Date.valueOf(employee.getBirthday()));
            preparedStatement.setInt(4, employee.getPositionId());
            preparedStatement.setInt(5, employee.getDepartmentId());

            if (preparedStatement.executeUpdate() > 0) {
                LOGGER.info("The employee " + employee.getFirstName() + " "
                        + employee.getLastName() + " " + employee.getBirthday() + " was successfully added to the list of employees");
                System.out.println("\n" + resourceBundle.getString("emp") + " " + employee.getFirstName() + " "
                        + employee.getLastName() + " " + employee.getBirthday() + " " + resourceBundle.getString("success.add") + "\n");
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void delete(DataSource dataSource, Employee employee) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
        }

        String sql = "delete from employee where fname=? and lname=? and birthday=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setDate(3, Date.valueOf(employee.getBirthday()));
            if (preparedStatement.executeUpdate() > 0) {
                LOGGER.info("The employee " + employee.getFirstName() + " " + employee.getLastName() +
                        " " + employee.getBirthday() + " was successfully deleted from the list of employees");
                System.out.println("\n" + resourceBundle.getString("emp") + " " + employee.getFirstName() + " " + employee.getLastName() +
                        " " + employee.getBirthday() + " " + resourceBundle.getString("success.delete") + "\n");
            } else {
                LOGGER.warn("The employee " + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getBirthday() + " was not found in the list of employees");
                System.out.println("\n" + resourceBundle.getString("emp") + " " + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getBirthday() + " " + resourceBundle.getString("not.in.list") + "\n");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void findByLastName(DataSource dataSource, String lastName) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
        }

        employees.clear();
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM employee where lname=?");
            preparedStatement.setString(1, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                employees.add(Converter.resultSetToEmployee(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        for (Employee emp : employees) {
            if (emp.getLastName().equals(lastName)) {
                System.out.println(emp.getLastName() + " " + emp.getFirstName() + " " + emp.getBirthday());
                count++;
            }
        }
        if (count == 0) {
            LOGGER.warn("The employee with the last name of " + lastName + " was not found in the list");
            System.out.println("\n" + resourceBundle.getString("emp") + " " + resourceBundle.getString("not.found") + " " + lastName + " " + resourceBundle.getString("not.in.list") + "\n");
        }
    }

    public void printAll(DataSource dataSource) {
        employees = getAll(dataSource);

        if (employees == null || employees.isEmpty()) {
            System.out.println("\n" + resourceBundle.getString("empty.list") + "\n");
            LOGGER.warn("The list of employees is empty");
            return;
        }
        System.out.println();
        employees.forEach(System.out::println);
    }

    public void sort(DataSource dataSource) {
        employees = getAll(dataSource);

        if (employees == null || employees.isEmpty()) {
            System.out.println("\n" + resourceBundle.getString("empty.list") + "\n");
            LOGGER.warn("The list of employees is empty");
            return;
        }
        System.out.println();
        employees.stream().sorted(Comparator.comparing(Employee::getLastName)).forEach(System.out::println);
    }

    public List<Employee> getAll(DataSource dataSource) {
        try {
            statement = dataSource.getConnection().createStatement();
        } catch (SQLException e) {
        }
        employees.clear();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");

            while (resultSet.next()) {
                employees.add(Converter.resultSetToEmployee(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return employees;
    }

}
