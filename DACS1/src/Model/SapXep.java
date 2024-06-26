package Model;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controller.JDBCConnection;

public class SapXep extends JFrame {
    private ArrayList<HangHoa> hangHoas;
    private JTable table;
    private DefaultTableModel model;
    private JDBCConnection jdbcConnection;
    private JComboBox<String> sortComboBox;
    private JButton cancelButton;

    public SapXep(String title, ArrayList<HangHoa> hangHoas, String sortBy) {
        super(title);
        this.hangHoas = hangHoas;
        this.jdbcConnection = new JDBCConnection();
        
        // Fetch data from database and sort based on sortBy initially
        fetchAndSortData(sortBy);

        // Create table model with columns
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Tên");
        model.addColumn("Loại");
        model.addColumn("Giá");
        model.addColumn("Số Lượng");
        model.addColumn("Đã Nhập");
        model.addColumn("Ngày Nhập");
        model.addColumn("Đã Xuất");
        model.addColumn("Ngày Xuất");

        // Add rows to the table model
        refreshTable();

        // Create JTable with the model
        table = new JTable(model);

        // Create JScrollPane for the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Create panel for title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Sắp Xếp Hàng Hóa Theo");
        titlePanel.add(titleLabel);

        // Create JComboBox for sorting options
        String[] sortOptions = {"Tên", "Giá", "Số Lượng", "Ngày Nhập", "Ngày Xuất"};
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setSelectedItem(sortBy); // Set initial selected item
        sortComboBox.addActionListener(e -> {
            String selectedSortBy = (String) sortComboBox.getSelectedItem();
            fetchAndSortData(selectedSortBy); // Fetch and sort data based on selected option
            refreshTable(); // Refresh table with sorted data
        });
        titlePanel.add(sortComboBox);

        // Create Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(233,150,122));
        cancelButton.addActionListener(e -> {
            this.dispose(); // Close current frame
            // Optionally, show the previous frame if needed
            // For example: new PreviousFrame().setVisible(true);
        });
        titlePanel.add(cancelButton);

        // Set layout for JFrame
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(titlePanel);
        add(scrollPane);

        // Set JFrame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchAndSortData(String sortBy) {
        // Fetch data from JDBCConnection
        hangHoas = jdbcConnection.getListHangHoa1();

        // Sort the ArrayList based on the given criteria
        switch (sortBy) {
            case "Tên":
                hangHoas.sort(Comparator.comparing(HangHoa::getTen));
                break;
            case "Giá":
                hangHoas.sort(Comparator.comparingInt(HangHoa::getGia));
                break;
            case "Số Lượng":
                hangHoas.sort(Comparator.comparingInt(HangHoa::getSoLuong));
                break;
            case "Ngày Nhập":
                hangHoas.sort(Comparator.comparing(HangHoa::getNgayNhap, Comparator.nullsLast(Comparator.naturalOrder())));
                break;
            case "Ngày Xuất":
                hangHoas.sort(Comparator.comparing(HangHoa::getNgayXuat, Comparator.nullsLast(Comparator.naturalOrder())));
                break;
        }
    }

    private void refreshTable() {
        // Clear existing rows
        model.setRowCount(0);

        // Add rows to the table model based on sorted hangHoas
        for (HangHoa hh : hangHoas) {
            model.addRow(new Object[]{
                    hh.getIDsp(), hh.getTen(), hh.getLoai(), hh.getGia(), hh.getSoLuong(),
                    hh.getDaNhap(), formatDate(hh.getNgayNhap()), hh.getDaXuat(), formatDate(hh.getNgayXuat())
            });
        }
    }

    private String formatDate(LocalDate date) {
        if (date == null) {
            return ""; // Handle null dates gracefully if needed
        }
        // Format LocalDate using DateTimeFormatter
        return date.toString(); // Modify as per your required date format
    }

    public static void main(String[] args) {
        // Create and display the sorting GUI
        SwingUtilities.invokeLater(() -> new SapXep("Sắp Xếp Hàng Hóa", new ArrayList<>(), "Tên"));
    }
}
