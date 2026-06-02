package entity;

import java.util.Objects;

public class KhachHang {
	private String maKH;
	private String tenKH;
	private String soDienThoai;
	private int diemTichLuy;
	private int diemDaNhan;
	private LoaiKhachHang loaiKhachHang;

	public KhachHang() {
		// TODO Auto-generated constructor stub
	}

	public KhachHang(String maKH) {
		this.maKH = maKH;
	}
	
	

	public KhachHang(String maKH, String tenKH) {
		this.maKH = maKH;
		this.tenKH = tenKH;
	}

	public KhachHang(String maKH, String tenKH, String soDienThoai,int diemTichLuy, int diemDaNhan,
			LoaiKhachHang loaiKhachHang) {	
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.soDienThoai = soDienThoai;
		this.diemTichLuy = diemTichLuy;
		this.diemDaNhan = diemDaNhan;
		this.loaiKhachHang = loaiKhachHang;
	}

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public int getDiemTichLuy() {
		return diemTichLuy;
	}

	public void setDiemTichLuy(int diemTichLuy) {
		this.diemTichLuy = diemTichLuy;
	}

	public int getDiemDaNhan() {
		return diemDaNhan;
	}

	public void setDiemDaNhan(int diemDaNhan) {
		this.diemDaNhan = diemDaNhan;
	}

	public LoaiKhachHang getLoaiKhachHang() {
		return loaiKhachHang;
	}

	public void setLoaiKhachHang(LoaiKhachHang loaiKhachHang) {
		this.loaiKhachHang = loaiKhachHang;
	}

	public void congDiem(int diemCong) {
		if (diemCong > 0) {
			this.diemTichLuy += diemCong;
			this.diemDaNhan += diemCong;
		}
	}

	public boolean truDiem(int diemTru) {
		if (diemTru > 0 && this.diemTichLuy >= diemTru) {
			this.diemTichLuy -= diemTru;
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(diemTichLuy, maKH, soDienThoai, tenKH);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(soDienThoai, other.soDienThoai);
	}

	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", soDienThoai=" + soDienThoai
				+ ", diemTichLuy=" + diemTichLuy + "]";
	}

}
