package entity;

import java.time.LocalDate;
import java.util.Objects;

public class PhieuNhap {
	private String maPN;
	private LocalDate thoiGianNhap;
	private NhaCungCap nCC;
	private NhanVien nV;
	private double tongTien;

	public PhieuNhap() {
		// TODO Auto-generated constructor stub
	}

	public PhieuNhap(String maPN) {
		this.maPN = maPN;
	}
	
	public PhieuNhap(String maPN, LocalDate thoiGianNhap, NhaCungCap nCC, NhanVien nV, double tongTien) {
		super();
		this.maPN = maPN;
		this.thoiGianNhap = thoiGianNhap;
		this.nCC = nCC;
		this.nV = nV;
		this.tongTien = tongTien;
	}

	public String getMaPN() {
		return maPN;
	}

	public void setMaPN(String maPN) {
		this.maPN = maPN;
	}

	public LocalDate getThoiGianNhap() {
		return thoiGianNhap;
	}

	public void setThoiGianNhap(LocalDate thoiGianNhap) {
		this.thoiGianNhap = thoiGianNhap;
	}

	public NhaCungCap getnCC() {
		return nCC;
	}

	public void setnCC(NhaCungCap nCC) {
		this.nCC = nCC;
	}

	public NhanVien getnV() {
		return nV;
	}

	public void setnV(NhanVien nV) {
		this.nV = nV;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maPN);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhieuNhap other = (PhieuNhap) obj;
		return Objects.equals(maPN, other.maPN);
	}

	
	

}
