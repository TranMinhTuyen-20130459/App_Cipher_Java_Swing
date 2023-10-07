package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Frame_Home extends JFrame {
    private int WIDTH = 1000;
    private int HEIGHT = 650;
    private JPanel panel_content_root;
    private JPanel panel_menu, panel_content, panel_content_top, panel_content_main;
    private Image img_avatar = new ImageIcon("src/it-nlu/resources/image/it-nlu.png").getImage().getScaledInstance(150, 150,
            Image.SCALE_SMOOTH);
    private java.util.List<Color> list_color_of_panel_content_top = new ArrayList<>();
    private int current_color_index = 0;

    /**
     * Create the frame.
     */
    public Frame_Home() {

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 15, WIDTH, HEIGHT);

        panel_content_root = new JPanel();
        panel_content_root.setBackground(new Color(136, 196, 230));
        panel_content_root.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel_content_root);
        panel_content_root.setLayout(null);

        createPanelMenu();
        createPanelContent();

    }

    public void createPanelMenu() {

        panel_menu = new JPanel();
        panel_menu.setBackground(new Color(136, 196, 230));

        // Tạo đường viền màu đen bao quanh panel_menu
        panel_menu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        panel_menu.setBounds(0, 0, WIDTH / 3, HEIGHT - 40);
        panel_content_root.add(panel_menu);
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
        Font custom_font_2 = new Font("Arial", Font.PLAIN, 15);
        // Đặt font chữ cho label_text_2
        label_text_2.setFont(custom_font_2);
        panel_menu.add(label_text_2);

        createPanelMenu_MA_HOA_DOI_XUNG();
        createPanelMenu_MA_HOA_BAT_DOI_XUNG();
        createPanelMenu_MA_HOA_HASH();
        createPanelMenu_CHU_KY_DIEN_TU();

    }

    public void createPanelContent() {

        panel_content = new JPanel();
        panel_content.setBackground(new Color(136, 196, 230));

        // Tạo đường viền màu đen bao quanh panel_menu
        panel_content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        panel_content.setBounds(WIDTH / 3, 0, WIDTH - (WIDTH / 3 + 18), HEIGHT - 40);
        panel_content_root.add(panel_content);
        panel_content.setLayout(null);

        createPanelContentTop();

        // Thêm các màu vào danh sách
        list_color_of_panel_content_top.add(new Color(215, 187, 18));
        list_color_of_panel_content_top.add(new Color(0, 255, 0));
        list_color_of_panel_content_top.add(new Color(136, 196, 230));

        // Tạo một Timer để thực hiện hiệu ứng thay đổi màu
        Timer colorChangeTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Thay đổi màu của panel_content_top
                panel_content_top.setBackground(list_color_of_panel_content_top.get(current_color_index));
                current_color_index = (current_color_index + 1) % list_color_of_panel_content_top.size(); // Di chuyển đến màu tiếp theo
            }
        });
        colorChangeTimer.setRepeats(true);
        colorChangeTimer.start();

    }

    public void createPanelContentTop() {

        panel_content_top = new JPanel();
        panel_content_top.setBackground(new Color(136, 196, 230));

        // Tạo đường viền màu đen bao quanh panel_menu
        panel_content_top.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        panel_content_top.setBounds(10, 10, WIDTH - (WIDTH / 3 + 38), HEIGHT / 7);
        panel_content.add(panel_content_top);
        panel_content_top.setLayout(null);

        JLabel label_author = new JLabel("Bản quyền thuộc về:");
        label_author.setForeground(Color.BLACK);
        label_author.setFont(new Font("Arial", Font.PLAIN, 15));
        label_author.setBounds(20, 10, 180, 20);
        panel_content_top.add(label_author);

        JLabel label_authorName = new JLabel("Trần Minh Tuyên");
        label_authorName.setForeground(new Color(9, 27, 229));
        label_authorName.setFont(new Font("Arial", Font.PLAIN, 15));
        label_authorName.setBounds(200, 10, 250, 20);
        panel_content_top.add(label_authorName);

        JLabel label_studentID = new JLabel("Mã số sinh viên:");
        label_studentID.setForeground(Color.BLACK);
        label_studentID.setFont(new Font("Arial", Font.PLAIN, 15));
        label_studentID.setBounds(20, 30, 180, 20);
        panel_content_top.add(label_studentID);

        JLabel label_studentIDValue = new JLabel("20130459");
        label_studentIDValue.setForeground(new Color(9, 27, 229));
        label_studentIDValue.setFont(new Font("Arial", Font.PLAIN, 15));
        label_studentIDValue.setBounds(200, 30, 250, 20);
        panel_content_top.add(label_studentIDValue);

        JLabel label_department = new JLabel("Khoa:");
        label_department.setForeground(Color.BLACK);
        label_department.setFont(new Font("Arial", Font.PLAIN, 15));
        label_department.setBounds(20, 50, 180, 20);
        panel_content_top.add(label_department);

        JLabel label_departmentValue = new JLabel("Công Nghệ Thông Tin");
        label_departmentValue.setForeground(new Color(9, 27, 229));
        label_departmentValue.setFont(new Font("Arial", Font.PLAIN, 15));
        label_departmentValue.setBounds(200, 50, 250, 20);
        panel_content_top.add(label_departmentValue);

    }

    public void createPanelMenu_MA_HOA_DOI_XUNG() {

        JPanel panel_menu_doi_xung = new JPanel();
        panel_menu_doi_xung.setForeground(new Color(255, 255, 255));
        panel_menu_doi_xung.setBackground(new Color(0, 139, 139));
        panel_menu_doi_xung.setBounds(5, 250, WIDTH / 3 - 10, HEIGHT / 7);

        panel_menu_doi_xung.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panel_menu_doi_xung.addMouseListener(new PanelButtonMouseAdapter(panel_menu_doi_xung) {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        panel_menu.add(panel_menu_doi_xung);
        panel_menu_doi_xung.setLayout(null);

        JLabel label_ma_hoa_doi_xung = new JLabel("MÃ HÓA ĐỐI XỨNG");
        label_ma_hoa_doi_xung.setForeground(Color.BLACK);
        label_ma_hoa_doi_xung.setHorizontalAlignment(SwingConstants.CENTER);
        label_ma_hoa_doi_xung.setFont(new Font("Arial", Font.PLAIN, 16));
        label_ma_hoa_doi_xung.setBounds(0, 25, WIDTH / 3, 40);
        panel_menu_doi_xung.add(label_ma_hoa_doi_xung);

    }

    public void createPanelMenu_MA_HOA_BAT_DOI_XUNG() {

        JPanel panel_menu_bat_doi_xung = new JPanel();
        panel_menu_bat_doi_xung.setForeground(new Color(255, 255, 255));
        panel_menu_bat_doi_xung.setBackground(new Color(0, 139, 139));
        panel_menu_bat_doi_xung.setBounds(5, 250 + (HEIGHT / 7), WIDTH / 3 - 10, HEIGHT / 7);

        panel_menu_bat_doi_xung.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panel_menu_bat_doi_xung.addMouseListener(new PanelButtonMouseAdapter(panel_menu_bat_doi_xung) {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        panel_menu.add(panel_menu_bat_doi_xung);
        panel_menu_bat_doi_xung.setLayout(null);

        JLabel label_ma_hoa_bat_doi_xung = new JLabel("MÃ HÓA BẤT ĐỐI XỨNG");
        label_ma_hoa_bat_doi_xung.setForeground(Color.BLACK);
        label_ma_hoa_bat_doi_xung.setHorizontalAlignment(SwingConstants.CENTER);
        label_ma_hoa_bat_doi_xung.setFont(new Font("Arial", Font.PLAIN, 16));
        label_ma_hoa_bat_doi_xung.setBounds(0, 25, WIDTH / 3, 40);
        panel_menu_bat_doi_xung.add(label_ma_hoa_bat_doi_xung);

    }

    public void createPanelMenu_MA_HOA_HASH() {

        JPanel panel_menu_hash = new JPanel();
        panel_menu_hash.setForeground(new Color(255, 255, 255));
        panel_menu_hash.setBackground(new Color(0, 139, 139));
        panel_menu_hash.setBounds(5, 250 + (HEIGHT / 7) * 2, WIDTH / 3 - 10, HEIGHT / 7);

        panel_menu_hash.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panel_menu_hash.addMouseListener(new PanelButtonMouseAdapter(panel_menu_hash) {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        panel_menu.add(panel_menu_hash);
        panel_menu_hash.setLayout(null);

        JLabel label_ma_hoa_hash = new JLabel("MÃ HÓA HASH");
        label_ma_hoa_hash.setForeground(Color.BLACK);
        label_ma_hoa_hash.setHorizontalAlignment(SwingConstants.CENTER);
        label_ma_hoa_hash.setFont(new Font("Arial", Font.PLAIN, 16));
        label_ma_hoa_hash.setBounds(0, 25, WIDTH / 3, 40);
        panel_menu_hash.add(label_ma_hoa_hash);

    }

    public void createPanelMenu_CHU_KY_DIEN_TU() {
        JPanel panel_menu_chu_ky_dien_tu = new JPanel();
        panel_menu_chu_ky_dien_tu.setForeground(new Color(255, 255, 255));
        panel_menu_chu_ky_dien_tu.setBackground(new Color(0, 139, 139));
        panel_menu_chu_ky_dien_tu.setBounds(5, 250 + (HEIGHT / 7) * 3, WIDTH / 3 - 10, HEIGHT / 8);

        panel_menu_chu_ky_dien_tu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panel_menu_chu_ky_dien_tu.addMouseListener(new PanelButtonMouseAdapter(panel_menu_chu_ky_dien_tu) {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        panel_menu.add(panel_menu_chu_ky_dien_tu);
        panel_menu_chu_ky_dien_tu.setLayout(null);

        JLabel label_chu_ky_dien_tu = new JLabel("CHỮ KÝ ĐIỆN TỬ");
        label_chu_ky_dien_tu.setForeground(Color.BLACK);
        label_chu_ky_dien_tu.setHorizontalAlignment(SwingConstants.CENTER);
        label_chu_ky_dien_tu.setFont(new Font("Arial", Font.PLAIN, 16));
        label_chu_ky_dien_tu.setBounds(0, 25, WIDTH / 3, 40);
        panel_menu_chu_ky_dien_tu.add(label_chu_ky_dien_tu);
    }

    private class PanelButtonMouseAdapter extends MouseAdapter {
        JPanel panel;

        public PanelButtonMouseAdapter(JPanel panel) {
            this.panel = panel;
        }

        @Override
        // khi rê chuột vào panel
        public void mouseEntered(MouseEvent e) {
            panel.setBackground(new Color(112, 128, 144)); // màu xanh lá
        }

        @Override
        // khi thả chuột ra khỏi panel
        public void mouseExited(MouseEvent e) {
            panel.setBackground(new Color(0, 139, 139));
        }

        @Override
        // khi nhấn chuột trong panel
        public void mousePressed(MouseEvent e) {
            panel.setBackground(new Color(60, 179, 113));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            panel.setBackground(new Color(112, 128, 14));
        }

    }

}
