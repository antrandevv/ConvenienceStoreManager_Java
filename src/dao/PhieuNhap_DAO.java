package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import entity.PhieuNhap;
import entity.NhaCungCap;
import entity.NhanVien;
import connectDB.ConnectDB;

public class PhieuNhap_DAO {

	public PhieuNhap_DAO() {

	}

	public ArrayList<PhieuNhap> getAllPhieuNhap() {

		ArrayList<PhieuNhap> dsPN = new ArrayList<PhieuNhap>();

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT pn.*, ncc.TenNCC, nv.TenNV FROM PhieuNhap pn JOIN NhaCungCap ncc ON pn.MaNCC = ncc.MaNCC JOIN NhanVien nv ON pn.MaNV = nv.MaNV";
			Statement statement = con.createStatement();

			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {

				String maPN = rs.getString("MaPN");
				Date date = rs.getDate("ThoiGianNhap");
				LocalDate thoiGianNhap = date.toLocalDate();
				NhaCungCap nCC = new NhaCungCap(rs.getString("MaNCC"), rs.getString("TenNCC"));
				NhanVien nV = new NhanVien(rs.getString("MaNV"), rs.getString("TenNV"));
				Double tongTien = rs.getDouble("TongTien");
				PhieuNhap pN = new PhieuNhap(maPN, thoiGianNhap, nCC, nV, tongTien);
				dsPN.add(pN);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsPN;
	}

	public boolean themPhieuNhap(PhieuNhap pN) throws Exception {

		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;

		try {

			String sql = "INSERT INTO PhieuNhap VALUES(?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, pN.getMaPN());
			stmt.setObject(2, pN.getThoiGianNhap());
			stmt.setString(3, pN.getnCC().getMaNCC());
			stmt.setString(4, pN.getnV().getMaNV());
			stmt.setDouble(5, pN.getTongTien());

			n = stmt.executeUpdate();
		} catch (SQLException e) {

			if (e.getErrorCode() == 2627 || e.getErrorCode() == 2601) {
				throw new Exception("Mã phiếu nhập đã tồn tại!");
			}
			e.printStackTrace();
		}

		finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return n > 0;
	}

	public boolean xoaPhieuNhap(String maPN) {

		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;

		try {

			String sql = "DELETE FROM PhieuNhap WHERE MaPN = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maPN);

			n = stmt.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		finally {
			try {

				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n > 0;
	}

	public boolean suaPhieuNhap(PhieuNhap pN) {

		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;

		try {

			String sql = "UPDATE PhieuNhap SET ThoiGianNhap = ?, TongTien = ?, MaNCC = ?, MaNV = ? WHERE MaPN = ?";
			stmt = con.prepareStatement(sql);
			stmt.setObject(1, pN.getThoiGianNhap());
			stmt.setDouble(2, pN.getTongTien());
			stmt.setString(3, pN.getnCC().getMaNCC());
			stmt.setString(4, pN.getnV().getMaNV());

			stmt.setString(5, pN.getMaPN());

			n = stmt.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return n > 0;
	}

	public String phatSinhMaPN() {

		ArrayList<PhieuNhap> listPN = getAllPhieuNhap();
		if (listPN.isEmpty()) {
			return "PN1";
		}
		int max = 0;
		for (PhieuNhap pn : listPN) {
			String ma = pn.getMaPN();
			try {
				int so = Integer.parseInt(ma.substring(2));
				if (so > max) {
					max = so;
				}
			} catch (Exception e) {
			}
		}
		return String.format("PN%d", max + 1);
	}
	
}