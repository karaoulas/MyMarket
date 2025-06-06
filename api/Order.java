package api;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private Date orderDate;
    private List<Product> products;
    private double totalCost;

    public Order(List<Product> products) {
        this.orderDate = new Date();
        this.products = products;
        this.totalCost = calculateTotalCost();
    }
    //getter για το συνολικό κόστος της παραγγελίας
    public double getTotalCost(){
        return totalCost;
    }
    //getter για την ημερομηνία που πραγματοποιήθηκε η παραγγελία
    public Date getOrderDate(){
        return orderDate;
    }
    // Υπολογίζει το συνολικό κόστος της παραγγελίας του χρήστη
    private double calculateTotalCost() {
        return products.stream().mapToDouble(p -> p.getPrice() * p.getQuantity()).sum();
    }
    //Εμφάνιση τις λεπτομέρειες μιας παραγγελίας
    public String getOrderDetails() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
        StringBuilder details = new StringBuilder("Ημερομηνία Παραγγελίας: " + dateFormat.format(orderDate) + "\n");

        for (Product product : products) {
            details.append("Προϊόν: ").append(product.getTitle())
                    .append(", Ποσότητα: ").append(product.getQuantity())
                    .append(" (Κόστος: ").append(product.getPrice()).append(" €)\n");
        }

        details.append("Συνολικό Κόστος Παραγγελίας: ").append(totalCost).append(" €\n");

        return details.toString();
    }



}

