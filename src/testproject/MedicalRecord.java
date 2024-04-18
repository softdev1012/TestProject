package testproject;
/**
 * The MedicalRecord class represents a medical record that contains information about a patient's diagnosis and treatment.
 */

public class MedicalRecord {
    private String diagnosis;
    private String treatment;

    // Constructor
    public MedicalRecord(String diagnosis, String treatment) {
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    // Getter and Setter methods for each attribute

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    @Override
    public String toString() {
        return "{" +
                "diagnosis='" + diagnosis + '\'' +
                ", treatment='" + treatment + '\'' +
                '}';
    }

}
