package view;

import controller.Controller_MA_HOA_DOI_XUNG_TEXT;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

public class Panel_MA_HOA_DOI_XUNG_TEXT extends JPanel {
    private JLabel label_chon_giai_thuat,
            label_chon_ngon_ngu,
            label_key,
            label_van_ban_ma_hoa,
            label_van_ban_giai_ma;
    private JButton bt_encrypt,
            bt_decrypt,
            bt_input_key,
            bt_create_new_key,
            bt_home;
    private JTextArea plain_text_area,
            encrypted_text_area,
            decrypted_text_area;

    private JScrollPane scroll_pane_plain_text_area,
            scroll_pane_encrypted_text_area,
            scroll_pane_decrypted_text_area;

    private JTextField key_text_field;
    private JComboBox combo_box_algorithm,
            combo_box_language;
    private String[] arr_algorithms = {"Vigenere", "Hill", "Affine", "DES", "Triple DES", "AES"};

    private String[] arr_languages = {"English", "Vietnamese"};

    private String plain_text = "",
            encrypted_text = "",
            decrypted_text = "",
            key = "",
            name_algorithm = "AES",
            name_language = "English";

    public Panel_MA_HOA_DOI_XUNG_TEXT(int WIDTH, int HEIGHT) {

        setBackground(new Color(255, 255, 255, 255));
        setBounds(0, 0, WIDTH, HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setLayout(null);
        setVisible(true);

        createLabelGroup();
        createTextAreaGroup();
        createButtonGroup();
        createComboBoxGroup();
        createTextFieldGroup();

        add(label_chon_giai_thuat);
        add(label_chon_ngon_ngu);
        add(label_key);
        add(label_van_ban_ma_hoa);
        add(label_van_ban_giai_ma);

        add(scroll_pane_plain_text_area);
        add(scroll_pane_encrypted_text_area);
        add(scroll_pane_decrypted_text_area);

        add(bt_encrypt);
        add(bt_decrypt);
        add(bt_create_new_key);
        add(bt_home);
        add(bt_input_key);

        add(combo_box_algorithm);
        add(combo_box_language);

        add(key_text_field);
    }


    public void createLabelGroup() {

        label_chon_giai_thuat = new JLabel("Chọn giải thuật:");
        label_chon_giai_thuat.setForeground(Color.BLACK);
        label_chon_giai_thuat.setHorizontalAlignment(SwingConstants.CENTER);
        label_chon_giai_thuat.setFont(new Font("Arial", Font.PLAIN, 14));
        label_chon_giai_thuat.setBounds(0, 19, 145, 37);

        label_chon_ngon_ngu = new JLabel("Chọn ngôn ngữ:");
        label_chon_ngon_ngu.setForeground(Color.BLACK);
        label_chon_ngon_ngu.setHorizontalAlignment(SwingConstants.CENTER);
        label_chon_ngon_ngu.setFont(new Font("Arial", Font.PLAIN, 14));
        label_chon_ngon_ngu.setBounds(364, 19, 119, 37);

        label_key = new JLabel("Key:");
        label_key.setForeground(Color.BLACK);
        label_key.setHorizontalAlignment(SwingConstants.CENTER);
        label_key.setFont(new Font("Arial", Font.PLAIN, 14));
        label_key.setBounds(0, 83, 80, 37);

        label_van_ban_ma_hoa = new JLabel("Đoạn văn bản mã hóa:");
        label_van_ban_ma_hoa.setForeground(Color.BLACK);
        label_van_ban_ma_hoa.setHorizontalAlignment(SwingConstants.CENTER);
        label_van_ban_ma_hoa.setFont(new Font("Arial", Font.PLAIN, 14));
        label_van_ban_ma_hoa.setBounds(19, 240, 143, 70);

        label_van_ban_giai_ma = new JLabel("Đoạn văn bản giải mã:");
        label_van_ban_giai_ma.setForeground(Color.BLACK);
        label_van_ban_giai_ma.setHorizontalAlignment(SwingConstants.CENTER);
        label_van_ban_giai_ma.setFont(new Font("Arial", Font.PLAIN, 14));
        label_van_ban_giai_ma.setBounds(17, 330, 145, 70);

    }

    public void createButtonGroup() {
        createButtonEncrypt();
        createButtonDecrypt();
        createButtonCreateNewKey();
        createButtonHome();
        createButtonInputKey();
    }

    public void createButtonEncrypt() {
        bt_encrypt = new RoundedButton("MÃ HÓA", 25, new Color(217, 217, 217));
        bt_encrypt.setBounds(19, 432, 115, 37);
        bt_encrypt.setEnabled(false);

        bt_encrypt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (bt_encrypt.isEnabled() == true) {

                    if (key.isEmpty()) {
                        key = Controller_MA_HOA_DOI_XUNG_TEXT.createKeyRandom(name_algorithm);
                        key_text_field.setText(key);

                        encrypted_text = Controller_MA_HOA_DOI_XUNG_TEXT.encryptText(name_algorithm,
                                name_language,
                                plain_text, key);

                        encrypted_text_area.setEnabled(true);
                        encrypted_text_area.setText(encrypted_text);

                        bt_decrypt.setEnabled(true);
                    } else {
                        encrypted_text = Controller_MA_HOA_DOI_XUNG_TEXT.encryptText(name_algorithm,
                                name_language,
                                plain_text, key);

                        encrypted_text_area.setEnabled(true);
                        encrypted_text_area.setText(encrypted_text);

                        bt_decrypt.setEnabled(true);
                    }
                }
            }
        });
    }

    public void createButtonDecrypt() {
        bt_decrypt = new RoundedButton("GIẢI MÃ", 25, new Color(217, 217, 217));
        bt_decrypt.setBounds(190, 432, 115, 37);
        bt_decrypt.setEnabled(false);

        bt_decrypt.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (bt_decrypt.isEnabled() == true) {

                    decrypted_text = Controller_MA_HOA_DOI_XUNG_TEXT.decryptText(name_algorithm,
                            name_language,
                            encrypted_text, key);

                    decrypted_text_area.setEnabled(true);
                    decrypted_text_area.setText(decrypted_text);
                }

            }
        });
    }

    public void createButtonCreateNewKey() {
        bt_create_new_key = new RoundedButton("KEY MỚI", 25, new Color(58, 205, 34));
        bt_create_new_key.setBounds(510, 82, 101, 37);
        bt_create_new_key.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                key = Controller_MA_HOA_DOI_XUNG_TEXT.createKeyRandom(name_algorithm);
                key_text_field.setText(key);

                if (key.length() > 0) {
                    resetEncryptedTextArea();
                    resetDecryptedTextArea();
                }

            }
        });
    }

    public void createButtonInputKey() {

        bt_input_key = new RoundedButton("NHẬP KEY", 25, new Color(217, 217, 217));
        bt_input_key.setBounds(350, 432, 101, 37);

    }

    public void createButtonHome() {
        bt_home = new RoundedButton("TRANG CHỦ", 15, new Color(136, 196, 230));
        bt_home.setBounds(495, 432, 115, 37);

        bt_home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Frame_Home.showPanel(Frame_Home.panel_ma_hoa_doi_xung);
            }
        });
    }

    public void createTextAreaGroup() {
        createPlainTextArea();
        createEncryptedTextArea();
        createDecryptedTextArea();
    }

    public void createPlainTextArea() {
        plain_text_area = new JTextAreaWithPlaceholder("Nhập đoạn văn bản cần mã hóa tại đây...");
        plain_text_area.setBackground(new Color(217, 217, 217));
        plain_text_area.setFont(new Font("Arial", Font.PLAIN, 16));

        scroll_pane_plain_text_area = new JScrollPane(plain_text_area);
        scroll_pane_plain_text_area.setBounds(19, 150, 592, 70);

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_plain_text_area.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        plain_text_area.setLineWrap(true);
        plain_text_area.setWrapStyleWord(true);

        // Thêm một DocumentListener để xử lý sự kiện khi nội dung thay đổi
        plain_text_area.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                plain_text = plain_text_area.getText();
                // System.out.println(plain_text);

                if (plain_text.isEmpty()) bt_encrypt.setEnabled(false);
                else bt_encrypt.setEnabled(true);

                resetEncryptedTextArea();
                resetDecryptedTextArea();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                plain_text = plain_text_area.getText();
                // System.out.println(plain_text);

                if (plain_text.isEmpty()) bt_encrypt.setEnabled(false);
                else bt_encrypt.setEnabled(true);

                resetEncryptedTextArea();
                resetDecryptedTextArea();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                plain_text = plain_text_area.getText();
                // System.out.println(plain_text);

                if (plain_text.isEmpty()) bt_encrypt.setEnabled(false);
                else bt_encrypt.setEnabled(true);

                resetEncryptedTextArea();
                resetDecryptedTextArea();
            }
        });

    }

    public void createEncryptedTextArea() {
        encrypted_text_area = new JTextArea();
        encrypted_text_area.setBackground(new Color(217, 217, 217));
        encrypted_text_area.setFont(new Font("Arial", Font.PLAIN, 16));

        scroll_pane_encrypted_text_area = new JScrollPane(encrypted_text_area);
        scroll_pane_encrypted_text_area.setBounds(190, 240, 420, 70);

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
        decrypted_text_area.setFont(new Font("Arial", Font.PLAIN, 16));

        scroll_pane_decrypted_text_area = new JScrollPane(decrypted_text_area);
        scroll_pane_decrypted_text_area.setBounds(190, 330, 420, 70);

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_decrypted_text_area.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        decrypted_text_area.setLineWrap(true);
        decrypted_text_area.setWrapStyleWord(true);

        decrypted_text_area.setEnabled(false);
    }

    public void createComboBoxGroup() {
        createComboBoxAlgorithm();
        createComboBoxLanguague();
    }

    public void createComboBoxAlgorithm() {
        Arrays.sort(arr_algorithms);
        combo_box_algorithm = new JComboBox<>(arr_algorithms);
        combo_box_algorithm.setBounds(145, 19, 210, 38);

        combo_box_algorithm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                name_algorithm = combo_box_algorithm.getSelectedItem().toString();
                // System.out.println(name_algorithm);
            }
        });
    }

    public void createComboBoxLanguague() {
        combo_box_language = new JComboBox<>(arr_languages);
        combo_box_language.setBounds(483, 20, 128, 34);

        combo_box_language.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name_language = combo_box_language.getSelectedItem().toString();
                // System.out.println(name_language);
            }
        });
    }

    public void createTextFieldGroup() {
        createTextFieldKey();
    }

    public void createTextFieldKey() {
        key_text_field = new JTextField();
        key_text_field.setBounds(145, 82, 338, 37);
        key_text_field.setFont(new Font("Arial", Font.PLAIN, 16));
        key_text_field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        key_text_field.setEditable(false);
    }

    public void resetTextFieldKey() {
        key = "";
        key_text_field.setText(key);
    }

    public void resetPlainTextArea() {
        plain_text = "";
        plain_text_area.setText(plain_text);
    }

    public void resetEncryptedTextArea() {
        encrypted_text = "";
        encrypted_text_area.setText(encrypted_text);
    }

    public void resetDecryptedTextArea() {
        decrypted_text = "";
        decrypted_text_area.setText(decrypted_text);
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
            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (getText().isEmpty()) {
                        repaint(); // Đảm bảo giao diện được refresh sau khi tập trung
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (getText().isEmpty()) {
                        repaint(); // Đảm bảo giao diện được refresh sau khi mất tập trung
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !hasFocus()) {
                g.setColor(Color.GRAY);
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString(placeholder, getInsets().left + 5, g.getFontMetrics().getHeight() + getInsets().top + 5);
            }
        }
    }
}
