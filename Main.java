import api.UserManager;
import api.ProductManager;
import gui.LoginFrame;
import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserManager users= new UserManager();
        ProductManager productManager= new ProductManager();
        // Εμφάνιση Frame για σύνδεση
        SwingUtilities.invokeLater(() -> {
            new LoginFrame(users,productManager).setVisible(true);

        });
    }
}
