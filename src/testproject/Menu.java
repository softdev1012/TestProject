package testproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import testproject.util;
import testproject.Administrator;
import testproject.Doctor;
import testproject.Employee;
import testproject.Patient;

/**
 * The Menu class represents the menu system of the Hospital Management System.
 * It provides options for different roles such as Administrator, Patient, and
 * Doctor.
 * Users can navigate through the menu to perform various operations and access
 * different functionalities of the system.
 */

public class Menu {

    public static HMS hms = new HMS();
    public static Session session;

    public static void Start() throws Exception {
        Patient pat = new Patient("omran", new Date(), "omran", "omran1");
        Doctor doc = new Doctor("sos", new Date(), "doctor", "doctor", 1, "Heart");
        Administrator adm = new Administrator("Ali", new Date(), "admin", "admin", 15000, 1);
        hms.addPatient(pat);
        hms.addEmployee(doc);
        hms.addEmployee(adm);
        logIn();
    }

    private static void logIn() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
                loginGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
    static class BgPanel extends JPanel {
        private Image bgImage;
        public BgPanel(String fileName) {
            bgImage = new ImageIcon(fileName).getImage();
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 0, 0, this);
        }
    }

    static class LoginGUI extends JFrame {
        public LoginGUI() {
            setTitle("Login - Hospital Management System");
            setExtendedState(JFrame.NORMAL); // to take full screen
            setLayout(new GridBagLayout());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //setLocationRelativeTo(null); // Center the window
            setResizable(false);
            setSize(600, 800);
        BgPanel bgPanel = new BgPanel("background.jpg");
        GridBagConstraints bgPanelConstraints = new GridBagConstraints();
        bgPanelConstraints.gridx = 0;
        bgPanelConstraints.gridy = 0; // Placing the logo at the top row
        bgPanelConstraints.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
        bgPanelConstraints.weightx = 0.0;
        bgPanelConstraints.weighty = 0.0;
        bgPanelConstraints.fill = GridBagConstraints.BOTH;
        bgPanelConstraints.anchor = GridBagConstraints.CENTER; // Center components
        bgPanelConstraints.insets = new Insets(0, 0, 0, 0); // Add padding
        bgPanel.setSize(600, 800);
            // Create a panel for holding the logo
        JPanel logoPanel = new JPanel();
        // Load the logo image
        ImageIcon logoImage = new ImageIcon("logo1.png");
        Image logo = logoImage.getImage();
        Image scaled = logo.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        logoImage.setImage(logo);
        
        // Create a label to display the logo
        JLabel logoLabel = new JLabel(logoImage);

        // Add the logo label to the logoPanel
        logoPanel.add(logoLabel);

        // Create GridBagConstraints for logoPanel
        GridBagConstraints logoPanelConstraints = new GridBagConstraints();
        logoPanelConstraints.gridx = 0;
        logoPanelConstraints.gridy = 0; // Placing the logo at the top row
        logoPanelConstraints.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
        logoPanelConstraints.weightx = 0.0;
        logoPanelConstraints.weighty = 0.0;
        logoPanelConstraints.fill = GridBagConstraints.NONE;
        logoPanelConstraints.anchor = GridBagConstraints.CENTER; // Center components
        logoPanelConstraints.insets = new Insets(10, 10, 250, 10); // Add padding

        // Add logoPanel to the frame
        
        bgPanel.add(logoPanel, logoPanelConstraints);

            // Create input panel for labels and text fields
            JPanel inputPanel = new JPanel(new GridLayout(2, 2));
            JLabel usernameLabel = new JLabel("Username:");
            JTextField usernameField = new JTextField();
            JLabel passwordLabel = new JLabel("Password:");
            JPasswordField passwordField = new JPasswordField();

            // Set dimensions for labels and input fields
            Dimension labelSize = new Dimension(150, 30);
            Dimension fieldSize = new Dimension(150, 30);

            // Set size for labels and input fields
            usernameLabel.setPreferredSize(labelSize);
            passwordLabel.setPreferredSize(labelSize);
            usernameField.setPreferredSize(fieldSize);
            passwordField.setPreferredSize(fieldSize);

            // Add components to the inputPanel
            inputPanel.add(usernameLabel);
            inputPanel.add(usernameField);
            inputPanel.add(passwordLabel);
            inputPanel.add(passwordField);
            
            

            // Create GridBagConstraints for inputPanel
            GridBagConstraints inputPanelConstraints = new GridBagConstraints();
            inputPanelConstraints.gridx = 0;
            inputPanelConstraints.gridy = 0;
            inputPanelConstraints.gridwidth = 5; // Span across 5 columns
            inputPanelConstraints.gridheight = 1; // Takes 1 row
            inputPanelConstraints.weightx = 0.0;
            inputPanelConstraints.weighty = 0.0;
            inputPanelConstraints.fill = GridBagConstraints.NONE;
            inputPanelConstraints.anchor = GridBagConstraints.CENTER; // Center components
            inputPanelConstraints.insets = new Insets(250, 10, 10, 10); // Add padding

            // Add inputPanel to the center of the frame
            bgPanel.add(inputPanel, inputPanelConstraints);

            // Create login button
            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText().trim();
                    String password = new String(passwordField.getPassword());
                    // Perform login
                    if (username.isEmpty() && password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "The User Name and password field is empty.",
                                "Write your Password",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (username.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "The User Name field is empty.", "Write your User Name",
                                JOptionPane.ERROR_MESSAGE);

                    } else if (password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "The Password field is empty.",
                                "Write your User Name",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            session = hms.login(username, password);
                            dispose(); // Close the login window

                            // Display appropriate menu based on the role
                            switch (session.getRole()) {
                                case Administrator:
                                    AdminMenuGUI adminMenuGUI = new AdminMenuGUI();
                                    adminMenuGUI.setVisible(true);
                                    adminMenuGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    break;
                                case Patient:
                                    patientMenu();
                                    break;
                                case Doctor:
                                    DoctorMenuGUI doctorMenuGUI = new DoctorMenuGUI();
                                    doctorMenuGUI.setVisible(true);
                                    doctorMenuGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(null, "Incorrect, Please try again", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    break;
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Incorrect, Please try again",
                                    "Login Failed",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            // add event when Enter key pressed <Sultan>
            // -----------------------------------------------------------------------------------------

            usernameField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        loginButton.doClick();
                    }
                }
            });

            passwordField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        loginButton.doClick();
                    }
                }
            });

            // -----------------------------------------------------------------------------------------

            GridBagConstraints loginButtonConstraints = new GridBagConstraints();
            loginButtonConstraints.gridx = 0;
            loginButtonConstraints.gridy = 1;
            loginButtonConstraints.gridwidth = 5;
            loginButtonConstraints.gridheight = 1;
            loginButtonConstraints.weightx = 0.0;
            loginButtonConstraints.weighty = 0.0;
            loginButtonConstraints.fill = GridBagConstraints.NONE;
            loginButtonConstraints.anchor = GridBagConstraints.CENTER; // Center components
            loginButtonConstraints.insets = new Insets(10, 10, 10, 10); // Add padding

            // Add loginButton below inputPanel
            bgPanel.add(loginButton, loginButtonConstraints);
            add(bgPanel, bgPanelConstraints);
        }
    }

    private static void patientMenu() {
        SwingUtilities.invokeLater(() -> {
            PatientMenuGUI patientMenuGUI = new PatientMenuGUI();
            patientMenuGUI.setVisible(true);
            patientMenuGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    static class PatientMenuGUI extends JFrame {
        public PatientMenuGUI() {
            setTitle("Patient Menu - Hospital Management System");
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setSize(400, 300);
            setLocationRelativeTo(null); // Center the window
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            

        JPanel panel = new JPanel();
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        panel.setBorder(BorderFactory.createEmptyBorder(50, 500, 250, 500)); 
        
        // Create a panel for holding the image and buttons
        JPanel topPanel = new JPanel(new BorderLayout());

        
          // Create ImageIcon using the path to the image
        ImageIcon logoImageIcon = new ImageIcon("logo.png");
        
// Create JLabel and set its icon to the hospital image
        JLabel imageLabel = new JLabel(logoImageIcon);
        
        topPanel.add(imageLabel, BorderLayout.CENTER);
        
        // Add imageLabel to the panel
        add(topPanel, BorderLayout.NORTH);
            

            JButton viewMedicalHistoryBtn = new JButton("View Medical History");
            JButton viewAppointmentsBtn = new JButton("View Appointments");
            JButton updateInfoBtn = new JButton("Update Personal Details");
            JButton signOutBtn = new JButton("Sign Out");
            
            Dimension buttonSize = new Dimension(200, 60);
            viewAppointmentsBtn.setPreferredSize(buttonSize);
            updateInfoBtn.setPreferredSize(buttonSize);
            signOutBtn.setPreferredSize(buttonSize);
            viewMedicalHistoryBtn.setPreferredSize(buttonSize);

            viewMedicalHistoryBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<MedicalRecord> medicalRecords = hms.getPatientHistory(session.getId());
                    StringBuilder medicalHistoryText = new StringBuilder();
                    if (medicalHistoryText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "You don't have a medical history yet", "Appointments",
                                JOptionPane.PLAIN_MESSAGE);

                    } else {
                        for (MedicalRecord record : medicalRecords) {
                            medicalHistoryText.append(record).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, medicalHistoryText.toString(), "Medical History",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            viewAppointmentsBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        ArrayList<Appointment> appointments = hms.getPatientAppointments(session.getId());
                        StringBuilder appointmentsText = new StringBuilder();
                        if (appointmentsText.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "There is not Appointments", "Appointments",
                                    JOptionPane.PLAIN_MESSAGE);
                        } else {
                            for (Appointment appointment : appointments) {
                                appointmentsText.append(appointment).append("\n");
                            }
                            JOptionPane.showMessageDialog(null, appointmentsText.toString(), "Appointments",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "An error occurred while retrieving appointments: " + ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Add ActionListener to the button
            updateInfoBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Prompt the user for input using JOptionPane
                    String name = JOptionPane.showInputDialog(null, "Enter new name:", "Update Name",
                            JOptionPane.PLAIN_MESSAGE);
                    String username = JOptionPane.showInputDialog(null, "Enter new username:", "Update Username",
                            JOptionPane.PLAIN_MESSAGE);
                    String password = JOptionPane.showInputDialog(null, "Enter new password:", "Update Password",
                            JOptionPane.PLAIN_MESSAGE);

                    // Call method to update personal info
                    updatePersonalInfo(name, username, password);
                }
            });

            signOutBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?",
                            "Confirm Sign Out", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        dispose(); // Close the window
                        session = null;
                        logIn();
                    }
                }
            });

            panel.add(viewMedicalHistoryBtn);
            panel.add(viewAppointmentsBtn);
            panel.add(updateInfoBtn);
            panel.add(signOutBtn);

            add(panel);
        }

    }

    static class DoctorMenuGUI extends JFrame {
        public DoctorMenuGUI() {
            setTitle("Doctor Menu");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 400);
            setLocationRelativeTo(null); // Center the window
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            

        JPanel panel = new JPanel();
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        panel.setBorder(BorderFactory.createEmptyBorder(50, 500, 250, 500)); 
        
        // Create a panel for holding the image and buttons
        JPanel topPanel = new JPanel(new BorderLayout());

        
          // Create ImageIcon using the path to the image
        ImageIcon logoImageIcon = new ImageIcon("logo.png");
        
// Create JLabel and set its icon to the hospital image
        JLabel imageLabel = new JLabel(logoImageIcon);
        
        topPanel.add(imageLabel, BorderLayout.CENTER);
        
        // Add imageLabel to the panel
        add(topPanel, BorderLayout.NORTH);
        
        JButton viewAppointmentsBtn = new JButton("View Appointments");
        JButton diagnoseBtn = new JButton("Diagnose and Add Diagnoses");
        //JButton logInBtn = new JButton("Log In Again");
        JButton signOutBtn = new JButton("Sign Out");
        JButton exitBtn = new JButton("Exit");
        
        Dimension buttonSize = new Dimension(200, 60);
        viewAppointmentsBtn.setPreferredSize(buttonSize);
        diagnoseBtn.setPreferredSize(buttonSize);
        signOutBtn.setPreferredSize(buttonSize);
        exitBtn.setPreferredSize(buttonSize);

            viewAppointmentsBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Get appointments and display them
                    ArrayList<Appointment> appointments = hms.getDoctorAppointments(session.getId());
                    System.out.println(session.getId());
                    StringBuilder appointmentsText = new StringBuilder();
                    if (appointments.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There is not Appointments", "Appointments",
                                JOptionPane.PLAIN_MESSAGE);

                    } else {
                        for (Appointment appointment : appointments) {
                            appointmentsText.append(appointment.toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, appointmentsText.toString(), "Appointments",
                                JOptionPane.PLAIN_MESSAGE);
                        System.out.println("View Appointments Button Clicked");
                    }
                }
            });

            diagnoseBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int pid = Integer.parseInt(JOptionPane.showInputDialog("Enter Patient ID:"));
                    String diagnosis = JOptionPane.showInputDialog("Enter Diagnosis:");
                    String treatment = JOptionPane.showInputDialog("Enter Treatment:");
                    try {
                        hms.createMedicalRecord(pid, diagnosis, treatment);
                        JOptionPane.showMessageDialog(null, "Medical Record updated successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error updating medical record. TRY AGAIN LATER");
                    }
                }
            });

            // logInBtn.addActionListener(new ActionListener() {
            // @Override
            // public void actionPerformed(ActionEvent e) {
            // // Code to log in again
            // logIn();
            // }
            // });

            signOutBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?",
                            "Confirm Sign Out", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        // Code to sign out (if needed)
                        System.out.println("Sign Out Button Clicked");
                        dispose(); // Close the window
                        session = null;
                        logIn();
                    }
                }
            });

            exitBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the system?",
                            "Confirm Exit", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        dispose(); // Close the window
                        System.exit(0); // Terminate the application
                    }
                }
            });

            panel.add(viewAppointmentsBtn);
            panel.add(diagnoseBtn);
            // panel.add(logInBtn);
            panel.add(signOutBtn);
            panel.add(exitBtn);

            add(panel);
        }
    }

    static class AdminMenuGUI extends JFrame {
        public AdminMenuGUI() {
            setTitle("Admin Menu");
            setExtendedState(JFrame.MAXIMIZED_BOTH); // to take fullscreen
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 400);
            setLocationRelativeTo(null); // Center the window

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.setBorder(BorderFactory.createEmptyBorder(50, 250, 550, 250));
            
            // Create a panel for holding the image and buttons
            JPanel topPanel = new JPanel(new BorderLayout());

        
            // Create ImageIcon using the path to the image
            ImageIcon logoImageIcon = new ImageIcon("logo.png");
        
            // Create JLabel and set its icon to the hospital image
            JLabel imageLabel = new JLabel(logoImageIcon);
        
            topPanel.add(imageLabel, BorderLayout.CENTER);
        
            // Add imageLabel to the panel
            add(topPanel, BorderLayout.NORTH);
            
           

            JButton addDoctorBtn = new JButton("Add Doctor");
            JButton removeEmployeeBtn = new JButton("Remove Employee");
            JButton updateDoctorBtn = new JButton("Update Doctor");
            JButton addPatientBtn = new JButton("Add Patient");
            JButton removePatientBtn = new JButton("Remove Patient");
            JButton updatePatientBtn = new JButton("Update Patient");
            JButton addWardBtn = new JButton("Add Ward");
            JButton removeWardBtn = new JButton("Remove Ward");
            JButton updateWardBtn = new JButton("Update Ward");
            JButton createAppointmentBtn = new JButton("Add Appointments");
            JButton removeAppointmentBtn = new JButton("Cancel Appointment");
            JButton updateAppointmentBtn = new JButton("Reschedule Appointment");
            JButton addAdministratorBtn = new JButton("Add Admin");
            JButton removeEmployee = new JButton("Delete Admin");
            JButton updateAdministrator = new JButton("Modify Admin");
            JButton showAllPatient = new JButton("Get All Patients");
            JButton showAllEmployee = new JButton("Get All Employees");
            JButton showAllWards = new JButton("Get All Wards");
            JButton showAllAppointments = new JButton("Get All Appointments");
            JButton showAllAdmin = new JButton("Get All Admissions");
            JButton admitPatientBtn = new JButton("Admit Patient");
            JButton dischargePatientBtn = new JButton("Discharge Patient");
            JButton SignOutBtn = new JButton("SignOut");
            JButton ExitBtn = new JButton("Exit");
            
            // Customize button size
            Dimension buttonSize = new Dimension(160, 40);
            addDoctorBtn.setPreferredSize(buttonSize);
            removeEmployeeBtn.setPreferredSize(buttonSize);
            updateDoctorBtn.setPreferredSize(buttonSize);
            addPatientBtn.setPreferredSize(buttonSize);
            removePatientBtn.setPreferredSize(buttonSize);
            updatePatientBtn.setPreferredSize(buttonSize);
            addWardBtn.setPreferredSize(buttonSize);
            removeWardBtn.setPreferredSize(buttonSize);
            updateWardBtn.setPreferredSize(buttonSize);
            createAppointmentBtn.setPreferredSize(buttonSize);
            removeAppointmentBtn.setPreferredSize(buttonSize);
            updateAppointmentBtn.setPreferredSize(buttonSize);
            addAdministratorBtn.setPreferredSize(buttonSize);
            removeEmployee.setPreferredSize(buttonSize);
            updateAdministrator.setPreferredSize(buttonSize);
            showAllPatient.setPreferredSize(buttonSize);
            showAllEmployee.setPreferredSize(buttonSize);
            showAllWards.setPreferredSize(buttonSize);
            showAllAppointments.setPreferredSize(buttonSize);
            showAllAdmin.setPreferredSize(buttonSize);
            admitPatientBtn.setPreferredSize(buttonSize);
            dischargePatientBtn.setPreferredSize(buttonSize);
            SignOutBtn.setPreferredSize(buttonSize);
            ExitBtn.setPreferredSize(buttonSize);

            addDoctorBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addDoctor();
                }
            });

            addPatientBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addPatient();
                }
            });

            removeEmployeeBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeEmployee();
                }
            });

            updateDoctorBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UpdateDoctor();
                }
            });

            addWardBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addWard();
                }
            });

            removeWardBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeWard();
                }
            });

            updateWardBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateWard();
                }
            });

            createAppointmentBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createAppointment();
                }
            });

            removeAppointmentBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeAppointment();
                }
            });

            updateAppointmentBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateAppointment();
                }
            });

            addAdministratorBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addAdministrator();
                }
            });

            removeEmployee.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeEmployee();
                }
            });

            updateAdministrator.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateAdministrator();
                }
            });

            showAllPatient.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Patient> patients = hms.getPatients();

                    if (patients.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There are no patients", "Patients",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JPanel mainPanel = new JPanel(new BorderLayout());

                        JPanel patientsPanel = new JPanel();
                        patientsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        patientsPanel.setLayout(new BoxLayout(patientsPanel, BoxLayout.Y_AXIS));

                        for (Patient patient : patients) {
                            JPanel patientPanel = new JPanel(new GridLayout(4, 2, 5, 5));
                            patientPanel.setBorder(BorderFactory.createEtchedBorder());

                            JLabel idLabel = new JLabel("Patient ID:");
                            idLabel.setFont(idLabel.getFont().deriveFont(Font.BOLD));
                            JTextField idField = new JTextField(String.valueOf(patient.getpId()));
                            idField.setEditable(false);

                            JLabel nameLabel = new JLabel("Name:");
                            nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
                            JTextField nameField = new JTextField(patient.getName());
                            nameField.setEditable(false);

                            JLabel dobLabel = new JLabel("Date of Birth:");
                            dobLabel.setFont(dobLabel.getFont().deriveFont(Font.BOLD));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            JTextField dobField = new JTextField(dateFormat.format(patient.getDateOfBirth()));
                            dobField.setEditable(false);

                            JLabel usernameLabel = new JLabel("Username:");
                            usernameLabel.setFont(usernameLabel.getFont().deriveFont(Font.BOLD));
                            JTextField usernameField = new JTextField(patient.getUserName());
                            usernameField.setEditable(false);

                            patientPanel.add(idLabel);
                            patientPanel.add(idField);
                            patientPanel.add(usernameLabel);
                            patientPanel.add(usernameField);
                            patientPanel.add(nameLabel);
                            patientPanel.add(nameField);
                            patientPanel.add(dobLabel);
                            patientPanel.add(dobField);

                            patientsPanel.add(patientPanel);
                        }

                        JScrollPane scrollPane = new JScrollPane(patientsPanel);
                        mainPanel.add(scrollPane, BorderLayout.CENTER);

                        JOptionPane.showMessageDialog(null, mainPanel, "All Patients Information",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            showAllEmployee.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Employee> employees = hms.getEmployees();

                    if (employees.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There are no employees", "Employees",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JPanel mainPanel = new JPanel(new BorderLayout());

                        JPanel employeesPanel = new JPanel();
                        employeesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        employeesPanel.setLayout(new BoxLayout(employeesPanel, BoxLayout.Y_AXIS));

                        for (Employee employee : employees) {
                            JPanel employeePanel = new JPanel(new GridLayout(4, 2, 5, 5));
                            employeePanel.setBorder(BorderFactory.createEtchedBorder());

                            JLabel idLabel = new JLabel("Employee ID:");
                            idLabel.setFont(idLabel.getFont().deriveFont(Font.BOLD));
                            JTextField idField = new JTextField(String.valueOf(employee.getEmpId()));
                            idField.setEditable(false);

                            JLabel nameLabel = new JLabel("Name:");
                            nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
                            JTextField nameField = new JTextField(employee.getName());
                            nameField.setEditable(false);

                            JLabel dobLabel = new JLabel("Date of Birth:");
                            dobLabel.setFont(dobLabel.getFont().deriveFont(Font.BOLD));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            JTextField dobField = new JTextField(dateFormat.format(employee.getDateOfBirth()));
                            dobField.setEditable(false);

                            JLabel usernameLabel = new JLabel("Username:");
                            usernameLabel.setFont(usernameLabel.getFont().deriveFont(Font.BOLD));
                            JTextField usernameField = new JTextField(employee.getUserName());
                            usernameField.setEditable(false);

                            employeePanel.add(idLabel);
                            employeePanel.add(idField);
                            employeePanel.add(usernameLabel);
                            employeePanel.add(usernameField);
                            employeePanel.add(nameLabel);
                            employeePanel.add(nameField);
                            employeePanel.add(dobLabel);
                            employeePanel.add(dobField);

                            employeesPanel.add(employeePanel);
                        }

                        JScrollPane scrollPane = new JScrollPane(employeesPanel);
                        mainPanel.add(scrollPane, BorderLayout.CENTER);

                        JOptionPane.showMessageDialog(null, mainPanel, "All Employees Information",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            showAllWards.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Ward> wards = hms.getWards();

                    if (wards.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There are no wards", "Wards", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JPanel mainPanel = new JPanel(new BorderLayout());

                        JPanel wardsPanel = new JPanel();
                        wardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        wardsPanel.setLayout(new BoxLayout(wardsPanel, BoxLayout.Y_AXIS));

                        for (Ward ward : wards) {
                            JPanel wardPanel = new JPanel(new GridLayout(1, 2, 5, 5));
                            wardPanel.setBorder(BorderFactory.createEtchedBorder());

                            JLabel idLabel = new JLabel("Ward ID:");
                            idLabel.setFont(idLabel.getFont().deriveFont(Font.BOLD));
                            JTextField idField = new JTextField(String.valueOf(ward.getWardId()));
                            idField.setEditable(false);

                            JLabel nameLabel = new JLabel("Name:");
                            nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
                            JTextField nameField = new JTextField(ward.getName());
                            nameField.setEditable(false);

                            JLabel capacityLabel = new JLabel("Capacity:");
                            capacityLabel.setFont(capacityLabel.getFont().deriveFont(Font.BOLD));
                            JTextField capacityField = new JTextField(String.valueOf(ward.getCapacity()));
                            capacityField.setEditable(false);

                            wardPanel.add(idLabel);
                            wardPanel.add(idField);
                            wardPanel.add(nameLabel);
                            wardPanel.add(nameField);
                            wardPanel.add(capacityLabel);
                            wardPanel.add(capacityField);

                            wardsPanel.add(wardPanel);
                        }

                        JScrollPane scrollPane = new JScrollPane(wardsPanel);
                        mainPanel.add(scrollPane, BorderLayout.CENTER);

                        JOptionPane.showMessageDialog(null, mainPanel, "All Wards Information",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            showAllAppointments.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Appointment> appointments = hms.getAppointments();

                    if (appointments.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There are no appointments", "Appointments",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JPanel mainPanel = new JPanel(new BorderLayout());

                        JPanel appointmentsPanel = new JPanel();
                        appointmentsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        appointmentsPanel.setLayout(new BoxLayout(appointmentsPanel, BoxLayout.Y_AXIS));

                        for (Appointment appointment : appointments) {
                            JPanel appointmentPanel = new JPanel(new GridLayout(4, 2, 5, 5));
                            appointmentPanel.setBorder(BorderFactory.createEtchedBorder());

                            JLabel idLabel = new JLabel("Appointment ID:");
                            idLabel.setFont(idLabel.getFont().deriveFont(Font.BOLD));
                            JTextField idField = new JTextField(String.valueOf(appointment.getAptId()));
                            idField.setEditable(false);

                            JLabel dateLabel = new JLabel("Date:");
                            dateLabel.setFont(dateLabel.getFont().deriveFont(Font.BOLD));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            JTextField dateField = new JTextField(dateFormat.format(appointment.getStartTime()));
                            dateField.setEditable(false);

                            JLabel doctorLabel = new JLabel("Doctor:");
                            doctorLabel.setFont(doctorLabel.getFont().deriveFont(Font.BOLD));
                            JTextField doctorField = new JTextField(appointment.getDoctor().getUserName());
                            doctorField.setEditable(false);

                            JLabel patientLabel = new JLabel("Patient:");
                            patientLabel.setFont(patientLabel.getFont().deriveFont(Font.BOLD));
                            JTextField patientField = new JTextField(appointment.getPatient().getUserName());
                            patientField.setEditable(false);

                            appointmentPanel.add(idLabel);
                            appointmentPanel.add(idField);
                            appointmentPanel.add(dateLabel);
                            appointmentPanel.add(dateField);
                            appointmentPanel.add(doctorLabel);
                            appointmentPanel.add(doctorField);
                            appointmentPanel.add(patientLabel);
                            appointmentPanel.add(patientField);

                            appointmentsPanel.add(appointmentPanel);
                        }

                        JScrollPane scrollPane = new JScrollPane(appointmentsPanel);
                        mainPanel.add(scrollPane, BorderLayout.CENTER);

                        JOptionPane.showMessageDialog(null, mainPanel, "All Appointments Information",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            showAllAdmin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Admission> admissions = hms.getAdmissions();
                    StringBuilder admissionsText = new StringBuilder();
                    if (admissions.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There are no admissions", "Admissions",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        for (Admission admission : admissions) {
                            admissionsText.append(admission.toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, admissionsText.toString(), "Admissions",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            admitPatientBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    admitPatient();
                }
            });

            dischargePatientBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dischargePatient();
                }
            });

            updatePatientBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updatePatient();
                }
            });

            removePatientBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removePatient();
                }
            });
            SignOutBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?",
                            "Confirm Sign Out", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        dispose(); // Close the window
                        session = null;
                        logIn();
                    }
                }
            });

            ExitBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the system?",
                            "Confirm Exit", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        dispose(); // Close the window
                        System.exit(0); // Terminate the application
                    }
                }
            });

            panel.add(addDoctorBtn);
            panel.add(removeEmployeeBtn);
            panel.add(updateDoctorBtn);
            panel.add(addPatientBtn);
            panel.add(removePatientBtn);
            panel.add(updatePatientBtn);
            panel.add(addWardBtn);
            panel.add(removeWardBtn);
            panel.add(updateWardBtn);
            panel.add(createAppointmentBtn);
            panel.add(removeAppointmentBtn);
            panel.add(updateAppointmentBtn);
            panel.add(addAdministratorBtn);
            panel.add(removeEmployee);
            panel.add(updateAdministrator);
            panel.add(showAllPatient);
            panel.add(showAllEmployee);
            panel.add(showAllWards);
            panel.add(showAllAppointments);
            panel.add(showAllAdmin);
            panel.add(admitPatientBtn);
            panel.add(dischargePatientBtn);
            panel.add(SignOutBtn);
            panel.add(ExitBtn);

            // Add the panel to the frame
            add(panel, BorderLayout.CENTER);
        }
    }

    private static void addDoctor() {
        JTextField nameField = new JTextField();
        JTextField dateOfBirthField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField salaryField = new JTextField();
        JTextField specialtyField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        panel.add(dateOfBirthField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Salary:"));
        panel.add(salaryField);
        panel.add(new JLabel("Specialty:"));
        panel.add(specialtyField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Add Doctor",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                String dateOfBirthStr = dateOfBirthField.getText().trim();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                String salaryStr = salaryField.getText().trim();
                String specialty = specialtyField.getText().trim();

                if (name.isEmpty() || dateOfBirthStr.isEmpty() || username.isEmpty() || password.isEmpty()
                        || salaryStr.isEmpty() || specialty.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                Date dateOfBirth = util.parseDate(dateOfBirthStr);
                if (dateOfBirth == null) {
                    JOptionPane.showMessageDialog(null, "Invalid date format! Use yyyy-MM-dd.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                int salary;
                try {
                    salary = Integer.parseInt(salaryStr);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid salary format! Please enter a number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                Doctor doctor = new Doctor(name, dateOfBirth, username, password, salary, specialty);
                hms.addEmployee(doctor);
                JOptionPane.showMessageDialog(null, "Doctor added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                break; // Exit loop if successfully added
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void UpdateDoctor() {
        JTextField empIdField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField salaryField = new JTextField();
        JTextField specialtyField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Doctor ID to be updated:"));
        panel.add(empIdField);
        panel.add(new JLabel("New Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Date of Birth (yyyy-MM-dd HH:mm):"));
        panel.add(dobField);
        panel.add(new JLabel("New Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("New Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Salary:"));
        panel.add(salaryField);
        panel.add(new JLabel("New Specialty:"));
        panel.add(specialtyField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Update Doctor",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String empIdInput = empIdField.getText().trim();
                String name = nameField.getText().trim();
                String dobInput = dobField.getText().trim();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                String salaryInput = salaryField.getText().trim();
                String specialty = specialtyField.getText().trim();

                if (empIdInput.isEmpty() || name.isEmpty() || dobInput.isEmpty() || username.isEmpty()
                        || password.isEmpty() || salaryInput.isEmpty() || specialty.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                int empId;
                int salary;
                try {
                    empId = Integer.parseInt(empIdInput);
                    salary = Integer.parseInt(salaryInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid ID or Salary. Please enter valid numbers.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                Date dateOfBirth = util.parseDate(dobInput);
                if (dateOfBirth == null) {
                    JOptionPane.showMessageDialog(null, "Invalid date format! Use yyyy-MM-dd HH:mm.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                Doctor doctor = new Doctor(name, dateOfBirth, username, password, salary, specialty);
                doctor.setEmpId(empId);
                hms.addEmployee(doctor);
                JOptionPane.showMessageDialog(null, "Doctor updated successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                break; // Exit loop if successfully updated
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void addPatient() {
        JTextField nameField = new JTextField();
        JTextField dateOfBirthField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Date of Birth (yyyy-MM-dd HH:mm):"));
        panel.add(dateOfBirthField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Add Patient",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                String dateOfBirthStr = dateOfBirthField.getText().trim();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (name.isEmpty() || dateOfBirthStr.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                Date dateOfBirth = util.parseDate(dateOfBirthStr);
                if (dateOfBirth == null) {
                    JOptionPane.showMessageDialog(null, "Invalid date format! Use yyyy-MM-dd HH:mm.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                Patient patient = new Patient(name, dateOfBirth, username, password);
                hms.addPatient(patient);
                JOptionPane.showMessageDialog(null, "Patient added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                break; // Exit loop if successfully added
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void updatePatient() {
        JTextField pidField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Patient ID:"));
        panel.add(pidField);
        panel.add(new JLabel("New Name:"));
        panel.add(nameField);
        panel.add(new JLabel("New DOB (yyyy-MM-dd HH:mm):"));
        panel.add(dobField);
        panel.add(new JLabel("New Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("New Password:"));
        panel.add(passwordField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Update Patient",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String pidInput = pidField.getText().trim();
                String name = nameField.getText().trim();
                String dobInput = dobField.getText().trim();
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();

                if (pidInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Patient ID cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                if (name.isEmpty() || dobInput.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                try {
                    int pid = Integer.parseInt(pidInput);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date dob = dateFormat.parse(dobInput);

                    Patient patient = new Patient(name, dob, username, password);
                    patient.setpId(pid);
                    hms.updatePatient(patient);
                    JOptionPane.showMessageDialog(null, "Patient updated successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    break; // Exit loop if successfully updated
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Patient ID. Please enter a valid number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, "Invalid DOB format. Please use yyyy-MM-dd HH:mm.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error updating patient. Please try again later.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void updatePersonalInfo(String name, String username, String password) {
        int pid = session.getId();
        // Use the provided values for name, username, and password
        Date dateOfBirth = new Date(); // You may want to improve this part if you have a better way to get the DOB
        Patient patient = new Patient(name, dateOfBirth, username, password);
        patient.setpId(pid);
        hms.updatePatient(patient);
        System.out.println("Patient updated successfully!");
    }

    private static void addWard() {
        JTextField nameField = new JTextField();
        JTextField capacityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Capacity:"));
        panel.add(capacityField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Add Ward",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String capacityInput = capacityField.getText().trim();
                if (capacityInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Capacity cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                try {
                    int capacity = Integer.parseInt(capacityInput);
                    Ward ward = new Ward(name, capacity);
                    hms.addWard(ward);
                    JOptionPane.showMessageDialog(null, "Ward added successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    break; // Exit loop if successfully added
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid capacity. Please enter a valid number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void updateWard() {
        JTextField wardIdField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField capacityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Ward ID:"));
        panel.add(wardIdField);
        panel.add(new JLabel("New Name:"));
        panel.add(nameField);
        panel.add(new JLabel("New Capacity:"));
        panel.add(capacityField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Update Ward",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String wardIdInput = wardIdField.getText().trim();
                if (wardIdInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ward ID cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                int wardId;
                try {
                    wardId = Integer.parseInt(wardIdInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Ward ID. Please enter a valid number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String capacityInput = capacityField.getText().trim();
                if (capacityInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Capacity cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                try {
                    int capacity = Integer.parseInt(capacityInput);
                    Ward ward = new Ward(name, capacity);
                    ward.setWardId(wardId);
                    hms.updateWard(ward);
                    JOptionPane.showMessageDialog(null, "Ward updated successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    break; // Exit loop if successfully updated
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid capacity. Please enter a valid number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void addAdministrator() {
        JTextField nameField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField adminLevelField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Date of Birth (yyyy-MM-dd HH:mm):"));
        panel.add(dobField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Salary:"));
        panel.add(salaryField);
        panel.add(new JLabel("Administrator Level:"));
        panel.add(adminLevelField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Add Administrator",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String dob = dobField.getText().trim();
                if (dob.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Date of Birth cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String username = usernameField.getText().trim();
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String password = passwordField.getText().trim();
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Password cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String salaryInput = salaryField.getText().trim();
                if (salaryInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Salary cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String adminLevelInput = adminLevelField.getText().trim();
                if (adminLevelInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Administrator Level cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                int salary, adminLevel;
                try {
                    salary = Integer.parseInt(salaryInput);
                    adminLevel = Integer.parseInt(adminLevelInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input for Salary or Administrator Level.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                Administrator administrator = new Administrator(name, util.parseDate(dob), username, password, salary,
                        adminLevel);
                hms.addEmployee(administrator);
                JOptionPane.showMessageDialog(null, "Administrator added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                break; // Exit loop if successfully added
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void updateAdministrator() {
        JTextField empIdField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField adminLevelField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Employee ID to be updated:"));
        panel.add(empIdField);
        panel.add(new JLabel("New Name:"));
        panel.add(nameField);
        panel.add(new JLabel("New Date of Birth (yyyy-MM-dd HH:mm):"));
        panel.add(dobField);
        panel.add(new JLabel("New Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("New Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("New Salary:"));
        panel.add(salaryField);
        panel.add(new JLabel("New Administrator Level:"));
        panel.add(adminLevelField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Update Administrator",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String empIdInput = empIdField.getText().trim();
                if (empIdInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Employee ID cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                int empId;
                try {
                    empId = Integer.parseInt(empIdInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String dob = dobField.getText().trim();
                if (dob.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Date of Birth cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String username = usernameField.getText().trim();
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String password = passwordField.getText().trim();
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Password cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String salaryInput = salaryField.getText().trim();
                if (salaryInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Salary cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String adminLevelInput = adminLevelField.getText().trim();
                if (adminLevelInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Administrator Level cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                int salary, adminLevel;
                try {
                    salary = Integer.parseInt(salaryInput);
                    adminLevel = Integer.parseInt(adminLevelInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input for Salary or Administrator Level.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                Administrator administrator = new Administrator(name, util.parseDate(dob), username, password, salary,
                        adminLevel);
                administrator.setEmpId(empId);
                hms.addEmployee(administrator);
                JOptionPane.showMessageDialog(null, "Administrator updated successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                break; // Exit loop if successfully updated
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void removePatient() {
        JTextField pidField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Patient ID:"));
        panel.add(pidField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Remove Patient",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String pidInput = pidField.getText().trim();
                if (pidInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Patient ID cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                try {
                    int pid = Integer.parseInt(pidInput);
                    int option = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to remove Patient with ID: " + pid + "?", "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        hms.removePatient(pid);
                        JOptionPane.showMessageDialog(null, "Patient with ID " + pid + " removed successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Patient removal canceled!", "Canceled",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    break; // Exit loop if successfully removed or canceled
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Patient ID. Please enter a valid number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error removing Patient. Please try again later.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void removeWard() {
        JTextField wardIdField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Ward ID:"));
        panel.add(wardIdField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Remove Ward",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String wardIdInput = wardIdField.getText().trim();
                if (wardIdInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ward ID cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                try {
                    int wardId = Integer.parseInt(wardIdInput);
                    hms.removeWard(wardId);
                    JOptionPane.showMessageDialog(null, "Ward removed successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    break; // Exit loop if successfully removed
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Ward ID. Please enter a valid number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error removing Ward. Please try again later.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void removeEmployee() {
        JTextField idField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Employee ID:"));
        panel.add(idField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Remove Employee",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String idInput = idField.getText().trim();
                if (!idInput.isEmpty()) {
                    try {
                        int id = Integer.parseInt(idInput);
                        int option = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to remove Employee with ID: " + id + "?", "Confirmation",
                                JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            hms.removeEmployee(id);
                            JOptionPane.showMessageDialog(null, "Employee with ID " + id + " removed successfully!",
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Employee removal canceled!", "Canceled",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Employee ID. Please enter a valid number.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error removing Employee. Please try again later.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Employee ID cannot be empty. Please enter a valid Employee ID.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void createAppointment() {
        JTextField docIdField = new JTextField();
        JTextField patientIdField = new JTextField();
        JTextField startTimeField = new JTextField();
        JTextField endTimeField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Doctor ID:"));
        panel.add(docIdField);
        panel.add(new JLabel("Patient ID:"));
        panel.add(patientIdField);
        panel.add(new JLabel("Start Time (yyyy-MM-dd HH:mm):"));
        panel.add(startTimeField);
        panel.add(new JLabel("End Time (yyyy-MM-dd HH:mm):"));
        panel.add(endTimeField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Create Appointment",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String docIdInput = docIdField.getText().trim();
                if (docIdInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Doctor ID cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                int docId;
                try {
                    docId = Integer.parseInt(docIdInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Doctor ID. Please enter a valid number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String patientIdInput = patientIdField.getText().trim();
                if (patientIdInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Patient ID cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                int patientId;
                try {
                    patientId = Integer.parseInt(patientIdInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Patient ID. Please enter a valid number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String startTimeInput = startTimeField.getText().trim();
                if (startTimeInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Start Time cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String endTimeInput = endTimeField.getText().trim();
                if (endTimeInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "End Time cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                Date startTime, endTime;
                try {
                    startTime = util.parseDateTime(startTimeInput);
                    endTime = util.parseDateTime(endTimeInput);
                    hms.createAppointment(docId, patientId, startTime, endTime);
                    JOptionPane.showMessageDialog(null, "Appointment Created Successfully", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    break; // Exit loop if successfully created
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error Creating Appointment. Please try again later.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void removeAppointment() {
        JTextField aptIdField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Appointment ID:"));
        panel.add(aptIdField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Remove Appointment",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String aptIdInput = aptIdField.getText().trim();
                if (aptIdInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Appointment ID cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                int aptId;
                try {
                    aptId = Integer.parseInt(aptIdInput);
                    hms.cancelAppointment(aptId);
                    JOptionPane.showMessageDialog(null, "Appointment Removed Successfully", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    break; // Exit loop if successfully removed
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Appointment ID. Please enter a valid number.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error Removing Appointment. Please try again later.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void updateAppointment() {
        JTextField aptIdField = new JTextField();
        JTextField startTimeField = new JTextField();
        JTextField endTimeField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Appointment ID:"));
        panel.add(aptIdField);
        panel.add(new JLabel("New Start Time (yyyy-MM-dd HH:mm):"));
        panel.add(startTimeField);
        panel.add(new JLabel("New End Time (yyyy-MM-dd HH:mm):"));
        panel.add(endTimeField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Update Appointment",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String aptIdInput = aptIdField.getText().trim();
                if (aptIdInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Appointment ID cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String startTimeInput = startTimeField.getText().trim();
                if (startTimeInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Start Time cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String endTimeInput = endTimeField.getText().trim();
                if (endTimeInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "End Time cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                int aptId;
                try {
                    aptId = Integer.parseInt(aptIdInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Appointment ID. Please enter a valid number.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                Date startTime, endTime;
                try {
                    startTime = util.parseDate(startTimeInput);
                    endTime = util.parseDate(endTimeInput);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid date format. Please enter dates in the format yyyy-MM-dd HH:mm.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                try {
                    hms.rescheduleAppointment(aptId, startTime, endTime);
                    JOptionPane.showMessageDialog(null, "Appointment Updated Successfully", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    break; // Exit loop if successfully updated
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error Updating Appointment. Please try again later.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void admitPatient() {
        JTextField patientIdField = new JTextField();
        JTextField wardIdField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Patient ID:"));
        panel.add(patientIdField);
        panel.add(new JLabel("Ward ID:"));
        panel.add(wardIdField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Admit Patient",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String patientIdInput = patientIdField.getText().trim();
                if (patientIdInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Patient ID cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                String wardIdInput = wardIdField.getText().trim();
                if (wardIdInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ward ID cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                try {
                    int patientId = Integer.parseInt(patientIdInput);
                    int wardId = Integer.parseInt(wardIdInput);
                    hms.admitPatient(patientId, wardId);
                    JOptionPane.showMessageDialog(null, "Patient admitted successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    break; // Exit loop if successfully admitted
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // User canceled the operation
                break;
            }
        }
    }

    private static void dischargePatient() {
        JTextField admissionIdField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Admission ID:"));
        panel.add(admissionIdField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Discharge Patient",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String admissionIdInput = admissionIdField.getText().trim();
                if (admissionIdInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Admission ID cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Retry
                }

                try {
                    int admissionId = Integer.parseInt(admissionIdInput);
                    hms.dischargePatient(admissionId);
                    JOptionPane.showMessageDialog(null, "Patient discharged successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    break; // Exit loop if successfully discharged
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // User canceled the operation
                break;
            }
        }
    }
}