package api;
import org.junit.Assert;
import org.junit.Test;




public class UserTest {
    @Test
    public void testUserConstructor() {
        // Δημιουργία αντικειμένου User
        User user = new User("testUser", "testPass") {
        };

        // Έλεγχος τιμών username
        Assert.assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testAuthenticate() {
        // Δημιουργία αντικειμένου User
        User user = new User("testUser", "testPass") {
        };

        // Έλεγχος authentication
        Assert.assertTrue("Ο κωδικός είναι σωστός, αλλά η ταυτοποίηση απέτυχε.", user.authenticate("testPass"));
        Assert.assertFalse("Η ταυτοποίηση δεν έπρεπε να επιτύχει με λάθος κωδικό.", user.authenticate("wrongPass"));
    }

    @Test
    public void testUserToString() {
        // Δημιουργία αντικειμένου User
        User user = new User("testUser", "testPass") {
        };

        // Έλεγχος της εξόδου της μεθόδου toString
        Assert.assertEquals("Username: testUser", user.toString());
    }
}


