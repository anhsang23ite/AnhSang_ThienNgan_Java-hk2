package View;

import javax.swing.*;

import Controller.JDBCConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DangKy extends JFrame implements ActionListener {
	 private JLabel usernameLabel, passwordLabel, roleLabel;
	    private JTextField usernameField;
	    private JPasswordField passwordField;
	    private JComboBox<String> roleComboBox;
	    private JButton registerButton;

	    public DangKy() {
	    	  setTitle("Đăng ký");
	          setSize(350, 300); // Đã thay đổi kích thước để làm cho form lớn hơn để chứa logo
	          setLocationRelativeTo(null);
	          setDefaultCloseOperation(EXIT_ON_CLOSE);

	          // Panel chứa các thành phần của form và đặt màu nền
	          JPanel panel = new JPanel(new GridBagLayout());
	          panel.setBackground(new Color(253,245,230) ); // Đổi màu nền 

	          GridBagConstraints gbc = new GridBagConstraints();
	          gbc.insets = new Insets(10, 20, 10, 20);

	          // Thêm các thành phần khác như tên đăng nhập, mật khẩu, quyền và nút đăng ký
	          usernameLabel = new JLabel("Tên đăng ký:");
	          gbc.gridx = 0;
	          gbc.gridy = 1;
	          gbc.gridwidth = 1;
	          panel.add(usernameLabel, gbc);

	          usernameField = new JTextField(15);
	          gbc.gridx = 1;
	          gbc.gridy = 1;
	          panel.add(usernameField, gbc);

	          passwordLabel = new JLabel("Mật khẩu:");
	          gbc.gridx = 0;
	          gbc.gridy = 2;
	          panel.add(passwordLabel, gbc);

	          passwordField = new JPasswordField(15);
	          gbc.gridx = 1;
	          gbc.gridy = 2;
	          panel.add(passwordField, gbc);

	          roleLabel = new JLabel("Quyền:");
	          gbc.gridx = 0;
	          gbc.gridy = 3;
	          panel.add(roleLabel, gbc);

	          String[] roles = {"nhanvien", "quanli"};
	          roleComboBox = new JComboBox<>(roles);
	          gbc.gridx = 1;
	          gbc.gridy = 3;
	          panel.add(roleComboBox, gbc);

	          registerButton = new JButton("Đăng ký");
	          registerButton.setBackground(new Color(233,150,122)); // Đổi màu nền của nút Đăng ký
	          registerButton.setForeground(Color.WHITE);
	          gbc.gridx = 0;
	          gbc.gridy = 4;
	          gbc.gridwidth = 2;
	          gbc.fill = GridBagConstraints.HORIZONTAL;
	          panel.add(registerButton, gbc);

	          registerButton.addActionListener(this);

	          add(panel, BorderLayout.CENTER);
	    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem(); // Lấy quyền từ JComboBox

            // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
            String hashedPassword = hashPassword(password);

            // Lưu thông tin đăng ký vào cơ sở dữ liệu
            boolean registered = registerUser(username, hashedPassword, role);
            if (registered) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
                dispose(); // Đóng form đăng ký sau khi đăng ký thành công
            } else {
                JOptionPane.showMessageDialog(this, "Đăng ký thất bại!");
            }
        }
    }

    private boolean registerUser(String username, String password, String role) {
        return JDBCConnection.registerUser(username, password, role);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());

            // Chuyển đổi mảng byte thành dạng hex
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DangKy().setVisible(true);
        });
    }
}
