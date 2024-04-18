package testproject;
import testproject.Role;

/**
 * The Session class represents a session in the system.
 * It contains information about the session ID and the role of the user.
 */

public class Session {
    private int id;
    private Role role;

    // Constructor
    public Session(int id, Role role) {
        this.id = id;
        this.role = role;
    }

    // Getter and Setter for all attributes

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
