package View;

import Controller.JDBCConnection; // Import the JDBCConnection class
import Model.NhanVien;
import Model.QuanLi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DangNhap extends JFrame implements ActionListener {
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;

    public DangNhap() {
    	 setTitle("Đăng nhập");
         setSize(350, 230);
         setLocationRelativeTo(null);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         setBackground(new Color(253,245,230)); // Màu nền nhạt

         JPanel panel = new JPanel(new GridBagLayout());
         panel.setBackground(new Color(253,245,230)); // Màu nền nhạt

         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(10, 20, 10, 20); // Khoảng cách giữa các thành phần

         usernameLabel = new JLabel("Tên đăng nhập:");
         usernameLabel.setForeground(new Color(50, 50, 50)); // Màu chữ tối
         gbc.gridx = 0;
         gbc.gridy = 0;
         panel.add(usernameLabel, gbc);

         usernameField = new JTextField(15);
         gbc.gridx = 1;
         gbc.gridy = 0;
         panel.add(usernameField, gbc);

         passwordLabel = new JLabel("Mật khẩu:");
         passwordLabel.setForeground(new Color(50, 50, 50)); // Màu chữ tối
         gbc.gridx = 0;
         gbc.gridy = 1;
         panel.add(passwordLabel, gbc);

         passwordField = new JPasswordField(15);
         gbc.gridx = 1;
         gbc.gridy = 1;
         panel.add(passwordField, gbc);

         loginButton = new JButton("Đăng nhập");
         loginButton.setBackground(new Color(233,150,122)); // Đổi màu nền của nút Đăng nhập thành màu cam
         loginButton.setForeground(Color.DARK_GRAY); // Đổi màu chữ của nút Đăng nhập thành màu trắng
         gbc.gridx = 0;
         gbc.gridy = 2;
         panel.add(loginButton, gbc);

         cancelButton = new JButton("Hủy bỏ");
         cancelButton.setBackground(new Color(233,150,122)); 
         cancelButton.setForeground(Color.DARK_GRAY);
         gbc.gridx = 1;
         gbc.gridy = 2;
         panel.add(cancelButton, gbc);

         loginButton.addActionListener(this);
         cancelButton.addActionListener(this);

         add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            String role = checkLogin(username, password);
            if (role != null) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                if (role.equals("nhanvien")) {
                    new NhanVien(role).setVisible(true);
                } else if (role.equals("quanli")) {
                    new QuanLi(role).setVisible(true);
                }
                this.dispose(); // Close the login window
            } else {
                JOptionPane.showMessageDialog(this, "Đăng nhập thất bại! Vui lòng kiểm tra lại thông tin đăng nhập.");
            }
        }
    }

    private String checkLogin(String username, String password) {
        try (Connection conn = JDBCConnection.getConnection()) { // Use JDBCConnection to get the connection
            String sql = "SELECT password, quyen FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPasswordFromDB = rs.getString("password");
                if (verifyPassword(password, hashedPasswordFromDB)) {
                    return rs.getString("quyen"); // Return the user's role if the password is correct
                }
            }
            return null; // Return null if the login fails
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private boolean verifyPassword(String plainPassword, String hashedPasswordFromDB) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(plainPassword.getBytes());

            // Chuyển đổi mảng byte thành dạng hex
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }

            String hashedPasswordInput = sb.toString();

            // So sánh mật khẩu đã mã hóa từ input với mật khẩu đã mã hóa trong cơ sở dữ liệu
            return hashedPasswordInput.equals(hashedPasswordFromDB);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DangNhap().setVisible(true);
        });
    }
}
