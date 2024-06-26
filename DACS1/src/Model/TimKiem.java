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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import Controller.JDBCConnection;

public class TimKiem extends JFrame implements ActionListener {
    JPanel p1, p2;
    JLabel l1, l2, l3, l4, l5, l6;
    JButton ok;
    JDBCConnection jdbcConnection;
    
    public TimKiem(String s, String tk) {
        super(s);
        jdbcConnection = new JDBCConnection();
        ArrayList<HangHoa> hh = jdbcConnection.searchHangHoa(tk);
        p1 = new JPanel();
        p1.setLayout(new GridLayout(6, 1));

        if (hh.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm nào!");
            dispose();
            return;
        }

        HangHoa o = hh.get(0);  // Assume you only want to display the first match
        l1 = new JLabel("Id: " + o.getIDsp());
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        l2 = new JLabel("Ten: " + o.getTen());
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        l3 = new JLabel("Gia: " + String.valueOf(o.getGia()));
        l3.setHorizontalAlignment(SwingConstants.CENTER);
        l4 = new JLabel("So luong trong kho: " + String.valueOf(o.getSoLuong()));
        l4.setHorizontalAlignment(SwingConstants.CENTER);
        l5 = new JLabel("So luong da Nhap kho: " + String.valueOf(o.getDaNhap()));
        l5.setHorizontalAlignment(SwingConstants.CENTER);
        l6 = new JLabel("So luong da Xuat kho: " + String.valueOf(o.getDaXuat()));
        l6.setHorizontalAlignment(SwingConstants.CENTER);

        p1.add(l1);
        p1.add(l2);
        p1.add(l3);
        p1.add(l4);
        p1.add(l5);
        p1.add(l6);
        
        ok = new JButton("Cancel");
        ok.addActionListener(this);
        ok.setBackground(new Color(233,150,122) );
        p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.add(ok);
        
        this.add(p1, BorderLayout.CENTER);
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
        SwingUtilities.invokeLater(() -> new TimKiem("Tìm Kiếm Sản Phẩm", "1"));
    }
}
