package view;

import controller.Controller_MA_HOA_DOI_XUNG;
import helper.DecryptFile;
import helper.EncryptFile;
import utils.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.Arrays;

public class Panel_MA_HOA_DOI_XUNG_FILE extends JPanel {

    private JPanel panel_choose_file;

    private JLabel label_chon_giai_thuat,
            label_chon_ngon_ngu,
            label_key,
            label_name_file;
    private JButton bt_encrypt,
            bt_decrypt,
            bt_input_key,
            bt_copy_key,
            bt_home,
            bt_choose_file;

    private JTextField key_text_field;
    private JComboBox combo_box_algorithm,
            combo_box_language;
    private String[] arr_algorithms = {"Vigenere", "Hill", "DES", "AES", "TwoFish"};

    private String[] arr_languages = {"English", "Vietnamese"};

    private String path_selected_file = "",
            name_selected_file = "",
            path_folder_contain_selected_file = "",
            key = "",
            name_algorithm = "AES",
            name_language = "English";

    public Panel_MA_HOA_DOI_XUNG_FILE(int WIDTH, int HEIGHT) {
        setBackground(new Color(255, 255, 255, 255));
        setBounds(0, 0, WIDTH, HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setLayout(null);
        setVisible(true);

        createLabelGroup();
        createButtonGroup();
        createComboBoxGroup();
        createTextFieldGroup();
        createPanelChooseFile();

        add(label_chon_giai_thuat);
        // add(label_chon_ngon_ngu);
        add(label_key);

        add(combo_box_algorithm);
        // add(combo_box_language);
        add(key_text_field);

        add(bt_copy_key);
        add(bt_encrypt);
        add(bt_decrypt);
        add(bt_input_key);
        add(bt_home);

        add(panel_choose_file);
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

    }

    public void createButtonGroup() {
        createButtonEncrypt();
        createButtonDecrypt();
        createButtonCopyKey();
        createButtonHome();
        createButtonInputKey();
    }

    public void createButtonEncrypt() {
        bt_encrypt = new Panel_MA_HOA_DOI_XUNG_FILE.RoundedButton("MÃ HÓA", 25, new Color(217, 217, 217));
        bt_encrypt.setBounds(19, 432, 115, 37);

        bt_encrypt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (bt_encrypt.isEnabled() == true) {

                    // Nếu user đã chọn File
                    if (name_selected_file.length() > 0 && path_selected_file.length() > 0 && path_folder_contain_selected_file.length() > 0) {

                        String dest_file = path_folder_contain_selected_file + "/" + name_algorithm.toUpperCase() + "_FILE_ENCRYPT_" + name_selected_file;

                        key = key_text_field.getText();

                        // Nếu chưa có key MÃ HÓA
                        if (key.isEmpty()) {

                            key = Controller_MA_HOA_DOI_XUNG.createKeyRandom(name_algorithm);

                            int check_encrypted_file = Controller_MA_HOA_DOI_XUNG.encryptFile(name_algorithm, name_language, path_selected_file, dest_file, key);

                            // TH: MÃ HÓA FILE THÀNH CÔNG
                            if (check_encrypted_file == EncryptFile.SUCCESS) {

                                JOptionPane.showMessageDialog(null, "MÃ HÓA FILE THÀNH CÔNG", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                key_text_field.setText(key);
                                resetSelectedFile(); // loại bỏ file đã được chọn bởi user

                            }
                            // TH: MÃ HÓA FILE THẤT BẠI
                            else if (check_encrypted_file == EncryptFile.ERROR) {

                                JOptionPane.showMessageDialog(null, "MÃ HÓA FILE THẤT BẠI", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                resetTextFieldKey();
                                FileUtil.deleteFile(dest_file); // đảm bảo không có File được tạo ra nếu đã MÃ HÓA THẤT BẠI

                            }
                            // TH: KHÔNG CÓ GIẢI THUẬT PHÙ HỢP ĐỂ MÃ HÓA FILE
                            else if (check_encrypted_file == EncryptFile.NOT_FOUND_ALGORITHM) {

                                JOptionPane.showMessageDialog(null, "KHÔNG TÌM THẤY GIẢI THUẬT PHÙ HỢP", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                                resetTextFieldKey();

                            }

                        }

                        // Nếu đã có key MÃ HÓA
                        else {

                            int check_encrypted_file = Controller_MA_HOA_DOI_XUNG.encryptFile(name_algorithm, name_language, path_selected_file, dest_file, key);

                            if (check_encrypted_file == EncryptFile.SUCCESS) {
                                JOptionPane.showMessageDialog(null, "MÃ HÓA FILE THÀNH CÔNG", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                resetSelectedFile(); // loại bỏ file đã được chọn bởi user

                            } else if (check_encrypted_file == EncryptFile.ERROR) {

                                JOptionPane.showMessageDialog(null, "MÃ HÓA FILE THẤT BẠI", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                FileUtil.deleteFile(dest_file); // đảm bảo không có File được tạo ra nếu đã MÃ HÓA THẤT BẠI

                            } else if (check_encrypted_file == EncryptFile.NOT_FOUND_ALGORITHM) {
                                JOptionPane.showMessageDialog(null, "KHÔNG TÌM THẤY GIẢI THUẬT PHÙ HỢP", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                            }
                        }

                    }

                    // Nếu user chưa chọn File
                    else {
                        JOptionPane.showMessageDialog(null, "BẠN CHƯA CHỌN FILE ĐỂ MÃ HÓA", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
        });
    }

    public void createButtonDecrypt() {
        bt_decrypt = new Panel_MA_HOA_DOI_XUNG_FILE.RoundedButton("GIẢI MÃ", 25, new Color(217, 217, 217));
        bt_decrypt.setBounds(190, 432, 115, 37);

        bt_decrypt.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (bt_decrypt.isEnabled() == true) {

                    // Nếu đã chọn File
                    if (name_selected_file.length() > 0 && path_selected_file.length() > 0 && path_folder_contain_selected_file.length() > 0) {

                        key = key_text_field.getText();

                        // Nếu chưa có key để GIẢI MÃ
                        if (key.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "BẠN HÃY NHẬP KEY ĐỂ GIẢI MÃ FILE", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        }
                        // Nếu đã có key đã GIẢI MÃ
                        else {
                            String dest_file = path_folder_contain_selected_file + "/" + name_algorithm.toUpperCase() + "_FILE_DECRYPT_" + name_selected_file;
                            int check_decrypted_file = Controller_MA_HOA_DOI_XUNG.decryptFile(name_algorithm, name_language, path_selected_file, dest_file, key);

                            // TH: GIẢI MÃ FILE THÀNH CÔNG
                            if (check_decrypted_file == DecryptFile.SUCCESS) {
                                JOptionPane.showMessageDialog(null, "GIẢI MÃ FILE THÀNH CÔNG", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                resetSelectedFile();
                            }
                            // TH: GIẢI MÃ FILE THẤT BẠI
                            else if (check_decrypted_file == DecryptFile.ERROR) {
                                JOptionPane.showMessageDialog(null, "GIẢI MÃ FILE THẤT BẠI", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                FileUtil.deleteFile(dest_file); // đảm bảo không có File được tạo ra nếu GIẢI MÃ FILE THẤT BẠI
                            }
                            // TH: KHÔNG CÓ GIẢI THUẬT PHÙ HỢP ĐỂ GIẢI MÃ FILE
                            else if (check_decrypted_file == DecryptFile.NOT_FOUND_ALGORITHM) {
                                JOptionPane.showMessageDialog(null, "KHÔNG TÌM THẤY GIẢI THUẬT PHÙ HỢP", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                            }

                        }

                    }
                    // Nếu chưa chọn File
                    else {
                        JOptionPane.showMessageDialog(null, "BẠN CHƯA CHỌN FILE ĐỂ GIẢI MÃ", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
    }

    public void createButtonCopyKey() {
        bt_copy_key = new Panel_MA_HOA_DOI_XUNG_FILE.RoundedButton("COPY", 25, new Color(136, 196, 230));
        bt_copy_key.setBounds(510, 82, 101, 37);
        bt_copy_key.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                key = key_text_field.getText();
                if (key.length() > 0) {
                    // Sao chép đoạn văn bản vào clipboard
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection selection = new StringSelection(key);
                    clipboard.setContents(selection, null);
                }
            }
        });
    }

    public void createButtonInputKey() {

        bt_input_key = new Panel_MA_HOA_DOI_XUNG_FILE.RoundedButton("TẠO KEY", 25, new Color(217, 217, 217));
        bt_input_key.setBounds(350, 432, 101, 37);

        bt_input_key.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String key_text_input = JOptionPane.showInputDialog(null, "Nhập KEY:");

                if (key_text_input == null || key_text_input.isEmpty()) {
                } else {


                }


            }
        });

    }

    public void createButtonHome() {
        bt_home = new RoundedButton("TRANG CHỦ", 15, new Color(136, 196, 230));
        bt_home.setBounds(495, 432, 115, 37);
        bt_home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetLayout();
                Frame_Home.showPanel(Frame_Home.panel_ma_hoa_doi_xung);
            }
        });
    }

    public void createComboBoxGroup() {
        createComboBoxAlgorithm();
        createComboBoxLanguage();
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

                resetTextFieldKey();
            }
        });
    }

    public void createComboBoxLanguage() {
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
    }

    public void createPanelChooseFile() {
        panel_choose_file = new JPanel();
        panel_choose_file.setBounds(17, 200, 594, 54);
        panel_choose_file.setBackground(new Color(217, 217, 217));
        panel_choose_file.setLayout(null);
        panel_choose_file.setVisible(true);

        label_name_file = new JLabel("");
        label_name_file.setBounds(125, 20, 500, 15);
        panel_choose_file.add(label_name_file);

        createButtonChooseFile();
        panel_choose_file.add(bt_choose_file);
    }

    public void createButtonChooseFile() {

        bt_choose_file = new RoundedButton("CHỌN FILE", 10, new Color(215, 187, 18));
        bt_choose_file.setBounds(10, 12, 100, 30);

        // Thêm sự kiện cho nút "CHỌN FILE"
        bt_choose_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int returnValue = fileChooser.showOpenDialog(null); // Hiển thị cửa sổ chọn tệp

                /***
                 - JFileChooser.APPROVE_OPTION: Được trả về khi người dùng đã chọn một tệp hoặc thư mục và xác nhận việc chọn (thường bằng cách nhấn nút "Open").
                 - JFileChooser.CANCEL_OPTION: Được trả về khi người dùng đã hủy việc chọn hoặc đóng cửa sổ chọn tệp.
                 - JFileChooser.ERROR_OPTION: Được trả về nếu có lỗi xảy ra trong quá trình chọn tệp.
                 */

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // Lấy tệp đã chọn
                    File selectedFile = fileChooser.getSelectedFile();

                    name_selected_file = selectedFile.getName();
                    path_selected_file = selectedFile.getAbsolutePath();
                    path_folder_contain_selected_file = selectedFile.getParent();

                    label_name_file.setText(name_selected_file);

                   /*
                    if (label_name_file.getText().length() > 0) {
                        bt_encrypt.setEnabled(true);
                        bt_decrypt.setEnabled(true);
                    }*/

                    // System.out.println("Đường dẫn tuyệt đối đến tệp: " + path_selected_file);
                    // System.out.println("Đường dẫn đến thư mục chứa tệp: " + path_folder_contain_selected_file);
                }
            }
        });

    }

    public void resetTextFieldKey() {
        key = "";
        key_text_field.setText(key);
    }

    public void resetSelectedFile() {
        name_selected_file = "";
        path_selected_file = "";
        path_folder_contain_selected_file = "";
        label_name_file.setText(name_selected_file);
    }

    public void resetComboBoxAlgorithm() {
        name_algorithm = "AES";
        combo_box_algorithm.setSelectedItem(name_algorithm);
    }

    public void resetComboBoxLanguage() {
        name_language = "English";
        combo_box_language.setSelectedItem(name_language);
    }

    public void resetLayout() {
        resetComboBoxAlgorithm();
        resetComboBoxLanguage();
        resetTextFieldKey();
        resetSelectedFile();
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
