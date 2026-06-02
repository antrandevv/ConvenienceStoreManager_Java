package entity;

public enum CaLamViec {
	CA_SANG("Ca sáng"), 
	CA_CHIEU("Ca chiều"), 
	CA_TOI("Ca tối");
//	CA_DEM("Ca đêm"),
//	CA_HANH_CHINH("Ca hành chính");

	private final String moTa;

	CaLamViec(String moTa) {
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