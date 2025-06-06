package api;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static Map<String, User> users; // Χάρτης που αποθηκεύει τους χρήστες με βάση το όνομα χρήστη
    private static final String FILE_PATH = "users.txt"; // Αρχείο αποθήκευσης δεδομένων χρηστών

    public UserManager() {
        users = new HashMap<>();
        loadUsers();
        initializeDefaultUsers();//Δημιουργεί προκαθορισμένους χρήστες
        saveUsers();
    }

    // Μέθοδος για την προσθήκη νέου χρήστη
    public boolean addUser(String username,String password) {
        if (userExists(username)) {
            return false; // Το όνομα χρήστη ήδη υπάρχει
        }
        Customer newCustomer = new Customer(username, password);
        users.put(username, newCustomer);
        saveUsers(); // Αποθήκευση των αλλαγών στο αρχείο
        return true;
    }

    // Μέθοδος για έλεγχο αν υπάρχει ήδη ο χρήστης
    public  boolean userExists(String username) {
        return users.containsKey(username);
    }


    // Μέθοδος για τη φόρτωση των χρηστών από το αρχείο
    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (Map<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Το αρχείο χρηστών δεν βρέθηκε. Δημιουργία νέου αρχείου.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Σφάλμα κατά τη φόρτωση των χρηστών: " + e.getMessage());
        }
    }

    // Μέθοδος για την αποθήκευση των χρηστών στο αρχείο
    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την αποθήκευση των χρηστών: " + e.getMessage());
        }
    }
    // Μέθοδος για την προσθήκη προκαθορισμένων χρηστών (διαχειριστών και πελατών)
    private void initializeDefaultUsers() {
        // Προσθήκη προκαθορισμένων διαχειριστών και πελατων
        if (users.isEmpty()) {
            users.put("admin1", new Admin("admin1", "password1"));
            users.put("admin2", new Admin("admin2", "password2"));
            users.put("user1", new Customer("user1", "password1"));
            users.put("user2", new Customer("user2", "password2"));
        }
    }

    // Μέθοδος ελέγχου πιστοποίησης
    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            return user;
        }
        return null;
    }
}



