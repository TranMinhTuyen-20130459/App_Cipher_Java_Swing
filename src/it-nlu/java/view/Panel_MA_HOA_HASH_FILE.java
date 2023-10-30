package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class Panel_MA_HOA_HASH_FILE extends JPanel {
    private JLabel label_chon_giai_thuat,
            label_output;
    private JComboBox combo_box_algorithm;

    private JButton bt_hash, bt_copy, bt_home;

    private JTextArea output_text_area;

    private JScrollPane scroll_pane_output_text_area;

    private String output_text = "",
            name_algorithm = "SHA-256";

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

        add(label_chon_giai_thuat);
        add(label_output);

        add(scroll_pane_output_text_area);

        add(bt_hash);
        add(bt_copy);
        add(bt_home);

        add(combo_box_algorithm);
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
        bt_hash.setBounds(250, 248, 144, 34);
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

    public void resetComboBoxAlgorithm() {
        combo_box_algorithm.setSelectedIndex(0);
        name_algorithm = combo_box_algorithm.getSelectedItem().toString();
        // System.out.println(name_algorithm);
    }

    public void resetOutputTextArea() {
        output_text = "";
        output_text_area.setText(output_text);
    }

    public void resetLayout() {
        resetComboBoxAlgorithm();
        resetOutputTextArea();
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
