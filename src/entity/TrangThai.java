package entity;

public enum TrangThai {

	CON_HANG("Còn hàng"), HET_HANG("Hết hàng");

	private final String moTa;

	TrangThai(String moTa) {
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