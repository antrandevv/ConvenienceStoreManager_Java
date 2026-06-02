package entity;

import java.util.Objects;

public class ChiTietPhieuNhap {
	private PhieuNhap maPN;
	private SanPham sP;
	private int soLuong;
	private double thanhTien;
	
	public ChiTietPhieuNhap() {
		// TODO Auto-generated constructor stub
	}

	
	public ChiTietPhieuNhap(PhieuNhap maPN, SanPham sP, int soLuong, double thanhTien) {

		this.maPN = maPN;
		this.sP = sP;
		this.soLuong = soLuong;
		this.thanhTien = thanhTien;
	}


	public PhieuNhap getMaPN() {
		return maPN;
	}

	public void setMaPN(PhieuNhap maPN) {
		this.maPN = maPN;
	}

	public SanPham getsP() {
		return sP;
	}

	public void setsP(SanPham sP) {
		this.sP = sP;
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

	@Override
	public int hashCode() {
		return Objects.hash(maPN, sP);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietPhieuNhap other = (ChiTietPhieuNhap) obj;
		return Objects.equals(maPN, other.maPN) && Objects.equals(sP, other.sP);
	}
	
}
