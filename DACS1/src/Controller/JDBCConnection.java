package Controller;

import java.sql.*;
import java.util.ArrayList;

import Model.HangHoa;
import Model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // Đối với file .xlsx

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;



public class JDBCConnection {
    private static final String URL = "jdbc:mysql://localhost:3307/petshop";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Đăng ký driver thất bại!");
        }
        
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    




    
    

    public ArrayList<String> fetchLoai() {
        ArrayList<String> loaiList = new ArrayList<>();
        String sql = "SELECT DISTINCT Loai FROM products";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                loaiList.add(rs.getString("Loai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loaiList;
    }

    public ArrayList<String> fetchIDs() {
        ArrayList<String> idList = new ArrayList<>();
        String sql = "SELECT IDsp FROM products";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                idList.add(rs.getString("IDsp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idList;
    }

    public HangHoa getHangHoaByID(String IDsp) {
        String sql = "SELECT IDsp, Ten, Loai, Gia, SoLuong, DaNhap, NgayNhap, DaXuat, NgayXuat FROM products WHERE IDsp = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, IDsp);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String id = rs.getString("IDsp");
                    String ten = rs.getString("Ten");
                    String loai = rs.getString("Loai");
                    int gia = rs.getInt("Gia");
                    int soLuong = rs.getInt("SoLuong");
                    int daNhap = rs.getInt("DaNhap");
                    Date ngayNhapDate = rs.getDate("NgayNhap");
                    int daXuat = rs.getInt("DaXuat");
                    Date ngayXuatDate = rs.getDate("NgayXuat");

                    HangHoa hh = new HangHoa(id, ten, gia, loai, soLuong, daNhap, daXuat);
                    hh.setNgayNhap(ngayNhapDate != null ? ngayNhapDate.toLocalDate() : null);
                    hh.setNgayXuat(ngayXuatDate != null ? ngayXuatDate.toLocalDate() : null);
                    return hh;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addHangHoa(HangHoa e) {
        String sql = "INSERT INTO products(IDsp, Ten, Gia, Loai, SoLuong, DaNhap, DaXuat, NgayNhap, NgayXuat) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getIDsp());
            ps.setString(2, e.getTen());
            ps.setInt(3, e.getGia());
            ps.setString(4, e.getLoai());
            ps.setInt(5, e.getSoLuong());
            ps.setInt(6, e.getDaNhap());
            ps.setInt(7, e.getDaXuat());
            ps.setDate(8, e.getNgayNhap() != null ? Date.valueOf(e.getNgayNhap()) : null);
            ps.setDate(9, e.getNgayXuat() != null ? Date.valueOf(e.getNgayXuat()) : null);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean banHang(HangHoa e, String IDsp, int soLuong, String ngayXuat) {
        String sql = "UPDATE products SET SoLuong=?, DaXuat=?, NgayXuat=? WHERE IDsp=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, e.getSoLuong() - soLuong);
            ps.setInt(2, e.getDaXuat() + soLuong);
            ps.setDate(3, Date.valueOf(ngayXuat));
            ps.setString(4, IDsp);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean xoaHangHoa(String IDsp) {
        String sql = "DELETE FROM products WHERE IDsp = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, IDsp);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean chinhSuaID(String newID, HangHoa hangHoa) {
        String sql = "UPDATE products SET IDsp = ? WHERE IDsp = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newID);
            ps.setString(2, hangHoa.getIDsp());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean chinhSuaHangHoa(HangHoa e, String IDsp) {
        String sql = "UPDATE products SET Ten=?, Gia=?, SoLuong=?, DaXuat=?, NgayXuat=?, Loai=?, DaNhap=?, NgayNhap=? WHERE IDsp=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getTen());
            ps.setInt(2, e.getGia());
            ps.setInt(3, e.getSoLuong());
            ps.setInt(4, e.getDaXuat());
            ps.setDate(5, e.getNgayXuat() != null ? Date.valueOf(e.getNgayXuat()) : null);
            ps.setString(6, e.getLoai());
            ps.setInt(7, e.getDaNhap());
            ps.setDate(8, e.getNgayNhap() != null ? Date.valueOf(e.getNgayNhap()) : null);
            ps.setString(9, IDsp);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ArrayList<HangHoa> getListHangHoa1() {
        ArrayList<HangHoa> list = new ArrayList<>();
        String sql = "SELECT IDsp, Ten, Loai, Gia, SoLuong, DaNhap, NgayNhap, DaXuat, NgayXuat FROM products";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String IDsp = rs.getString("IDsp");
                String ten = rs.getString("Ten");
                String loai = rs.getString("Loai");
                int gia = rs.getInt("Gia");
                int soLuong = rs.getInt("SoLuong");
                int daNhap = rs.getInt("DaNhap");
                Date ngayNhapDate = rs.getDate("NgayNhap");
                int daXuat = rs.getInt("DaXuat");
                Date ngayXuatDate = rs.getDate("NgayXuat");

                HangHoa hh = new HangHoa(IDsp, ten, gia, loai, soLuong, daNhap, daXuat);
                hh.setNgayNhap(ngayNhapDate != null ? ngayNhapDate.toLocalDate() : null);
                hh.setNgayXuat(ngayXuatDate != null ? ngayXuatDate.toLocalDate() : null);
                list.add(hh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<HangHoa> searchHangHoa(String tk) {
        ArrayList<HangHoa> list = new ArrayList<>();
        String sql = "SELECT IDsp, Ten, Loai, Gia, SoLuong, DaNhap, NgayNhap, DaXuat, NgayXuat FROM products WHERE IDsp = ? OR Ten LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tk);
            ps.setString(2, "%" + tk + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String IDsp = rs.getString("IDsp");
                    String ten = rs.getString("Ten");
                    String loai = rs.getString("Loai");
                    int gia = rs.getInt("Gia");
                    int soLuong = rs.getInt("SoLuong");
                    int daNhap = rs.getInt("DaNhap");
                    Date ngayNhapDate = rs.getDate("NgayNhap");
                    int daXuat = rs.getInt("DaXuat");
                    Date ngayXuatDate = rs.getDate("NgayXuat");

                    HangHoa hh = new HangHoa(IDsp, ten, gia, loai, soLuong, daNhap, daXuat);
                    hh.setNgayNhap(ngayNhapDate != null ? ngayNhapDate.toLocalDate() : null);
                    hh.setNgayXuat(ngayXuatDate != null ? ngayXuatDate.toLocalDate() : null);
                    list.add(hh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phương thức đăng ký người dùng mới
    public static boolean registerUser(String username, String hashedPassword, String role) {
        String sql = "INSERT INTO users(username, password, nickname, quyen) VALUES(?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setString(3, username); // Set nickname as username by default
            ps.setString(4, role);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static String getPasswordByUsername(String username) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

 // Ví dụ câu truy vấn SQL trong phương thức getUserRole
    public String getUserRole(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String role = null;

        try {
            connection = DriverManager.getConnection(URL, username, PASSWORD);
            String sql = "SELECT user_role FROM users WHERE username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                role = resultSet.getString("user_role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối, statement và resultSet
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return role;
    }


    public boolean authenticateUser(String username, String password) {
        // Mã hóa mật khẩu để so sánh với giá trị trong cơ sở dữ liệu
        String hashedPassword = hashPassword(password);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true nếu có kết quả (tức là đăng nhập thành công)
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());

            // Chuyển đổi mảng byte thành dạng hex
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    

    public static void main(String[] args) {
        try (Connection connection = JDBCConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Kết nối cơ sở dữ liệu thành công!");
            } else {
                System.out.println("Kết nối cơ sở dữ liệu thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

