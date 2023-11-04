package view;

import controller.Controller_MA_HOA_DOI_XUNG;
import helper.Algorithm;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

public class Panel_MA_HOA_DOI_XUNG_TEXT extends JPanel {
    private JLabel label_chon_giai_thuat,
            label_chon_ngon_ngu,
            label_key,
            label_van_ban_ma_hoa,
            label_van_ban_giai_ma,
            label_chon_mode_padding;
    private JButton bt_encrypt,
            bt_decrypt,
            bt_copy_key,
            bt_create_key,
            bt_home,
            bt_copy_encrypted_text;
    private JTextArea plain_text_area,
            encrypted_text_area,
            decrypted_text_area;
    private JScrollPane scroll_pane_plain_text_area,
            scroll_pane_encrypted_text_area,
            scroll_pane_decrypted_text_area;
    private JTextField key_text_field;
    private JComboBox combo_box_algorithm,
            combo_box_language,
            combo_box_mode_padding;
    private final String[] arr_algorithms = {"Vigenere", "DES", "AES", "TwoFish", "Serpent"};
    private final String[] arr_languages = {"English", "Vietnamese"};
    private final String[] arr_mode_paddings = {
            "ECB/PKCS5",
            "CBC/PKCS5",
            "CFB/PKCS5",
            "OFB/PKCS5",
            "ECB/ISO10126",
            "CBC/ISO10126",
            "CFB/ISO10126",
            "OFB/ISO10126"
    };

    private String plain_text = "",
            encrypted_text = "",
            decrypted_text = "",
            key = "",
            name_algorithm = "",
            name_language = "",
            name_mode_padding = "";

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
        add(label_chon_mode_padding);

        add(scroll_pane_plain_text_area);
        add(scroll_pane_encrypted_text_area);
        add(scroll_pane_decrypted_text_area);

        add(bt_encrypt);
        add(bt_decrypt);
        add(bt_copy_key);
        add(bt_copy_encrypted_text);
        add(bt_home);
        add(bt_create_key);

        add(combo_box_algorithm);
        add(combo_box_language);
        add(combo_box_mode_padding);

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
        label_chon_ngon_ngu.setHorizontalAlignment(SwingConstants.LEFT);
        label_chon_ngon_ngu.setFont(new Font("Arial", Font.PLAIN, 14));
        label_chon_ngon_ngu.setBounds(375, 19, 120, 37);
        label_chon_ngon_ngu.setVisible(false);

        label_key = new JLabel("Key:");
        label_key.setForeground(Color.BLACK);
        label_key.setHorizontalAlignment(SwingConstants.CENTER);
        label_key.setFont(new Font("Arial", Font.PLAIN, 14));
        label_key.setBounds(15, 83, 50, 37);

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

        label_chon_mode_padding = new JLabel("Mode/Padding:");
        label_chon_mode_padding.setForeground(Color.BLACK);
        label_chon_mode_padding.setHorizontalAlignment(SwingConstants.LEFT);
        label_chon_mode_padding.setFont(new Font("Arial", Font.PLAIN, 14));
        label_chon_mode_padding.setBounds(375, 19, 120, 37);

    }

    public void createButtonGroup() {
        createButtonEncrypt();
        createButtonDecrypt();
        createButtonCopyKey();
        createButtonCopyEncryptedText();
        createButtonHome();
        createButtonCreateKey();
    }

    public void createButtonEncrypt() {
        bt_encrypt = new RoundedButton("MÃ HÓA", 0, new Color(217, 217, 217));
        bt_encrypt.setBounds(19, 432, 115, 37);
        bt_encrypt.setEnabled(false);

        bt_encrypt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (bt_encrypt.isEnabled() == true) {

                    key = key_text_field.getText();

                    // Nếu chưa có key MÃ HÓA
                    if (key == null || key.isEmpty()) {

                        if (name_algorithm == null) return;

                        // TH: Nếu là giải thuật Hill, Vigenere
                        if (name_algorithm.equalsIgnoreCase(Algorithm.HILL)
                                || name_algorithm.equalsIgnoreCase(Algorithm.VIGENERE)) {

                            plain_text = plain_text_area.getText();

                            if (plain_text == null || plain_text.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Bạn cần nhập vào đoạn văn bản cần mã hóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                                return;
                            }

                            key = Controller_MA_HOA_DOI_XUNG.createKeyRandomFor_Hill_Vigenere(name_algorithm, name_language, plain_text);

                            if (key == null || key.isEmpty() || key.equalsIgnoreCase("NOT_FOUND_ALGORITHM")) {
                                JOptionPane.showMessageDialog(null, "Không tạo được Key", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            key_text_field.setText(key);

                            encrypted_text = Controller_MA_HOA_DOI_XUNG.encryptTextWithKeyText(name_algorithm, name_language, plain_text, key);

                            if (encrypted_text == null || encrypted_text.isEmpty() || encrypted_text.equalsIgnoreCase("NOT_FOUND_ALGORITHM")) {
                                JOptionPane.showMessageDialog(null, "Mã hóa văn bản không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            encrypted_text_area.setText(encrypted_text);

                            return;
                        }

                        // TH: Nếu là giải thuật AES, DES, TwoFish,Serpent
                        key = Controller_MA_HOA_DOI_XUNG.createKeyRandom(name_algorithm);

                        // TH: Không có lỗi khi tạo key
                        if (key != null) {

                            // TH: Nếu không tìm thấy được GIẢI THUẬT phù hợp
                            if (key.equalsIgnoreCase("NOT_FOUND_ALGORITHM")) {
                                JOptionPane.showMessageDialog(null, "KHÔNG TÌM THẤY GIẢI THUẬT PHÙ HỢP", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                                resetTextFieldKey();
                            }
                            // TH: Tạo được key THÀNH CÔNG
                            else {
                                key_text_field.setText(key);

                                encrypted_text = Controller_MA_HOA_DOI_XUNG.encryptTextWithKeyBase64(name_algorithm,
                                        name_language,
                                        plain_text, key, name_mode_padding);

                                encrypted_text_area.setEnabled(true);
                                encrypted_text_area.setText(encrypted_text);

                                bt_decrypt.setEnabled(true);
                            }
                        }

                        // TH: Xảy ra lỗi trong quá trình tạo key
                        else {
                            JOptionPane.showMessageDialog(null, "ĐÃ XẢY RA LỖI", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            resetTextFieldKey();
                        }
                    }

                    // Nếu đã có key MÃ HÓA
                    else {

                        if (name_algorithm == null) return;

                        // TH: Nếu là giải thuật Hill, Vigenere
                        if (name_algorithm.equalsIgnoreCase(Algorithm.HILL)
                                || name_algorithm.equalsIgnoreCase(Algorithm.VIGENERE)) {

                            encrypted_text = Controller_MA_HOA_DOI_XUNG.encryptTextWithKeyText(name_algorithm, name_language, plain_text, key);

                            if (encrypted_text == null || encrypted_text.isEmpty() || encrypted_text.equalsIgnoreCase("NOT_FOUND_ALGORITHM")) {
                                JOptionPane.showMessageDialog(null, "Mã hóa văn bản không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                // System.out.println(encrypted_text);
                                return;
                            }

                            encrypted_text_area.setText(encrypted_text);

                            return;
                        }

                        // TH: Nếu là giải thuật AES, DES, TwoFish, Serpent
                        encrypted_text = Controller_MA_HOA_DOI_XUNG.encryptTextWithKeyBase64(name_algorithm,
                                name_language,
                                plain_text, key, name_mode_padding);

                        encrypted_text_area.setEnabled(true);
                        encrypted_text_area.setText(encrypted_text);

                        bt_decrypt.setEnabled(true);
                    }

                }
            }
        });
    }

    public void createButtonDecrypt() {
        bt_decrypt = new RoundedButton("GIẢI MÃ", 0, new Color(217, 217, 217));
        bt_decrypt.setBounds(190, 432, 115, 37);
        bt_decrypt.setEnabled(false);

        bt_decrypt.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (bt_decrypt.isEnabled() == true) {

                    key = key_text_field.getText();

                    if (key == null || key.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "CHƯA CÓ KEY ĐỂ GIẢI MÃ", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    } else {
                        encrypted_text = encrypted_text_area.getText();

                        if (name_algorithm == null) return;

                        // TH: Nếu là giải thuật Hill,Viginere
                        if (name_algorithm.equalsIgnoreCase(Algorithm.HILL)
                                || name_algorithm.equalsIgnoreCase(Algorithm.VIGENERE)) {

                            decrypted_text = Controller_MA_HOA_DOI_XUNG.decryptTextWithKeyText(name_algorithm, name_language, encrypted_text, key);
                            decrypted_text_area.setText(decrypted_text);
                            return;

                        }

                        // TH: Nếu là giải thuật AES, DES, TwoFish, Serpent
                        decrypted_text = Controller_MA_HOA_DOI_XUNG.decryptTextWithKeyBase64(name_algorithm,
                                name_language,
                                encrypted_text, key, name_mode_padding);

                        decrypted_text_area.setText(decrypted_text);
                    }

                }

            }
        });
    }

    public void createButtonCopyKey() {
        bt_copy_key = new RoundedButton("COPY", 0, new Color(136, 196, 230));
        bt_copy_key.setBounds(565, 82, 100, 37);
        bt_copy_key.setFont(new Font("Arial", Font.PLAIN, 12));

        bt_copy_key.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                key = key_text_field.getText();
                if (key != null && key.length() > 0) {
                    // Sao chép đoạn văn bản vào clipboard
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection selection = new StringSelection(key);
                    clipboard.setContents(selection, null);
                }
            }
        });
    }

    public void createButtonCopyEncryptedText() {
        bt_copy_encrypted_text = new RoundedButton("COPY", 0, new Color(217, 217, 217));
        bt_copy_encrypted_text.setBounds(615, 240, 75, 70);
        bt_copy_encrypted_text.setFont(new Font("Arial", Font.PLAIN, 12));
        bt_copy_encrypted_text.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                encrypted_text = encrypted_text_area.getText();
                if (encrypted_text != null && encrypted_text.length() > 0) {
                    // Sao chép đoạn văn bản vào clipboard
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection selection = new StringSelection(encrypted_text);
                    clipboard.setContents(selection, null);
                }

            }
        });

    }

    public void createButtonHome() {
        bt_home = new RoundedButton("TRANG CHỦ", 15, new Color(136, 196, 230));
        bt_home.setBounds(550, 432, 115, 37);

        bt_home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetLayout();
                Frame_Home.showPanel(Frame_Home.panel_ma_hoa_doi_xung);
            }
        });
    }

    public void createButtonCreateKey() {
        bt_create_key = new RoundedButton("TẠO KEY", 0, new Color(217, 217, 217));
        bt_create_key.setBounds(400, 82, 120, 37);

        bt_create_key.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (bt_create_key.isEnabled()) {

                    if (name_algorithm == null) return;

                    switch (name_algorithm.toUpperCase()) {

                        case Algorithm.AES:
                        case Algorithm.DES:
                        case Algorithm.TWO_FISH:
                        case Algorithm.SERPENT: {
                            key = Controller_MA_HOA_DOI_XUNG.createKeyRandom(name_algorithm);

                            if (key == null || key.isEmpty() || key.equalsIgnoreCase("NOT_FOUND_ALGORITHM")) {
                                JOptionPane.showMessageDialog(null, "Không tạo được Key", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            key_text_field.setText(key);
                            return;
                        }

                        case Algorithm.VIGENERE:
                        case Algorithm.HILL: {

                            plain_text = plain_text_area.getText();
                            if (plain_text == null || plain_text.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Bạn cần nhập vào đoạn văn bản cần mã hóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                                return;
                            }

                            key = Controller_MA_HOA_DOI_XUNG.createKeyRandomFor_Hill_Vigenere(name_algorithm, name_language, plain_text);

                            if (key == null || key.isEmpty() || key.equalsIgnoreCase("NOT_FOUND_ALGORITHM")) {
                                JOptionPane.showMessageDialog(null, "Không tạo được Key", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            key_text_field.setText(key);
                            return;
                        }

                        default:
                            JOptionPane.showMessageDialog(null, "KHÔNG TÌM THẤY GIẢI THUẬT PHÙ HỢP", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    }
                }

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

        encrypted_text_area.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

                encrypted_text = encrypted_text_area.getText();
                if (encrypted_text.isEmpty()) {
                    bt_decrypt.setEnabled(false);
                    resetDecryptedTextArea();
                } else bt_decrypt.setEnabled(true);

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                encrypted_text = encrypted_text_area.getText();
                if (encrypted_text.isEmpty()) {
                    bt_decrypt.setEnabled(false);
                    resetDecryptedTextArea();
                } else bt_decrypt.setEnabled(true);


            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                encrypted_text = encrypted_text_area.getText();
                if (encrypted_text.isEmpty()) {
                    bt_decrypt.setEnabled(false);
                    resetDecryptedTextArea();
                } else bt_decrypt.setEnabled(true);

            }
        });

    }

    public void createDecryptedTextArea() {
        decrypted_text_area = new JTextArea();
        decrypted_text_area.setBackground(new Color(217, 217, 217));
        decrypted_text_area.setFont(new Font("Arial", Font.PLAIN, 16));
        decrypted_text_area.setEditable(false);

        scroll_pane_decrypted_text_area = new JScrollPane(decrypted_text_area);
        scroll_pane_decrypted_text_area.setBounds(190, 330, 420, 70);

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_decrypted_text_area.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        decrypted_text_area.setLineWrap(true);
        decrypted_text_area.setWrapStyleWord(true);

    }

    public void createComboBoxGroup() {
        createComboBoxAlgorithm();
        createComboBoxLanguage();
        createComboBoxModePadding();
    }

    private void createComboBoxModePadding() {
        combo_box_mode_padding = new JComboBox<>(arr_mode_paddings);
        combo_box_mode_padding.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        combo_box_mode_padding.setFont(new Font("Arial", Font.PLAIN, 14));
        combo_box_mode_padding.setBounds(490, 19, 175, 38);

        name_mode_padding = combo_box_mode_padding.getSelectedItem().toString();

        combo_box_mode_padding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name_mode_padding = combo_box_mode_padding.getSelectedItem().toString();

                resetEncryptedTextArea();
                resetDecryptedTextArea();
            }
        });

    }

    public void createComboBoxAlgorithm() {
        Arrays.sort(arr_algorithms);
        combo_box_algorithm = new JComboBox<>(arr_algorithms);
        combo_box_algorithm.setBounds(145, 19, 210, 38);
        combo_box_algorithm.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        combo_box_algorithm.setFont(new Font("Arial", Font.PLAIN, 14));

        name_algorithm = combo_box_algorithm.getSelectedItem().toString();
        // System.out.println(name_algorithm);

        combo_box_algorithm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                name_algorithm = combo_box_algorithm.getSelectedItem().toString();
                // System.out.println(name_algorithm);
                resetTextFieldKey();
                resetEncryptedTextArea();
                resetDecryptedTextArea();

                // Nếu là giải thuật Hill or Vigenere thì xuất hiện ComboBox để cho phép user chọn ngôn ngữ cần MÃ HÓA
                if (name_algorithm.equalsIgnoreCase("Hill")
                        || name_algorithm.equalsIgnoreCase("Vigenere")) {
                    label_chon_ngon_ngu.setVisible(true);
                    combo_box_language.setVisible(true);

                    label_chon_mode_padding.setVisible(false);
                    combo_box_mode_padding.setVisible(false);
                } else {
                    label_chon_ngon_ngu.setVisible(false);
                    combo_box_language.setVisible(false);

                    label_chon_mode_padding.setVisible(true);
                    combo_box_mode_padding.setVisible(true);
                }
            }
        });
    }

    public void createComboBoxLanguage() {
        combo_box_language = new JComboBox<>(arr_languages);
        combo_box_language.setBounds(490, 19, 175, 38);
        combo_box_language.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        combo_box_language.setVisible(false);
        combo_box_language.setFont(new Font("Arial", Font.PLAIN, 14));

        name_language = combo_box_language.getSelectedItem().toString();
        // System.out.println(name_language);

        combo_box_language.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name_language = combo_box_language.getSelectedItem().toString();
                // System.out.println(name_language);

                resetTextFieldKey();
                resetPlainTextArea();
                resetEncryptedTextArea();
                resetDecryptedTextArea();
            }
        });
    }

    /*
    public void createComboBoxTypeKey() {

        combo_box_type_key = new JComboBox<>(arr_type_key);
        combo_box_type_key.setBounds(370, 82, 120, 37);
        combo_box_type_key.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name_type_key = combo_box_type_key.getSelectedItem().toString();
                // System.out.println(name_type_key);
            }
        });
    }*/

    public void createTextFieldGroup() {
        createTextFieldKey();
    }

    public void createTextFieldKey() {
        key_text_field = new JTextField();
        key_text_field.setBounds(145, 82, 210, 37);
        key_text_field.setFont(new Font("Arial", Font.PLAIN, 16));
        key_text_field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
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

    public void resetComboBoxAlgorithm() {
        combo_box_algorithm.setSelectedIndex(0);
        name_algorithm = combo_box_algorithm.getSelectedItem().toString();
    }

    public void resetComboBoxLanguage() {
        combo_box_language.setSelectedIndex(0);
        name_language = combo_box_language.getSelectedItem().toString();
    }

   /*
   public void resetComboBoxTypeKey() {
        name_type_key = "Base64";
        combo_box_type_key.setSelectedItem(name_type_key);
    }
    */

    public void resetLayout() {
        resetComboBoxAlgorithm();
        resetComboBoxLanguage();
        resetTextFieldKey();
        resetPlainTextArea();
        resetEncryptedTextArea();
        resetDecryptedTextArea();
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
