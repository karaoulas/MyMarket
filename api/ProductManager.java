package api;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class ProductManager  {
    private static Map<String, Product> products;
    private static Map<String, List<String>> categories;
    private static final String FILE_PATH1 = "products.txt";
    private static final String FILE_PATH2 = "categories.txt";

    public ProductManager(){
        products=new HashMap<>();
        loadProducts();// Φόρτωση των χρηστών κατά την αρχικοποίηση
        initializeDefaultProducts();//Δημιουργεί προκαθορισμένους χρήστες
        saveProducts();

        categories = new HashMap<>();
        loadCategories();
        initializeDefaultCategories();
        saveCategories();

    }
    // Φόρτωση των προϊόντων
    private void loadProducts(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH1))) {
            products = (Map<String, Product>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Το αρχείο προϊόντων δεν βρέθηκε. Δημιουργία νέου αρχείου.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Σφάλμα κατά τη φόρτωση των προϊόντων: " + e.getMessage());
            
        }
    }
    //Αρχικοποίηση προϊόντων με μοναδικό τυχαίο id για κάθε προϊόν
    private void initializeDefaultProducts(){
        if(products.isEmpty()) {
            products.put(UUID.randomUUID().toString(), new Product("Πορτοκάλια 1kg", "Φρέσκα πορτοκάλια, ιδανικά για χυμό ή κατανάλωση.", "Φρέσκα τρόφιμα", "Φρούτα", 1.20, 200,"kg"));
            products.put(UUID.randomUUID().toString(), new Product("Καρότα 1kg", "Τραγανά καρότα, κατάλληλα για σαλάτες και μαγείρεμα.", "Φρέσκα τρόφιμα", "Λαχανικά", 1.00, 150,"kg"));
            products.put(UUID.randomUUID().toString(), new Product("Φιλέτο Σολομού 300g", "Φρέσκος σολομός φιλέτο έτοιμος για μαγείρεμα.", "Φρέσκα τρόφιμα", "Ψάρια", 12.00, 50,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Κιμάς Μοσχαρίσιος 500g", "Φρέσκος κιμάς μοσχαρίσιος από τοπικό κρεοπωλείο.", "Φρέσκα τρόφιμα", "Κρέατα", 6.50, 100,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Κατεψυγμένες Πίτσες Margherita 400g", "Πίτσα Margherita με φρέσκια σάλτσα ντομάτας και τυρί.", "Κατεψυγμένα τρόφιμα", "Κατεψυγμένες πίτσες", 4.00, 80,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Κατεψυγμένα Γεύματα Σπαγγέτι Μπολονέζ 450g", "Έτοιμο κατεψυγμένο γεύμα σπαγγέτι μπολονέζ.", "Κατεψυγμένα τρόφιμα", "Κατεψυγμένα γεύματα", 3.80, 60,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Γιαούρτι Στραγγιστό 2% Λιπαρά 200g", "Ελαφρύ στραγγιστό γιαούρτι με χαμηλά λιπαρά.", "Προϊόντα ψυγείου", "Γιαούρτια", 1.80, 200,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Γάλα Πλήρες 1L", "Πλήρες γάλα με πλούσια γεύση και θρεπτικά συστατικά.", "Προϊόντα ψυγείου", "Γάλα", 1.30, 250,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Βούτυρο Αγελαδινό 250g", "Βούτυρο από αγελαδινό γάλα για μαγείρεμα και γλυκά.", "Προϊόντα ψυγείου", "Βούτυρο", 2.50, 100,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Σαλάμι Αέρος 200g", "Αυθεντικό σαλάμι αέρος με έντονη γεύση.", "Αλλαντικά", "Σαλάμι", 3.50, 70,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Μπέικον Καπνιστό 200g", "Καπνιστό μπέικον για μαγείρεμα ή σάντουιτς.", "Αλλαντικά", "Μπέικον", 3.20, 90,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Κρασί Ερυθρό Ξηρό 750ml", "Ερυθρό ξηρό κρασί από ελληνικά σταφύλια.", "Αλκοολούχα ποτά", "Κρασί", 7.00, 120,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Ούζο Πλωμαρίου 200ml", "Παραδοσιακό ούζο από τη Λέσβο με γλυκάνισο.", "Αλκοολούχα ποτά", "Ούζο", 5.00, 150,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Τσίπουρο Χωρίς Γλυκάνισο 200ml", "Αυθεντικό τσίπουρο χωρίς γλυκάνισο.", "Αλκοολούχα ποτά", "Τσίπουρο", 6.50, 100,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Αναψυκτικό Coca Cola 1.5L", "Κλασικό αναψυκτικό Coca Cola με ζάχαρη.", "Μη αλκοολούχα ποτά", "Αναψυκτικά", 1.30, 300,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Νερό Μεταλλικό 1.5L", "Φυσικό μεταλλικό νερό από ελληνικές πηγές.", "Μη αλκοολούχα ποτά", "Νερό", 0.50, 500,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Ενεργειακό Ποτό Red Bull 250ml", "Ενεργειακό ποτό με καφεΐνη για άμεση ενέργεια.", "Μη αλκοολούχα ποτά", "Ενεργειακά ποτά", 1.80, 200,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Καθαριστικό Τζαμιών 750ml", "Αποτελεσματικό καθαριστικό για τζάμια και καθρέπτες.", "Καθαριστικά για το σπίτι", "Καθαριστικά για τα τζάμια", 2.50, 180,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Καθαριστικό Κουζίνας 500ml", "Ισχυρό καθαριστικό για επιφάνειες κουζίνας.", "Καθαριστικά για το σπίτι", "Καθαριστικά κουζίνας", 3.00, 150,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Υγρό Πλυντηρίου Ρούχων 3L", "Υγρό πλυντηρίου για έντονα λεκέδες και καθημερινά ρούχα.", "Απορρυπαντικά ρούχων", "Υγρά πλυντηρίου", 6.00, 120,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Μαλακτικό Ρούχων 2L", "Μαλακτικό για ρούχα με φρέσκο άρωμα λουλουδιών.", "Απορρυπαντικά ρούχων", "Μαλακτικά", 3.80, 100,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Μούσλι με Ξηρούς Καρπούς 500g", "Μούσλι με ξηρούς καρπούς για ένα θρεπτικό πρωινό.", "Δημητριακά", "Μούσλι", 3.00, 100,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Βρώμη Ολικής Άλεσης 500g", "Βρώμη ολικής άλεσης για υγιεινά πρωινά και σνακ.", "Δημητριακά", "Βρώμη", 2.20, 120,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Κριθαράκι 500g", "Κλασικό κριθαράκι για γιουβέτσι και παραδοσιακά πιάτα.", "Ζυμαρικά", "Κριθαράκι", 1.00, 150,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Ταλιατέλες 500g", "Φαρδιές ταλιατέλες από σιμιγδάλι για μαγειρικά πιάτα.", "Ζυμαρικά", "Ταλιατέλες", 1.80, 130,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Κράκερς Σίκαλης 200g", "Κράκερς σίκαλης με χαμηλά λιπαρά για σνακ.", "Σνακ", "Κράκερς", 2.00, 140,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Μπάρες Δημητριακών με Σοκολάτα 6x25g", "Μπάρες δημητριακών με επικάλυψη σοκολάτας για ενέργεια.", "Σνακ", "Μπάρες δημητριακών", 3.50, 90,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Ηλιέλαιο 1L", "Ηλιέλαιο ιδανικό για τηγάνισμα και μαγειρική χρήση.", "Έλαια", "Ηλιέλαιο", 2.80, 200,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Σογιέλαιο 1L", "Σογιέλαιο για ελαφριά μαγειρική και σαλάτες.", "Έλαια", "Σογιέλαιο", 3.00, 150,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Κονσέρβα Φασόλια 400g", "Φασόλια σε κονσέρβα έτοιμα για κατανάλωση.", "Κονσέρβες", "Κονσέρβες λαχανικών", 1.80, 100,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Κονσέρβα Ροδάκινα 400g", "Γλυκά ροδάκινα σε σιρόπι, έτοιμα για κατανάλωση.", "Κονσέρβες", "Κονσέρβες φρούτων", 2.00, 80,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Χαρτοπετσέτες 100 τεμάχια", "Απορροφητικές χαρτοπετσέτες για καθημερινή χρήση.", "Χαρτικά", "Χαρτοπετσέτες", 2.50, 300,"τεμάχια"));
            products.put(UUID.randomUUID().toString(), new Product("Χαρτομάντηλα 10x10τμχ", "Χαρτομάντηλα σε ατομική συσκευασία για εύκολη μεταφορά.", "Χαρτικά", "Χαρτομάντηλα", 1.50, 400,"τεμάχια"));

        }
    }
    // Αποθήκευση προϊόντων στο αρχείο
    public void saveProducts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH1))) {
            oos.writeObject(products);
        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την αποθήκευση των προϊόντων: " + e.getMessage());
        }
    }
   // Φόρτωση των κατηγοριών
    private void loadCategories(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH2))) {
            categories = (Map<String, List<String>>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Το αρχείο κατηγοριών δεν βρέθηκε. Δημιουργία νέου αρχείου.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Σφάλμα κατά τη φόρτωση των κατηγοριών: " + e.getMessage());
        }
    }
    //Αρχικοποίηση κατηγοριών
    private void initializeDefaultCategories(){
        if (categories.isEmpty()) {
            categories.put("Ποτά", List.of("Αναψυκτικά", "Χυμοί", "Καφές"));
            categories.put("Τρόφιμα", List.of("Σνακ", "Γαλακτοκομικά", "Ζυμαρικά"));
            categories.put("Φρέσκα τρόφιμα", List.of("Φρούτα", "Λαχανικά", "Ψάρια", "Κρέατα"));
            categories.put("Κατεψυγμένα τρόφιμα", List.of("Κατεψυγμένα λαχανικά", "Κατεψυγμένα κρέατα", "Κατεψυγμένες πίτσες", "Κατεψυγμένα γεύματα"));
            categories.put("Προϊόντα ψυγείου", List.of("Τυριά", "Γιαούρτια", "Γάλα", "Βούτυρο"));
            categories.put("Αλλαντικά", List.of("Ζαμπόν", "Σαλάμι", "Μπέικον"));
            categories.put("Αλκοολούχα ποτά", List.of("Μπύρα", "Κρασί", "Ούζο", "Τσίπουρο"));
            categories.put("Μη αλκοολούχα ποτά", List.of("Χυμοί", "Αναψυκτικά", "Νερό", "Ενεργειακά ποτά"));
            categories.put("Καθαριστικά για το σπίτι", List.of("Καθαριστικά για το πάτωμα", "Καθαριστικά για τα τζάμια", "Καθαριστικά κουζίνας"));
            categories.put("Απορρυπαντικά ρούχων", List.of("Σκόνες πλυντηρίου", "Υγρά πλυντηρίου", "Μαλακτικά"));
            categories.put("Καλλυντικά", List.of("Κρέμες προσώπου", "Μακιγιάζ", "Λοσιόν σώματος"));
            categories.put("Προϊόντα στοματικής υγιεινής", List.of("Οδοντόκρεμες", "Οδοντόβουρτσες", "Στοματικά διαλύματα"));
            categories.put("Πάνες", List.of("Πάνες για μωρά", "Πάνες ενηλίκων"));
            categories.put("Δημητριακά", List.of("Νιφάδες καλαμποκιού", "Μούσλι", "Βρώμη"));
            categories.put("Ζυμαρικά", List.of("Μακαρόνια", "Κριθαράκι", "Ταλιατέλες"));
            categories.put("Σνακ", List.of("Πατατάκια", "Κράκερς", "Μπάρες δημητριακών"));
            categories.put("Έλαια", List.of("Ελαιόλαδο", "Ηλιέλαιο", "Σογιέλαιο"));
            categories.put("Κονσέρβες", List.of("Κονσέρβες ψαριών", "Κονσέρβες λαχανικών", "Κονσέρβες φρούτων"));
            categories.put("Χαρτικά", List.of("Χαρτί υγείας", "Χαρτοπετσέτες", "Χαρτομάντηλα"));
        }
    }


    //Αποθήκευση κατηγοριών στο αρχείο
    public void saveCategories() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH2))) {
            oos.writeObject(categories);
        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την αποθήκευση των κατηγοριών: " + e.getMessage());
        }
    }
    // Προσθήκη νέου προϊόντος
    public void addProduct(Product product) {
        products.put(product.getTitle(), product);
        saveProducts();
    }
    //Διαγραφή προϊόντος
    public void deleteProduct(Product product) {
        products.remove(product.getTitle(),product);
        saveProducts();
    }

    // Αναζήτηση προϊόντων βάση τίτλου,κατηγορίας,υποκατηγορίας
    public List<Product> searchProducts(String keyword) {
        return products.values().stream()
                .filter(p -> p.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        p.getCategory().toLowerCase().contains(keyword.toLowerCase()) ||
                        p.getSubCategory().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
    // Εύρεση προϊόντος με βάση το ID
    public Product findProductById(String id) {
        for (Product product : products.values()) { // Όπου products είναι η λίστα με τα προϊόντα
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null; // Επιστρέφει null αν δεν βρεθεί το προϊόν
    }
    //Έυρεση προϊόντος με βάση τον τίτλο του προϊόντος
    public Product findProductByTitle(String title) {
        for (Product product : products.values()) {
            if (product.getTitle().equalsIgnoreCase(title)) { // Σύγκριση τίτλου χωρίς διάκριση πεζών-κεφαλαίων
                return product;
            }
        }
        return null; // Επιστροφή null αν δεν βρεθεί προϊόν με τον συγκεκριμένο τίτλο
    }

    // Επιστροφή όλων των προϊόντων
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
    // Επιστροφή όλων των κατηγοριών και υποκατηγοριών
    public Map<String, List<String>> getCategories() {
        return categories;
    }
    //Επιστρέφει τις επιτρεπόμενες μονάδες που αποδέχεται το σύστημα
    public List<String> getUnits(){
        List<String> units = new ArrayList<>();
        units.add("kg");
        units.add("τεμάχια");
        return units;
    }
    //Ενημέρωση και αποθήκευση της διαθέσιμης ποσότητας των προϊόντων μετά από πραγματοποιήση παραγγελίας
    public void updateStock(List<Product> cartProducts) {
        for (Product cartProduct : cartProducts) {
            String productId = cartProduct.getId();
            Product originalProduct = products.get(productId);

            if (originalProduct != null) {
                // Μείωση της ποσότητας του αρχικού προϊόντος κατά την ποσότητα του καλαθιού
                int updatedQuantity = originalProduct.getQuantity() - cartProduct.getQuantity();

                if (updatedQuantity >= 0) {
                    originalProduct.setQuantity(updatedQuantity);
                } else {
                    System.out.println("Σφάλμα: Δεν υπάρχει επαρκής ποσότητα για το προϊόν " + originalProduct.getTitle());
                }
            }
        }
        saveProducts(); // Αποθήκευση των αλλαγών στο απόθεμα στο αρχείο
    }

}
