package view;

import javax.swing.*;
import java.awt.*;

public class Panel_MA_HOA_BAT_DOI_XUNG extends JPanel {

    public Panel_MA_HOA_BAT_DOI_XUNG(int WIDTH, int HEIGHT) {

        setBackground(new Color(255, 255, 255, 255));
        setBounds(0, 0, WIDTH, HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setLayout(null);
        setVisible(true);

        JLabel text = new JLabel("MÃ HÓA BẤT ĐỐI XỨNG");
        text.setForeground(Color.BLACK);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setFont(new Font("Arial", Font.PLAIN, 16));
        text.setBounds(0, 25, WIDTH / 3, 40);

        add(text);

    }
}
