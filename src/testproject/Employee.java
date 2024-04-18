package testproject;
import java.util.Date;

import testproject.Appointment;

import java.util.ArrayList;

/**
 * Represents an employee and it is a super class in the system.
 * Inherits from the Person class.
 */

public class Employee extends Person {
    private static int nextEmployeeId = 1; // Static variable to generate unique employee IDs
    private int empId;
    private int salary;

    // Constructor
    public Employee(String name, Date dateOfBirth, String userName, String password, int salary) {
        super(name, dateOfBirth, userName, password);
        this.empId = nextEmployeeId++;
        this.salary = salary;
    }

    // Getter and Setter methods for each attribute

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void createAppointment() {
        throw new IllegalAccessError();
    }

    public ArrayList<Appointment> getAppointments() {
        throw new IllegalAccessError();
    }

    public void cancelAppointment(Appointment apt) {
        throw new IllegalAccessError();
    }

}
