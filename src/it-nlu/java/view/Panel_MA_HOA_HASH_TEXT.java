package view;

import controller.Controller_MA_HOA_HASH;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class Panel_MA_HOA_HASH_TEXT extends JPanel {

    private JLabel label_chon_giai_thuat,
            label_input,
            label_output;

    private JComboBox combo_box_algorithm;

    private JButton bt_hash, bt_copy, bt_switch_file;

    private JTextArea input_text_area, output_text_area;

    private JScrollPane scroll_pane_input_text_area,
            scroll_pane_output_text_area;

    private String input_text = "",
            output_text = "",
            name_algorithm = "";
    private final String[] arr_algorithms = {"MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512"};

    public Panel_MA_HOA_HASH_TEXT(int WIDTH, int HEIGHT) {

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
        add(label_input);
        add(label_output);

        add(scroll_pane_input_text_area);
        add(scroll_pane_output_text_area);

        add(bt_hash);
        add(bt_copy);
        add(bt_switch_file);

        add(combo_box_algorithm);

    }

    public void createLabelGroup() {

        label_chon_giai_thuat = new JLabel("Chọn giải thuật:");
        label_chon_giai_thuat.setForeground(Color.BLACK);
        label_chon_giai_thuat.setHorizontalAlignment(SwingConstants.CENTER);
        label_chon_giai_thuat.setFont(new Font("Arial", Font.PLAIN, 14));
        label_chon_giai_thuat.setBounds(0, 19, 145, 37);

        label_input = new JLabel("Input:");
        label_input.setForeground(Color.BLACK);
        label_input.setHorizontalAlignment(SwingConstants.CENTER);
        label_input.setFont(new Font("Arial", Font.PLAIN, 14));
        label_input.setBounds(-5, 88, 87, 21);

        label_output = new JLabel("Output:");
        label_output.setForeground(Color.BLACK);
        label_output.setHorizontalAlignment(SwingConstants.CENTER);
        label_output.setFont(new Font("Arial", Font.PLAIN, 14));
        label_output.setBounds(0, 276, 87, 21);


    }

    public void createButtonGroup() {
        createButtonHash();
        createButtonCopy();
        createButtonSwitchFile();
    }

    public void createTextAreaGroup() {
        createInputTextArea();
        createOutputTextArea();
    }

    public void createComboBoxGroup() {
        createComboBoxAlgorithm();
    }

    public void createComboBoxAlgorithm() {
        combo_box_algorithm = new JComboBox<>(arr_algorithms);
        combo_box_algorithm.setBounds(145, 19, 210, 38);
        combo_box_algorithm.setFont(new Font("Arial", Font.PLAIN, 14));
        combo_box_algorithm.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        name_algorithm = combo_box_algorithm.getSelectedItem().toString();
        // System.out.println(name_algorithm);
        combo_box_algorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name_algorithm = combo_box_algorithm.getSelectedItem().toString();

                resetOutputTextArea();
            }
        });
    }

    public void createInputTextArea() {
        input_text_area = new JTextArea();
        input_text_area.setBackground(new Color(217, 217, 217));
        input_text_area.setFont(new Font("Arial", Font.PLAIN, 16));

        scroll_pane_input_text_area = new JScrollPane(input_text_area);
        scroll_pane_input_text_area.setBounds(22, 109, 587, 123);

        // Thiết lập hiển thị thanh cuộn theo cần thiết
        scroll_pane_input_text_area.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Đảm bảo thanh cuộn sẽ hiển thị khi nội dung vượt quá kích thước của JTextArea
        input_text_area.setLineWrap(true);
        input_text_area.setWrapStyleWord(true);

        input_text_area.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                input_text = input_text_area.getText();
                resetOutputTextArea();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                input_text = input_text_area.getText();
                resetOutputTextArea();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                input_text = input_text_area.getText();
                resetOutputTextArea();
            }
        });

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
        bt_hash = new RoundedButton("HASH", 15, new Color(9, 135, 232));
        bt_hash.setBounds(250, 248, 144, 34);
        bt_hash.setFont(new Font("Arial", Font.BOLD, 14));
        bt_hash.setForeground(Color.WHITE);
        bt_hash.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (bt_hash.isEnabled() == true) {

                    input_text = input_text_area.getText();

                    if (name_algorithm == null || name_algorithm.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn chưa chọn Giải Thuật !!!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (input_text == null || input_text.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn hãy nhập vào đoạn văn bản cần Hash !!!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    output_text = Controller_MA_HOA_HASH.hashText(name_algorithm, input_text);
                    if (output_text.equalsIgnoreCase("NOT_FOUND_ALGORITHM")) {
                        JOptionPane.showMessageDialog(null, "KHÔNG TÌM THẤY GIẢI THUẬT PHÙ HỢP", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    } else {
                        output_text_area.setText(output_text);
                    }

                }
            }
        });
    }

    public void createButtonCopy() {
        bt_copy = new RoundedButton("COPY", 0, new Color(217, 217, 217));
        bt_copy.setBounds(517, 276, 90, 22);
        bt_copy.setFont(new Font("Arial", Font.PLAIN, 14));

        bt_copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (bt_copy.isEnabled()) {

                    output_text = output_text_area.getText();
                    if (output_text != null && output_text.length() > 0) {
                        // Sao chép đoạn văn bản vào clipboard
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        StringSelection selection = new StringSelection(output_text);
                        clipboard.setContents(selection, null);
                    }
                }

            }
        });
    }

    public void createButtonSwitchFile() {
        bt_switch_file = new RoundedButton("FILE", 15, new Color(215, 187, 18));
        bt_switch_file.setBounds(550, 432, 115, 35);
        bt_switch_file.setFont(new Font("Arial", Font.ITALIC, 20));

        bt_switch_file.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                resetLayout();
                Frame_Home.showPanel(Frame_Home.panel_ma_hoa_hash_file);
            }
        });
    }

    public void resetComboBoxAlgorithm() {
        combo_box_algorithm.setSelectedIndex(0);
        name_algorithm = combo_box_algorithm.getSelectedItem().toString();
        // System.out.println(name_algorithm);
    }

    public void resetInputTextArea() {
        input_text = "";
        input_text_area.setText(input_text);
    }

    public void resetOutputTextArea() {
        output_text = "";
        output_text_area.setText(output_text);
    }

    public void resetLayout() {
        resetComboBoxAlgorithm();
        resetInputTextArea();
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
