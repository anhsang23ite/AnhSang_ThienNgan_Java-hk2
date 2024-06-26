package Model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import Controller.JDBCConnection;

public class NhapHang extends JFrame implements ActionListener {
    JPanel p1, p2;
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9;
    JTextField t1, t2, t3, t4, t5, t6;
    JComboBox<String> cbLoai;
    JDateChooser ngNhap, ngXuat;
    JButton b1, b2;
    DefaultTableModel dataModel;
    ArrayList<HangHoa> hh;
    JDBCConnection jdbcConnection;

    public NhapHang(String s, ArrayList<HangHoa> h, DefaultTableModel model) {
        super(s);
        dataModel = model;
        hh = h;
        jdbcConnection = new JDBCConnection();

        p1 = new JPanel();
        p1.setLayout(new GridLayout(9, 2));

        l1 = new JLabel("IDsp");
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        t1 = new JTextField();
        p1.add(l1);
        p1.add(t1);

        l2 = new JLabel("Ten");
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        t2 = new JTextField();
        p1.add(l2);
        p1.add(t2);

        l9 = new JLabel("Loai");
        l9.setHorizontalAlignment(SwingConstants.CENTER);
        cbLoai = new JComboBox<>(getLoaiFromDatabase());
        p1.add(l9);
        p1.add(cbLoai);

        l3 = new JLabel("Gia");
        l3.setHorizontalAlignment(SwingConstants.CENTER);
        t3 = new JTextField();
        p1.add(l3);
        p1.add(t3);

        l4 = new JLabel("SoLuong");
        l4.setHorizontalAlignment(SwingConstants.CENTER);
        t4 = new JTextField();
        p1.add(l4);
        p1.add(t4);

        l5 = new JLabel("DaNhap");
        l5.setHorizontalAlignment(SwingConstants.CENTER);
        t5 = new JTextField();
        p1.add(l5);
        p1.add(t5);

        l6 = new JLabel("NgayNhap");
        l6.setHorizontalAlignment(SwingConstants.CENTER);
        ngNhap = new JDateChooser();
        ngNhap.setDateFormatString("dd-MM-yyyy");
        p1.add(l6);
        p1.add(ngNhap);

        l7 = new JLabel("DaXuat");
        l7.setHorizontalAlignment(SwingConstants.CENTER);
        t6 = new JTextField();
        p1.add(l7);
        p1.add(t6);

        l8 = new JLabel("NgayXuat");
        l8.setHorizontalAlignment(SwingConstants.CENTER);
        ngXuat = new JDateChooser();
        ngXuat.setDateFormatString("dd-MM-yyyy");
        p1.add(l8);
        p1.add(ngXuat);

        this.add(p1, "North");

        p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        b1 = new JButton("OK");
        b1.addActionListener(this);
        b2 = new JButton("Cancel");
        b2.addActionListener(this);
        p2.add(b1);
        p2.add(b2);
        this.add(p2, "South");

        setSize(350, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String[] getLoaiFromDatabase() {
        ArrayList<String> loaiList = new ArrayList<>();
        try {
            loaiList = jdbcConnection.fetchLoai();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loaiList.toArray(new String[0]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")) {
            try {
                String IDsp = t1.getText();
                String ten = t2.getText();
                String loai = (String) cbLoai.getSelectedItem();
                int gia = Integer.parseInt(t3.getText());
                int soLuong = Integer.parseInt(t4.getText());
                int daNhap = Integer.parseInt(t5.getText());
                LocalDate ngayNhap = ngNhap.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int daXuat = Integer.parseInt(t6.getText());
                LocalDate ngayXuat = ngXuat.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                HangHoa hanghoa = new HangHoa(IDsp, ten, gia, loai, soLuong, daNhap, daXuat);
                hanghoa.setNgayNhap(ngayNhap);
                hanghoa.setNgayXuat(ngayXuat);

                boolean success = jdbcConnection.addHangHoa(hanghoa);
                if (success) {
                    hh.add(hanghoa);
                    dataModel.addRow(new Object[]{
                            hanghoa.getIDsp(), hanghoa.getTen(), hanghoa.getLoai(), hanghoa.getGia(),
                            hanghoa.getSoLuong(), hanghoa.getDaNhap(),
                            hanghoa.getNgayNhap().toString(), hanghoa.getDaXuat(),
                            hanghoa.getNgayXuat().toString()
                    });
                    JOptionPane.showMessageDialog(rootPane, "Thêm hàng hóa thành công!");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Thêm hàng hóa thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(rootPane, "Dữ liệu nhập không hợp lệ! Vui lòng kiểm tra lại.");
            }
        } else if (e.getActionCommand().equals("Cancel")) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArrayList<HangHoa> h = new ArrayList<>();
            DefaultTableModel model = new DefaultTableModel();
            new NhapHang("Nhập Hàng", h, model);
        });
    }
}
