package Model;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

import Controller.JDBCConnection;

public class XoaHH {
    private ArrayList<HangHoa> hh;
    private DefaultTableModel dataModel;
    private int getRow;

    public XoaHH(ArrayList<HangHoa> h, DefaultTableModel model, int row) {
        getRow = row;
        hh = h;
        dataModel = model;
        
        HangHoa hangHoaToDelete = h.get(row);
        
        // Deleting the selected product from the database
        if (new JDBCConnection().xoaHangHoa(hangHoaToDelete.getIDsp())) {
            h.remove(row);
            model.removeRow(row);

            // Update IDs and refresh rows
            for (int i = row; i < h.size(); i++) {
                HangHoa currentHangHoa = h.get(i);
                currentHangHoa.setIDsp(String.valueOf(i + 1));
                
                // Update the database with the new ID
                new JDBCConnection().chinhSuaID(currentHangHoa.getIDsp(), currentHangHoa);

                // Refresh the table model
                model.setValueAt(currentHangHoa.getIDsp(), i, 0);
                model.setValueAt(currentHangHoa.getTen(), i, 1);
                model.setValueAt(currentHangHoa.getGia(), i, 2);
                model.setValueAt(currentHangHoa.getSoLuong(), i, 3);
                model.setValueAt(currentHangHoa.getDaNhap(), i, 4);
                model.setValueAt(currentHangHoa.getNgayNhap(), i, 5);
                model.setValueAt(currentHangHoa.getDaXuat(), i, 6);
                model.setValueAt(currentHangHoa.getNgayXuat(), i, 7);
            }
        }
    }
}
