
public class Doctor {

    private String id;
    private String name;
    private String specialty;
    private boolean available;

    public Doctor(String id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Doctor ID: " + id +
               "\nName: " + name +
               "\nSpecialty: " + specialty +
               "\nAvailable: " + available;
    }
}
