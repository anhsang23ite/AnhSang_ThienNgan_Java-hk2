package Model;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import Controller.JDBCConnection;

public class ChinhSua extends JFrame implements ActionListener {
    JPanel p1, p2;
    JLabel l1, l2, l3, l4, l5, l55, l6, l66, l7, l8;
    JTextField t1, t2, t3, t4, t5, t6;
    JComboBox<String> cbLoai, cbID;
    JButton b1, b2;
    DefaultTableModel dataModel;
    ArrayList<HangHoa> hh = new ArrayList<HangHoa>();
    JDateChooser ng, ng1;
    JDBCConnection jdbcConnection;

    public ChinhSua(String s, ArrayList<HangHoa> h, DefaultTableModel model) {
        super(s);
        dataModel = model;
        hh = h;
        jdbcConnection = new JDBCConnection();

        p1 = new JPanel();
        p1.setLayout(new GridLayout(10, 2));

        l8 = new JLabel("Chọn ID sản phẩm:");
        l8.setHorizontalAlignment(SwingConstants.CENTER);
        p1.add(l8);

        cbID = new JComboBox<>(getIDsFromDatabase());
        cbID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cbID.getSelectedItem() != null) {
                    String selectedID = cbID.getSelectedItem().toString();
                    displayProductInfo(selectedID);
                }
            }
        });
        p1.add(cbID);

        l1 = new JLabel("IDsp");
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        t1 = new JTextField();
        t1.setEnabled(false);
        t1.setFont(new Font("Tahoma", Font.BOLD, 12));
        t1.setForeground(Color.RED);
        p1.add(l1);
        p1.add(t1);

        l2 = new JLabel("Ten");
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        t2 = new JTextField();
        p1.add(l2);
        p1.add(t2);

        l7 = new JLabel("Loai");
        l7.setHorizontalAlignment(SwingConstants.CENTER);
        cbLoai = new JComboBox<>(getLoaiFromDatabase());
        p1.add(l7);
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

        l55 = new JLabel("NgayNhap");
        l55.setHorizontalAlignment(SwingConstants.CENTER);
        ng = new JDateChooser();
        ng.setDateFormatString("dd-MM-yyyy");
        p1.add(l55);
        p1.add(ng);

        l6 = new JLabel("DaXuat");
        l6.setHorizontalAlignment(SwingConstants.CENTER);
        t6 = new JTextField();
        p1.add(l6);
        p1.add(t6);

        l66 = new JLabel("NgayXuat");
        l66.setHorizontalAlignment(SwingConstants.CENTER);
        ng1 = new JDateChooser();
        ng1.setDateFormatString("dd-MM-yyyy");
        p1.add(l66);
        p1.add(ng1);

        this.add(p1, "North");

        p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        b1 = new JButton("OK");
        b1.setBackground(new Color(233,150,122)); 
        b1.addActionListener(this);
        b2 = new JButton("Cancel");
        b2.setBackground(new Color(233,150,122)); 
        b2.addActionListener(this);
        p2.add(b1);
        p2.add(b2);
        this.add(p2, "South");

        setSize(350, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String[] getIDsFromDatabase() {
        ArrayList<String> idList = new ArrayList<>();
        try {
            idList = jdbcConnection.fetchIDs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idList.toArray(new String[0]);
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

    private void displayProductInfo(String selectedID) {
        HangHoa product = jdbcConnection.getHangHoaByID(selectedID);
        if (product != null) {
            t1.setText(product.getIDsp());
            t2.setText(product.getTen());
            cbLoai.setSelectedItem(product.getLoai());
            t3.setText(String.valueOf(product.getGia()));
            t4.setText(String.valueOf(product.getSoLuong()));
            t5.setText(String.valueOf(product.getDaNhap()));
            ng.setDate(product.getNgayNhap() != null ? java.sql.Date.valueOf(product.getNgayNhap()) : null);
            t6.setText(String.valueOf(product.getDaXuat()));
            ng1.setDate(product.getNgayXuat() != null ? java.sql.Date.valueOf(product.getNgayXuat()) : null);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin sản phẩm!");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")) {
            try {
                String IDsp = t1.getText();
                String ten = t2.getText();
                int gia = Integer.valueOf(t3.getText());
                int soLuong = Integer.valueOf(t4.getText());
                int daNhap = Integer.valueOf(t5.getText());
                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate ngayNhap = ng.getDate() != null ? ng.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                int daXuat = Integer.valueOf(t6.getText());
                LocalDate ngayXuat = ng1.getDate() != null ? ng1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                String loai = (String) cbLoai.getSelectedItem();

                HangHoa updatedProduct = new HangHoa(IDsp, ten, gia, loai, soLuong, daNhap, daXuat);
                updatedProduct.setNgayNhap(ngayNhap);
                updatedProduct.setNgayXuat(ngayXuat);

                boolean success = jdbcConnection.chinhSuaHangHoa(updatedProduct, IDsp);
                if (success) {
                    for (int i = 0; i < dataModel.getRowCount(); i++) {
                        if (dataModel.getValueAt(i, 0).equals(IDsp)) {
                            dataModel.setValueAt(ten, i, 1);
                            dataModel.setValueAt(gia, i, 2);
                            dataModel.setValueAt(soLuong, i, 3);
                            dataModel.setValueAt(daNhap, i, 4);
                            dataModel.setValueAt(ngayNhap != null ? df.format(ngayNhap) : null, i, 5);
                            dataModel.setValueAt(daXuat, i, 6);
                            dataModel.setValueAt(ngayXuat != null ? df.format(ngayXuat) : null, i, 7);
                            dataModel.setValueAt(loai, i, 8);
                            break;
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Thông tin sản phẩm đã được cập nhật thành công!");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin sản phẩm thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ! Vui lòng kiểm tra lại.");
            }
        } else if (e.getActionCommand().equals("Cancel")) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArrayList<HangHoa> h = new ArrayList<>();
            DefaultTableModel model = new DefaultTableModel();
            new ChinhSua("Chỉnh Sửa Sản Phẩm", h, model);
        });
    }
}
