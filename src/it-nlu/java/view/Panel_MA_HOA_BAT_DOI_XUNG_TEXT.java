package view;

import controller.Controller_MA_HOA_BAT_DOI_XUNG;
import helper.Algorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class Panel_MA_HOA_BAT_DOI_XUNG_TEXT extends JPanel {
    private JLabel lb_key_size,
            lb_public_key,
            lb_private_key,
            lb_mode_padding,
            lb_input,
            lb_output;

    private JComboBox combo_box_key_size, combo_box_mode_padding;

    private JButton bt_create_key,
            bt_encrypt,
            bt_decrypt,
            bt_home;
    private JTextArea text_area_public_key,
            text_area_private_key,
            text_area_input,
            text_area_output;

    private JScrollPane scroll_pane_public_key,
            scroll_pane_private_key,
            scroll_pane_input,
            scroll_pane_output;

    private final String[] arr_key_sizes = {"512 bit", "1024 bit", "2048 bit", "4096 bit"};
    private final String[] arr_mode_padding = {"RSA", "RSA/ECB/PKCS1Padding"};
    private String name_key_size = "", name_mode_padding = "",
            public_key = "", private_key = "", text_input = "", text_output = "";

    public Panel_MA_HOA_BAT_DOI_XUNG_TEXT(int WIDTH, int HEIGHT) {

        setBackground(new Color(255, 255, 255, 255));
        setBounds(0, 0, WIDTH, HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setLayout(null);
        setVisible(true);

        createLabelGroup();
        createComboBoxGroup();
        createButtonGroup();
        createTextAreaGroup();

        add(lb_key_size);
        add(lb_public_key);
        add(lb_private_key);
        add(lb_mode_padding);
        add(lb_input);
        add(lb_output);

        add(combo_box_key_size);
        add(combo_box_mode_padding);

        add(bt_create_key);
        add(bt_encrypt);
        add(bt_decrypt);
        add(bt_home);

        add(scroll_pane_public_key);
        add(scroll_pane_private_key);
        add(scroll_pane_input);
        add(scroll_pane_output);
    }

    public void createLabelGroup() {
        lb_key_size = new JLabel("Chọn Key Size:");
        lb_key_size.setForeground(Color.BLACK);
        lb_key_size.setHorizontalAlignment(SwingConstants.LEFT);
        lb_key_size.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_key_size.setBounds(27, 12, 125, 19);

        lb_public_key = new JLabel("Public Key:");
        lb_public_key.setForeground(Color.BLACK);
        lb_public_key.setHorizontalAlignment(SwingConstants.LEFT);
        lb_public_key.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_public_key.setBounds(314, 12, 110, 21);

        lb_private_key = new JLabel("Private Key:");
        lb_private_key.setForeground(Color.BLACK);
        lb_private_key.setHorizontalAlignment(SwingConstants.LEFT);
        lb_private_key.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_private_key.setBounds(522, 12, 95, 21);

        lb_mode_padding = new JLabel("Cipher Type:");
        lb_mode_padding.setForeground(Color.BLACK);
        lb_mode_padding.setHorizontalAlignment(SwingConstants.LEFT);
        lb_mode_padding.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_mode_padding.setBounds(27, 88, 125, 19);

        lb_input = new JLabel("Input:");
        lb_input.setForeground(Color.BLACK);
        lb_input.setHorizontalAlignment(SwingConstants.LEFT);
        lb_input.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_input.setBounds(27, 181, 78, 19);

        lb_output = new JLabel("Output:");
        lb_output.setForeground(Color.BLACK);
        lb_output.setHorizontalAlignment(SwingConstants.LEFT);
        lb_output.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_output.setBounds(402, 181, 70, 19);

    }

    public void createComboBoxGroup() {
        createComboBoxKeySize();
        createComboBoxModePadding();
    }

    private void createComboBoxModePadding() {
        combo_box_mode_padding = new JComboBox<>(arr_mode_padding);
        combo_box_mode_padding.setFont(new Font("Arial", Font.PLAIN, 14));
        combo_box_mode_padding.setBounds(24, 115, 180, 32);
        combo_box_mode_padding.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        name_mode_padding = combo_box_mode_padding.getSelectedItem().toString();

        combo_box_mode_padding.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                name_mode_padding = combo_box_mode_padding.getSelectedItem().toString();
            }
        });

    }

    private void createComboBoxKeySize() {
        combo_box_key_size = new JComboBox<>(arr_key_sizes);
        combo_box_key_size.setFont(new Font("Arial", Font.PLAIN, 14));
        combo_box_key_size.setBounds(24, 38, 159, 32);
        combo_box_key_size.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        name_key_size = combo_box_key_size.getSelectedItem().toString();

        combo_box_key_size.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                name_key_size = combo_box_key_size.getSelectedItem().toString();
            }
        });

    }

    public void createButtonGroup() {
        createButtonCreateKey();
        createButtonHome();
        createButtonEncrypt();
        createButtonDecrypt();
    }

    private void createButtonEncrypt() {

        bt_encrypt = new RoundedButton("MÃ HÓA", 0, new Color(217, 217, 217));
        bt_encrypt.setBounds(309, 271, 83, 30);

        bt_encrypt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (bt_encrypt.isEnabled()) {

                    text_input = text_area_input.getText();
                    if (text_input == null || text_input.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn hãy nhập vào văn bản cần MÃ HÓA", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    public_key = text_area_public_key.getText();
                    if (public_key == null || public_key.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn hãy nhập vào PUBLIC KEY", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    text_output = Controller_MA_HOA_BAT_DOI_XUNG.encryptText(Algorithm.RSA, text_input, public_key, name_mode_padding);
                    if (text_output == null) {
                        JOptionPane.showMessageDialog(null, "MÃ HÓA THẤT BẠI", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    text_area_output.setText(text_output);
                }
            }
        });

    }

    private void createButtonDecrypt() {

        bt_decrypt = new RoundedButton("GIẢI MÃ", 0, new Color(217, 217, 217));
        bt_decrypt.setBounds(309, 326, 83, 30);

        bt_decrypt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (bt_decrypt.isEnabled()) {

                    text_input = text_area_input.getText();
                    if (text_input == null || text_input.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn hãy nhập vào văn bản cần GIẢI MÃ", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    private_key = text_area_private_key.getText();
                    if (private_key == null || private_key.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn hãy nhập vào PRIVATE KEY", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    text_output = Controller_MA_HOA_BAT_DOI_XUNG.decryptText(Algorithm.RSA, text_input, private_key, name_mode_padding);
                    if (text_output == null) {
                        JOptionPane.showMessageDialog(null, "GIẢI MÃ THẤT BẠI", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    text_area_output.setText(text_output);
                }

            }
        });

    }

    private void createButtonHome() {

        bt_home = new RoundedButton("TRANG CHỦ", 0, new Color(217, 217, 217));
        bt_home.setBounds(565, 439, 108, 31);

        bt_home.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    private void createButtonCreateKey() {

        bt_create_key = new RoundedButton("TẠO KEY", 0, new Color(217, 217, 217));
        bt_create_key.setBounds(207, 38, 83, 31);

        bt_create_key.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (bt_create_key.isEnabled()) {

                    if (name_key_size == null) return;
                    int key_size = Integer.parseInt(name_key_size.replaceAll("[^0-9]", ""));

                    var arr_key = Controller_MA_HOA_BAT_DOI_XUNG.createKeyRandom(Algorithm.RSA, key_size);
                    if (arr_key == null) return;

                    public_key = arr_key[0];
                    private_key = arr_key[1];

                    text_area_public_key.setText(public_key);
                    text_area_private_key.setText(private_key);

                }
            }
        });

    }

    public void createTextAreaGroup() {
        createTextAreaPublicKey();
        createTextAreaPrivateKey();
        createTextAreaInput();
        createTextAreaOutput();
    }

    private void createTextAreaOutput() {

        text_area_output = new JTextArea();
        text_area_output.setFont(new Font("Arial", Font.PLAIN, 16));
        text_area_output.setBackground(new Color(217, 217, 217));

        scroll_pane_output = new JScrollPane(text_area_output);
        scroll_pane_output.setBounds(402, 204, 271, 215);
        scroll_pane_output.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_output.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        text_area_output.setLineWrap(true);
        text_area_output.setWrapStyleWord(true);

    }

    private void createTextAreaInput() {

        text_area_input = new JTextArea();
        text_area_input.setFont(new Font("Arial", Font.PLAIN, 16));
        text_area_input.setBackground(new Color(217, 217, 217));

        scroll_pane_input = new JScrollPane(text_area_input);
        scroll_pane_input.setBounds(24, 204, 271, 215);
        scroll_pane_input.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_input.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        text_area_input.setLineWrap(true);
        text_area_input.setWrapStyleWord(true);

    }

    private void createTextAreaPublicKey() {
        text_area_public_key = new JTextArea();
        text_area_public_key.setFont(new Font("Arial", Font.PLAIN, 16));
        text_area_public_key.setBackground(new Color(217, 217, 217));

        scroll_pane_public_key = new JScrollPane(text_area_public_key);
        scroll_pane_public_key.setBounds(314, 38, 154, 127);
        scroll_pane_public_key.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_public_key.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        text_area_public_key.setLineWrap(true);
        text_area_public_key.setWrapStyleWord(true);
    }

    private void createTextAreaPrivateKey() {

        text_area_private_key = new JTextArea();
        text_area_private_key.setFont(new Font("Arial", Font.PLAIN, 16));
        text_area_private_key.setBackground(new Color(217, 217, 217));

        scroll_pane_private_key = new JScrollPane(text_area_private_key);
        scroll_pane_private_key.setBounds(519, 38, 154, 127);
        scroll_pane_private_key.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_private_key.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        text_area_private_key.setLineWrap(true);
        text_area_private_key.setWrapStyleWord(true);

    }

    public class RoundedButton extends JButton {
        private int radius;
        private Color backgroundColor;

        public RoundedButton(String text, int radius, Color backgroundColor) {
            super(text);
            this.radius = radius;
            this.backgroundColor = backgroundColor;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(backgroundColor.darker());
            } else {
                g.setColor(backgroundColor);
            }
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            g2d.setColor(Color.BLACK); // Màu viền đen
            g2d.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            // Do nothing to hide the default button border
        }
    }
}
