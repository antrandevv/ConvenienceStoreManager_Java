package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.SanPham;

public class ChiTietHoaDon_dao {
	public ArrayList<ChiTietHoaDon> getAllCTHD(){
		ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String query = "SELECT ct.*, sp.TenSP, sp.GiaBan " +
                    "FROM ChiTietHoaDon ct " +
                    "JOIN SanPham sp ON ct.MaSP = sp.MaSP";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				 HoaDon hd = new HoaDon(rs.getString("MaHD"));
				 SanPham sp = new SanPham(rs.getString("MaSP"), rs.getString("TenSP"),rs.getDouble("GiaBan"));
				 int soLuong = rs.getInt("SoLuong");
				 double thanhTien = rs.getDouble("ThanhTien");
				 double thue = rs.getDouble("Thue");
				 
				 ChiTietHoaDon cthd = new ChiTietHoaDon(hd, sp, soLuong, thanhTien, thue);
				 dsCTHD.add(cthd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsCTHD;
	}
	
	public ArrayList<ChiTietHoaDon> getChiTietTheoMaHD(String maHD) {
	    ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
	    try {
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();
	        String query = "SELECT ct.*, sp.TenSP, sp.GiaBan " +
	                       "FROM ChiTietHoaDon ct " +
	                       "JOIN SanPham sp ON ct.MaSP = sp.MaSP " +
	                       "WHERE ct.MaHD = ?";
	        PreparedStatement stmt = con.prepareStatement(query);
	        stmt.setString(1, maHD);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            HoaDon hd = new HoaDon(rs.getString("MaHD"));
	            SanPham sp = new SanPham();
	            sp.setMaSP(rs.getString("MaSP"));
	            sp.setTenSP(rs.getString("TenSP"));
	            sp.setGiaBan(rs.getDouble("GiaBan"));   
	            int soLuong = rs.getInt("SoLuong");
	            double thanhTien = rs.getDouble("ThanhTien");
	            double thue = rs.getDouble("Thue");
	            ChiTietHoaDon cthd = new ChiTietHoaDon(hd, sp, soLuong, thanhTien, thue);
	            dsCTHD.add(cthd);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsCTHD;
	}
	public boolean themChiTietHoaDon(ChiTietHoaDon cthd) {
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        
	        String sql = "INSERT INTO ChiTietHoaDon (MaHD, MaSP, TenSP, GiaBan, SoLuong, Thue, ThanhTien) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        stmt = con.prepareStatement(sql);
	        
	        stmt.setString(1, cthd.getMaHD().getMaHD());
	        stmt.setString(2, cthd.getSanPham().getMaSP());
	        stmt.setString(3, cthd.getSanPham().getTenSP()); 
	        stmt.setDouble(4, cthd.getSanPham().getGiaBan()); 
	        stmt.setInt(5, cthd.getSoLuong());
	        stmt.setDouble(6, cthd.getThue());
	        stmt.setDouble(7, cthd.getThanhTien());
	        
	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Lỗi SQL tại ChiTietHoaDon_dao: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	
}
