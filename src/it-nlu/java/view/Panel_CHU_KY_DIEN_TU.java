package view;

import controller.Controller_CHU_KI_DIEN_TU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class Panel_CHU_KY_DIEN_TU extends JPanel {

    private JLabel lb_input,
            lb_chon_giai_thuat,
            lb_name_file;
    private JScrollPane scroll_pane_input_text_area;
    private JTextArea input_text_area;
    private JComboBox combo_box_algorithm;

    private JButton bt_choose_file, bt_check, bt_reset;

    private JPanel panel_choose_file;
    private final String[] arr_algorithms = {"MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512"};

    private String name_algorithm = "",
            hash_text_input = "",
            name_file_selected = "",
            path_file_selected = "";

    public Panel_CHU_KY_DIEN_TU(int WIDTH, int HEIGHT) {

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

        add(panel_choose_file);

        add(lb_input);
        add(lb_chon_giai_thuat);

        add(scroll_pane_input_text_area);

        add(combo_box_algorithm);

        add(bt_check);
        add(bt_reset);
    }

    public void createLabelGroup() {

        lb_input = new JLabel("Input:");
        lb_input.setForeground(Color.BLACK);
        lb_input.setHorizontalAlignment(SwingConstants.LEFT);
        lb_input.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_input.setBounds(23, 168, 46, 15);

        lb_chon_giai_thuat = new JLabel("Chọn giải thuật:");
        lb_chon_giai_thuat.setForeground(Color.BLACK);
        lb_chon_giai_thuat.setHorizontalAlignment(SwingConstants.LEFT);
        lb_chon_giai_thuat.setFont(new Font("Arial", Font.PLAIN, 14));
        lb_chon_giai_thuat.setBounds(23, 30, 110, 22);

    }

    public void createComboBoxGroup() {
        createComboBoxAlgorithm();
    }

    public void createComboBoxAlgorithm() {
        combo_box_algorithm = new JComboBox(arr_algorithms);
        combo_box_algorithm.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        combo_box_algorithm.setFont(new Font("Arial", Font.PLAIN, 14));
        combo_box_algorithm.setBounds(128, 25, 200, 38);

        name_algorithm = combo_box_algorithm.getSelectedItem().toString();
        combo_box_algorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name_algorithm = combo_box_algorithm.getSelectedItem().toString();
            }
        });

    }

    public void createTextAreaGroup() {
        createInputTextArea();
    }

    private void createInputTextArea() {

        input_text_area = new JTextArea();
        input_text_area.setFont(new Font("Arial", Font.PLAIN, 16));
        input_text_area.setBackground(new Color(217, 217, 217));

        scroll_pane_input_text_area = new JScrollPane(input_text_area);
        scroll_pane_input_text_area.setBounds(23, 185, 435, 109);

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_input_text_area.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        input_text_area.setLineWrap(true);
        input_text_area.setWrapStyleWord(true);

    }

    public void createButtonGroup() {
        createButtonCheck();
        createButtonReset();
    }

    public void createButtonCheck() {

        bt_check = new RoundedButton("KIỂM TRA", 20, new Color(9, 135, 232));
        bt_check.setForeground(Color.WHITE);
        bt_check.setFont(new Font("Arial", Font.BOLD, 12));
        bt_check.setBounds(494, 185, 123, 109);
        bt_check.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (bt_check.isEnabled()) {

                    if (name_algorithm == null || name_algorithm.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn cần chọn GIẢI THUẬT", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (name_file_selected == null || path_file_selected == null ||
                            name_file_selected.isEmpty() || path_file_selected.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn cần chọn FILE để kiểm tra", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    hash_text_input = input_text_area.getText();
                    if (hash_text_input == null || hash_text_input.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn cần nhập vào chuỗi HASH để kiểm tra", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    boolean checkFileValid = Controller_CHU_KI_DIEN_TU.checkFileValid(name_algorithm, path_file_selected, hash_text_input);

                    if (checkFileValid == true)
                        JOptionPane.showMessageDialog(null, "FILE HỢP LỆ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "FILE KHÔNG HỢP LỆ !!!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);

                }

            }
        });

    }

    public void createPanelChooseFile() {
        panel_choose_file = new JPanel();
        panel_choose_file.setBounds(23, 83, 594, 54);
        panel_choose_file.setBackground(new Color(217, 217, 217));
        panel_choose_file.setLayout(null);
        panel_choose_file.setVisible(true);

        lb_name_file = new JLabel("");
        lb_name_file.setBounds(125, 20, 500, 15);
        panel_choose_file.add(lb_name_file);

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

                    name_file_selected = selectedFile.getName();
                    path_file_selected = selectedFile.getAbsolutePath();

                    lb_name_file.setText(name_file_selected);

                    // System.out.println("Đường dẫn tuyệt đối đến tệp: " + path_selected_file);
                    // System.out.println("Đường dẫn đến thư mục chứa tệp: " + path_folder_contain_selected_file);
                }
            }
        });

    }

    public void createButtonReset() {
        bt_reset = new RoundedButton("RESET", 0, new Color(217, 217, 217));
        bt_reset.setBounds(540, 25, 75, 35);
        bt_reset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                resetLayout();
            }
        });
    }

    public void resetComboBoxAlgorithm() {
        combo_box_algorithm.setSelectedIndex(0);
        name_algorithm = combo_box_algorithm.getSelectedItem().toString();
    }

    public void resetInputTextArea() {
        hash_text_input = "";
        input_text_area.setText(hash_text_input);
    }

    public void resetSelectedFile() {
        name_file_selected = "";
        path_file_selected = "";
        lb_name_file.setText(name_file_selected);
    }

    public void resetLayout() {
        resetComboBoxAlgorithm();
        resetSelectedFile();
        resetInputTextArea();
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
