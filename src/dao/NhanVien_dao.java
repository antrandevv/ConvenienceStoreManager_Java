package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.CaLamViec; // import dung package
import entity.NhanVien;
import entity.ViTri;

public class NhanVien_dao {
	

	// lay dsnv tu database
	public ArrayList<NhanVien> getTableNhanVien() {
		ArrayList<NhanVien> dsNV = new ArrayList<NhanVien>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT * FROM NhanVien";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				String maNV = rs.getString("MaNV");
				String tenNV = rs.getString("TenNV");
				boolean gioiTinh = rs.getBoolean("GioiTinh");
				
				int tuoi = rs.getInt("Tuoi");
				String diaChi = rs.getString("DiaChi");
				String soDienThoai = rs.getString("SoDienThoai");
				String email = rs.getString("Email");
				
				
				String hinhAnh = rs.getString("HinhAnh");

				String caLamStr = rs.getString("CaLamViec");
				String viTriStr = rs.getString("ViTri");

				CaLamViec ca = null;
				if (caLamStr != null) {
				    try { ca = CaLamViec.valueOf(caLamStr); } catch (Exception e) {}
				}

				ViTri vt = null;
				if (viTriStr != null) {
				    try { vt = ViTri.valueOf(viTriStr); } catch (Exception e) {}
				}
				double heSo = rs.getDouble("HeSoLuong");
				double luongCB = rs.getDouble("LuongCoBan");

				NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, tuoi, soDienThoai, email, diaChi, ca, vt, hinhAnh, heSo, luongCB);

				dsNV.add(nv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsNV;
	}

	// them nhan vien
	public boolean themNhanVien(NhanVien nv) throws Exception {
	    Connection con = connectDB.ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	    		String sql = "INSERT INTO NhanVien VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        stmt = con.prepareStatement(sql);
	        
	        stmt.setString(1, nv.getMaNV());
	        stmt.setString(2, nv.getTenNV());
	        stmt.setBoolean(3, nv.getGioiTinh()); 
	        stmt.setInt(4, nv.getTuoi()); 
	        stmt.setString(5, nv.getDiaChi());
	        stmt.setString(6, nv.getSoDienThoai());
	        stmt.setString(7, nv.getEmail());
	        
	        // neu la enum thi .name(), neu object thi .getMaCa()
	        stmt.setString(8, nv.getHinhAnh());
	        stmt.setString(9, nv.getCaLamViec().name()); 
	        stmt.setString(10, nv.getViTri().name());
	        stmt.setDouble(11, nv.getHeSoLuong());
	        stmt.setDouble(12, nv.getLuongCoBan());
	        
	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	    	if (e.getErrorCode() == 2627 || e.getErrorCode() == 2601) {
	            throw new Exception("Mã nhân viên này đã tồn tại trong hệ thống. Ấn 'Thêm' để phát sinh mã");
	        } else {
	            throw e;
	        }
	    }
	    return n > 0;
	}

	// sua nhan vien
	public boolean suaNhanVien(NhanVien nv) {
	    Connection con = connectDB.ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	    	// sua mau don
	    	String sql = "UPDATE NhanVien SET TenNV = ?, GioiTinh = ?, Tuoi = ?, DiaChi = ?, SoDienThoai = ?, Email = ?, CaLamViec = ?, ViTri = ?, hinhAnh = ?, HeSoLuong = ?, LuongCoBan = ? WHERE maNV = ?";	    
	    	stmt = con.prepareStatement(sql);

	    	stmt.setString(1, nv.getTenNV());
	    	
	    	// sua gioi tinh
	    	stmt.setBoolean(2, nv.getGioiTinh()); 

	    	//doi voi int
	        stmt.setInt(3, nv.getTuoi());
	        stmt.setString(4, nv.getDiaChi());
	        
	        stmt.setString(5, nv.getSoDienThoai());
	        stmt.setString(6, nv.getEmail());
	        
	        // neu ca lam bi bo trong
	        stmt.setString(7, nv.getCaLamViec() != null ? nv.getCaLamViec().name() : null);
	        stmt.setString(8, nv.getViTri() != null ? nv.getViTri().name() : null);
	        stmt.setString(9, nv.getHinhAnh());
	        
	        // WHERE trong SQL
	        stmt.setDouble(10, nv.getHeSoLuong());
	        stmt.setDouble(11, nv.getLuongCoBan());
	        stmt.setString(12, nv.getMaNV());
	        
	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}

	//xoa nhan vien
	public boolean xoaNhanVien(String maNV) throws Exception {
		Connection con = connectDB.ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			String sql = "DELETE FROM NhanVien WHERE MaNV = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maNV);
			
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			//loi khoa ngoai, 547 nhan vien dang co hoa don
			if (e.getErrorCode() == 547) {
				throw new Exception("Không thể xóa! Nhân viên này đang có dữ liệu liên quan ở bảng khác (ví dụ: Hóa đơn/Phiếu nhập).");
			} else {
				throw new Exception("Lỗi ràng buộc dữ liệu " + e.getMessage());
			}
		}
		return n > 0;
	}
	
	public double layLuongNhanVien(String maNV) {
	    for (NhanVien nv : getTableNhanVien()) {
	        if (nv.getMaNV().equals(maNV)) {
	            return nv.getLuong(); // Sử dụng logic đã có trong Entity
	        }
	    }
	    return 0;
	}
}