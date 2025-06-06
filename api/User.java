package api;
import java.io.Serializable;


public abstract class User implements Serializable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    //Ταυτοποίηση του χρήστη με βάση τον κωδικό
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Username: " + username;
    }
}

