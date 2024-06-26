package View;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TrangSP extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private List<Product> products;
    private List<JLabel> productLabels;

    public TrangSP() {
        setBackground(new Color(255, 250, 205));
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 680, 836);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 248, 220));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        products = new ArrayList<>();
        productLabels = new ArrayList<>();
        
        // Define products
        products.add(new Product("Pate mèo vị cá thu", "20,000 VND", "a3.jpg", "Thức ăn"));
        products.add(new Product("Bát ăn khô", "30,000 VND", "a4.jpg", "Phụ kiện"));
        products.add(new Product("Thảm cào móng", "40,000 VND", "a5.jpg", "Phụ kiện"));
        products.add(new Product("Áo Tết size m", "35,000 VND", "a6.jpg", "Phụ kiện"));
        products.add(new Product("Thức ăn cao cấp", "55,000 VND", "a7.jpg", "Thức ăn"));
        products.add(new Product("Balo cho thú cưng", "155,000 VND", "a8.jpg", "Phụ kiện"));
        products.add(new Product("Thức ăn hạt 200g", "25,000 VND", "a9.jpg", "Thức ăn"));
        products.add(new Product("Súp thưởng", "50,000 VND", "a10.jpg", "Thức ăn"));
        products.add(new Product("Sữa tắm", "3,000 VND", "a11.jpg", "Phụ kiện"));
        products.add(new Product("Bồn vệ sinh", "150,000 VND", "a12.jpg", "Phụ kiện"));
        products.add(new Product("Áo đỏ size", "40,000 VND", "a13.jpg", "Phụ kiện"));
        products.add(new Product("Bóng thoáng khí", "60,000 VND", "a14.jpg", "Phụ kiện"));
        products.add(new Product("Yếm thú cưng", "40,000 VND", "a15.jpg", "Phụ kiện"));
        products.add(new Product("Kính kiểu", "20,000 VND", "a16.jpg", "Phụ kiện"));
        products.add(new Product("Cát mèo 8lit", "60,000 VND", "a17.jpg", "Phụ kiện"));

        // Display products
        displayProducts(products);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Sản phẩm");
        lblTitle.setBounds(111, 5, 232, 36);
        lblTitle.setFont(new Font("Segoe UI Semibold", Font.BOLD, 34));
        lblTitle.setBackground(new Color(255, 153, 102));
        contentPane.add(lblTitle);

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBounds(465, 14, 164, 34);
        comboBox.setBackground(new Color(255, 204, 153));
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBox.addItem("Tất cả");
        comboBox.addItem("Thức ăn");
        comboBox.addItem("Phụ kiện");
        contentPane.add(comboBox);

        JButton btnFilter = new JButton("Lọc");
        btnFilter.setBounds(355, 14, 100, 33);
        btnFilter.setBackground(new Color(255, 204, 153));
        btnFilter.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(btnFilter);

        btnFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) comboBox.getSelectedItem();
                filterProducts(selectedCategory);
            }
        });

        // Create Cancel button
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(10, 14, 100, 33);
        btnCancel.setBackground(new Color(255, 204, 153));
        btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(btnCancel);

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                // Optionally, show the previous frame if needed
                // For example: new PreviousFrame().setVisible(true);
            }
        });
    }

    private void displayProducts(List<Product> products) {
        int x = 10, y = 58;
        for (Product product : products) {
            ImageIcon originalIcon = new ImageIcon(TrangSP.class.getResource(product.imagePath));
            Image image = originalIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);

            JLabel lblProductImage = new JLabel();
            lblProductImage.setIcon((Icon) new ImageIcon(image));
            lblProductImage.setBounds(x, y, 120, 120);
            contentPane.add(lblProductImage);
            productLabels.add(lblProductImage);

            JLabel lblProductName = new JLabel(product.name);
            lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblProductName.setBounds(x, y + 126, 150, 25);
            contentPane.add(lblProductName);
            productLabels.add(lblProductName);

            JLabel lblPrice = new JLabel("Giá: " + product.price);
            lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblPrice.setBounds(x, y + 150, 150, 25);
            contentPane.add(lblPrice);
            productLabels.add(lblPrice);

            x += 154;
            if (x > 600) {
                x = 10;
                y += 180;
            }
        }
        contentPane.revalidate();
        contentPane.repaint();
    }

    private void filterProducts(String category) {
        for (JLabel label : productLabels) {
            contentPane.remove(label);
        }
        productLabels.clear();

        List<Product> filteredProducts;
        if (category.equals("Tất cả")) {
            filteredProducts = products;
        } else {
            filteredProducts = new ArrayList<>();
            for (Product product : products) {
                if (product.category.equals(category)) {
                    filteredProducts.add(product);
                }
            }
        }
        displayProducts(filteredProducts);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TrangSP frame = new TrangSP();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
