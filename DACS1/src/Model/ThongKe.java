package Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Controller.JDBCConnection;

public class ThongKe extends JFrame implements ActionListener {
    JPanel p1, p2;
    JLabel l2, l3, l4, lh, l5;
    JButton ok;

    public ThongKe(String s, ArrayList<HangHoa> h) {
        super(s);

        int soLuongHH = 0, soLuongNhap = 0, soLuongXuat = 0;
        String hhSapHet = "  ";
        String hhHet = "  ";

        // Tính toán các giá trị thống kê từ danh sách hàng hóa
        for (HangHoa o : h) {
            soLuongHH += o.getSoLuong();
            soLuongNhap += o.getDaNhap();
            soLuongXuat += o.getDaXuat();
            if (o.getSoLuong() < 5 && o.getSoLuong() > 0)
                hhSapHet += o.getTen() + ", ";
            if (o.getSoLuong() == 0)
                hhHet += o.getTen() + ", ";
        }

        // Xóa ký tự phần tử cuối cùng là dấu phẩy và khoảng trắng
        hhSapHet = hhSapHet.substring(0, hhSapHet.length() - 2);
        hhHet = hhHet.substring(0, hhHet.length() - 2);

        // Khởi tạo các label để hiển thị thông tin thống kê
        l2 = new JLabel("Tong so luong hang hoa : " + String.valueOf(soLuongHH));
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        l3 = new JLabel("So luong hang hoa da Nhap : " + String.valueOf(soLuongNhap));
        l3.setHorizontalAlignment(SwingConstants.CENTER);
        l4 = new JLabel("So luong hang hoa da Xuat : " + String.valueOf(soLuongXuat));
        l4.setHorizontalAlignment(SwingConstants.CENTER);

        lh = new JLabel("Cac loai hang hoa da het : " + hhHet);
        lh.setHorizontalAlignment(SwingConstants.CENTER);
        lh.setForeground(new Color(255, 0, 0));

        l5 = new JLabel("Cac loai hang hoa sap het : " + hhSapHet);
        l5.setHorizontalAlignment(SwingConstants.CENTER);
        l5.setForeground(new Color(255, 99, 71));

        // Sắp xếp các label vào panel p1
        p1 = new JPanel();
        p1.setLayout(new GridLayout(5, 1)); // Số lượng label đã sửa thành 5
        p1.add(l2);
        p1.add(l3);
        p1.add(l4);
        p1.add(lh);
        p1.add(l5);

        // Khởi tạo nút Cancel và bắt sự kiện
        ok = new JButton("Cancel");
        ok.addActionListener(this);
        ok.setBackground(new Color(233,150,122) );

        // Sắp xếp panel p1 và nút vào các vị trí trong JFrame
        this.add(p1, BorderLayout.CENTER);
        p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.add(ok);
        this.add(p2, BorderLayout.SOUTH);

        setLocation(450, 200);
        setSize(500, 300);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Cancel")) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        // Lấy danh sách hàng hóa từ cơ sở dữ liệu
        ArrayList<HangHoa> hangHoas = new JDBCConnection().getListHangHoa1();
        // Khởi tạo và hiển thị giao diện thống kê
        new ThongKe("Thống kê hàng hoá", hangHoas);
    }
}
