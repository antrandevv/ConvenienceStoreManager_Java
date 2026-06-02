package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.NhaCungCap;

public class NhaCungCap_dao {
	public ArrayList<NhaCungCap> getTableNhaCungCap() {
		ArrayList<NhaCungCap> dsNCC = new ArrayList<NhaCungCap>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from NhaCungCap";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String maNCC = rs.getString("MaNCC");
				String tenNCC = rs.getString("TenNCC");
				String soDT = rs.getString("SoDienThoai");
				String diaChi = rs.getString("DiaChi");
				String email = rs.getString("Email");

				NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, soDT, diaChi, email);
				dsNCC.add(ncc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsNCC;
	}

	public boolean themNhaCungCap(NhaCungCap ncc) throws Exception {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			String sql = "INSERT INTO NhaCungCap VALUES(?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ncc.getMaNCC());
			stmt.setString(2, ncc.getTenNCC());
			stmt.setString(3, ncc.getSoDienThoai());
			stmt.setString(4, ncc.getDiaChi());
			stmt.setString(5, ncc.getEmail());
			
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == 2627 || e.getErrorCode() == 2601) {
				throw new Exception("Mã nhà cung cấp này đã tồn tại trong hệ thống. Ấn 'Thêm' để phát sinh mã");
			} else {
				throw e;
			}
		}
		return n > 0;
	}

	public boolean xoaNhaCungCap(String maNCC) throws SQLException {
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		// nem loi len gui de xu ly loi k xoa dc
		String sql = "DELETE FROM NhaCungCap WHERE MaNCC = ?";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, maNCC);
		n = stmt.executeUpdate();

		return n > 0;
	}

	public boolean suaNhaCungCap(NhaCungCap ncc) {
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			String sql = "UPDATE NhaCungCap SET TenNCC = ?, SoDienThoai = ?, DiaChi = ?, Email = ? WHERE MaNCC = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ncc.getTenNCC());
			stmt.setString(2, ncc.getSoDienThoai());
			stmt.setString(3, ncc.getDiaChi());
			stmt.setString(4, ncc.getEmail());
			stmt.setString(5, ncc.getMaNCC());

			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}
}
