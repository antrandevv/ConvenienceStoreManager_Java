package entity;


public enum LoaiKhachHang {

	THANH_VIEN("Thành viên"),VANG_LAI("Vãng lai"), VIP("VIP");

	private final String moTa;

	LoaiKhachHang(String moTa) {
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
