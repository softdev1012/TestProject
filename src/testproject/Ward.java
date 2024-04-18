package testproject;
import java.util.ArrayList;

/**
 * The Ward class represents a hospital ward.
 * It stores information about the ward's ID, name, capacity, occupancy, and admissions.
 */

public class Ward {
    private static int nextWardId = 1; 
    private int wardId;
    private String name;
    private int capacity;
    private int occupancy;
    private ArrayList<Admission> admissions;

    // Constructor
    public Ward(String name, int capacity) {
        this.wardId = nextWardId++;
        this.name = name;
        this.capacity = capacity;
        this.occupancy = 0; // Initialize occupancy to 0 when the ward is created
        admissions = new ArrayList<Admission>();
    }

    // Getter and setter methods of all attributes

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void addAdmission(Admission adm) {
        if (occupancy < capacity) {
            occupancy++;
            admissions.add(adm);
        } else {
            throw new IllegalArgumentException("Ward Capacity Reached");
        }
    }

    public void removeAdmission(Admission adm) {
        if (occupancy > 0) {
            occupancy--;
            admissions.add(adm);
        } else {
            throw new IllegalArgumentException("Ward already Empty");
        }
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + wardId + '\'' +
                ", name='" + name + '\'' +
                ", capacity='" + capacity + '\'' +
                ", occupancy='" + occupancy + '\'' +
                '}';
    }
}
