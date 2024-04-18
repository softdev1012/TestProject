package testproject;

import java.util.Date;

import testproject.Patient;

/**
 * The Admission class represents a patient's admission to a ward in a hospital.
 * It stores information such as admission ID, ward, patient, admit time, and discharge time.
 */

public class Admission {
    private static int nextAdmId = 1; // Static variable to generate unique admIds
    private int admId;
    private Ward ward;
    private Patient patient;
    private Date admitTime;
    private Date dischargeTime;

    public Admission(Ward ward, Patient patient, Date admitTime, Date dischargeTime) {
        this.admId = nextAdmId++;
        this.ward = ward;
        this.patient = patient;
        this.admitTime = admitTime;
        this.dischargeTime = dischargeTime;
    }

    public int getAdmId() {
        return this.admId;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public Ward getWard() {
        return this.ward;
    }

    public void setDischargeTime(Date dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + admId + '\'' +
                ", wardId='" + ward.getWardId() + '\'' +
                ", patientId='" + patient.getpId() + '\'' +
                ", admitTime='" + admitTime + '\'' +
                ", dischargeTime='" + dischargeTime + '\'' +
                '}';
    }
}
