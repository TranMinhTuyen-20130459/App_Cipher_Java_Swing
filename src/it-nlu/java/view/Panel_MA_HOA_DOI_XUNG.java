package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Panel_MA_HOA_DOI_XUNG extends JPanel {
    private JPanel panel_root,
            panel_cipher_text,
            panel_cipher_file,
            panel_mhdx_text,
            panel_mhdx_file;

    private JLabel label_cipher_text,
            label_cipher_file;

    private int WIDTH = 0,
            HEIGHT = 0;

    public Panel_MA_HOA_DOI_XUNG(int WIDTH, int HEIGHT) {

        setBackground(new Color(255, 255, 255, 255));
        setBounds(0, 0, WIDTH, HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setLayout(null);
        setVisible(true);

        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;

        createPanelRoot();
        createPanelCipherText();
        createPanelCipherFile();
        createLabelGroup();

        add(panel_root);
    }

    public void createPanelRoot() {
        panel_root = new JPanel();
        panel_root.setBackground(new Color(255, 255, 255, 255));
        panel_root.setBounds(0, 0, WIDTH, HEIGHT);
        panel_root.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panel_root.setLayout(null);
        panel_root.setVisible(true);
    }

    public void createPanelCipherText() {

        panel_cipher_text = new JPanel();
        panel_cipher_text.setBackground(new Color(217, 217, 217));
        panel_cipher_text.setBounds(55, 134, 248, 192);
        panel_cipher_text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panel_cipher_text.setLayout(null);

        panel_root.add(panel_cipher_text);
        panel_cipher_text.addMouseListener(new PanelButtonMouseAdapter(panel_cipher_text) {
            @Override
            public void mouseClicked(MouseEvent e) {

                // System.out.println("WIDTH:" + getWIDTH() + " ,HEIGHT:" + getHEIGHT());
                panel_mhdx_text = new Panel_MA_HOA_DOI_XUNG_TEXT(getWIDTH(), getHEIGHT());
                panel_root.add(panel_mhdx_text);
                showPanel(panel_mhdx_text);
            }
        });

    }

    public void createPanelCipherFile() {

        panel_cipher_file = new JPanel();
        panel_cipher_file.setBackground(new Color(217, 217, 217));
        panel_cipher_file.setBounds(340, 134, 248, 192);
        panel_cipher_file.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panel_cipher_file.setLayout(null);

        panel_root.add(panel_cipher_file);

        panel_cipher_file.addMouseListener(new PanelButtonMouseAdapter(panel_cipher_file) {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel_mhdx_file = new Panel_MA_HOA_DOI_XUNG_FILE(getWIDTH(), getHEIGHT());
                panel_root.add(panel_mhdx_file);
                showPanel(panel_mhdx_file);
            }
        });
    }

    public void createLabelGroup() {
        label_cipher_text = new JLabel("VĂN BẢN");
        label_cipher_text.setForeground(Color.BLACK);
        label_cipher_text.setFont(new Font("Arial", Font.PLAIN, 20));
        label_cipher_text.setBounds(77, 0,
                panel_cipher_text.getWidth(),
                panel_cipher_text.getHeight());
        panel_cipher_text.add(label_cipher_text);


        label_cipher_file = new JLabel("FILE");
        label_cipher_file.setForeground(Color.BLACK);
        label_cipher_file.setFont(new Font("Arial", Font.PLAIN, 20));
        label_cipher_file.setBounds(100, 0,
                panel_cipher_file.getWidth(),
                panel_cipher_file.getHeight());
        panel_cipher_file.add(label_cipher_file);
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void showPanel(JPanel panel) {
        if (panel_cipher_text != null) panel_cipher_text.setVisible(false);
        if (panel_cipher_file != null) panel_cipher_file.setVisible(false);
        if (panel_mhdx_text != null) panel_mhdx_text.setVisible(false);
        if (panel_mhdx_file != null) panel_mhdx_file.setVisible(false);
        panel.setVisible(true);
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
