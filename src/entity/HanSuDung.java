package entity;

public enum HanSuDung {
	BA_THANG("3 Tháng"), SAU_THANG("6 Tháng"), CHIN_THANG("9 Tháng"), MUOI_HAI_THANG("12 Tháng"), MOT_NAM("1 Năm"), BA_NAM("3 Năm");

	private final String moTa;

	HanSuDung(String moTa) {
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
