package entity;

public enum DonViTinh {
	THUNG("Thùng"), LOC("Lốc"), LON("Lon"), GOI("Gói"), CAY("Cây"), HOP("Hộp"), BICH("Bịch"), CHAI("Chai");

	private final String moTa;

	DonViTinh(String moTa) {
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
