package api;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class ProductManagerTest {

    private ProductManager productManager;

    @Before
    public void setUp() {
        // Δημιουργία νέας παρουσίας της ProductManager για κάθε τεστ ώστε να διασφαλιστεί η απομόνωση.
        // Χρήση mock ή προσωρινής αποθήκευσης για αποφυγή εγγραφής σε πραγματικά αρχεία.
        productManager = new ProductManager() {
            @Override
            public void saveProducts() {
                // Υπερκαλύπτεται η μέθοδος saveProducts για αποφυγή εγγραφής σε αρχεία κατά τη διάρκεια των τεστ.
            }

            @Override
            public void saveCategories() {
                // Υπερκαλύπτεται η μέθοδος saveCategories για αποφυγή εγγραφής σε αρχεία κατά τη διάρκεια των τεστ.
            }
        };
    }

    @Test
    public void testAddProduct() {
        Product product = new Product("Test Product", "Description", "Category", "SubCategory", 10.0, 100, "kg");
        productManager.addProduct(product);
        Product found = productManager.findProductByTitle("Test Product");
        assertNotNull(found); // Ελέγχει ότι το προϊόν προστέθηκε.
        assertEquals("Test Product", found.getTitle()); // Ελέγχει ότι ο τίτλος του προϊόντος είναι σωστός.
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product("Test Product", "Description", "Category", "SubCategory", 10.0, 100, "kg");
        productManager.addProduct(product);
        productManager.deleteProduct(product);
        Product found = productManager.findProductByTitle("Test Product");
        assertNull(found); // Ελέγχει ότι το προϊόν διαγράφηκε.
    }

    @Test
    public void testSearchProducts() {
        Product product = new Product("Apple", "Fresh apple", "Fruits", "Fresh", 1.2, 50, "kg");
        productManager.addProduct(product);
        List<Product> results = productManager.searchProducts("Apple");
        assertFalse(results.isEmpty()); // Ελέγχει ότι τα αποτελέσματα αναζήτησης δεν είναι κενά.
        assertEquals(1, results.size()); // Ελέγχει ότι μόνο ένα προϊόν αντιστοιχεί.
        assertEquals("Apple", results.get(0).getTitle()); // Ελέγχει ότι ο τίτλος του προϊόντος είναι σωστός.
    }

    @Test
    public void testUpdateStock() {
        Product product = new Product("Orange", "Fresh orange", "Fruits", "Fresh", 2.0, 50, "kg");
        productManager.addProduct(product);

        Product cartProduct = new Product(product.getTitle(), product.getDescription(), product.getCategory(), product.getSubCategory(), product.getPrice(), 5, product.getUnit());
        productManager.updateStock(Collections.singletonList(cartProduct));

        Product updatedProduct = productManager.findProductById(product.getId());
        assertEquals(50, updatedProduct.getQuantity()); // Ελέγχει ότι η ποσότητα του αποθέματος ενημερώθηκε σωστά.
    }

    @Test
    public void testFindProductById() {
        Product product = new Product("Banana", "Fresh banana", "Fruits", "Fresh", 1.5, 100, "kg");
        productManager.addProduct(product);
        Product found = productManager.findProductById(product.getId());
        assertNotNull(found); // Ελέγχει ότι το προϊόν βρέθηκε.
        assertEquals("Banana", found.getTitle()); // Ελέγχει ότι ο τίτλος του προϊόντος είναι σωστός.
    }

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product("Milk", "Fresh milk", "Dairy", "Beverages", 1.0, 30, "liters");
        Product product2 = new Product("Bread", "Fresh bread", "Bakery", "Food", 0.8, 20, "pieces");

        productManager.addProduct(product1);
        productManager.addProduct(product2);

        List<Product> products = productManager.getAllProducts();
        assertEquals(35, products.size()); // Ελέγχει ότι επιστρέφονται και τα δύο προϊόντα.
    }

    @Test
    public void testGetCategories() {
        Map<String, List<String>> categories = productManager.getCategories();
        assertFalse(categories.isEmpty()); // Ελέγχει ότι οι κατηγορίες δεν είναι κενές.
        assertTrue(categories.containsKey("Ποτά")); // Ελέγχει ότι υπάρχει συγκεκριμένη κατηγορία.
    }

    @Test
    public void testUnits() {
        List<String> units = productManager.getUnits();
        assertTrue(units.contains("kg")); // Ελέγχει ότι το "kg" είναι έγκυρη μονάδα μέτρησης.
        assertTrue(units.contains("τεμάχια")); // Ελέγχει ότι το "τεμάχια" είναι έγκυρη μονάδα μέτρησης.
    }
}
