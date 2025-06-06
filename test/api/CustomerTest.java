package api;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class CustomerTest {

    private Customer customer;
    private Order order1;
    private Order order2;

    @Before
    public void setUp() {
        customer = new Customer("testUser", "password123");

        List<Product> products1 = Arrays.asList(
                new Product("Apple", "Fresh apple", "Fruits", "Fresh", 1.2, 3, "kg"),
                new Product("Banana", "Fresh banana", "Fruits", "Fresh", 0.8, 2, "kg")
        );
        order1 = new Order(products1);

        List<Product> products2 = Arrays.asList(
                new Product("Orange", "Fresh orange", "Fruits", "Fresh", 1.5, 4, "kg")
        );
        order2 = new Order(products2);
    }

    @Test
    public void testAddOrder() {
        customer.addOrder(order1);
        customer.addOrder(order2);

        List<Order> orderHistory = customer.getOrderHistory();
        assertEquals(2, orderHistory.size());
        assertTrue(orderHistory.contains(order1));
        assertTrue(orderHistory.contains(order2));
    }

    @Test
    public void testGetOrderHistory() {
        customer.addOrder(order1);
        List<Order> orderHistory = customer.getOrderHistory();

        assertNotNull(orderHistory);
        assertEquals(1, orderHistory.size());
        assertEquals(order1, orderHistory.get(0));
    }

    @Test
    public void testSetOrderHistory() {
        List<Order> newHistory = Arrays.asList(order1, order2);
        customer.setOrderHistory(newHistory);

        List<Order> orderHistory = customer.getOrderHistory();
        assertEquals(2, orderHistory.size());
        assertEquals(order1, orderHistory.get(0));
        assertEquals(order2, orderHistory.get(1));
    }

    @Test
    public void testToString() {
        assertEquals("Customer testUser", customer.toString());
    }
}

