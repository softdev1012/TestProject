package testproject;
import java.util.Date;

import testproject.Role;

/**
 * The Administrator class represents an administrator in the system.
 * It extends the Employee class and adds an adminLevel field to specify the level of administrative.
 */

public class Administrator extends Employee {
    private int adminLevel;

    // Constructor
    public Administrator(String name, Date dateOfBirth, String userName, String password, int salary, int adminLevel) {
        super(name, dateOfBirth, userName, password, salary);
        this.adminLevel = adminLevel;
    }

    // Getter and Setter for adminLevel
    public int getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(int adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Role getRole() {
        return Role.Administrator;
    }

    @Override
    public String toString() {
        return "{" +
                "empId='" + getEmpId() + '\'' +
                ", name='" + getName() + '\'' +
                ", dateOfBirth='" + getDateOfBirth() + '\'' +
                ", role='" + getRole() + '\'' +
                ", salary='" + getSalary() + '\'' +
                ", adminLevel='" + adminLevel + '\'' +
                '}';
    }
}
