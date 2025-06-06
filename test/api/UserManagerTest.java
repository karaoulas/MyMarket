package api;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class UserManagerTest {

    private UserManager userManager;

    @Before
    public void setUp() {
        userManager = new UserManager();
    }

    @Test
    public void testAddUser() {
        assertTrue("Expected user to exist after being added, but it does not.", userManager.userExists("newUser"));
    }

    @Test
    public void testAddExistingUser() {
        userManager.addUser("existingUser", "password123");
        boolean addedAgain = userManager.addUser("existingUser", "password123");
        assertFalse("Expected user not to be added again, but it was.", addedAgain);
    }

    @Test
    public void testUserExists() {
        userManager.addUser("testUser", "testPassword");
        assertTrue("Expected user to exist, but it does not.", userManager.userExists("testUser"));
        assertFalse("Expected non-existent user to not exist, but it does.", userManager.userExists("nonExistentUser"));
    }

    @Test
    public void testAuthenticateUser() {
        userManager.addUser("authUser", "securePassword");
        User user = userManager.authenticateUser("authUser", "securePassword");
        assertNotNull("Expected authentication to succeed, but it failed.", user);
        assertEquals("Expected authenticated username to match, but it does not.", "authUser", user.getUsername());
    }

    @Test
    public void testAuthenticateInvalidUser() {
        User user = userManager.authenticateUser("invalidUser", "wrongPassword");
        assertNull("Expected authentication to fail for invalid user, but it succeeded.", user);
    }

    @Test
    public void testInitializeDefaultUsers() {
        assertTrue("Expected default admin1 to exist, but it does not.", userManager.userExists("admin1"));
        assertTrue("Expected default user1 to exist, but it does not.", userManager.userExists("user1"));
    }
}
