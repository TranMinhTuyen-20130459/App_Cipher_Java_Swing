package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class Panel_MA_HOA_HASH_FILE extends JPanel {

    private JPanel panel_choose_file;
    private JLabel label_chon_giai_thuat,
            label_output,
            label_name_file;
    private JComboBox combo_box_algorithm;

    private JButton bt_hash, bt_copy, bt_home, bt_choose_file;

    private JTextArea output_text_area;

    private JScrollPane scroll_pane_output_text_area;

    private String output_text = "",
            name_algorithm = "SHA-256",
            name_selected_file = "",
            path_selected_file = "";
    private final String[] arr_algorithms = {"SHA-256", "MD5"};

    public Panel_MA_HOA_HASH_FILE(int WIDTH, int HEIGHT) {

        setBackground(new Color(255, 255, 255, 255));
        setBounds(0, 0, WIDTH, HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setLayout(null);
        setVisible(true);

        createLabelGroup();
        createButtonGroup();
        createTextAreaGroup();
        createComboBoxGroup();
        createPanelChooseFile();

        add(label_chon_giai_thuat);
        add(label_output);

        add(scroll_pane_output_text_area);

        add(bt_hash);
        add(bt_copy);
        add(bt_home);

        add(combo_box_algorithm);

        add(panel_choose_file);
    }

    public void createLabelGroup() {

        label_chon_giai_thuat = new JLabel("Chọn giải thuật:");
        label_chon_giai_thuat.setForeground(Color.BLACK);
        label_chon_giai_thuat.setHorizontalAlignment(SwingConstants.CENTER);
        label_chon_giai_thuat.setFont(new Font("Arial", Font.PLAIN, 14));
        label_chon_giai_thuat.setBounds(0, 19, 145, 37);

        label_output = new JLabel("Output:");
        label_output.setForeground(Color.BLACK);
        label_output.setHorizontalAlignment(SwingConstants.CENTER);
        label_output.setFont(new Font("Arial", Font.PLAIN, 14));
        label_output.setBounds(0, 276, 87, 21);

    }

    public void createButtonGroup() {
        createButtonHash();
        createButtonCopy();
        createButtonHome();
    }

    public void createTextAreaGroup() {
        createOutputTextArea();
    }

    public void createComboBoxGroup() {
        createComboBoxAlgorithm();
    }

    public void createComboBoxAlgorithm() {
        combo_box_algorithm = new JComboBox<>(arr_algorithms);
        combo_box_algorithm.setBounds(145, 19, 210, 38);
    }

    public void createOutputTextArea() {
        output_text_area = new JTextArea();
        output_text_area.setBackground(new Color(217, 217, 217));
        output_text_area.setFont(new Font("Arial", Font.PLAIN, 16));
        output_text_area.setEditable(false);

        scroll_pane_output_text_area = new JScrollPane(output_text_area);
        scroll_pane_output_text_area.setBounds(22, 297, 587, 123);

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_output_text_area.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        output_text_area.setLineWrap(true);
        output_text_area.setWrapStyleWord(true);

    }

    public void createButtonHash() {
        bt_hash = new RoundedButton("HASH", 15, new Color(229, 117, 216));
        bt_hash.setBounds(245, 218, 144, 34);
        bt_hash.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    public void createButtonCopy() {
        bt_copy = new RoundedButton("COPY", 0, new Color(217, 217, 217));
        bt_copy.setBounds(517, 276, 90, 22);
        bt_copy.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    public void createButtonHome() {
        bt_home = new RoundedButton("TRANG CHỦ", 15, new Color(136, 196, 230));
        bt_home.setBounds(495, 432, 115, 35);

        bt_home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetLayout();
                Frame_Home.showPanel(Frame_Home.panel_ma_hoa_hash);
            }
        });
    }

    public void createPanelChooseFile() {
        panel_choose_file = new JPanel();
        panel_choose_file.setBounds(23, 119, 594, 54);
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

                    label_name_file.setText(name_selected_file);

                    // System.out.println("Đường dẫn tuyệt đối đến tệp: " + path_selected_file);
                    // System.out.println("Đường dẫn đến thư mục chứa tệp: " + path_folder_contain_selected_file);
                }
            }
        });

    }

    public void resetComboBoxAlgorithm() {
        combo_box_algorithm.setSelectedIndex(0);
        name_algorithm = combo_box_algorithm.getSelectedItem().toString();
        // System.out.println(name_algorithm);
    }

    public void resetOutputTextArea() {
        output_text = "";
        output_text_area.setText(output_text);
    }

    public void resetSelectedFile() {
        name_selected_file = "";
        path_selected_file = "";
        label_name_file.setText(name_selected_file);
    }

    public void resetLayout() {
        resetComboBoxAlgorithm();
        resetOutputTextArea();
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
