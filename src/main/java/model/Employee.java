package model;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {

    private int employeeId;
    private String fName;
    private String lName;
    private LocalDate birthday;
    private int positionId = 1;
    private int departmentId = 1;

    public Employee() {
    }

    public Employee(String fName, String lName, LocalDate birthday) {
        this.fName = fName;
        this.lName = lName;
        this.birthday = birthday;
    }

    public Employee(String fName, String lName, LocalDate birthday, int positionId, int departmentId) {
        this.fName = fName;
        this.lName = lName;
        this.birthday = birthday;
        this.positionId = positionId;
        this.departmentId = departmentId;
    }

    public String getFirstName() {
        return fName;
    }

    public void setFirstName(String fName) {
        this.fName = fName;
    }

    public String getLastName() {
        return lName;
    }

    public void setLastName(String lName) {
        this.lName = lName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return fName.equalsIgnoreCase(employee.fName) && lName.equalsIgnoreCase(employee.lName) && birthday.equals(employee.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fName, lName, birthday);
    }

    @Override
    public String toString() {
        return lName + " " + fName + " " + birthday;
    }
}

