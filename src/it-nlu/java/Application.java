import view.Frame_Home;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            var frame_home = new Frame_Home();
            frame_home.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
