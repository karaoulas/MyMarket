package api;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements Serializable  {
    private List<Order> orderHistory;


    public Customer(String username, String password) {
        super(username, password);
        this.orderHistory = new ArrayList<>();
    }
    //Προσθέτει μια νέα παραγγελία στο ιστορικό παραγγελιών του πελάτη.
    public void addOrder(Order order) {
        orderHistory.add(order);
    }
    //Επιστρέφει το ιστορικό παραγγελιών του πελάτη
    public List<Order> getOrderHistory() {
        return orderHistory;
    }
    //Χρήσιμη για την ενημέρωση του ιστορικού από αρχείο όταν φορτώνουμε τις αποθηκευμένες παραγγελίες
    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    @Override
    public String toString(){
        return "Customer " + getUsername();
    }
}

