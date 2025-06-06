package api;
import org.junit.Test;
import org.junit.Assert;

    public class ProductTest {
        @Test
        public void setAndGet() {
            Product product = new Product("Laptop", "High-end gaming laptop", "Electronics", "Computers", 1500.0, 10, "pcs");
            Assert.assertEquals("Laptop", product.getTitle());
            Assert.assertEquals("High-end gaming laptop", product.getDescription());
            Assert.assertEquals("Electronics", product.getCategory());
            Assert.assertEquals("Computers", product.getSubCategory());
            Assert.assertEquals(1500.0, product.getPrice(), 0.001);
            Assert.assertEquals(10, product.getQuantity());
            Assert.assertEquals("pcs", product.getUnit());
        }

        @Test
        public void testSettersAndGetters() {
            Product product = new Product("Laptop", "High-end gaming laptop", "Electronics", "Computers", 1500.0, 10, "pcs");

            // Αλλαγή τιμών
            product.setTitle("Desktop PC");
            product.setDescription("Powerful desktop for gaming");
            product.setCategory("Hardware");
            product.setSubCategory("Desktops");
            product.setPrice(1200.0);
            product.setQuantity(5);
            product.setUnit("units");

            // Έλεγχος αλλαγμένων τιμών
            Assert.assertEquals("Desktop PC", product.getTitle());
            Assert.assertEquals("Powerful desktop for gaming", product.getDescription());
            Assert.assertEquals("Hardware", product.getCategory());
            Assert.assertEquals("Desktops", product.getSubCategory());
            Assert.assertEquals(1200.0, product.getPrice(), 0.001);
            Assert.assertEquals(5, product.getQuantity());
            Assert.assertEquals("units", product.getUnit());


        }
        @Test
        public void testIncreaseOrderFrequency() {
            Product product = new Product("Laptop", "High-end gaming laptop", "Electronics", "Computers", 1500.0, 10, "pcs");

            // Αύξηση συχνότητας παραγγελίας
            product.increaseOrderFrequency();
            product.increaseOrderFrequency();

            // Έλεγχος συχνότητας παραγγελίας
            Assert.assertEquals(2, product.getOrderFrequency());
        }
        @Test
        public void testToString() {
            Product product = new Product("Laptop", "High-end gaming laptop", "Electronics", "Computers", 1500.0, 10, "pcs");

            // Έλεγχος μορφοποίησης της μεθόδου toString
            String expected = "Product{ID='" + product.getId() + "', Τίτλος='Laptop', Περιγραφή='High-end gaming laptop', Κατηγορία='Electronics', Υποκατηγορία='Computers', Τιμή=1500.0, Ποσότητα=10, Συχνότητα παραγγελίας=0}";
            Assert.assertEquals(expected, product.toString());
        }
    }


