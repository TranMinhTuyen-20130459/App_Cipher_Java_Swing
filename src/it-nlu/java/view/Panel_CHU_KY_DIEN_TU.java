package view;

import javax.swing.*;
import java.awt.*;

public class Panel_CHU_KY_DIEN_TU extends JPanel {

    public Panel_CHU_KY_DIEN_TU(int WIDTH, int HEIGHT) {

        setBackground(new Color(255, 255, 255, 255));
        setBounds(0, 0, WIDTH, HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setLayout(null);
        setVisible(true);

        JLabel text = new JLabel("CHỮ KÝ ĐIỆN TỬ");
        text.setForeground(Color.BLACK);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setFont(new Font("Arial", Font.PLAIN, 16));
        text.setBounds(0, 25, WIDTH / 3, 40);

        add(text);

    }
}
