
public class TestEmployees {

    public static void main(String[] args) {

        EmployeeManager manager = new EmployeeManager();

        // Create doctors
        Doctor doctor1 = new Doctor("D001", "Carlos Rivera", "General");
        Doctor doctor2 = new Doctor("D002", "Maria Lopez", "Pediatric");
        Doctor doctor3 = new Doctor("D003", "Jose Perez", "Retina");
        Doctor doctor4 = new Doctor("D004", "Ana Torres", "Neuro-Ophthalmology");

        // Create nurses and secretaries
        Nurse nurse1 = new Nurse("N001", "Laura Diaz");
        Secretary secretary1 = new Secretary("S001", "Pedro Garcia");

        // Register employees
        System.out.println("=== REGISTER EMPLOYEES ===");
        System.out.println(manager.addDoctor(doctor1));
        System.out.println(manager.addDoctor(doctor2));
        System.out.println(manager.addDoctor(doctor3));
        System.out.println(manager.addDoctor(doctor4));
        System.out.println(manager.addNurse(nurse1));
        System.out.println(manager.addSecretary(secretary1));

        // Test duplicate ID
        System.out.println("\n=== DUPLICATE ID TEST ===");
        System.out.println(manager.addDoctor(
            new Doctor("D001", "Duplicate Doctor", "General")
        ));

        // Search employee by ID
        System.out.println("\n=== SEARCH EMPLOYEE BY ID ===");
        System.out.println(manager.findEmployeeById("N001"));

        // Search doctor by specialty
        System.out.println("\n=== SEARCH BY SPECIALTY ===");
        for (Doctor doctor : manager.findDoctorsBySpecialty("General")) {
            System.out.println(doctor);
        }

        // Show available doctors
        System.out.println("\n=== AVAILABLE DOCTORS ===");
        for (Doctor doctor : manager.getAvailableDoctors()) {
            System.out.println(doctor.getName());
        }

        // Change doctor availability
        System.out.println("\n=== UPDATE AVAILABILITY ===");
        manager.setDoctorAvailability("D001", false);
        System.out.println("D001 available: "
            + manager.isDoctorAvailable("D001"));

        // Remove doctor
        System.out.println("\n=== REMOVE DOCTOR ===");
        System.out.println(manager.removeDoctor("D003"));
        System.out.println("D003 exists: "
            + (manager.findDoctorById("D003") != null));
    }
}
