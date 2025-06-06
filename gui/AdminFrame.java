package gui;
import api.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;



public class AdminFrame extends JFrame {
    private ProductManager productManager;
    private JTextField nameField, descriptionField,priceField, quantityField, searchField;
    private JTable productTable;
    private JComboBox<String> categoryComboBox,subCategoryComboBox,unitComboBox;
    private JTextArea statsArea;



    public AdminFrame(User user, ProductManager productManager) {
        this.productManager = productManager;




        setTitle("Πίνακας Διαχειριστή-"+ user.getUsername()  );
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
        tabbedPane.add("Διαχείριση-Προβολή Προϊόντων", createProductSearchAndEditPanel());
        tabbedPane.add("Στατιστικά Προϊόντων", createProductStatsPanel());
        tabbedPane.add("Καταχώρηση Προϊόντος", createProductRegistrationPanel());
        add(tabbedPane, BorderLayout.CENTER);
    }
    //Παράυθρο για την καταχώρηση προϊόντος
    private JPanel createProductRegistrationPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2));

        panel.add(new JLabel("Όνομα Προϊόντος (*):"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Περιγραφή (*):"));
        descriptionField = new JTextField();
        panel.add(descriptionField);

        panel.add(new JLabel("Κατηγορία (*):"));
        categoryComboBox = new JComboBox<>(productManager.getCategories().keySet().toArray(new String[0]));
        categoryComboBox.addActionListener(e -> updateSubCategoryComboBox(subCategoryComboBox,(String) categoryComboBox.getSelectedItem(),null));
        panel.add(categoryComboBox);

        panel.add(new JLabel("Υποκατηγορία (*):"));
        subCategoryComboBox = new JComboBox<>();
        updateSubCategoryComboBox(subCategoryComboBox,(String) categoryComboBox.getSelectedItem(),null); // Ενημέρωση υποκατηγοριών αρχικά
        panel.add(subCategoryComboBox);

        panel.add(new JLabel("Τιμή (*):"));
        priceField = new JTextField();
        panel.add(priceField);


        panel.add(new JLabel("Ποσότητα  (*):"));
        quantityField = new JTextField();
        panel.add(quantityField);

        panel.add(new JLabel("Κιλά/Τεμάχια  (*):" ));
        unitComboBox= new JComboBox<>(productManager.getUnits().toArray(new String[0]));
        panel.add(unitComboBox);

        JButton addButton = new JButton("Καταχώρηση");
        addButton.addActionListener(e -> registerProduct());
        panel.add(addButton);

        return panel;
    }
    //Παράθυρο για την αναζήτηση και την επεξεργασία προϊόντων
    private JPanel createProductSearchAndEditPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Αναζήτηση");
        searchButton.addActionListener(e -> searchProduct());

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Αναζήτηση Προϊόντος:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton editButton = new JButton("Επεξεργασία");
        JButton viewButton = new JButton("Προβολή Προϊόντος");


        viewButton.addActionListener(new ViewButtonListener());
        editButton.addActionListener(new EditButtonListener());

        buttonPanel.add(editButton);
        buttonPanel.add(viewButton);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        String[] columnNames = {"ID", "Όνομα", "Περιγραφή", "Κατηγορία", "Υποκατηγορία", "Τιμή", "Ποσότητα", "Μονάδα"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        panel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        loadProductsToTable();//Εμφανίζει τα προϊόντα που υπάρχουν στον πίνακα

        return panel;


    }
    //Παράθυρο για την προβολή των απαιτούμενων στατιστικών των προϊόντων
    private JPanel createProductStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        statsArea = new JTextArea();
        statsArea.setEditable(false);

        JButton loadStatsButton = new JButton("Προβολή Στατιστικών");
        loadStatsButton.addActionListener(e -> displayStatistics());

        panel.add(new JScrollPane(statsArea), BorderLayout.CENTER);
        panel.add(loadStatsButton, BorderLayout.SOUTH);

        return panel;
    }

    //Μέθοδος για την καταχώρηση προϊόντων
    private void registerProduct() {
        // Έλεγχος υποχρεωτικών πεδίων
        if (nameField.getText().isEmpty() || descriptionField.getText().isEmpty() ||
                priceField.getText().isEmpty() || quantityField.getText().isEmpty() ||
                categoryComboBox.getSelectedItem() == null || subCategoryComboBox.getSelectedItem() == null || unitComboBox.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(this, "Όλα τα πεδία με * είναι υποχρεωτικά.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {

            String name = nameField.getText();
            String description = descriptionField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            String subCategory = (String) subCategoryComboBox.getSelectedItem();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String unit = (String) unitComboBox.getSelectedItem();


            Product product = new Product(name, description, category, subCategory, price, quantity,unit);
            productManager.addProduct(product);
            loadProductsToTable();

            JOptionPane.showMessageDialog(this, "Το προϊόν καταχωρήθηκε επιτυχώς!");
            loadProductsToTable();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Η τιμή και η ποσότητα πρέπει να είναι αριθμοί.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Μέθοδος για την αναζήτηση προϊόντων
    private void searchProduct() {
        String keyword = searchField.getText();
        List<Product> searchResults = productManager.searchProducts(keyword);
        if(keyword==null){
            loadProductsToTable();
        }
        else {
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
    }
    //Φόρτωση προϊόντων στον πίνακα
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
    // Εμφάνιση στατιστικών στοιχείων
    private void displayStatistics() {

        StringBuilder statsText = new StringBuilder();

        // Λήψη όλων των προϊόντων από το ProductManager
        List<Product> allProducts = productManager.getAllProducts();

        // Προϊόντα με τη μεγαλύτερη συχνότητα παραγγελίας
        List<Product> mostOrderedProducts = allProducts.stream()
                .filter(p -> p.getOrderFrequency() > 0) // Φιλτράρουμε προϊόντα που έχουν παραγγελθεί τουλάχιστον μία φορά
                .sorted((p1, p2) -> Integer.compare(p2.getOrderFrequency(), p1.getOrderFrequency())) // Ταξινόμηση σε φθίνουσα σειρά συχνότητας
                .limit(5) // Προβολή των κορυφαίων 5 προϊόντων
                .toList();

        statsText.append("Προϊόντα με τη μεγαλύτερη συχνότητα παραγγελίας:\n");

        if (!mostOrderedProducts.isEmpty()) {
            for (Product product : mostOrderedProducts) {
                statsText.append(product.getTitle()).append(" - Συχνότητα παραγγελίας: ").append(product.getOrderFrequency()).append("\n");
            }
        } else {
            statsText.append("Δεν υπάρχουν προϊόντα με παραγγελίες.\n");
        }

        statsText.append("\n");

        // Προϊόντα χωρίς διαθέσιμη ποσότητα (ποσότητα = 0)
        List<Product> unavailableProducts = allProducts.stream()
                .filter(p -> p.getQuantity() == 0)
                .toList();

        statsText.append("Μη διαθέσιμα προϊόντα (ποσότητα = 0):\n");
        if (!unavailableProducts.isEmpty()) {
            for (Product product : unavailableProducts) {
                statsText.append(product.getTitle()).append("\n");
            }
        } else {
            statsText.append("Όλα τα προϊόντα έχουν διαθέσιμη ποσότητα.\n");
        }

        statsArea.setText(statsText.toString());

    }
    //Προβολή επιλεγμένου προϊόντος
    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = productTable.getSelectedRow();

            if (selectedRow == -1) {
                // Μήνυμα αν δεν έχει επιλεγεί κανένα προϊόν
                JOptionPane.showMessageDialog(AdminFrame.this, "Παρακαλώ επιλέξτε ένα προϊόν για προβολή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Λήψη του ID του προϊόντος από την επιλεγμένη σειρά
            String id = productTable.getValueAt(selectedRow, 0).toString();
            //System.out.println(id);
            Product product = productManager.findProductById(id);
            //System.out.println(product.getId());

            if (product == null) {
                // Μήνυμα αν δεν βρέθηκε το προϊόν
                JOptionPane.showMessageDialog(AdminFrame.this, "Το προϊόν με το ID " + id + " δεν βρέθηκε.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Εμφάνιση των χαρακτηριστικών του προϊόντος
            String productDetails = "Τίτλος: " + product.getTitle() + "\n" +
                    "Περιγραφή: " + product.getDescription() + "\n" +
                    "Κατηγορία: " + product.getCategory() + "\n" +
                    "Υποκατηγορία: " + product.getSubCategory() + "\n" +
                    "Τιμή: " + product.getPrice() + " €\n" +
                    "Ποσότητα: " + product.getQuantity() + "\n" +
                    "Μονάδα: " + product.getUnit();

            JOptionPane.showMessageDialog(AdminFrame.this, productDetails, "Προβολή Προϊόντος", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    //Επεξεργασία επιλεγμένου προϊόντος
    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = productTable.getSelectedRow();

            if (selectedRow != -1) {
                // Λήψη του ID του προϊόντος από την επιλεγμένη σειρά του πίνακα
                String id = productTable.getValueAt(selectedRow, 0).toString();
                Product product = productManager.findProductById(id);

                if (product != null) {
                    // Αν βρέθηκε το προϊόν, ανοίγει το παράθυρο επεξεργασίας
                    showEditProductDialog(product);
                }
            } else {
                JOptionPane.showMessageDialog(AdminFrame.this, "Παρακαλώ επιλέξτε ένα προϊόν για επεξεργασία.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //Παράθυρο επεξεργασίας προϊόντος
    private void showEditProductDialog(Product product) {
        JDialog editDialog = new JDialog(AdminFrame.this, "Επεξεργασία Προϊόντος", true);
        editDialog.setLayout(new GridLayout(8, 2));

        // Πεδία για την επεξεργασία των χαρακτηριστικών του προϊόντος
        JTextField nameField = new JTextField(product.getTitle());
        JTextField descriptionField = new JTextField(product.getDescription());

        // Κατηγορία και Υποκατηγορία
        JComboBox<String> categoryComboBox = new JComboBox<>(productManager.getCategories().keySet().toArray(new String[0]));
        categoryComboBox.setSelectedItem(product.getCategory()); // Επιλογή της αρχικής κατηγορίας του προϊόντος

        JComboBox<String> subCategoryComboBox = new JComboBox<>();
        updateSubCategoryComboBox(subCategoryComboBox, (String) categoryComboBox.getSelectedItem(), product.getSubCategory());

        // Προσθήκη listener για αλλαγή υποκατηγορίας όταν αλλάζει η κατηγορία
        categoryComboBox.addActionListener(e -> updateSubCategoryComboBox(subCategoryComboBox, (String) categoryComboBox.getSelectedItem(), null));

        // Τιμή και Ποσότητα
        JTextField priceField = new JTextField(String.valueOf(product.getPrice()));
        JTextField quantityField = new JTextField(String.valueOf(product.getQuantity()));

        JComboBox<String> unitComboBox= new JComboBox<>(productManager.getUnits().toArray(new String[0]));

        // Προσθήκη των πεδίων στο παράθυρο διαλόγου
        editDialog.add(new JLabel("Όνομα:"));
        editDialog.add(nameField);

        editDialog.add(new JLabel("Περιγραφή:"));
        editDialog.add(descriptionField);

        editDialog.add(new JLabel("Κατηγορία:"));
        editDialog.add(categoryComboBox);

        editDialog.add(new JLabel("Υποκατηγορία:"));
        editDialog.add(subCategoryComboBox);

        editDialog.add(new JLabel("Τιμή:"));
        editDialog.add(priceField);

        editDialog.add(new JLabel("Ποσότητα:"));
        editDialog.add(quantityField);

        editDialog.add(new JLabel("Μονάδα:"));
        editDialog.add(unitComboBox);

        // Κουμπί αποθήκευσης
        JButton saveButton = new JButton("Αποθήκευση Αλλαγών");
        saveButton.addActionListener(e -> {
            try {
                // Ενημέρωση του προϊόντος με τις νέες τιμές
                product.setTitle(nameField.getText());
                product.setDescription(descriptionField.getText());
                product.setCategory((String) categoryComboBox.getSelectedItem());
                product.setSubCategory((String) subCategoryComboBox.getSelectedItem());
                product.setPrice(Double.parseDouble(priceField.getText()));
                product.setQuantity(Integer.parseInt(quantityField.getText()));
                product.setUnit((String) unitComboBox.getSelectedItem());
                productManager.saveProducts();
                loadProductsToTable(); // Ενημέρωση του πίνακα προϊόντων
                editDialog.dispose(); // Κλείσιμο του παραθύρου επεξεργασίας
                JOptionPane.showMessageDialog(AdminFrame.this, "Το προϊόν ενημερώθηκε επιτυχώς!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDialog, "Η τιμή και η ποσότητα πρέπει να είναι αριθμοί.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton deleteButton = new JButton("Διαγραφή Προϊόντος");
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    editDialog,
                    "Είστε σίγουρος ότι θέλετε να διαγράψετε το προϊόν;",
                    "Επιβεβαίωση Διαγραφής",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                productManager.deleteProduct(product); // Διαγραφή του προϊόντος
                loadProductsToTable(); // Ενημέρωση του πίνακα προϊόντων
                editDialog.dispose(); // Κλείσιμο του παραθύρου επεξεργασίας
                JOptionPane.showMessageDialog(AdminFrame.this, "Το προϊόν διαγράφηκε επιτυχώς!");
            }
        });
        editDialog.add(deleteButton);
        editDialog.add(saveButton);

        // Ρυθμίσεις παραθύρου
        editDialog.setSize(400, 300);
        editDialog.setLocationRelativeTo(AdminFrame.this);
        editDialog.setVisible(true);
    }


    // Μέθοδος που ενημερώνει τις υποκατηγορίες ανάλογα με την επιλεγμένη κατηγορία
    private void updateSubCategoryComboBox(JComboBox<String> subCategoryComboBox, String selectedCategory, String selectedSubCategory) {
        // Καθαρισμός των τρεχουσών υποκατηγοριών
        subCategoryComboBox.removeAllItems();

        if (selectedCategory != null) {
            // Λήψη των υποκατηγοριών για την επιλεγμένη κατηγορία
            List<String> subCategories = productManager.getCategories().get(selectedCategory);

            if (subCategories != null) {
                for (String subCategory : subCategories) {
                    subCategoryComboBox.addItem(subCategory);
                }
                // Επιλογή της αρχικής υποκατηγορίας αν υπάρχει
                if (selectedSubCategory != null) {
                    subCategoryComboBox.setSelectedItem(selectedSubCategory);
                } else {
                    subCategoryComboBox.setSelectedIndex(0); // Επιλέγει την πρώτη υποκατηγορία αν δεν υπάρχει άλλη
                }
            }
        }
    }
    // Ανάλογα με την επιλεγμένη κατηγορία, ενημερώνει τις υποκατηγορίες
    /*private void updateSubCategoryComboBox1() {

        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        subCategoryComboBox.removeAllItems();

        if (selectedCategory != null) {
            List<String> subCategories = productManager.getCategories().get(selectedCategory);
            for (String subCategory : subCategories) {
                subCategoryComboBox.addItem(subCategory);
            }
        }
    }*/


    // Μέθοδος για την αποσύνδεση και επιστροφή στο gui.LoginFrame
    private void logout() {
        UserManager users= new UserManager();
        ProductManager productManager= new ProductManager();
        this.dispose(); // Κλείσιμο του gui.AdminFrame
        SwingUtilities.invokeLater(() -> new LoginFrame(users,productManager).setVisible(true)); // Εμφάνιση του gui.LoginFrame
    }
}
