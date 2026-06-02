package entity;

import java.util.Objects;

public class TaiKhoan {
	private String tenTK;
	private String matKhau;
	private ViTri viTri;
	NhanVien maNV;

	public TaiKhoan() {
		// TODO Auto-generated constructor stub
	}

	

	public TaiKhoan(String tenTK, String matKhau, ViTri viTri, NhanVien maNV) {
		super();
		this.tenTK = tenTK;
		this.matKhau = matKhau;
		this.viTri = viTri;
		this.maNV = maNV;
	}



	public String getTenTK() {
		return tenTK;
	}

	public void setTenTK(String tenTK) {
		this.tenTK = tenTK;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public ViTri getViTri() {
		return viTri;
	}

	public void setViTri(ViTri viTri) {
		this.viTri = viTri;
	}
	
	

	public NhanVien getMaNV() {
		return maNV;
	}



	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}



	@Override
	public int hashCode() {
		return Objects.hash(tenTK);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaiKhoan other = (TaiKhoan) obj;
		return Objects.equals(tenTK, other.tenTK);
	}



	

}
