import model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.EmployeeService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Console {

    private static final Logger LOGGER = LoggerFactory.getLogger(Console.class);

    private static ResourceBundle resourceBundle;

    public static void startApplication() {
        Scanner input = new Scanner(System.in);
        String command;
        int employeeId = 0;
        String firstName, lastName;
        LocalDate birthday;
        System.out.println("Welcome to our company!!!\n");
        System.out.println("Please choose the language: en or fr?");

        System.setProperty("lang", new Locale(input.nextLine().trim()).getLanguage());
        resourceBundle = ResourceBundle.getBundle("messages", new Locale(System.getProperty("lang")));
        while (true) {

            usage();
            command = input.nextLine().toLowerCase().trim();

            if (command.equals("q")) {
                System.out.println(resourceBundle.getString("bye"));
                LOGGER.info("The user exited from the application");
                break;
            }

            switch (command) {
                case "a":
                    // add new employee
                    System.out.println(resourceBundle.getString("add.message"));
                    LOGGER.info("The user pressed a to add an employee");

                    System.out.print(resourceBundle.getString("first.name") + " ");
                    firstName = input.nextLine().trim().toLowerCase();
                    if (firstName.isEmpty()) {
                        System.out.println("\n" + resourceBundle.getString("empty.first.name") + "\n");
                        LOGGER.warn("The user entered an empty first name");
                        break;
                    }

                    System.out.print(resourceBundle.getString("last.name") + " ");
                    lastName = input.nextLine().trim().toLowerCase();
                    if (lastName.isEmpty()) {
                        System.out.println("\n" + resourceBundle.getString("empty.last.name") + "\n");
                        LOGGER.warn("The user entered an empty last name");
                        break;
                    }

                    System.out.print(resourceBundle.getString("birthday") + " ");
                    try {
                        birthday = LocalDate.parse(input.nextLine());
                    } catch (DateTimeParseException e) {
                        System.out.println("\n" + resourceBundle.getString("wrong.date") + "\n");
                        LOGGER.warn("The user entered a wrong format of birthday");
                        break;
                    }

                    try {
                        EmployeeService.addEmployee(new Employee(firstName, lastName, birthday));
                    } catch (SQLException e) {
                    }
                    break;
                case "d":
                    // delete an employee
                    System.out.println(resourceBundle.getString("delete.message"));
                    LOGGER.info("The user pressed d to delete an employee");

                    System.out.print(resourceBundle.getString("first.name") + " ");
                    firstName = input.nextLine().trim().toLowerCase();

                    if (firstName.isEmpty()) {
                        System.out.println("\n" + resourceBundle.getString("empty.first.name") + "\n");
                        LOGGER.warn("The user entered an empty first name");
                        break;
                    }

                    System.out.print(resourceBundle.getString("last.name") + " ");
                    lastName = input.nextLine().trim().toLowerCase();
                    if (lastName.isEmpty()) {
                        System.out.println("\n" + resourceBundle.getString("empty.last.name") + "\n");
                        LOGGER.warn("The user entered an empty last name");
                        break;
                    }

                    System.out.print(resourceBundle.getString("birthday") + " ");
                    try {
                        birthday = LocalDate.parse(input.nextLine());
                    } catch (DateTimeParseException e) {
                        System.out.println("\n" + resourceBundle.getString("wrong.date") + "\n");
                        LOGGER.warn("The user entered a wrong format of birthday");
                        break;
                    }

                    try {
                        EmployeeService.deleteEmployee(new Employee(firstName, lastName, birthday));
                    } catch (SQLException e) {
                    }
                    break;
                case "f":
                    // find an employee
                    System.out.println(resourceBundle.getString("find.message"));
                    LOGGER.info("The user pressed f to find an employee by the last name");
                    System.out.print(resourceBundle.getString("last.name") + " ");
                    lastName = input.nextLine().trim().toLowerCase();

                    System.out.println();
                    try {
                        EmployeeService.findEmployeeByLastName(lastName);
                    } catch (SQLException e) {
                    }
                    System.out.println();
                    break;
                case "s":
                    // sort the list of employees
                    System.out.println(resourceBundle.getString("sort.message"));
                    LOGGER.info("The user pressed s to see the sorted list of employees");
                    try {
                        EmployeeService.sortListOfEmployees();
                    } catch (SQLException e) {
                    }
                    System.out.println();
                    break;
                case "p":
                    // print the list of employees
                    System.out.println(resourceBundle.getString("print.message"));
                    LOGGER.info("The user pressed p to see the list of employees");
                    try {
                        EmployeeService.printEmployees();
                    } catch (SQLException e) {
                    }
                    System.out.println();
                    break;
                default:
                    // help
                    System.out.println(resourceBundle.getString("help.message"));
                    LOGGER.info("The user pressed h or something else to get some help");
                    break;
            }
        }
    }

    private static void usage() {
        System.out.print(resourceBundle.getString("press") + " a " + resourceBundle.getString("to") + " " + resourceBundle.getString("add") + "\n" +
                resourceBundle.getString("press") + " d " + resourceBundle.getString("to") + " " + resourceBundle.getString("delete") + "\n" +
                resourceBundle.getString("press") + " f " + resourceBundle.getString("to") + " " + resourceBundle.getString("find") + "\n" +
                resourceBundle.getString("press") + " s " + resourceBundle.getString("to") + " " + resourceBundle.getString("sort") + "\n" +
                resourceBundle.getString("press") + " p " + resourceBundle.getString("to") + " " + resourceBundle.getString("print") + "\n" +
                resourceBundle.getString("press") + " q " + resourceBundle.getString("to") + " " + resourceBundle.getString("quit") + "\n" +
                resourceBundle.getString("press") + " h " + resourceBundle.getString("to") + " " + resourceBundle.getString("help") + "\n\n" +
                resourceBundle.getString("com") + ": ");
    }
}

