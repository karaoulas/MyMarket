package api;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class OrderTest {

    private List<Product> products;

    @Before
    public void setUp() {
        products = new ArrayList<>();
        products.add(new Product("Apple", "Fresh apple", "Fruits", "Fresh", 1.2, 5, "kg"));
        products.add(new Product("Banana", "Fresh banana", "Fruits", "Fresh", 0.8, 10, "kg"));
    }

    @Test
    public void testCalculateTotalCost() {
        Order order = new Order(products);
        double expectedTotalCost = (1.2 * 5) + (0.8 * 10);
        assertEquals(expectedTotalCost, order.getTotalCost(), 0.01);
    }

    @Test
    public void testGetOrderDetails() {
        Order order = new Order(products);
        String details = order.getOrderDetails();

        assertTrue(details.contains("Apple"));
        assertTrue(details.contains("Banana"));
        assertTrue(details.contains("Συνολικό Κόστος Παραγγελίας"));
    }

    @Test
    public void testOrderDate() {
        Order order = new Order(products);
        assertNotNull(order.getOrderDate());
    }
}

