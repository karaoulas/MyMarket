/*package gui;
import api.UserManager;
import javax.swing.*;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private UserManager userManager;

    public RegisterFrame(UserManager userManager) {
        this.userManager = userManager;
        initialize();
    }

    private void initialize() {
        setTitle("Εγγραφή Χρήστη");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Όνομα Χρήστη:");
        usernameLabel.setBounds(50, 30, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 30, 150, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Κωδικός:");
        passwordLabel.setBounds(50, 70, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 70, 150, 25);
        add(passwordField);

        JButton registerButton = new JButton("Εγγραφή");
        registerButton.setBounds(150, 110, 100, 25);
        add(registerButton);

        messageLabel = new JLabel("");
        messageLabel.setBounds(90, 140, 300, 25);
        add(messageLabel);

        registerButton.addActionListener(e -> registerUser());
    }
    //Μέθοδος για την εγγραφή χρήστη
    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Παρακαλώ εισάγετε όνομα και κωδικό.");
            return;
        }

        if (userManager.userExists(username)) {
            messageLabel.setText("Το όνομα χρήστη υπάρχει ήδη.");
        } else {
            boolean success = userManager.addUser(username, password);//Προσθήκη του νέου χρήστη στο αρχείο χρηστών
            if (success) {
                System.out.println("Επιτυχής εγγραφή! Μπορείτε να συνδεθείτε.");
                // κλείσιμο της φόρμας μετά την εγγραφή
                dispose();
            } else {
                System.out.println("Σφάλμα κατά την εγγραφή. Δοκιμάστε ξανά.");
            }
        }
    }
}*/
package gui;
import api.UserManager;
import javax.swing.*;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JLabel messageLabel;
    private UserManager userManager;

    public RegisterFrame(UserManager userManager) {
        this.userManager = userManager;
        initialize();
    }

    private void initialize() {
        setTitle("Εγγραφή Χρήστη");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel firstNameLabel = new JLabel("Όνομα:");
        firstNameLabel.setBounds(50, 30, 100, 25);
        add(firstNameLabel);

        firstNameField = new JTextField();
        firstNameField.setBounds(150, 30, 150, 25);
        add(firstNameField);

        JLabel lastNameLabel = new JLabel("Επώνυμο:");
        lastNameLabel.setBounds(50, 70, 100, 25);
        add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(150, 70, 150, 25);
        add(lastNameField);

        JLabel usernameLabel = new JLabel("Όνομα Χρήστη:");
        usernameLabel.setBounds(50, 110, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 110, 150, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Κωδικός:");
        passwordLabel.setBounds(50, 150, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 150, 25);
        add(passwordField);

        JButton registerButton = new JButton("Εγγραφή");
        registerButton.setBounds(150, 190, 100, 25);
        add(registerButton);

        messageLabel = new JLabel("");
        messageLabel.setBounds(90, 230, 300, 25);
        add(messageLabel);

        registerButton.addActionListener(e -> registerUser());
    }

    private void registerUser() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Παρακαλώ συμπληρώστε όλα τα πεδία.");
            return;
        }

        if (userManager.userExists(username)) {
            messageLabel.setText("Το όνομα χρήστη υπάρχει ήδη.");
        } else {
            boolean success = userManager.addUser(username, password); // Προσαρμογή του UserManager
            if (success) {
                System.out.println("Επιτυχής εγγραφή! Μπορείτε να συνδεθείτε.");
                dispose();
            } else {
                System.out.println("Σφάλμα κατά την εγγραφή. Δοκιμάστε ξανά.");
            }
        }
    }
}




