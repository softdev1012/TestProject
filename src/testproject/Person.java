package testproject;
import java.util.Date;

import testproject.Role;

/**
 * The Person class represents a person in the system and it's super class.
 * It contains attributes such as name, date of birth, username, and password.
 * This class provides getter and setter methods for each attribute.
 */

public class Person {
    private String name;
    private Date dateOfBirth;
    private String userName;
    private String password;

    // Constructor
    public Person(String name, Date dateOfBirth, String userName, String password) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.userName = userName;
        this.password = password;
    }

    // Getter and Setter methods for each attribute

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return Role.Patient;
    }
}
