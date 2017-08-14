import demo.TransactionApp;
import transaction.TransactionManager;

import java.awt.*;

/**
 * Created by bumur on 14.08.2017.
 */
public class Main {
    public static void main(String[] args) {
        TransactionManager manager = new TransactionManager();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TransactionApp application = new TransactionApp(manager);
                    application.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
