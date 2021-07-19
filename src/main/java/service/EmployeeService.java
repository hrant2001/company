package service;

import model.Employee;
import repository.EmployeeRepository;
import repository.Repository;
import util.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class EmployeeService {

    private static Repository<Employee> employeeRepository = new EmployeeRepository();

    private static DataSource dataSource;

    static {
        dataSource = DataSourceFactory.getInstance();
    }

    /**
     * Adds a new employee to the database.
     *
     * @param employee The employee to add to the list.
     */
    public static void addEmployee(Employee employee) throws SQLException {
        employeeRepository.insert(dataSource, employee);
    }

    /**
     * Deletes an employee from the database.
     *
     * @param employee The employee to delete from the list.
     */
    public static void deleteEmployee(Employee employee) throws SQLException {
        employeeRepository.delete(dataSource, employee);
    }

    /**
     * Finds the employees by their last name in the database.
     *
     * @param lastName The last name of the employees to find by in the list.
     */
    public static void findEmployeeByLastName(String lastName) throws SQLException {
        EmployeeRepository.findByLastName(dataSource, lastName);
    }

    /**
     * Prints the sorted list of the employees by the last name.
     */
    public static void sortListOfEmployees() throws SQLException {
        employeeRepository.sort(dataSource);
    }

    /**
     * Prints the list of the employees.
     */
    public static void printEmployees() throws SQLException {
        employeeRepository.printAll(dataSource);
    }
}