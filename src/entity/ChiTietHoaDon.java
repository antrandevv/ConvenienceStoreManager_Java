package entity;

import java.util.Objects;

public class ChiTietHoaDon {
	private HoaDon maHD;
	private SanPham sanPham;
	private int soLuong; // so luong san pham cua 1 hóa đơn nên dùng int
	private double thanhTien;
	private double thue; // khong biet nen de double hay enum;

	public ChiTietHoaDon() {
		// TODO Auto-generated constructor stub
	}

	
	
	public ChiTietHoaDon(HoaDon maHD, SanPham sanPham, int soLuong, double thanhTien,double thue) {
		this.maHD = maHD;
		this.sanPham = sanPham;
		this.soLuong = soLuong;
		this.thanhTien = thanhTien;
		this.thue = thue;
	}



	public HoaDon getMaHD() {
		return maHD;
	}

	public void setMaHD(HoaDon maHD) {
		this.maHD = maHD;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}


	public double getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}


	public double getThue() {
		return thue;
	}

	public void setThue(double thue) {
		this.thue = thue;
	}





	@Override
	public int hashCode() {
		return Objects.hash(maHD, sanPham);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietHoaDon other = (ChiTietHoaDon) obj;
		return Objects.equals(maHD, other.maHD) && Objects.equals(sanPham, other.sanPham);
	}



	@Override
	public String toString() {
		return "ChiTietHoaDon [maHD=" + maHD + ", sanPham=" + sanPham + ", soLuong=" + soLuong + 
			 ", ThanhTien=" + thanhTien +  ", thue=" + thue + "]";
	}

	

}
