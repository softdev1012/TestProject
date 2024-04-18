package testproject;

import java.util.Date;

import testproject.Doctor;
import testproject.Patient;

/**
 * The Appointment class represents a scheduled appointment between a doctor and a patient.
 * It contains information such as the appointment ID, doctor, patient, start time, and end time.
 */

public class Appointment {
    private static int nextAptId = 1;
    private int aptId;
    private Doctor doctor;
    private Patient patient;
    private Date startTime;
    private Date endTime;

    // Constructor
    public Appointment(Doctor doctor, Patient patient, Date startTime, Date endTime) {
        this.aptId = nextAptId++;
        this.doctor = doctor;
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getter and Setter for each attribute

    public int getAptId() {
        return aptId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "{" +
                "aptId='" + aptId + '\'' +
                ", patientID='" + patient.getpId() + '\'' +
                ", doctorID='" + doctor.getEmpId() + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

}
