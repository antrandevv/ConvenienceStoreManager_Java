package entity	;

import java.util.Objects;

public class SanPham {
	private String maSP;
	private String tenSP;
	private LoaiSanPham loaiSanPham;
	private double giaBan;
	private double giaNhap;
	private int soLuongTon;
	private DonViTinh donViTinh;
	private NhaCungCap nhaCungCap;
	private String hinhAnh;
	private HanSuDung hanSD;
	private TrangThai trangThai;

	public SanPham() {
	}

	public SanPham(String maSP) {
		this.maSP = maSP;
	}
	
	public SanPham(String maSP, String tenSP, double giaNhap) {
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.giaNhap = giaNhap;
	}

	public SanPham(String maSP, String tenSP, LoaiSanPham loaiSanPham, double giaBan, double giaNhap, int soLuongTon,
			DonViTinh donViTinh, NhaCungCap nhaCungCap, String hinhAnh, HanSuDung hanSD, TrangThai trangThai) {
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.loaiSanPham = loaiSanPham;
		this.giaBan = giaBan;
		this.giaNhap = giaNhap;
		this.soLuongTon = soLuongTon;
		this.donViTinh = donViTinh;
		this.nhaCungCap = nhaCungCap;
		this.hinhAnh = hinhAnh;
		this.hanSD = hanSD;
		this.trangThai = trangThai;
	}

	public String getMaSP() {
		return maSP;
	}

	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}

	public String getTenSP() {
		return tenSP;
	}

	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}

	public LoaiSanPham getLoaiSanPham() {
		return loaiSanPham;
	}

	public void setLoaiSanPham(LoaiSanPham loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
	}

	public double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}

	public double getGiaNhap() {
		return giaNhap;
	}

	public void setGiaNhap(double giaNhap) {
		this.giaNhap = giaNhap;
	}

	public int getSoLuongTon() {
		return soLuongTon;
	}

	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}

	public DonViTinh getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(DonViTinh donViTinh) {
		this.donViTinh = donViTinh;
	}

	public NhaCungCap getNhaCungCap() {
		return nhaCungCap;
	}

	public void setNhaCungCap(NhaCungCap nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	public HanSuDung getHanSD() {
		return hanSD;
	}

	public void setHanSD(HanSuDung hanSD) {
		this.hanSD = hanSD;
	}

	public TrangThai getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(TrangThai trangThai) {
		this.trangThai = trangThai;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maSP);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SanPham other = (SanPham) obj;
		return Objects.equals(maSP, other.maSP);
	}

	@Override
	public String toString() {
		return "SanPham [maSP=" + maSP + ", tenSP=" + tenSP + ", loaiSanPham=" + loaiSanPham + ", giaBan=" + giaBan
				+ ", giaNhap=" + giaNhap + ", soLuongTon=" + soLuongTon + ", donViTinh=" + donViTinh + ", nhaCungCap="
				+ nhaCungCap + ", hinhAnh=" + hinhAnh + ", hanSD=" + hanSD + ", trangThai=" + trangThai + "]";
	}

	

	

}
