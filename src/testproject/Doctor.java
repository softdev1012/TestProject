package testproject;

import java.util.Date;

import testproject.Role;
import testproject.Appointment;

import java.util.ArrayList;

/**
 * The Doctor class represents a doctor in a hospital.
 * It extends the Employee class and includes additional properties and methods specific to doctors.
 */

public class Doctor extends Employee {
    private String specialization;
    private ArrayList<Appointment> appointments;

    // Constructor
    public Doctor(String name, Date dateOfBirth, String userName, String password, int salary, String specialization) {
        super(name, dateOfBirth, userName, password, salary);
        this.specialization = specialization;
        this.appointments = new ArrayList<Appointment>();
    }

    // Getter and Setter for specialization
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void addAppointment(Appointment app) {
        appointments.add(app);
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void cancelAppointment(Appointment apt) {
        appointments.remove(apt);
    }

    public Role getRole() {
        return Role.Doctor;
    }

    @Override
    public String toString() {
        return "{" +
                "empId='" + getEmpId() + '\'' +
                ", name='" + getName() + '\'' +
                ", dateOfBirth='" + getDateOfBirth() + '\'' +
                ", role='" + getRole() + '\'' +
                ", salary='" + getSalary() + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
