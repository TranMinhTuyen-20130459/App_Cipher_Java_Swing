package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Panel_MA_HOA_BAT_DOI_XUNG extends JPanel {

    private JPanel panel_cipher_text,
            panel_cipher_file;

    private JLabel label_cipher_text,
            label_cipher_file;

    public Panel_MA_HOA_BAT_DOI_XUNG(int WIDTH, int HEIGHT) {

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
        panel_cipher_text.setBounds(65, 134, 248, 192);
        panel_cipher_text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panel_cipher_text.setLayout(null);

        label_cipher_text = new JLabel("VĂN BẢN");
        label_cipher_text.setForeground(Color.BLACK);
        label_cipher_text.setFont(new Font("Arial", Font.PLAIN, 20));
        label_cipher_text.setBounds(77, 0,
                panel_cipher_text.getWidth(),
                panel_cipher_text.getHeight());
        panel_cipher_text.add(label_cipher_text);

        panel_cipher_text.addMouseListener(new PanelButtonMouseAdapter(panel_cipher_text) {
            @Override
            public void mouseClicked(MouseEvent e) {
                Frame_Home.showPanel(Frame_Home.panel_mhbdx_text);
            }
        });

    }

    public void createPanelCipherFile() {

        panel_cipher_file = new JPanel();
        panel_cipher_file.setLayout(null);
        panel_cipher_file.setBackground(new Color(217, 217, 217));
        panel_cipher_file.setBounds(390, 134, 248, 192);
        panel_cipher_file.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        label_cipher_file = new JLabel("FILE");
        label_cipher_file.setForeground(Color.BLACK);
        label_cipher_file.setFont(new Font("Arial", Font.PLAIN, 20));
        label_cipher_file.setBounds(100, 0,
                panel_cipher_file.getWidth(),
                panel_cipher_file.getHeight());
        panel_cipher_file.add(label_cipher_file);

        panel_cipher_file.addMouseListener(new PanelButtonMouseAdapter(panel_cipher_file) {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
    }

    private class PanelButtonMouseAdapter extends MouseAdapter {

        JPanel panel;

        public PanelButtonMouseAdapter(JPanel panel) {
            this.panel = panel;
        }

        @Override

        public void mouseEntered(MouseEvent e) {
            panel.setBackground(new Color(112, 128, 144));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            panel.setBackground(new Color(217, 217, 217));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            panel.setBackground(new Color(112, 128, 144));
        }

    }
}
