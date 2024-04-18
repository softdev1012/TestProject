package testproject;
import java.util.Date;

import testproject.Admission;
import testproject.Appointment;
import testproject.MedicalRecord;

import java.util.ArrayList;

/**
 * The Patient class represents a patient in a hospital management system.
 * It extends the Person class and contains information about the patient's
 * admissions, appointments, and medical records.
 */

public class Patient extends Person {
    private static int nextPatientId = 1; // Static variable to generate unique patient IDs
    private int pId;
    private ArrayList<Admission> admissions;
    private ArrayList<Appointment> appointments;
    private ArrayList<MedicalRecord> medicalRecords;

    // Constructor
    public Patient(String name, Date dateOfBirth, String userName, String password) {
        super(name, dateOfBirth, userName, password);
        this.pId = nextPatientId++;
        admissions = new ArrayList<Admission>();
        appointments = new ArrayList<Appointment>();
        medicalRecords = new ArrayList<MedicalRecord>();
    }

    // Getter for pId
    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public void addAdmission(Admission adm) {
        admissions.add(adm);
    }

    public void removeAdmission(Admission adm) {
        admissions.remove(adm);
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecords.add(medicalRecord);
    }

    public ArrayList<MedicalRecord> getPatientHistory() {
        return medicalRecords;
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

    @Override
    public String toString() {
        return "{" +
                "pId='" + pId + '\'' +
                ", name='" + getName() + '\'' +
                ", dateOfBirth='" + getDateOfBirth() + '\'' +
                '}';
    }

}
