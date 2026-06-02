package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DonViTinh;
import entity.HanSuDung;
import entity.LoaiSanPham;
import entity.NhaCungCap;
import entity.SanPham;
import entity.TrangThai;

public class SanPham_dao {
	public ArrayList<SanPham> getTableSanPham(){
		ArrayList<SanPham> dsSP = new ArrayList<SanPham>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
//			String sql = "Select * from SanPham";
			String sql = "SELECT sp.*, ncc.* FROM SanPham sp JOIN NhaCungCap ncc ON sp.NhaCungCap = ncc.MaNCC";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				String maSP = rs.getString(1);
				String tenSP = rs.getString(2);
				String loaiSP = rs.getString(3);
				double giaBan = rs.getDouble(4);
				double giaNhap = rs.getDouble(5);
				int soLuongTon = rs.getInt(6);
				String donViTinh = rs.getString(7);
				NhaCungCap nhaCungCap = new NhaCungCap(rs.getString(8), rs.getString("TenNCC"), rs.getString("SoDienThoai"), rs.getString("DiaChi"), rs.getString("Email"));
				String hinhAnh = rs.getString(9);
				String hanSD = rs.getString(10);
				String trangThai = rs.getString(11);
				
				SanPham sp = new SanPham(maSP, tenSP, LoaiSanPham.valueOf(loaiSP), giaBan, giaNhap, soLuongTon, DonViTinh.valueOf(donViTinh), nhaCungCap, hinhAnh, HanSuDung.valueOf(hanSD), TrangThai.valueOf(trangThai));
				dsSP.add(sp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsSP;
	}
	public boolean themSanPham(SanPham sp) throws Exception{
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        
	        String sql = "INSERT INTO SanPham VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        stmt = con.prepareStatement(sql);
	        
	        stmt.setString(1, sp.getMaSP());
	        stmt.setString(2, sp.getTenSP());
	        stmt.setString(3, sp.getLoaiSanPham().name());
	        stmt.setDouble(4, sp.getGiaBan());
	        stmt.setDouble(5, sp.getGiaNhap()); 
	        stmt.setInt(6, sp.getSoLuongTon());
	        stmt.setString(7, sp.getDonViTinh().name());
	        stmt.setString(8, sp.getNhaCungCap().getMaNCC()); 
	        stmt.setString(9, sp.getHinhAnh());
	        stmt.setString(10, sp.getHanSD().name());
	        stmt.setString(11, sp.getTrangThai().name());
	        
	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	    	if (e.getErrorCode() == 2627 || e.getErrorCode() == 2601) {
	            throw new Exception("Mã sản phẩm này đã tồn tại trong hệ thống. Ấn 'Thêm' để phát sinh mã");
	        } else {
	            throw e;
	        }
	    }
	    return n > 0;
	}
	public boolean suaSanPham(SanPham sp) {
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        String sql = "UPDATE SanPham SET TenSP = ?, LoaiSP = ?, GiaBan = ?, GiaNhap = ?, SoLuongTon = ?, DonViTinh = ?, NhaCungCap = ?, HinhAnh = ?, HanSuDung = ?, TrangThai = ? WHERE MaSP = ?";
	        stmt = con.prepareStatement(sql);
	        
	        stmt.setString(1, sp.getTenSP());
	        stmt.setString(2, sp.getLoaiSanPham().name());
	        stmt.setDouble(3, sp.getGiaBan());
	        stmt.setDouble(4, sp.getGiaNhap()); 
	        stmt.setInt(5, sp.getSoLuongTon());
	        stmt.setString(6, sp.getDonViTinh().name());
	        stmt.setString(7, sp.getNhaCungCap().getMaNCC()); 
	        stmt.setString(8, sp.getHinhAnh());
	        stmt.setString(9, sp.getHanSD().name());
	        stmt.setString(10, sp.getTrangThai().name());
	        stmt.setString(11, sp.getMaSP());
	        
	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	public boolean xoaSanPham(String maSP) throws Exception {
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			String sql = "DELETE FROM SanPham WHERE MaSP = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maSP);
			
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// bat loi khoa ngoai 547
			if (e.getErrorCode() == 547) {
				throw new Exception("Không thể xóa! Sản phẩm này đang có dữ liệu liên quan ở bảng khác.");
			} else {
				throw new Exception("Lỗi ràng buộc dữ liệu " + e.getMessage());
			}
		}
		return n > 0;
	}
	
	public int getSoLuongTon(String tenSP) {
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int sl = 0;
		try {
			String query = "Select SoLuongTon From SanPham Where TenSP = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, tenSP);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
	            sl = rs.getInt("SoLuongTon"); 
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sl;
	}
	
	public SanPham getSanPhamTheoMa(String maSP) {
	    SanPham sp = null;
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    try {
	        String sql = "SELECT * FROM SanPham WHERE MaSP = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, maSP);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            sp = new SanPham();
	            sp.setMaSP(rs.getString("MaSP"));
	            sp.setTenSP(rs.getString("TenSP"));
	            sp.setGiaBan(rs.getDouble("GiaBan")); 
	            sp.setGiaNhap(rs.getDouble("GiaNhap"));
	            sp.setSoLuongTon(rs.getInt("SoLuongTon"));
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return sp;
	}
	
	public boolean capNhatSoLuong(String maSP, int soLuongThem) {
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {

	        String sql = "UPDATE SanPham SET SoLuongTon = SoLuongTon + ? WHERE MaSP = ?";
	        stmt = con.prepareStatement(sql);
	        
	        stmt.setInt(1, soLuongThem);
	        stmt.setString(2, maSP);
	        
	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	    return n > 0;
	}
	public SanPham getSanPhamTheoTen(String tenSP) {
	    SanPham sp = null;
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    try {
	        String sql = "SELECT sp.*, ncc.* FROM SanPham sp " +
	                     "JOIN NhaCungCap ncc ON sp.NhaCungCap = ncc.MaNCC " + 
	                     "WHERE TenSP = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, tenSP);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            NhaCungCap ncc = new NhaCungCap(rs.getString("NhaCungCap"), rs.getString("TenNCC"), 
	                                            rs.getString("SoDienThoai"), rs.getString("DiaChi"), rs.getString("Email"));
	            
	            sp = new SanPham(rs.getString("MaSP"), rs.getString("TenSP"), 
	                             LoaiSanPham.valueOf(rs.getString("LoaiSP")), rs.getDouble("GiaBan"), 
	                             rs.getDouble("GiaNhap"), rs.getInt("SoLuongTon"), 
	                             DonViTinh.valueOf(rs.getString("DonViTinh")), ncc, 
	                             rs.getString("HinhAnh"), HanSuDung.valueOf(rs.getString("HanSuDung")), 
	                             TrangThai.valueOf(rs.getString("TrangThai")));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return sp;
	}
}
