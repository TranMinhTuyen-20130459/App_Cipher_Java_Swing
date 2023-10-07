package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Frame_Home extends JFrame {
    private int WIDTH = 1000;
    private int HEIGHT = 650;
    private JPanel contentPane;
    private Image img_avatar = new ImageIcon("src/it-nlu/resources/image/it-nlu.png").getImage().getScaledInstance(150, 150,
            Image.SCALE_SMOOTH);


    /**
     * Create the frame.
     */
    public Frame_Home() {

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 15, WIDTH, HEIGHT);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(136, 196, 230));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel_menu = new JPanel();
        panel_menu.setBackground(new Color(136, 196, 230));

        // Tạo đường viền màu đen bao quanh panel_menu
        panel_menu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        panel_menu.setBounds(0, 0, WIDTH / 3, HEIGHT - 40);
        contentPane.add(panel_menu);
        panel_menu.setLayout(null);

        JLabel label_avatar = new JLabel("");
        label_avatar.setHorizontalAlignment(SwingConstants.CENTER);
        label_avatar.setBounds(65, 0, 180, 180);
        label_avatar.setIcon(new ImageIcon(img_avatar));
        panel_menu.add(label_avatar);

        JLabel label_text_1 = new JLabel("AN TOÀN BẢO MẬT HỆ THỐNG THÔNG TIN");
        label_text_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_text_1.setForeground(new Color(255, 255, 255));
        label_text_1.setBounds(0, 150, 330, 100);
        // Tạo một đối tượng Font mới
        Font custom_font_1 = new Font("Arial", Font.BOLD, 14);
        label_text_1.setFont(custom_font_1);
        panel_menu.add(label_text_1);

        JLabel label_text_2 = new JLabel("GV: Ths.Phan Đình Long");
        label_text_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_text_2.setForeground(new Color(9, 27, 229));
        label_text_2.setBounds(0, 180, 330, 100);
        // Tạo một đối tượng Font mới
        Font custom_font_2 = new Font("Arial", Font.PLAIN, 16);
        // Đặt font chữ cho label_text_2
        label_text_2.setFont(custom_font_2);
        panel_menu.add(label_text_2);


    }


}
