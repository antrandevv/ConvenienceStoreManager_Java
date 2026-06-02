package entity;

public enum LoaiSanPham {
	DO_UONG("Đồ uống"), VAN_PHONG_PHAM("Văn phòng phẩm"), BANH_KEO("Bánh kẹo"), SUA("Sữa"), MI_GOI("Mì gói");

	private final String moTa;

	LoaiSanPham(String moTa) {
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
