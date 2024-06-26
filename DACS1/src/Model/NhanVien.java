package Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Controller.JDBCConnection;
import View.TrangSP;

public class NhanVien extends JFrame implements ActionListener {
    JScrollPane sp;
    JPanel p, pS;
    JTable tb;
    DefaultTableModel model;
    ArrayList<HangHoa> list;
    JButton bThongKe, bTimKiem, bSapXep, bXemSanPham, bChat;
    JLabel lableTK, lableSX, lableTimKiem, lableSapXep, tenSV;
    JComboBox<String> comSX; // Sửa JComboBox để chỉ định kiểu dữ liệu
    JTextField tfTK, tfSX;
    MessageBroker broker; // Khai báo đối tượng MessageBroker

    public NhanVien(String s) {
        super(s);
        getContentPane().setLayout(new BorderLayout());

        pS = new JPanel();
        pS.setLayout(new FlowLayout());
        JLabel img = new JLabel("");
        tenSV = new JLabel("Cửa Hàng Thú Cưng");
        tenSV.setForeground(new Color(255, 0, 0));
        tenSV.setFont(new Font("Sitka Text", Font.BOLD, 25));

        pS.add(img, BorderLayout.WEST);
        pS.add(tenSV, BorderLayout.CENTER);
        pS.setBackground(new Color(253,245,230));
        getContentPane().add(pS, BorderLayout.NORTH);

        tb = new JTable();
        list = new JDBCConnection().getListHangHoa1();
        model = new DefaultTableModel();
        model.addColumn("IDsp");
        model.addColumn("Tên");
        model.addColumn("Loại"); 
        model.addColumn("Giá");
        model.addColumn("Số Lượng");
        model.addColumn("Đã Nhập");
        model.addColumn("Đã Xuất");
        model.addColumn("Ngày Xuất");

        for (HangHoa e : list) {
            model.addRow(new Object[]{e.getIDsp(), e.getTen(), e.getLoai(), e.getGia(), e.getSoLuong(), // Included "Loại" in the row data
                    e.getDaNhap(), e.getDaXuat(), e.getNgayXuat()});
        }
        tb.setModel(model);
        sp = new JScrollPane(tb);
        sp.setBorder(new TitledBorder(new LineBorder(Color.RED, 2), "DANH SÁCH HÀNG HÓA   ", TitledBorder.CENTER,
                TitledBorder.TOP, null, Color.MAGENTA));
        getContentPane().add(sp, BorderLayout.CENTER);
        sp.setBackground(new Color(255, 255, 255));

        p = new JPanel();
        bThongKe = new JButton("Thống Kê Hàng Hóa");
        bThongKe.addActionListener(this);

        lableTK = new JLabel("Tìm Kiếm");
        lableTK.setHorizontalAlignment(SwingConstants.CENTER);
        lableTimKiem = new JLabel("Nhập IDsp hoặc Tên");
        lableTimKiem.setHorizontalAlignment(SwingConstants.CENTER);
        tfTK = new JTextField("");
        bTimKiem = new JButton("Tìm Kiếm");
        bTimKiem.addActionListener(this);

        lableSX = new JLabel("Sắp Xếp");
        lableSX.setHorizontalAlignment(SwingConstants.CENTER);
        lableSapXep = new JLabel("Chọn Thông Tin Muốn Sắp Xếp");
        lableSapXep.setHorizontalAlignment(SwingConstants.CENTER);
        comSX = new JComboBox<>();
        comSX.addItem("Tên");
        comSX.addItem("Giá");
        comSX.addItem("Số Lượng");
        comSX.addItem("Ngày Nhập Kho");
        comSX.addItem("Ngày Xuất Kho");
        bSapXep = new JButton("Sắp Xếp");
        bSapXep.addActionListener(this);

        // Nút Xem sản phẩm
        bXemSanPham = new JButton("Xem Sản Phẩm");
        bXemSanPham.addActionListener(this);

        // Nút Chat
        bChat = new JButton("Chat");
        bChat.addActionListener(this);

        // Style buttons and labels
        styleButton(bThongKe);
        styleButton(bTimKiem);
        styleButton(bSapXep);
        styleButton(bXemSanPham);
        styleButton(bChat); // Style cho nút Chat

        styleLabel(lableTK);
        styleLabel(lableSX);
        styleLabel(lableTimKiem);
        styleLabel(lableSapXep);

        // Add components to panel
        p.setLayout(new GridLayout(7, 2));
        p.setBackground(new Color(253,245,230));
        p.setBorder(new TitledBorder(new LineBorder(Color.RED, 2), "CÁC CHỨC NĂNG  ",
                TitledBorder.CENTER, TitledBorder.TOP, null, Color.MAGENTA));
        p.add(lableTK);
        p.add(lableSX);
        p.add(lableTimKiem);
        p.add(lableSapXep);
        p.add(tfTK);
        p.add(comSX);
        p.add(bTimKiem);
        p.add(bSapXep);
        p.add(bThongKe);
        p.add(bXemSanPham); 
        p.add(bChat); 

        getContentPane().add(p, BorderLayout.SOUTH);
        setLocation(300, 100);
        setSize(800, 500);
        setVisible(true);
        
        // Khởi tạo đối tượng MessageBroker
        broker = new MessageBroker();
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setBackground(new Color(233,150,122)); // Màu cam
        button.setForeground(Color.BLACK);
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bThongKe) {
            new ThongKe("Thống Kê Hàng Hóa", list);
        } else if (e.getSource() == bTimKiem) {
            String searchText = tfTK.getText().trim();
            if (searchText.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập IDsp hoặc Tên để tìm kiếm");
            } else {
                boolean found = false;
                for (HangHoa hh : list) {
                    if (hh.getIDsp().equalsIgnoreCase(searchText) || hh.getTen().equalsIgnoreCase(searchText)) {
                        found = true;
                        new TimKiem("Tìm Kiếm", searchText);
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(rootPane, "Không tìm thấy hàng hóa trong kho");
                }
            }
        } else if (e.getSource() == bSapXep) {
            String selectedOption = comSX.getSelectedItem().toString();
            new SapXep("Sắp Xếp", list, selectedOption);
        } else if (e.getSource() == bXemSanPham) {
            // Hiển thị trang sản phẩm khi nhấn vào nút Xem Sản Phẩm
            TrangSP trangSP = new TrangSP();
            trangSP.setVisible(true);
        } else if (e.getSource() == bChat) {
            // Mở cửa sổ Chat khi nhấn vào nút Chat
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Chat("QUAN LI", broker).setVisible(true);
                    new Chat("NHAN VIEN", broker).setVisible(true);
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NhanVien("Quản Lý Cửa Hàng Thú Cưng"));
    }
}
