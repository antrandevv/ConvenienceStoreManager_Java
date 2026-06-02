package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entity.PhieuNhap;
import entity.ChiTietPhieuNhap;
import entity.SanPham;
import connectDB.ConnectDB;

public class ChiTietPhieuNhap_DAO {
	
	public ChiTietPhieuNhap_DAO() {
		
	}
	
	public ArrayList<ChiTietPhieuNhap> getAllChiTietPhieuNhap(){
		
		ArrayList<ChiTietPhieuNhap> dsCTPN = new ArrayList<ChiTietPhieuNhap>();
		
		try {
				ConnectDB.getInstance();
				Connection con = ConnectDB.getConnection();
				String sql = "Select * from ChiTietPhieuNhap";
				Statement statement = con.createStatement();
				
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()) {
					
					PhieuNhap maPN = new PhieuNhap(rs.getString("MaPN"));
					SanPham sP = new SanPham(rs.getString("MaSP"), rs.getString("TenSP"), rs.getDouble("GiaNhap"));
					int soLuong = rs.getInt("SoLuong");
					double thanhTien = rs.getDouble("ThanhTien");
					ChiTietPhieuNhap cTPN = new ChiTietPhieuNhap(maPN, sP, soLuong, thanhTien);
					dsCTPN.add(cTPN);
				}
		}catch(SQLException e) {
				e.printStackTrace();
		}
		return dsCTPN;
	}
	
	public boolean themChiTietPhieuNhap(ChiTietPhieuNhap cTPN) throws Exception {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			
			String sql = "INSERT INTO ChiTietPhieuNhap VALUES(?, ?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cTPN.getMaPN().getMaPN());
			stmt.setString(2, cTPN.getsP().getMaSP());
			stmt.setString(3, cTPN.getsP().getTenSP());
			stmt.setInt(4, cTPN.getSoLuong());
			stmt.setDouble(5, cTPN.getsP().getGiaNhap());
			stmt.setDouble(6, cTPN.getThanhTien());
			n = stmt.executeUpdate();
		} catch(SQLException e){
			
			if(e.getErrorCode() == 2627 || e.getErrorCode() == 2601) {
				throw new Exception("Phiếu nhập và sản phẩm này đã tồn tại. Vui lòng tạo phiếu mới hoặc thêm sản phẩm khác!");
			}
			e.printStackTrace();
		}
		
		finally{
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
	
	public boolean xoaChiTietPhieuNhap(String maPN, String maSP) {
		
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			String sql = "DELETE FROM ChiTietPhieuNhap WHERE MaPN = ? AND MaSP = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maPN);
			stmt.setString(2, maSP);
			n = stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		
		finally{
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
	
	public boolean suaChiTietPhieuNhap(ChiTietPhieuNhap cTPN) {
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		 
		try {
			String sql = "UPDATE ChiTietPhieuNhap SET TenSP = ?, SoLuong = ?, GiaNhap = ?, ThanhTien = ? WHERE MaPN = ? AND MaSP = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cTPN.getsP().getTenSP());
			stmt.setInt(2, cTPN.getSoLuong());
			stmt.setDouble(3, cTPN.getsP().getGiaNhap());
			stmt.setDouble(4, cTPN.getThanhTien());
			stmt.setString(5, cTPN.getMaPN().getMaPN());
			stmt.setString(6, cTPN.getsP().getMaSP());
			
			n = stmt.executeUpdate();
		} catch(SQLException e) {
			
			e.printStackTrace();
		}
		finally{
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
	
	public ArrayList<ChiTietPhieuNhap> getChiTietTheoMa(String maPN) {
		
	    ArrayList<ChiTietPhieuNhap> ds = new ArrayList<ChiTietPhieuNhap>();
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;

	    try {
	        String sql = "SELECT * FROM ChiTietPhieuNhap WHERE MaPN = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, maPN);

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            PhieuNhap pN = new PhieuNhap(rs.getString("MaPN"));
	            SanPham sP = new SanPham(rs.getString("MaSP"), rs.getString("TenSP"), rs.getDouble("GiaNhap"));
	            int soLuong = rs.getInt("SoLuong");
	            double thanhTien = rs.getDouble("ThanhTien");

	            ChiTietPhieuNhap cTPN = new ChiTietPhieuNhap(pN, sP, soLuong, thanhTien);
	            ds.add(cTPN);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	    return ds;
	}
}
