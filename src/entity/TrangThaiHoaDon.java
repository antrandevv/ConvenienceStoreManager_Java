package entity;

public enum TrangThaiHoaDon {
	CHUA_THANH_TOAN("Chưa thanh toán"),DA_THANH_TOAN("Đã thanh toán"),DA_HUY("Đã hủy");
	
	private final String moTa;

	private TrangThaiHoaDon(String moTa) {
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
