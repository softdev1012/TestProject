package testproject;
import java.util.ArrayList;
import java.util.Date;

import testproject.Role;
import testproject.Doctor;
import testproject.Employee;
import testproject.Patient;

/**
 * The HMS class represents a Hospital Management System.
 * It manages patients, employees, wards, appointments, and admissions.
 */

 public class HMS {
    private ArrayList<Patient> patients;
    private ArrayList<Employee> employees;
    private ArrayList<Ward> wards;
    private ArrayList<Appointment> appointments;
    private ArrayList<Admission> admissions;

    public HMS() {
        patients = new ArrayList<Patient>();
        employees = new ArrayList<Employee>();
        wards = new ArrayList<Ward>();
        appointments = new ArrayList<Appointment>();
        admissions = new ArrayList<Admission>();
    }

    private int getIndexByPatientIdUtil(int pId) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getpId() == pId) {
                return i;
            }
        }

        return -1;
    }

    private int getIndexByEmpIdUtil(int empId) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmpId() == empId) {
                return i;
            }
        }

        return -1;
    }

    private int getIndexByWardIdUtil(int wardId) {
        for (int i = 0; i < wards.size(); i++) {
            if (wards.get(i).getWardId() == wardId) {
                return i;
            }
        }

        return -1;
    }

    private int getIndexByAptIdUtil(int aptId) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAptId() == aptId) {
                return i;
            }
        }

        return -1;
    }

    private int getIndexByAdmIdUtil(int admId) {
        for (int i = 0; i < admissions.size(); i++) {
            if (admissions.get(i).getAdmId() == admId) {
                return i;
            }
        }

        return -1;
    }

    public Session login(String userName, String password) {
        for (Patient patient : patients) {
            if (patient.getUserName().equals(userName) && patient.getPassword().equals(password)) {
                return new Session(patient.getpId(), patient.getRole());
            }
        }

        for (Employee emp : employees) {
            if (emp.getUserName().equals(userName) && emp.getPassword().equals(password)) {
                return new Session(emp.getEmpId(), emp.getRole());
            }
        }

        throw new IllegalArgumentException("userName or password");
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<Ward> getWards() {
        return wards;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public ArrayList<Admission> getAdmissions() {
        return admissions;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void addEmployee(Employee emp) {
        employees.add(emp);
    }

    public void addWard(Ward ward) {
        wards.add(ward);
    }

    public void removePatient(int pId) {
        int index = getIndexByPatientIdUtil(pId);

        if (index == -1) {
            throw new IllegalArgumentException(String.valueOf(pId));
        }

        patients.remove(index);
    }

    public void removeEmployee(int empId) {
        int index = getIndexByEmpIdUtil(empId);

        if (index == -1) {
            throw new IllegalArgumentException(String.valueOf(empId));
        }

        employees.remove(index);
    }

    public void removeWard(int wardId) {
        int index = getIndexByWardIdUtil(wardId);

        if (index == -1) {
            throw new IllegalArgumentException(String.valueOf(wardId));
        }

        wards.remove(index);
    }

    public void updatePatient(Patient updatedPatient) {
        int index = getIndexByPatientIdUtil(updatedPatient.getpId());

        if (index == -1) {
            throw new IllegalArgumentException(String.valueOf((updatedPatient.getpId())));
        }

        patients.remove(index);
        patients.add(updatedPatient);
    }

    public void updateEmployee(Employee updatedEmployee) {
        int index = getIndexByEmpIdUtil(updatedEmployee.getEmpId());

        if (index == -1) {
            throw new IllegalArgumentException(String.valueOf((updatedEmployee.getEmpId())));
        }

        employees.remove(index);
        employees.add(updatedEmployee);
    }

    public void updateWard(Ward updatedWard) {
        int index = getIndexByWardIdUtil(updatedWard.getWardId());

        if (index == -1) {
            throw new IllegalArgumentException(String.valueOf((updatedWard.getWardId())));
        }

        wards.remove(index);
        wards.add(updatedWard);
    }

    public ArrayList<MedicalRecord> getPatientHistory(int pId) {
        for (Patient patient : patients) {
            if (patient.getpId() == pId) {
                return patient.getPatientHistory();
            }
        }
        throw new IllegalArgumentException(String.valueOf(pId));
    }

    public ArrayList<Appointment> getPatientAppointments(int pId) {
        for (Patient patient : patients) {
            if (patient.getpId() == pId) {
                return patient.getAppointments();
            }
        }
        throw new IllegalArgumentException(String.valueOf(pId));
    }

    public void setPatientName(int pId, String name) {
        for (Patient patient : patients) {
            if (patient.getpId() == pId) {
                patient.setName(name);
                return;
            }
        }
        throw new IllegalArgumentException(String.valueOf(pId));
    }

    public void setPatientDateOfBirth(int pId, Date dob) {
        for (Patient patient : patients) {
            if (patient.getpId() == pId) {
                patient.setDateOfBirth(dob);
                return;
            }
        }
        throw new IllegalArgumentException(String.valueOf(pId));
    }

    public ArrayList<Appointment> getDoctorAppointments(int empId) {
        for (Employee emp : employees) {
            if (emp.getEmpId() == empId) {
                return emp.getAppointments();
            }
        }

        throw new IllegalArgumentException(String.valueOf(empId));
    }

    public void createAppointment(int docId, int patientId, Date startTime, Date endTime) {
        int doctorIndex = getIndexByEmpIdUtil(docId);
        int patientIndex = getIndexByPatientIdUtil(patientId);

        if (doctorIndex == -1 || employees.get(doctorIndex).getRole() != Role.Doctor) {
            throw new IllegalArgumentException(String.valueOf(doctorIndex));
        }

        if (patientIndex == -1) {
            throw new IllegalArgumentException(String.valueOf(patientIndex));
        }

        Doctor doctor = (Doctor) employees.get(doctorIndex);
        Patient patient = patients.get(patientIndex);

        Appointment appointment = new Appointment(doctor, patient, startTime, endTime);
        appointments.add(appointment);

        doctor.addAppointment(appointment);
        patient.addAppointment(appointment);
    }

    public void cancelAppointment(int aptId) {
        int index = getIndexByAptIdUtil(aptId);

        if (index == -1) {
            throw new IllegalArgumentException(String.valueOf(aptId));
        }

        Appointment appointment = appointments.get(index);
        appointment.getDoctor().cancelAppointment(appointment);
        appointment.getPatient().cancelAppointment(appointment);
        appointments.remove(index);
    }

    public void rescheduleAppointment(int aptId, Date startTime, Date endTime) {
        int index = getIndexByAptIdUtil(aptId);

        if (index == -1) {
            throw new IllegalArgumentException(String.valueOf(aptId));
        }

        Appointment appointment = appointments.get(index);

        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
    }

    public void admitPatient(int patientId, int wardId) {
        int patientIndex = getIndexByPatientIdUtil(patientId);
        int wardIndex = getIndexByWardIdUtil(wardId);

        if (patientIndex == -1) {
            throw new IllegalArgumentException(String.valueOf(patientId));
        }

        if (wardIndex == -1) {
            throw new IllegalArgumentException(String.valueOf(wardId));
        }

        Patient patient = patients.get(patientIndex);
        Ward ward = wards.get(wardIndex);

        Admission admission = new Admission(ward, patient, new Date(), null);

        patient.addAdmission(admission);
        ward.addAdmission(admission);
        admissions.add(admission);
    }

    public void dischargePatient(int admId) {
        int admIndex = getIndexByAdmIdUtil(admId);

        if (admIndex == -1) {
            throw new IllegalArgumentException(String.valueOf(admId));
        }

        Admission adm = admissions.get(admIndex);

        adm.setDischargeTime(new Date());
        adm.getPatient().removeAdmission(adm);
        adm.getWard().removeAdmission(adm);
    }

    public void createMedicalRecord(int patientID, String diagnosis, String treatment) {
        int patientIndex = getIndexByPatientIdUtil(patientID);

        if (patientID == -1) {
            throw new IllegalArgumentException(String.valueOf(patientID));
        }

        Patient patient = patients.get(patientIndex);

        patient.addMedicalRecord(new MedicalRecord(diagnosis, treatment));
    }

}
