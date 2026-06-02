package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.CaLamViec;
import entity.KhachHang;
import entity.LoaiKhachHang;
import entity.NhanVien;
import entity.ViTri; 

public class KhachHang_dao {
	
	// doc du lieu tu database
	public ArrayList<KhachHang> getTableKhachHang() {
		ArrayList<KhachHang> dsKH = new ArrayList<KhachHang>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT * FROM KhachHang";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
					String ma = rs.getString("MaKH"); // hoac cach 2:      rs.getString(1)
					String ten = rs.getString("TenKH");
					String sdt = rs.getString("SoDienThoai");
					int diemTL = rs.getInt("DiemTichLuy"); // Dùng rs.getInt chuan
					int diemDN = rs.getInt("DiemDaNhan");
	
					// doc chuoi tu dataSQL
					String loaiStr = rs.getString("LoaiKH"); 
					LoaiKhachHang loai = LoaiKhachHang.valueOf(loaiStr); 

				
				// constructor
				KhachHang kh = new KhachHang(ma, ten, sdt, diemTL, diemDN, loai);
				dsKH.add(kh);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsKH;
	}

	// them
	public boolean themKhachHang(KhachHang kh) throws Exception {
	    Connection con = connectDB.ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        String sql = "INSERT INTO KhachHang VALUES(?, ?, ?, ?, ?, ?)";
	        stmt = con.prepareStatement(sql);
	        
	        stmt.setString(1, kh.getMaKH());
	        stmt.setString(2, kh.getTenKH());
	        stmt.setString(3, kh.getSoDienThoai());
	        stmt.setInt(4, kh.getDiemTichLuy()); // int
	        stmt.setInt(5, kh.getDiemDaNhan());  
	        stmt.setString(6, kh.getLoaiKhachHang().name()); // Luu Enum
	        
	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	    	//loi trung khoa chinh
	    	if (e.getErrorCode() == 2627 || e.getErrorCode() == 2601) {
	            throw new Exception("Mã khách hàng này đã tồn tại trong hệ thống!");
	        } else {
	            throw e;
	        }
	    }
	    return n > 0;
	}

	// sua thong tin khach hang
	public boolean suaKhachHang(KhachHang kh) {
	    Connection con = connectDB.ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	    	String sql = "UPDATE KhachHang SET tenKH = ?, soDienThoai = ?, diemTichLuy = ?, diemDaNhan = ?, loaiKH = ? WHERE maKH = ?";
	    	stmt = con.prepareStatement(sql);
	        
	    	stmt.setString(1, kh.getTenKH());
	    	stmt.setString(2, kh.getSoDienThoai());
	    	stmt.setInt(3, kh.getDiemTichLuy());
	    	stmt.setInt(4, kh.getDiemDaNhan());
	    	stmt.setString(5, kh.getLoaiKhachHang().name()); //lay enum
	    	stmt.setString(6, kh.getMaKH()); // maKH dat o where    
	    	
	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}

	// xoa
	public boolean xoaKhachHang(String maKH) throws Exception {
		Connection con = connectDB.ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			String sql = "DELETE FROM KhachHang WHERE maKH = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maKH);
			
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// Loi khoa ngoai (VD: Khach hang da tung dat hang)
			if (e.getErrorCode() == 547) {
				throw new Exception("Không thể xóa! Khách hàng này đang có dữ liệu hóa đơn liên quan.");
			} else {
				throw new Exception("Lỗi ràng buộc dữ liệu: " + e.getMessage());
			}
		}
		return n > 0;
	}
	public KhachHang timKiemKhachHangTheoSDT(String sdt) {
		Connection con  = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		try {
			String query = "Select * From KhachHang Where SoDienThoai = ?";
					stmt = con.prepareStatement(query);
					stmt.setString(1, sdt);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String maKH = rs.getString("maKH"); // hoac cach 2:      rs.getString(1)
				String ten = rs.getString("tenKH");
				String dt = rs.getString("soDienThoai");
				int diemTL = rs.getInt("diemTichLuy"); // Dùng rs.getInt chuan
				int diemDN = rs.getInt("diemDaNhan");

				// doc chuoi tu dataSQL
				String loaiStr = rs.getString("loaiKH"); 
				LoaiKhachHang loai = LoaiKhachHang.valueOf(loaiStr); 
				
				KhachHang kh = new KhachHang(maKH, ten, dt, diemTL, diemDN, loai);
				return kh;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}