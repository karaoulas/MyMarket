package api;
import org.junit.Assert;
import org.junit.Test;


public class AdminTest {
    @Test
    public void testAdminConstructor() {
        // Δημιουργία αντικειμένου Admin
        Admin admin = new Admin("adminUser", "securePassword");

        // Έλεγχος τιμών username και password
        Assert.assertEquals("adminUser", admin.getUsername());

    }

    @Test
    public void testToString() {
        // Δημιουργία αντικειμένου Admin
        Admin admin = new Admin("adminUser", "securePassword");

        // Έλεγχος της εξόδου της μεθόδου toString
        Assert.assertEquals("Admin adminUser", admin.toString());
    }
}
