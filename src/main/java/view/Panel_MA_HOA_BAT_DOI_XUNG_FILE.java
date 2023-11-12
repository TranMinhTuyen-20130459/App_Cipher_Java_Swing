package view;

import controller.Controller_MA_HOA_BAT_DOI_XUNG;
import controller.Controller_MA_HOA_DOI_XUNG;
import helper.Algorithm;
import helper.DecryptFile;
import helper.EncryptFile;
import helper.Image;
import utils.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Panel_MA_HOA_BAT_DOI_XUNG_FILE extends JPanel {
    private JLabel lb_key_size,
            lb_public_key,
            lb_private_key,
            lb_cipher_type,
            lb_name_file,
            lb_algorithm_symmetry,
            lb_combo_box_mode_padding_symmetry;
    private JComboBox combo_box_key_size,
            combo_box_cipher_type,
            combo_box_algorithm_symmetry,
            combo_box_mode_padding_symmetry;
    private JTextArea text_area_public_key,
            text_area_private_key;
    private JScrollPane scroll_pane_public_key,
            scroll_pane_private_key;
    private JPanel panel_choose_file;
    private JButton bt_choose_file,
            bt_create_key,
            bt_encrypt,
            bt_decrypt,
            bt_switch_text,
            bt_reset;
    private final String[] arr_key_sizes = {"512 bit", "1024 bit", "2048 bit", "4096 bit"};
    private final String[] arr_cipher_type = {"RSA", "RSA/ECB/PKCS1Padding"};
    private final String[] arr_algorithm_symmetry = {"AES", "DES", "Blowfish"};
    private final String[] arr_mode_padding_symmetry = {
            "ECB/PKCS5",
            "CBC/PKCS5",
            "CFB/PKCS5",
            "OFB/PKCS5",
            "ECB/ISO10126",
            "CBC/ISO10126",
            "CFB/ISO10126",
            "OFB/ISO10126"
    };
    private String name_key_size = "",
            cipher_type = "",
            public_key = "",
            private_key = "",
            name_selected_file = "",
            path_selected_file = "",
            path_folder_contain_selected_file = "",
            algorithm_symmetry = "",
            mode_padding_symmetry = "";

    public Panel_MA_HOA_BAT_DOI_XUNG_FILE(int WIDTH, int HEIGHT) {

        setBackground(new Color(255, 255, 255, 255));
        setBounds(0, 0, WIDTH, HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setLayout(null);
        setVisible(true);

        createLabelGroup();
        createButtonGroup();
        createComboBoxGroup();
        createTextAreaGroup();

        createPanelChooseFile();

        add(bt_encrypt);
        add(bt_decrypt);
        add(bt_switch_text);
        add(bt_create_key);
        add(bt_reset);

        add(panel_choose_file);

        add(lb_key_size);
        add(lb_cipher_type);
        add(lb_public_key);
        add(lb_private_key);
        add(lb_algorithm_symmetry);
        add(lb_combo_box_mode_padding_symmetry);

        add(combo_box_key_size);
        add(combo_box_cipher_type);
        add(combo_box_algorithm_symmetry);
        add(combo_box_mode_padding_symmetry);

        add(scroll_pane_public_key);
        add(scroll_pane_private_key);
    }

    public void createPanelChooseFile() {

        panel_choose_file = new JPanel();
        panel_choose_file.setBounds(19, 279, 654, 54);
        panel_choose_file.setBackground(new Color(217, 217, 217));
        panel_choose_file.setLayout(null);
        panel_choose_file.setVisible(true);

        lb_name_file = new JLabel("");
        lb_name_file.setBounds(125, 20, 500, 15);
        panel_choose_file.add(lb_name_file);

        createButtonChooseFile();
        panel_choose_file.add(bt_choose_file);

    }

    public void createLabelGroup() {
        lb_key_size = new JLabel("Key Size:");
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

        lb_cipher_type = new JLabel("Cipher Type:");
        lb_cipher_type.setForeground(Color.BLACK);
        lb_cipher_type.setHorizontalAlignment(SwingConstants.LEFT);
        lb_cipher_type.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_cipher_type.setBounds(27, 88, 125, 19);

        lb_algorithm_symmetry = new JLabel("Thuật toán đối xứng:");
        lb_algorithm_symmetry.setForeground(Color.BLACK);
        lb_algorithm_symmetry.setHorizontalAlignment(SwingConstants.LEFT);
        lb_algorithm_symmetry.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_algorithm_symmetry.setBounds(314, 185, 154, 19);

        lb_combo_box_mode_padding_symmetry = new JLabel("Mode/Padding:");
        lb_combo_box_mode_padding_symmetry.setForeground(Color.BLACK);
        lb_combo_box_mode_padding_symmetry.setHorizontalAlignment(SwingConstants.LEFT);
        lb_combo_box_mode_padding_symmetry.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_combo_box_mode_padding_symmetry.setBounds(519, 185, 154, 19);

    }

    public void createButtonGroup() {
        createButtonCreateKey();
        createButtonSwitchText();
        createButtonEncrypt();
        createButtonDecrypt();
        createButtonReset();
    }

    public void createComboBoxGroup() {
        createComboBoxKeySize();
        createComboBoxCipherType();
        createComboBoxAlgorithmSymmetry();
        createComboBoxModePaddingSymmetry();
    }

    public void createTextAreaGroup() {
        createTextAreaPublicKey();
        createTextAreaPrivateKey();
    }


    public void createComboBoxCipherType() {
        combo_box_cipher_type = new JComboBox<>(arr_cipher_type);
        combo_box_cipher_type.setFont(new Font("Arial", Font.PLAIN, 14));
        combo_box_cipher_type.setBounds(24, 115, 180, 32);
        combo_box_cipher_type.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        cipher_type = combo_box_cipher_type.getSelectedItem().toString();
        System.out.println("Cipher type: " + cipher_type);
        combo_box_cipher_type.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cipher_type = combo_box_cipher_type.getSelectedItem().toString();
                System.out.println("Cipher type: " + cipher_type);
            }
        });

    }

    public void createComboBoxKeySize() {
        combo_box_key_size = new JComboBox<>(arr_key_sizes);
        combo_box_key_size.setFont(new Font("Arial", Font.PLAIN, 14));
        combo_box_key_size.setBounds(24, 38, 159, 32);
        combo_box_key_size.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        name_key_size = combo_box_key_size.getSelectedItem().toString();
        System.out.println("Key size: " + name_key_size);
        combo_box_key_size.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                name_key_size = combo_box_key_size.getSelectedItem().toString();
                System.out.println("Key size: " + name_key_size);

            }
        });

    }

    public void createComboBoxAlgorithmSymmetry() {
        combo_box_algorithm_symmetry = new JComboBox(arr_algorithm_symmetry);
        combo_box_algorithm_symmetry.setFont(new Font("Arial", Font.PLAIN, 14));
        combo_box_algorithm_symmetry.setBounds(314, 209, 154, 32);
        combo_box_algorithm_symmetry.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        algorithm_symmetry = combo_box_algorithm_symmetry.getSelectedItem().toString();
        System.out.println("Algorithm Symmetry: " + algorithm_symmetry);
        combo_box_algorithm_symmetry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithm_symmetry = combo_box_algorithm_symmetry.getSelectedItem().toString();
                System.out.println("Algorithm Symmetry: " + algorithm_symmetry);
            }
        });

    }

    public void createComboBoxModePaddingSymmetry() {

        combo_box_mode_padding_symmetry = new JComboBox(arr_mode_padding_symmetry);
        combo_box_mode_padding_symmetry.setFont(new Font("Arial", Font.PLAIN, 14));
        combo_box_mode_padding_symmetry.setBounds(519, 209, 154, 32);
        combo_box_mode_padding_symmetry.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        mode_padding_symmetry = combo_box_mode_padding_symmetry.getSelectedItem().toString();
        System.out.println("Mode/Padding :" + mode_padding_symmetry);
        combo_box_mode_padding_symmetry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode_padding_symmetry = combo_box_mode_padding_symmetry.getSelectedItem().toString();
                System.out.println("Mode/Padding :" + mode_padding_symmetry);
            }
        });


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

                    lb_name_file.setText(name_selected_file);

                    // System.out.println("Đường dẫn tuyệt đối đến tệp: " + path_selected_file);
                    // System.out.println("Đường dẫn đến thư mục chứa tệp: " + path_folder_contain_selected_file);
                }
            }
        });
    }

    public void createButtonEncrypt() {
        bt_encrypt = new RoundedButton("MÃ HÓA", 0, new Color(217, 217, 217));
        bt_encrypt.setBounds(19, 432, 115, 37);
        bt_encrypt.setIcon(new ImageIcon(Image.img_encrypt));

        bt_encrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (bt_encrypt.isEnabled()) {

                    public_key = text_area_public_key.getText();

                    if (public_key == null || public_key.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn cần nhập vào PUBLIC KEY", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (name_selected_file == null || path_selected_file == null || path_folder_contain_selected_file == null ||
                            name_selected_file.isEmpty() || path_selected_file.isEmpty() || path_folder_contain_selected_file.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn cần chọn FILE để MÃ HÓA", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // đây là Key của thuật toán đối xứng dùng để mã hóa File
                    String symmetry_key = Controller_MA_HOA_DOI_XUNG.createKeyRandom(algorithm_symmetry);

                    // Lấy thời gian hiện tại
                    Date currentTime = new Date();

                    // Định dạng thời gian thành chuỗi
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    String timestamp = dateFormat.format(currentTime);

                    String dest_file = path_folder_contain_selected_file + "/" + "RSA_MA_HOA_KEY_"
                            + algorithm_symmetry.toUpperCase() + "_" + timestamp + "_" + name_selected_file;

                    int checkEncryptFile = Controller_MA_HOA_BAT_DOI_XUNG.encryptFileWithPublicKeyRSA
                            (path_selected_file, dest_file, algorithm_symmetry, mode_padding_symmetry, symmetry_key, public_key);

                    if (checkEncryptFile == EncryptFile.SUCCESS) {

                        JOptionPane.showMessageDialog(null, "MÃ HÓA FILE THÀNH CÔNG", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        resetSelectedFile();

                    } else if (checkEncryptFile == EncryptFile.ERROR) {

                        JOptionPane.showMessageDialog(null, "MÃ HÓA FILE THẤT BẠI", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        FileUtil.deleteFile(dest_file);
                    }


                }

            }
        });
    }

    public void createButtonDecrypt() {
        bt_decrypt = new RoundedButton("GIẢI MÃ", 0, new Color(217, 217, 217));
        bt_decrypt.setBounds(190, 432, 115, 37);
        bt_decrypt.setIcon(new ImageIcon(Image.img_decrypt));

        bt_decrypt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (bt_decrypt.isEnabled()) {

                    private_key = text_area_private_key.getText();
                    if (private_key == null || private_key.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn cần nhập vào PRIVATE KEY", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (name_selected_file == null || path_selected_file == null || path_folder_contain_selected_file == null ||
                            name_selected_file.isEmpty() || path_selected_file.isEmpty() || path_folder_contain_selected_file.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn cần chọn FILE để GIẢI MÃ", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Lấy thời gian hiện tại
                    Date currentTime = new Date();

                    // Định dạng thời gian thành chuỗi
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    String timestamp = dateFormat.format(currentTime);

                    String dest_file = path_folder_contain_selected_file + "/" + "RSA_GIAI_MA_KEY_"
                            + algorithm_symmetry.toUpperCase() + "_" + timestamp + "_" + name_selected_file;

                    int checkDecryptFile = Controller_MA_HOA_BAT_DOI_XUNG.decryptFileWithPrivateKeyRSA
                            (path_selected_file, dest_file, algorithm_symmetry, mode_padding_symmetry, private_key);

                    if (checkDecryptFile == DecryptFile.SUCCESS) {

                        JOptionPane.showMessageDialog(null, "GIẢI MÃ FILE THÀNH CÔNG", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        resetSelectedFile();

                    } else if (checkDecryptFile == DecryptFile.ERROR) {

                        JOptionPane.showMessageDialog(null, "GIẢI MÃ FILE THẤT BẠI", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        FileUtil.deleteFile(dest_file);

                    }

                }

            }
        });
    }

    public void createButtonCreateKey() {

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

    public void createButtonSwitchText() {

        bt_switch_text = new RoundedButton("TEXT", 15, new Color(215, 187, 18));
        bt_switch_text.setBounds(550, 432, 115, 35);
        bt_switch_text.setFont(new Font("Arial", Font.ITALIC, 20));
        bt_switch_text.setIcon(new ImageIcon(Image.img_text));
        bt_switch_text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                resetLayout();
                Frame_Home.showPanel(Frame_Home.panel_mhbdx_text);
            }
        });

    }

    public void createButtonReset() {

        bt_reset = new RoundedButton("", 0, new Color(217, 217, 217));
        bt_reset.setBounds(370, 432, 110, 37);
        bt_reset.setIcon(new ImageIcon(Image.img_reset));

        bt_reset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                resetLayout();
            }
        });

    }

    public void createTextAreaPublicKey() {
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

    public void resetPublicKeyTextArea() {
        public_key = "";
        text_area_public_key.setText(public_key);
    }

    public void resetPrivateKeyTextArea() {
        private_key = "";
        text_area_private_key.setText(private_key);
    }

    public void resetComboBoxKeySize() {
        combo_box_key_size.setSelectedIndex(0);
        name_key_size = combo_box_key_size.getSelectedItem().toString();
        System.out.println("Key Size: " + name_key_size);
    }

    public void resetComboBoxCipherType() {
        combo_box_cipher_type.setSelectedIndex(0);
        cipher_type = combo_box_cipher_type.getSelectedItem().toString();
        System.out.println("Cipher Type: " + cipher_type);
    }

    public void resetComboBoxAlgorithmSymmetry() {
        combo_box_algorithm_symmetry.setSelectedIndex(0);
        algorithm_symmetry = combo_box_algorithm_symmetry.getSelectedItem().toString();

        System.out.println("Algorithm: " + algorithm_symmetry);
    }

    public void resetComboboxModePaddingSymmetry() {
        combo_box_mode_padding_symmetry.setSelectedIndex(0);
        mode_padding_symmetry = combo_box_mode_padding_symmetry.getSelectedItem().toString();
        System.out.println("Mode/Padding: " + mode_padding_symmetry);
    }

    public void resetSelectedFile() {
        name_selected_file = "";
        path_selected_file = "";
        path_folder_contain_selected_file = "";
        lb_name_file.setText(name_selected_file);
    }

    public void resetLayout() {
        resetComboBoxKeySize();
        resetComboBoxCipherType();

        resetPublicKeyTextArea();
        resetPrivateKeyTextArea();

        resetComboBoxAlgorithmSymmetry();
        resetComboboxModePaddingSymmetry();

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
