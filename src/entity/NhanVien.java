package entity;

import java.util.Objects;

import entity.CaLamViec;
import entity.NhanVien;
import entity.ViTri;

	public class NhanVien {
	private String maNV;
	private String tenNV;
	private boolean gioiTinh;
	private int tuoi;
	private String soDienThoai;
	private String email;
	private String diaChi;
	private CaLamViec caLamViec;
	private ViTri viTri;
	private String hinhAnh;
	private double heSoLuong;
	private double luongCoBan;

	public NhanVien() {	
	// TODO Auto-generated constructor stub
	}

	public NhanVien(String maNV) {
	this.maNV = maNV;
	}
	
	

	public NhanVien(String maNV, String tenNV) {

		this.maNV = maNV;
		this.tenNV = tenNV;
	}

	public NhanVien(String maNV, String tenNV, boolean gioiTinh, int tuoi, String soDienThoai, String email,
		String diaChi, CaLamViec caLamViec, ViTri viTri, String hinhAnh, double heSoLuong, double luongCoBan) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.gioiTinh = gioiTinh;
		this.tuoi = tuoi;
		this.soDienThoai = soDienThoai;
		this.email = email;
		this.diaChi = diaChi;
		this.caLamViec = caLamViec;
		this.viTri = viTri;
		this.hinhAnh = hinhAnh;
		this.heSoLuong=heSoLuong;
		this.luongCoBan=luongCoBan;
	}

	public String getMaNV() {
		return maNV;
	}
	
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	
	public String getTenNV() {
		return tenNV;
	}
	
	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}
	
	public boolean getGioiTinh() {
		return gioiTinh;
	}
	
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	
	public int getTuoi() {
		return tuoi;
	}
	
	public void setTuoi(int tuoi) {
		this.tuoi = tuoi;
	}
	
	public String getSoDienThoai() {
		return soDienThoai;
	}
	
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDiaChi() {
		return diaChi;
	}
	
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	
	public CaLamViec getCaLamViec() {
		return caLamViec;
	}
	
	public void setCaLamViec(CaLamViec caLamViec) {
		this.caLamViec = caLamViec;
	}
	
	public ViTri getViTri() {
		return viTri;
	}
	
	public void setViTri(ViTri viTri) {
		this.viTri = viTri;
	}
	
	public String getHinhAnh() {
		return hinhAnh;
	}
	
	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	
	
	
	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", gioiTinh=" + gioiTinh + ", tuoi=" + tuoi
				+ ", soDienThoai=" + soDienThoai + ", email=" + email + ", diaChi=" + diaChi + ", caLamViec="
				+ caLamViec + ", viTri=" + viTri + ", hinhAnh=" + hinhAnh + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(maNV);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNV, other.maNV);
	}

	public double getHeSoLuong() {
		return heSoLuong;
	}

	public void setHeSoLuong(double heSoLuong) {
		this.heSoLuong = heSoLuong;
	}

	public double getLuongCoBan() {
		return luongCoBan;
	}

	public void setLuongCoBan(double luongCoBan) {
		this.luongCoBan = luongCoBan;
	}
	
	public double getLuong() {
	    return this.heSoLuong * this.luongCoBan;
	}
	
	
}
