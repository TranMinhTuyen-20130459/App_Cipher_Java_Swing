import org.bouncycastle.jce.provider.BouncyCastleProvider;
import view.Frame_Home;

import javax.swing.*;
import java.security.Security;

public class App {

    public static void main(String[] args) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            var frame_home = new Frame_Home();
            frame_home.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
