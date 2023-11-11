package view;

import helper.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class Panel_MA_HOA_BAT_DOI_XUNG_FILE extends JPanel {

    private JLabel lb_algorithm_encrypt,
            lb_mode_padding_encrypt,
            lb_algorithm_decrypt,
            lb_mode_padding_decrypt,
            lb_key,
            lb_public_key,
            lb_private_key,
            lb_name_file;

    private JComboBox combo_box_algorithm_encrypt,
            combo_box_mode_padding_encrypt,
            combo_box_algorithm_decrypt,
            combo_box_mode_padding_decrypt;

    private JTextField text_field_key,
            text_field_public_key,
            text_field_private_key;
    private JPanel panel_choose_file;
    private JButton bt_choose_file,
            bt_encrypt,
            bt_decrypt,
            bt_switch_text;
    private JSeparator separator_one;
    private String name_selected_file = "",
            path_selected_file = "",
            path_folder_contain_selected_file = "";

    public Panel_MA_HOA_BAT_DOI_XUNG_FILE(int WIDTH, int HEIGHT) {

        setBackground(new Color(255, 255, 255, 255));
        setBounds(0, 0, WIDTH, HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setLayout(null);
        setVisible(true);

        createLabelGroup();
        createButtonGroup();
        createComboBoxGroup();
        createTextFieldGroup();
        createSeparatorGroup();

        createPanelChooseFile();

        add(bt_encrypt);
        add(bt_decrypt);
        add(bt_switch_text);

        add(separator_one);

        add(panel_choose_file);

        add(lb_algorithm_encrypt);
        add(lb_algorithm_decrypt);
        add(lb_mode_padding_encrypt);
        add(lb_mode_padding_decrypt);
        add(lb_key);
        add(lb_public_key);
        add(lb_private_key);
    }

    public void createLabelGroup() {

        lb_algorithm_encrypt = new JLabel("Chọn giải thuật:");
        lb_algorithm_encrypt.setForeground(Color.BLACK);
        lb_algorithm_encrypt.setHorizontalAlignment(SwingConstants.LEFT);
        lb_algorithm_encrypt.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_algorithm_encrypt.setBounds(16, 107, 117, 27);

        lb_algorithm_decrypt = new JLabel("Chọn giải thuật:");
        lb_algorithm_decrypt.setForeground(Color.BLACK);
        lb_algorithm_decrypt.setHorizontalAlignment(SwingConstants.LEFT);
        lb_algorithm_decrypt.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_algorithm_decrypt.setBounds(373, 107, 117, 27);

        lb_mode_padding_encrypt = new JLabel("Mode/Padding:");
        lb_mode_padding_encrypt.setForeground(Color.BLACK);
        lb_mode_padding_encrypt.setHorizontalAlignment(SwingConstants.LEFT);
        lb_mode_padding_encrypt.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_mode_padding_encrypt.setBounds(16, 161, 117, 27);

        lb_mode_padding_decrypt = new JLabel("Mode/Padding:");
        lb_mode_padding_decrypt.setForeground(Color.BLACK);
        lb_mode_padding_decrypt.setHorizontalAlignment(SwingConstants.LEFT);
        lb_mode_padding_decrypt.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_mode_padding_decrypt.setBounds(373, 161, 117, 27);

        lb_key = new JLabel("Key:");
        lb_key.setForeground(Color.BLACK);
        lb_key.setHorizontalAlignment(SwingConstants.LEFT);
        lb_key.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_key.setBounds(16, 220, 117, 27);

        lb_public_key = new JLabel("Public Key:");
        lb_public_key.setForeground(Color.BLACK);
        lb_public_key.setHorizontalAlignment(SwingConstants.LEFT);
        lb_public_key.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_public_key.setBounds(16, 279, 117, 27);

        lb_private_key = new JLabel("Private Key:");
        lb_private_key.setForeground(Color.BLACK);
        lb_private_key.setHorizontalAlignment(SwingConstants.LEFT);
        lb_private_key.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_private_key.setBounds(373, 279, 117, 27);

    }

    public void createButtonGroup() {
        createButtonSwitchText();
        createButtonEncrypt();
        createButtonDecrypt();
    }

    public void createComboBoxGroup() {

    }

    public void createTextFieldGroup() {

    }

    public void createSeparatorGroup() {
        createSeparatorOne();
    }

    public void createPanelChooseFile() {

        panel_choose_file = new JPanel();
        panel_choose_file.setBounds(16, 21, 662, 54);
        panel_choose_file.setBackground(new Color(217, 217, 217));
        panel_choose_file.setLayout(null);
        panel_choose_file.setVisible(true);

        lb_name_file = new JLabel("");
        lb_name_file.setBounds(125, 20, 500, 15);
        panel_choose_file.add(lb_name_file);

        createButtonChooseFile();
        panel_choose_file.add(bt_choose_file);

    }


    public void resetLayout() {

    }

    public void createSeparatorOne() {
        separator_one = new JSeparator(SwingConstants.VERTICAL);
        separator_one.setBounds(347, 88, 2, 380);
        separator_one.setBackground(Color.BLACK);
        separator_one.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
    }

    public void createButtonSwitchText() {

        bt_switch_text = new RoundedButton("TEXT", 15, new Color(215, 187, 18));
        bt_switch_text.setBounds(550, 432, 115, 35);
        bt_switch_text.setFont(new Font("Arial", Font.ITALIC, 20));
        bt_switch_text.setIcon(new ImageIcon(Image.img_folder));
        bt_switch_text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetLayout();
                Frame_Home.showPanel(Frame_Home.panel_mhbdx_text);
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
        bt_encrypt.setBounds(16, 387, 115, 37);
        bt_encrypt.setIcon(new ImageIcon(Image.img_encrypt));
    }

    public void createButtonDecrypt() {
        bt_decrypt = new RoundedButton("GIẢI MÃ", 0, new Color(217, 217, 217));
        bt_decrypt.setBounds(373, 387, 115, 37);
        bt_decrypt.setIcon(new ImageIcon(Image.img_decrypt));
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
