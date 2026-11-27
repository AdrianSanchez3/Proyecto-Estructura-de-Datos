
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class EmployeeManager {

    // Store employees by their unique ID
    private HashMap<String, Object> employees;

    // Store doctors separately for easy access
    private ArrayList<Doctor> doctors;

    // Keep track of registered doctor IDs
    private HashSet<String> doctorIds;

    public EmployeeManager() {
        employees = new HashMap<>();
        doctors = new ArrayList<>();
        doctorIds = new HashSet<>();
    }

    // Register a doctor
    public boolean addDoctor(Doctor doctor) {
        if (doctor == null || employees.containsKey(doctor.getId())) {
            return false;
        }

        employees.put(doctor.getId(), doctor);
        doctors.add(doctor);
        doctorIds.add(doctor.getId());
        return true;
    }

    // Register a nurse
    public boolean addNurse(Nurse nurse) {
        if (nurse == null || employees.containsKey(nurse.getId())) {
            return false;
        }

        employees.put(nurse.getId(), nurse);
        return true;
    }

    // Register a secretary
    public boolean addSecretary(Secretary secretary) {
        if (secretary == null || employees.containsKey(secretary.getId())) {
            return false;
        }

        employees.put(secretary.getId(), secretary);
        return true;
    }

    // Search for any employee by ID
    public Object findEmployeeById(String id) {
        return employees.get(id);
    }

    // Search for a doctor by ID
    public Doctor findDoctorById(String id) {
        Object employee = employees.get(id);

        if (employee instanceof Doctor) {
            return (Doctor) employee;
        }

        return null;
    }

    // Search doctors by specialty
    public ArrayList<Doctor> findDoctorsBySpecialty(String specialty) {
        ArrayList<Doctor> results = new ArrayList<>();

        if (specialty == null) {
            return results;
        }

        for (Doctor doctor : doctors) {
            if (doctor.getSpecialty().equalsIgnoreCase(specialty)) {
                results.add(doctor);
            }
        }

        return results;
    }

    // Get all available doctors
    public ArrayList<Doctor> getAvailableDoctors() {
        ArrayList<Doctor> available = new ArrayList<>();

        for (Doctor doctor : doctors) {
            if (doctor.isAvailable()) {
                available.add(doctor);
            }
        }

        return available;
    }

    // Check whether a doctor is available
    public boolean isDoctorAvailable(String id) {
        Doctor doctor = findDoctorById(id);
        return doctor != null && doctor.isAvailable();
    }

    // Update doctor availability
    public boolean setDoctorAvailability(String id, boolean available) {
        Doctor doctor = findDoctorById(id);

        if (doctor == null) {
            return false;
        }

        doctor.setAvailable(available);
        return true;
    }

    // Remove a doctor
    public boolean removeDoctor(String id) {
        Doctor doctor = findDoctorById(id);

        if (doctor == null) {
            return false;
        }

        employees.remove(id);
        doctors.remove(doctor);
        doctorIds.remove(id);
        return true;
    }

    // Display all registered doctors
    public void showAllDoctors() {
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
            System.out.println("--------------------");
         }}}
