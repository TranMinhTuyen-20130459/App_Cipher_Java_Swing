package view;

import javax.swing.*;
import java.awt.*;

public class Panel_MA_HOA_DOI_XUNG extends JPanel {
    private JPanel panel_cipher_text,
            panel_cipher_file;

    public Panel_MA_HOA_DOI_XUNG(int WIDTH, int HEIGHT) {

        setBackground(new Color(255, 255, 255, 255));
        setBounds(0, 0, WIDTH, HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setLayout(null);
        setVisible(true);

        createPanelCipherText();
        createPanelCipherFile();

        add(panel_cipher_text);
        add(panel_cipher_file);
    }

    public void createPanelCipherText() {

        panel_cipher_text = new JPanel();
        panel_cipher_text.setBackground(new Color(217, 217, 217));
        panel_cipher_text.setBounds(55, 134, 248, 192);
        panel_cipher_text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

    }

    public void createPanelCipherFile() {

        panel_cipher_file = new JPanel();
        panel_cipher_file.setBackground(new Color(217, 217, 217));
        panel_cipher_file.setBounds(340, 134, 248, 192);
        panel_cipher_file.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

    }


}
