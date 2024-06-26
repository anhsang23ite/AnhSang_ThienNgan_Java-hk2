package Model;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Controller.JDBCConnection;
import View.TrangSP;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class QuanLi extends JFrame implements ActionListener, MouseListener {
    JScrollPane sp;
    JPanel p, pS;
    JTable tb;
    DefaultTableModel model;
    ArrayList<HangHoa> list;
    JButton bNew, bEdit, bDelete, bThongKe, bTimKiem, bSapXep, bLamMoi, bChat, bXemSanPham;
    JLabel lableTK, lableSX, lableTimKiem, lableSapXep, tenSV;
    JComboBox<String> comSX;
    JTextField tfTK;
    int getRow;
    JDBCConnection jdbcConnection;
    MessageBroker broker;

    public QuanLi(String s) {
        super(s);
        getContentPane().setLayout(new BorderLayout());

        // Panel title
        pS = new JPanel();
        pS.setLayout(new FlowLayout());
        JLabel img = new JLabel("");
        tenSV = new JLabel("Cửa Hàng Thú Cưng");
        tenSV.setForeground(new Color(255, 0, 0));
        tenSV.setFont(new Font("Sitka Text", Font.BOLD, 25));

        pS.add(img, BorderLayout.WEST);
        pS.add(tenSV, BorderLayout.CENTER);
        pS.setBackground(new Color(255, 255, 255));
        getContentPane().add(pS, BorderLayout.NORTH);

        // Table and model setup
        tb = new JTable();
        tb.addMouseListener(this);
        jdbcConnection = new JDBCConnection();
        list = jdbcConnection.getListHangHoa1();
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
            model.addRow(new Object[]{e.getIDsp(), e.getTen(), e.getLoai(), e.getGia(), e.getSoLuong(), e.getDaNhap(), e.getDaXuat(), e.getNgayXuat()});
        }

        tb.setModel(model);
        sp = new JScrollPane(tb);
        sp.setBorder(new TitledBorder(new LineBorder(Color.RED, 2), "DANH SÁCH HÀNG HÓA   ", TitledBorder.CENTER,
                TitledBorder.TOP, null, Color.MAGENTA));
        getContentPane().add(sp, BorderLayout.CENTER);
        sp.setBackground(new Color(253,245,230));

        // Panel buttons setup
        p = new JPanel();
        bNew = new JButton("Nhập Hàng Hóa Mới");
        bNew.addActionListener(this);

        bEdit = new JButton("Chỉnh Sửa Thông Tin");
        bEdit.addActionListener(this);

        bDelete = new JButton("Xuất Hàng Hóa");
        bDelete.addActionListener(this);

        bThongKe = new JButton("Thống Kê Hàng Hóa");
        bThongKe.addActionListener(this);

        lableTK = new JLabel("Tìm Kiếm");
        lableTK.setBackground(new Color(233,150,122));
        lableTK.setHorizontalAlignment(SwingConstants.CENTER);
        lableTimKiem = new JLabel("Nhập IDsp hoặc Tên");
        lableTimKiem.setBackground(new Color(233,150,122));
        lableTimKiem.setHorizontalAlignment(SwingConstants.CENTER);
        tfTK = new JTextField("");
        bTimKiem = new JButton("Tìm Kiếm");
        bTimKiem.addActionListener(this);

        lableSX = new JLabel("Sắp Xếp");
        lableSX.setBackground(new Color(233,150,122));
        lableSX.setHorizontalAlignment(SwingConstants.CENTER);

        lableSapXep = new JLabel("Chọn Thông Tin Muốn Sắp Xếp");
        lableSapXep.setBackground(new Color(233,150,122));
        lableSapXep.setHorizontalAlignment(SwingConstants.CENTER);
        comSX = new JComboBox<>();
        comSX.addItem("Tên");
        comSX.addItem("Giá");
        comSX.addItem("Số Lượng");
        comSX.addItem("Ngày Nhập Kho");
        comSX.addItem("Ngày Xuất Kho");
        bSapXep = new JButton("Sắp Xếp");
        bSapXep.addActionListener(this);
        bLamMoi = new JButton("Xóa Hàng Hóa");
        bLamMoi.addActionListener(this);

        // Nút Xem sản phẩm
        bXemSanPham = new JButton("Xem Sản Phẩm");
        bXemSanPham.setBackground(new Color(253,245,230));
        bXemSanPham.setFont(new Font("Tahoma", Font.BOLD, 12));
        bXemSanPham.addActionListener(this);

        // Nút Chat
        bChat = new JButton("Chat");
        bChat.addActionListener(this);
        
        // Button để xuất file văn bản
        JButton bXuatTxt = new JButton("Xuất File Văn Bản");
        bXuatTxt.setFont(new Font("Tahoma", Font.BOLD, 12));
        bXuatTxt.addActionListener(this);
        bXuatTxt.setBackground(new Color(253,245,230));
        p.add(bXuatTxt);

        // Button để đọc file văn bản
        JButton bDocTxt = new JButton("Đọc File Văn Bản");
        bDocTxt.setFont(new Font("Tahoma", Font.BOLD, 12));
        bDocTxt.addActionListener(this);
        bDocTxt.setBackground(new Color(253,245,230));
        p.add(bDocTxt);

        // Lam mau
        bNew.setFont(new Font("Tahoma", Font.BOLD, 12));
        bNew.setBackground(new Color(253,245,230));
        bNew.setForeground(Color.BLACK);

        bDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
        bDelete.setBackground(new Color(253,245,230));
        bDelete.setForeground(Color.BLACK);

        bEdit.setFont(new Font("Tahoma", Font.BOLD, 12));
        bEdit.setBackground(new Color(253,245,230));
        bEdit.setForeground(Color.BLACK);

        bLamMoi.setFont(new Font("Tahoma", Font.BOLD, 12));
        bLamMoi.setBackground(new Color(253,245,230));
        bLamMoi.setForeground(Color.BLACK);

        bThongKe.setFont(new Font("Tahoma", Font.BOLD, 12));
        bThongKe.setBackground(new Color(253,245,230));
        bThongKe.setForeground(Color.BLACK);

        bTimKiem.setFont(new Font("Tahoma", Font.BOLD, 12));
        bTimKiem.setBackground(new Color(253,245,230));
        bTimKiem.setForeground(Color.BLACK);

        bSapXep.setFont(new Font("Tahoma", Font.BOLD, 12));
        bSapXep.setBackground(new Color(253,245,230));
        bSapXep.setForeground(Color.BLACK);

        bChat.setFont(new Font("Tahoma", Font.BOLD, 12));
        bChat.setBackground(new Color(253,245,230));
        bChat.setForeground(Color.BLACK);

        lableTK.setFont(new Font("Tahoma", Font.BOLD, 14));
        lableTK.setForeground(Color.BLACK);
        lableSX.setFont(new Font("Tahoma", Font.BOLD, 14));
        lableSX.setForeground(Color.BLACK);

        lableTimKiem.setFont(new Font("Tahoma", Font.BOLD, 12));
        lableTimKiem.setForeground(Color.BLACK);
        lableSapXep.setFont(new Font("Tahoma", Font.BOLD, 12));
        lableSapXep.setForeground(Color.BLACK);

        // Add
        p.setLayout(new GridLayout(8, 3));
        p.setBackground(new Color(255, 255, 255));
        p.setBorder(new TitledBorder(new LineBorder(Color.RED, 2), "CÁC CHỨC NĂNG  ",
                TitledBorder.CENTER, TitledBorder.TOP, null, Color.MAGENTA));
        p.add(bNew);
        p.add(lableTK);
        p.add(lableSX);

        p.add(bDelete);
        p.add(lableTimKiem);
        p.add(lableSapXep);

        p.add(bEdit);
        p.add(tfTK);
        p.add(comSX);

        p.add(bLamMoi);
        p.add(bTimKiem);
        p.add(bSapXep);

        p.add(bThongKe);
        p.add(new JLabel());
        p.add(bChat);

        p.add(bXemSanPham);
        p.add(new JLabel());
        p.add(new JLabel());

        getContentPane().add(p, BorderLayout.SOUTH);
        setLocation(300, 100);
        setSize(800, 500);
        setVisible(true);

        // Khởi tạo đối tượng MessageBroker
        broker = new MessageBroker();
    }

    public void mouseClicked(MouseEvent e) {
        getRow = tb.getSelectedRow();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bNew) {
            new NhapHang("Nhập Hàng Hóa Mới", list, model).setVisible(true);
        } else if (e.getSource() == bEdit) {
            ArrayList<HangHoa> h = new ArrayList<>();
            DefaultTableModel model = new DefaultTableModel();
            ChinhSua cs = new ChinhSua("Chỉnh Sửa Sản Phẩm", h, model);
            cs.setVisible(true);
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
        } else if (e.getSource() == bDelete) {
            new XuatKho("Xuất Hàng Hóa", list, model).setVisible(true);
        } else if (e.getSource() == bThongKe) {
            new ThongKe("Thống Kê Hàng Hóa", list).setVisible(true);
        } else if (e.getSource() == bSapXep) {
            String selectedOption = comSX.getSelectedItem().toString();
            new SapXep("Sắp Xếp", list, selectedOption);
        } else if (e.getSource() == bLamMoi) {
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa hàng hóa này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int row = tb.getSelectedRow();
                if (row != -1) {
                    String id = (String) tb.getValueAt(row, 0);
                    new JDBCConnection().xoaHangHoa(id);
                    model.removeRow(row);
                }
            }
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
        } else if (e.getActionCommand().equals("Xuất File Văn Bản")) {
            xuatFileVanBan();
        } else if (e.getActionCommand().equals("Đọc File Văn Bản")) {
            docFileVanBan();
        }
    }

    private void xuatFileVanBan() {
        try {
            FileWriter fw = new FileWriter("DanhSachHangHoa.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            // Ghi header
            bw.write("Danh sách hàng hóa trong kho:\n\n");

            // Ghi dữ liệu từ bảng vào file
            for (int i = 0; i < tb.getRowCount(); i++) {
                for (int j = 0; j < tb.getColumnCount(); j++) {
                    bw.write(tb.getValueAt(i, j).toString() + "\t");
                }
                bw.newLine();
            }

            bw.close();
            fw.close();
            JOptionPane.showMessageDialog(this, "Xuất file văn bản thành công!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file văn bản: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void docFileVanBan() {
        try {
            FileReader fr = new FileReader("DanhSachHangHoa.txt");
            BufferedReader br = new BufferedReader(fr);

            // Đọc dữ liệu từ file và hiển thị lên text area
            String line;
            JTextArea textArea = new JTextArea();
            while ((line = br.readLine()) != null) {
                textArea.append(line + "\n");
            }
            br.close();
            fr.close();

            // Hiển thị cửa sổ mới để hiển thị nội dung file
            JFrame frame = new JFrame("Danh Sách Hàng Hóa");
            frame.getContentPane().add(new JScrollPane(textArea));
            frame.setSize(600, 400);
            frame.setVisible(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file văn bản: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new QuanLi("Quản Lý Hàng Hóa Cửa Hàng Thú Cưng").setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
