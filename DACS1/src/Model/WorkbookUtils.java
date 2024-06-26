package Model;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class WorkbookUtils {

    /**
     * Phương thức xuất danh sách hàng hóa ra file văn bản (.txt).
     * @param list Danh sách hàng hóa cần xuất
     * @param fileName Tên file xuất
     */
    public static void xuatFileVanBan(ArrayList<HangHoa> list, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);

            // Ghi header
            writer.write("IDsp\tTên\tLoại\tGiá\tSố Lượng\tĐã Nhập\tĐã Xuất\tNgày Xuất");
            writer.newLine();

            // Ghi danh sách hàng hóa
            for (HangHoa hh : list) {
                writer.write(hh.getIDsp() + "\t" + hh.getTen() + "\t" + hh.getLoai() + "\t" +
                             hh.getGia() + "\t" + hh.getSoLuong() + "\t" + hh.getDaNhap() + "\t" +
                             hh.getDaXuat() + "\t" + hh.getNgayXuat());
                writer.newLine();
            }

            writer.close();
            JOptionPane.showMessageDialog(null, "Xuất file văn bản thành công!");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file văn bản: " + e.getMessage());
        }
    }

    /**
     * Phương thức đọc danh sách hàng hóa từ file văn bản (.txt).
     * @param fileName Tên file đọc
     * @return Danh sách hàng hóa đã đọc từ file
     */
    public static ArrayList<HangHoa> docFileVanBan(String fileName) {
        ArrayList<HangHoa> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            String line;

            // Bỏ qua header
            reader.readLine();

            // Đọc từng dòng và thêm vào danh sách
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");
                HangHoa hh = new HangHoa(fileName, null, 0, 0);
                hh.setIDsp(data[0]);
                hh.setTen(data[1]);
                hh.setLoai(data[2]);
                hh.setGia(Integer.parseInt(data[3]));
                hh.setSoLuong(Integer.parseInt(data[4]));
                hh.setDaNhap(Integer.parseInt(data[5]));
                hh.setDaXuat(Integer.parseInt(data[6]));
                list.add(hh);
            }

            fis.close();
            reader.close();

            JOptionPane.showMessageDialog(null, "Đọc file văn bản thành công!");

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đọc file văn bản: " + e.getMessage());
        }

        return list;
    }

}
