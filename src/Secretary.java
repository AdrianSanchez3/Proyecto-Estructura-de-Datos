
public class Secretary {

    private String id;
    private String name;

    public Secretary(String id, String name) {
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
        return "Secretary ID: " + id +
               "\nName: " + name;
    }
}
