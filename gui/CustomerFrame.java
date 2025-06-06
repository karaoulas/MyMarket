package gui;
import javax.swing.*;
import api.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class CustomerFrame extends JFrame {
    private ProductManager productManager;
    private Customer customer;
    private JTextField searchField;
    private JTable productTable, cartTable;
    private DefaultTableModel cartTableModel;
    private List<Product> cart;
    private JLabel totalCostLabel;
    private TextArea orderHistoryArea;

    public CustomerFrame(Customer customer, ProductManager productManager) {
        this.customer = customer;
        this.productManager = productManager;
        this.cart = new ArrayList<>();
        loadOrderHistoryFromFile(); // Φόρτωση παραγγελιών από το αρχείο


        setTitle("Πίνακας Πελάτη - " + customer.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Προσθήκη κουμπιού αποσύνδεσης στο πάνω μέρος του πλαισίου
        JButton logoutButton = new JButton("Αποσύνδεση");
        logoutButton.addActionListener(e -> logout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(logoutButton);
        add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Αναζήτηση Προϊόντων", createProductPanel());
        tabbedPane.add("Καλάθι", createCartPanel());
        tabbedPane.add("Ιστορικό Παραγγελιών", createOrderHistoryPanel());
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Αναζήτηση");
        searchButton.addActionListener(e -> searchProduct());

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Προϊόντα:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        panel.add(searchPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Όνομα", "Περιγραφή", "Κατηγορία", "Υποκατηγορία", "Τιμή", "Ποσότητα", "Μονάδα"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        panel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        JButton addToCartButton = new JButton("Προσθήκη στο Καλάθι");
        addToCartButton.addActionListener(e -> addToCart());
        panel.add(addToCartButton, BorderLayout.SOUTH);

        loadProductsToTable(); // Αρχική φόρτωση προϊόντων

        return panel;
    }

    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Προϊόν", "Περιγραφή", "Κατηγορία", "Υποκατηγορία", "Τιμή", "Ποσότητα", "Μονάδα"};
        cartTableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(cartTableModel);
        panel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateCartButton = new JButton("Ενημέρωση Ποσότητας");
        updateCartButton.addActionListener(e -> updateCartQuantity());

        JButton removeFromCartButton = new JButton("Διαγραφή από Καλάθι");
        removeFromCartButton.addActionListener(e -> removeFromCart());

        JButton checkoutButton = new JButton("Ολοκλήρωση Παραγγελίας");
        checkoutButton.addActionListener(e -> checkout());

        buttonPanel.add(updateCartButton);
        buttonPanel.add(removeFromCartButton);
        buttonPanel.add(checkoutButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Συνολικό κόστος καλαθιού
        totalCostLabel = new JLabel("Συνολικό Κόστος: 0 €");
        panel.add(totalCostLabel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createOrderHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        orderHistoryArea = new TextArea();
        orderHistoryArea.setEditable(false);

        JButton loadOrderHistoryButton = new JButton("Προβολή Ιστορικού Παραγγελιών");
        loadOrderHistoryButton.addActionListener(e -> loadOrderHistory());

        panel.add(new JScrollPane(orderHistoryArea), BorderLayout.CENTER);
        panel.add(loadOrderHistoryButton, BorderLayout.SOUTH);

        return panel;
    }

    private void loadProductsToTable() {
        List<Product> products = productManager.getAllProducts();
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);
        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getId(), product.getTitle(), product.getDescription(),
                    product.getCategory(), product.getSubCategory(),
                    product.getPrice(), product.getQuantity(), product.getUnit()
            });
        }
    }

    private void searchProduct() {
        String keyword = searchField.getText();
        List<Product> searchResults = productManager.searchProducts(keyword);

        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);
        for (Product product : searchResults) {
            model.addRow(new Object[]{
                    product.getId(), product.getTitle(), product.getDescription(),
                    product.getCategory(), product.getSubCategory(),
                    product.getPrice(), product.getQuantity(),product.getUnit()
            });
        }
    }


        private void addToCart () {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε ένα προϊόν για προσθήκη στο καλάθι.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String title = productTable.getValueAt(selectedRow, 1).toString();
            Product product = productManager.findProductByTitle(title);

            if (product != null) {
                String quantityInput = JOptionPane.showInputDialog("Εισάγετε ποσότητα:");
                // Έλεγχος αν η είσοδος είναι κενή ή περιέχει μη αριθμητικούς χαρακτήρες


                if (quantityInput == null || quantityInput.isEmpty() || !quantityInput.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε έναν έγκυρο αριθμό για την ποσότητα.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int quantity = Integer.parseInt(quantityInput);

                if (quantity <= product.getQuantity()) {
                    product.setQuantity(product.getQuantity() - quantity);

                    Product cartProduct = cart.stream()
                            .filter(p -> p.getTitle().equals(title))
                            .findFirst()
                            .orElse(null);

                    if (cartProduct != null) {
                        cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
                    } else {
                        cartProduct = new Product(product.getTitle(), product.getDescription(),
                                product.getCategory(), product.getSubCategory(), product.getPrice(), quantity, product.getUnit());
                        cart.add(cartProduct);
                    }

                    updateCartTable();
                    updateTotalCost();
                    loadProductsToTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Η ποσότητα δεν είναι διαθέσιμη.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void updateCartTable() {
        DecimalFormat df = new DecimalFormat("#.00"); // Ορισμός μορφοποίησης με 2 δεκαδικά ψηφία
        cartTableModel.setRowCount(0);

        for (Product product : cart) {
            double totalCostForItem = product.getPrice() * product.getQuantity();
            cartTableModel.addRow(new Object[]{
                    product.getTitle(),
                    product.getDescription(),
                    product.getCategory(),
                    product.getSubCategory(),
                    df.format(totalCostForItem),
                    product.getQuantity(),
                    product.getUnit()
            });
        }
    }

    private void updateTotalCost() {
        double totalCost = cart.stream().mapToDouble(p -> p.getPrice() * p.getQuantity()).sum();
        totalCostLabel.setText("Συνολικό Κόστος: " + String.format("%.2f", totalCost) + " €");
    }

    private void updateCartQuantity() {
        if (cart.isEmpty()){
            JOptionPane.showMessageDialog(this, "Το καλάθι είναι άδειο.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε ένα προϊόν για ενημέρωση.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String title = cartTableModel.getValueAt(selectedRow, 0).toString();
        Product cartProduct = cart.stream()
                .filter(p -> p.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (cartProduct != null) {
            int newQuantity = Integer.parseInt(JOptionPane.showInputDialog("Εισάγετε νέα ποσότητα:"));
            Product originalProduct = productManager.findProductByTitle(title);

            if (newQuantity <= originalProduct.getQuantity() + cartProduct.getQuantity()) {
                originalProduct.setQuantity(originalProduct.getQuantity() + cartProduct.getQuantity() - newQuantity);
                cartProduct.setQuantity(newQuantity);
                updateCartTable();
                updateTotalCost();
                loadProductsToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Η νέα ποσότητα δεν είναι διαθέσιμη.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeFromCart() {
        if (cart.isEmpty()){
            JOptionPane.showMessageDialog(this, "Το καλάθι είναι άδειο.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε ένα προϊόν για διαγραφή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String title = cartTableModel.getValueAt(selectedRow, 0).toString();
        Product cartProduct = cart.stream()
                .filter(p -> p.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (cartProduct != null) {
            // Εμφάνιση παραθύρου επιβεβαίωσης
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Είστε σίγουρος ότι θέλετε να διαγράψετε το προϊόν από το καλάθι;",
                    "Επιβεβαίωση Διαγραφής",
                    JOptionPane.YES_NO_OPTION);

            // Έλεγχος επιλογής του χρήστη
            if (confirm == JOptionPane.YES_OPTION) {
                Product originalProduct = productManager.findProductByTitle(title);
                originalProduct.setQuantity(originalProduct.getQuantity() + cartProduct.getQuantity());
                cart.remove(cartProduct);
                updateCartTable();
                updateTotalCost();
                loadProductsToTable();
            }
        }
    }

    private void checkout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Το καλάθι είναι άδειο.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Order order = new Order(new ArrayList<>(cart));
        for (Product product : cart) {
            Product originalProduct = productManager.findProductByTitle(product.getTitle());
            if (originalProduct != null) {
                originalProduct.increaseOrderFrequency(); // Αύξηση συχνότητας στο αρχικό προϊόν
            }
        }
        customer.addOrder(order);
        productManager.updateStock(cart); // Ενημέρωση του αποθέματος
        cart.clear(); // Άδειασμα του καλαθιού
        updateCartTable();
        updateTotalCost();
        saveOrderHistory();

        JOptionPane.showMessageDialog(this, "Η παραγγελία ολοκληρώθηκε επιτυχώς!");
    }
    //Εμφανίζει το ιστορικό παραγγελιών
    private void loadOrderHistory() {
        loadOrderHistoryFromFile(); // Φόρτωση παραγγελιών από το αρχείο
        StringBuilder orderHistoryText = new StringBuilder();

        for (Order order : customer.getOrderHistory()) {
            orderHistoryText.append(order.getOrderDetails()).append("\n\n");
        }

        orderHistoryArea.setText(orderHistoryText.toString());
    }
    //Αποθηκεύει το ιστορικό παραγγελιών του πελάτη σε αρχείο
    private void saveOrderHistory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(customer.getUsername() + "_orders.txt"))) {
            oos.writeObject(customer.getOrderHistory());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Φορτώνει το ιστορικό παραγγελιών από το αρχείο και το προσθέτει στο orderHistory του πελάτη
    private void loadOrderHistoryFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(customer.getUsername() + "_orders.txt"))) {
            List<Order> orderHistory = (List<Order>) ois.readObject();
            customer.setOrderHistory(orderHistory);
        } catch (FileNotFoundException e) {
            System.out.println("Δεν βρέθηκε ιστορικό παραγγελιών για τον χρήστη: " + customer.getUsername());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //Μέθοδος για την αποσύνδεση του χρήστη
    private void logout() {
        UserManager users= new UserManager();
        ProductManager productManager= new ProductManager();
        this.dispose(); // Κλείσιμο του gui.AdminFrame
        SwingUtilities.invokeLater(() -> new LoginFrame(users,productManager).setVisible(true)); // Εμφάνιση του gui.LoginFrame
    }

}
