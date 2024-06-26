package Model;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import Controller.JDBCConnection;

public class XuatKho extends JFrame implements ActionListener {
    JPanel p1, p2;
    JLabel l1, l2, l3;
    JTextField t1, t2, t3;
    JButton b1, b2;
    DefaultTableModel dataModel;
    ArrayList<HangHoa> hh = new ArrayList<HangHoa>();
    JDateChooser ng;
    JDBCConnection jdbcConnection;

    public XuatKho(String s, ArrayList<HangHoa> h, DefaultTableModel model) {
        super(s);
        dataModel = model;
        hh = h;
        jdbcConnection = new JDBCConnection();

        p1 = new JPanel();
        p2 = new JPanel();
        l1 = new JLabel("ID");
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        l2 = new JLabel("Số Lượng");
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        t1 = new JTextField();
        t2 = new JTextField();
        l3 = new JLabel("Ngày Xuất");
        l3.setHorizontalAlignment(SwingConstants.CENTER);
        ng = new JDateChooser();
        ng.setDateFormatString("dd-MM-yyyy");

        p1.setLayout(new GridLayout(3, 2));
        p1.add(l1);
        p1.add(t1);
        p1.add(l2);
        p1.add(t2);
        p1.add(l3);
        p1.add(ng);

        this.add(p1, "North");

        p2.setLayout(new FlowLayout());
        b1 = new JButton("OK");
        b1.addActionListener(this);
        b2 = new JButton("Cancel");
        b2.addActionListener(this);
        p2.add(b1);
        p2.add(b2);
        this.add(p2, "South");

        setSize(350, 250);
        setLocation(525, 350);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")) {
            try {
                int id = Integer.parseInt(t1.getText());
                int soLuong = Integer.parseInt(t2.getText());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String ngayXuat = df.format(ng.getDate());

                if (!hh.isEmpty() && id > 0 && id <= hh.size()) {
                    HangHoa selectedHangHoa = hh.get(id - 1);

                    if (selectedHangHoa.getIDsp().equals(String.valueOf(id)) && selectedHangHoa.getSoLuong() >= soLuong) {
                        if (jdbcConnection.banHang(selectedHangHoa, String.valueOf(id), soLuong, ngayXuat)) {
                            selectedHangHoa.setSoLuong(selectedHangHoa.getSoLuong() - soLuong);
                            this.dataModel.setValueAt(selectedHangHoa.getSoLuong(), id - 1, 4);
                            JOptionPane.showMessageDialog(rootPane, "Xuất hàng thành công!");
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Xuất hàng thất bại!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Không đủ hàng hoá trong kho!");
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "ID không hợp lệ hoặc danh sách hàng hóa rỗng!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(rootPane, "Nhập số nguyên cho ID và số lượng!");
            }
        } else if (e.getActionCommand().equals("Cancel")) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArrayList<HangHoa> h = new ArrayList<>();
            DefaultTableModel model = new DefaultTableModel();
            new XuatKho("Xuất Hàng Hóa", h, model);
        });
    }
}
