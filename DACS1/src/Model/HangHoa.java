package Model;

import java.time.LocalDate;

public class HangHoa {
	private String IDsp;
	private String ten;
	private String loai;
	private int gia;
	private int soLuong;
	private int daNhap;
	private LocalDate ngayNhap;
	private int daXuat;
	private LocalDate ngayXuat;



	public HangHoa(String IDsp, String ten, int gia, int soLuong) {
		super();
		this.IDsp = IDsp;
		this.ten = ten;
		this.gia = gia;
		this.soLuong = soLuong;
	}

	   public HangHoa (String IDsp, String ten, int gia, String loai, int soLuong, int daNhap, int daXuat) {
	        super(); 
		    this.IDsp = IDsp;
	        this.ten = ten;
	        this.loai = loai;
	        this.gia = gia;
	        this.soLuong = soLuong;
	        this.daNhap = daNhap;
	        this.daXuat = daXuat;
	    }

	public LocalDate getNgayNhap() {
		return ngayNhap;
	}

	public void setNgayNhap(LocalDate ngayNhap) {
		this.ngayNhap = ngayNhap;
	}

	public LocalDate getNgayXuat() {
		return ngayXuat;
	}

	public void setNgayXuat(LocalDate ngayXuat) {
		this.ngayXuat = ngayXuat;
	}

	public String getIDsp() {
		return IDsp;
	}

	public void setIDsp(String IDsp) {
		this.IDsp = IDsp;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public int getGia() {
		return gia;
	}

	public void setGia(int gia) {
		this.gia = gia;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public int getDaNhap() {
		return daNhap;
	}

	public void setDaNhap(int daNhap) {
		this.daNhap = daNhap;
	}

	public int getDaXuat() {
		return daXuat;
	}

	public void setDaXuat(int daXuat) {
		this.daXuat = daXuat;
	}

	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}
}
