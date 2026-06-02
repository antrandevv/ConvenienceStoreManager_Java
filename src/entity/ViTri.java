package entity;

public enum ViTri {
	NHAN_VIEN("Nhân viên"), 
	QUAN_LY("Quản lý");

	private final String moTa;

	ViTri(String moTa) {
		this.moTa = moTa;
	}

	public String getMoTa() {
		return moTa;
	}

	@Override
	public String toString() {
		return this.moTa;
	}
}