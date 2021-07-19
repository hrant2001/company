package service;

import model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.EmployeeRepository;
import repository.Repository;
import util.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class EmployeeService {

    private static Repository<Employee> employeeRepository = new EmployeeRepository();

    private static DataSource dataSource;

    static {
        dataSource = DataSourceFactory.getInstance();
    }

    /**
     * The resource bundle
     */
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", new Locale(System.getProperty("lang")));

    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    /**
     * Adds a new employee to the database.
     *
     * @param employee The employee to add to the list.
     */
    public static void addEmployee(Employee employee) throws SQLException {
        if (employeeRepository.insert(dataSource, employee) == 1) {
            LOGGER.warn("The employee " + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getBirthday() + " is already in the list");
            System.out.println("\n" + resourceBundle.getString("emp") + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getBirthday() + " " + resourceBundle.getString("in-list") + "\n");
        } else {
            LOGGER.info("The employee " + employee.getFirstName() + " "
                    + employee.getLastName() + " " + employee.getBirthday() + " was successfully added to the list of employees");
            System.out.println("\n" + resourceBundle.getString("emp") + " " + employee.getFirstName() + " "
                    + employee.getLastName() + " " + employee.getBirthday() + " " + resourceBundle.getString("success.add") + "\n");
        }
    }

    /**
     * Deletes an employee from the database.
     *
     * @param employee The employee to delete from the list.
     */
    public static void deleteEmployee(Employee employee) throws SQLException {
        if (employeeRepository.delete(dataSource, employee) == 0) {
            LOGGER.info("The employee " + employee.getFirstName() + " " + employee.getLastName() +
                    " " + employee.getBirthday() + " was successfully deleted from the list of employees");
            System.out.println("\n" + resourceBundle.getString("emp") + " " + employee.getFirstName() + " " + employee.getLastName() +
                    " " + employee.getBirthday() + " " + resourceBundle.getString("success.delete") + "\n");
        } else {
            LOGGER.warn("The employee " + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getBirthday() + " was not found in the list of employees");
            System.out.println("\n" + resourceBundle.getString("emp") + " " + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getBirthday() + " " + resourceBundle.getString("not.in.list") + "\n");
        }
    }

    /**
     * Finds the employees by their last name in the database.
     *
     * @param lastName The last name of the employees to find by in the list.
     */
    public static void findEmployeeByLastName(String lastName) throws SQLException {
        List<Employee> employees = EmployeeRepository.findByLastName(dataSource, lastName);
        if (employees.isEmpty()) {
            LOGGER.warn("The employee with the last name of " + lastName + " was not found in the list");
            System.out.println("\n" + resourceBundle.getString("emp") + " " + resourceBundle.getString("not.found") + " " + lastName + " "
                    + resourceBundle.getString("not.in.list") + "\n");
            return;
        }
        employees.forEach(System.out::println);
    }

    /**
     * Prints the sorted list of the employees by the last name.
     */
    public static void sortListOfEmployees() throws SQLException {
        List<Employee> employees = employeeRepository.getAll(dataSource);
        if (employees.isEmpty()) {
            System.out.println("\n" + resourceBundle.getString("empty.list") + "\n");
            LOGGER.warn("The list of employees is empty");
            return;
        }
        System.out.println();
        employees.stream().sorted(Comparator.comparing(Employee::getLastName)).forEach(System.out::println);
    }

    /**
     * Prints the list of the employees.
     */
    public static void printEmployees() throws SQLException {
        List<Employee> employees = employeeRepository.getAll(dataSource);
        if (employees.isEmpty()) {
            System.out.println("\n" + resourceBundle.getString("empty.list") + "\n");
            LOGGER.warn("The list of employees is empty");
            return;
        }
        System.out.println();
        employees.forEach(System.out::println);
    }
}