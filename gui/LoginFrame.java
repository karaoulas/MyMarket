package gui;
import api.*;
import javax.swing.*;


public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private UserManager userManager;
    private ProductManager productManager;

    public LoginFrame(UserManager userManager,ProductManager productManager) {
        this.userManager = userManager;
        this.productManager=productManager;
        initialize();
    }

    private void initialize() {
        setTitle("Σύνδεση Χρήστη");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        JButton loginButton = new JButton("Σύνδεση");
        loginButton.setBounds(150, 110, 100, 25);
        add(loginButton);

        JButton registerButton = new JButton("Εγγραφή");
        registerButton.setBounds(250, 110, 100, 25);
        add(registerButton);

        messageLabel = new JLabel("");
        messageLabel.setBounds(90, 140, 300, 25);
        add(messageLabel);

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> register());
    }
    //Μέθοδος σύνδεσης χρήστη/διαχειριστή
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = userManager.authenticateUser(username, password);
        if (user != null) {
            if (user instanceof Admin) {
                messageLabel.setText("Σύνδεση ως Διαχειριστής");
                new AdminFrame(user,productManager).setVisible(true);
            } else if (user instanceof Customer) {
                messageLabel.setText("Σύνδεση ως Πελάτης");
                new CustomerFrame((Customer) user,productManager).setVisible(true);
            }
            this.dispose();
        } else {
            messageLabel.setText("Λάθος όνομα χρήστη ή κωδικός");
        }


    }
    //Δημιουργία Φόρμας Εγγραφής
    private void register(){
        RegisterFrame registerFrame;
        registerFrame = new RegisterFrame(userManager);
        registerFrame.setVisible(true);
        this.dispose();
    }
}
