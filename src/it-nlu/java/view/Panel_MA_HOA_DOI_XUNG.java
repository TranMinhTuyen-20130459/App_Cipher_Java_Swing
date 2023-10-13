package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Panel_MA_HOA_DOI_XUNG extends JPanel {
    private JButton bt_encrypt, bt_decrypt;
    private JTextArea plain_text_area,
            encrypted_text_area,
            decrypted_text_area;

    private String plain_text = "",
            encrypted_text = "",
            decrypted_text = "";
    private JScrollPane scroll_pane_plain_text_area,
            scroll_pane_encrypted_text_area,
            scroll_pane_decrypted_text_area;

    public Panel_MA_HOA_DOI_XUNG(int WIDTH, int HEIGHT) {

        setBackground(new Color(255, 255, 255, 255));
        setBounds(0, 0, WIDTH, HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setLayout(null);
        setVisible(true);

        JLabel text_chon_giai_thuat = new JLabel("Chọn giải thuật:");
        text_chon_giai_thuat.setForeground(Color.BLACK);
        text_chon_giai_thuat.setHorizontalAlignment(SwingConstants.CENTER);
        text_chon_giai_thuat.setFont(new Font("Arial", Font.PLAIN, 14));
        text_chon_giai_thuat.setBounds(0, 19, 145, 37);

        JLabel text_chon_ngon_ngu = new JLabel("Chọn ngôn ngữ:");
        text_chon_ngon_ngu.setForeground(Color.BLACK);
        text_chon_ngon_ngu.setHorizontalAlignment(SwingConstants.CENTER);
        text_chon_ngon_ngu.setFont(new Font("Arial", Font.PLAIN, 14));
        text_chon_ngon_ngu.setBounds(364, 19, 119, 37);

        JLabel text_van_ban_ma_hoa = new JLabel("Đoạn văn bản mã hóa:");
        text_van_ban_ma_hoa.setForeground(Color.BLACK);
        text_van_ban_ma_hoa.setHorizontalAlignment(SwingConstants.CENTER);
        text_van_ban_ma_hoa.setFont(new Font("Arial", Font.PLAIN, 14));
        text_van_ban_ma_hoa.setBounds(19, 196, 149, 90);

        JLabel text_van_ban_giai_ma = new JLabel("Đoạn văn bản giải mã:");
        text_van_ban_giai_ma.setForeground(Color.BLACK);
        text_van_ban_giai_ma.setHorizontalAlignment(SwingConstants.CENTER);
        text_van_ban_giai_ma.setFont(new Font("Arial", Font.PLAIN, 14));
        text_van_ban_giai_ma.setBounds(17, 318, 151, 90);

        add(text_chon_giai_thuat);
        add(text_chon_ngon_ngu);
        add(text_van_ban_ma_hoa);
        add(text_van_ban_giai_ma);

        createTextAreaGroup();
        createButtonGroup();

        add(scroll_pane_plain_text_area);
        add(scroll_pane_encrypted_text_area);
        add(scroll_pane_decrypted_text_area);
        add(bt_encrypt);
        add(bt_decrypt);
    }

    public void createButtonGroup() {
        bt_encrypt = new RoundedButton("MÃ HÓA", 25, new Color(217, 217, 217));
        bt_encrypt.setBounds(19, 439, 106, 26);

        bt_decrypt = new RoundedButton("GIẢI MÃ", 25, new Color(217, 217, 217));
        bt_decrypt.setBounds(499, 439, 106, 26);
    }

    public void createTextAreaGroup() {
        createPlainTextArea();
        createEncryptedTextArea();
        createDecryptedTextArea();
    }

    public void createPlainTextArea() {
        plain_text_area = new JTextAreaWithPlaceholder("Nhập đoạn văn bản cần mã hóa tại đây...");
        plain_text_area.setBackground(new Color(217, 217, 217));

        scroll_pane_plain_text_area = new JScrollPane(plain_text_area);
        scroll_pane_plain_text_area.setBounds(19, 78, 592, 90);

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_plain_text_area.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        plain_text_area.setLineWrap(true);
        plain_text_area.setWrapStyleWord(true);
    }

    public void createEncryptedTextArea() {
        encrypted_text_area = new JTextArea();
        encrypted_text_area.setBackground(new Color(217, 217, 217));

        scroll_pane_encrypted_text_area = new JScrollPane(encrypted_text_area);
        scroll_pane_encrypted_text_area.setBounds(168, 196, 443, 90);

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_encrypted_text_area.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        encrypted_text_area.setLineWrap(true);
        encrypted_text_area.setWrapStyleWord(true);

        encrypted_text_area.setEnabled(false);
    }

    public void createDecryptedTextArea() {
        decrypted_text_area = new JTextArea();
        decrypted_text_area.setBackground(new Color(217, 217, 217));

        scroll_pane_decrypted_text_area = new JScrollPane(decrypted_text_area);
        scroll_pane_decrypted_text_area.setBounds(168, 318, 443, 90);

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_decrypted_text_area.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        decrypted_text_area.setLineWrap(true);
        decrypted_text_area.setWrapStyleWord(true);

        decrypted_text_area.setEnabled(false);
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

    public class JTextAreaWithPlaceholder extends JTextArea {
        private String placeholder;

        public JTextAreaWithPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !isFocusOwner()) {
                g.setColor(Color.GRAY);
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString(placeholder, getInsets().left + 150, g.getFontMetrics().getHeight() + getInsets().top + 30);
            }
        }
    }
}
