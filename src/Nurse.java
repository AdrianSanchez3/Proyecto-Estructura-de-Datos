
public class Nurse {

    private String id;
    private String name;

    public Nurse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Nurse ID: " + id +
               "\nName: " + name;
    }
}
