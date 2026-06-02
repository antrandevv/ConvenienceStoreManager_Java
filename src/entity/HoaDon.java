package entity;

import java.time.LocalDate;
import java.util.Objects;

public class HoaDon {
	private String maHD;
	private LocalDate ngayLap;
	private NhanVien nhanVien;
	private KhachHang khachHang;
	private TrangThaiHoaDon trangThai;
	private double tongTien;

	public HoaDon() {

	}

	public HoaDon(String maHD) {
		this.maHD = maHD;
	}

	public HoaDon(String maHD, LocalDate ngayLap, NhanVien tenNV, KhachHang tenKH, TrangThaiHoaDon trangThai,
			double tongTien) {
		this.maHD = maHD;
		this.ngayLap = ngayLap;
		this.nhanVien = tenNV;
		this.khachHang = tenKH;
		this.trangThai = trangThai;
		this.tongTien = tongTien;
	}

	public String getMaHD() {
		return maHD;
	}

	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}

	public LocalDate getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(LocalDate ngayLap) {
		this.ngayLap = ngayLap;
	}

	public NhanVien getNV() {
		return nhanVien;
	}

	public void setNV(NhanVien tenNV) {
		this.nhanVien = tenNV;
	}

	public KhachHang getKH() {
		return khachHang;
	}

	public void setKH(KhachHang tenKH) {
		this.khachHang = tenKH;
	}

	public TrangThaiHoaDon getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(TrangThaiHoaDon trangThai) {
		this.trangThai = trangThai;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maHD, ngayLap, khachHang, nhanVien, tongTien, trangThai);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(maHD, other.maHD) && Objects.equals(ngayLap, other.ngayLap)
				&& Objects.equals(khachHang, other.khachHang) && Objects.equals(nhanVien, other.nhanVien)
				&& Double.doubleToLongBits(tongTien) == Double.doubleToLongBits(other.tongTien)
				&& trangThai == other.trangThai;
	}

	@Override
	public String toString() {
		return "HoaDon [maHD=" + maHD + ", ngayLap=" + ngayLap + ", tenNV=" + nhanVien + ", tenKH=" + khachHang
				+ ", trangThai=" + trangThai + ", tongTien=" + tongTien + "]";
	}

}
