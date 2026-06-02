package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.TaiKhoan;
import entity.ViTri;
import entity.CaLamViec;
import entity.NhanVien;
public class TaiKhoan_dao {
	
	public ArrayList<TaiKhoan> getAllTaiKhoan(){
		
		ArrayList<TaiKhoan> dsTK = new ArrayList<TaiKhoan>();
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from TaiKhoan";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String user = rs.getString("TenTaiKhoan");
				String pwd = rs.getString("MatKhau");
				String viTri = rs.getString("ViTri");
				NhanVien maNV = new NhanVien(rs.getString("MaNV")); 
				ViTri vt = ViTri.valueOf(viTri);
				
				TaiKhoan tk = new TaiKhoan(user, pwd, vt, maNV);
				dsTK.add(tk);
			}
		} catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		return dsTK;
	}
	
	public boolean themTaiKhoan(TaiKhoan tk) throws Exception {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			
			String sql = "INSERT INTO TaiKhoan VALUES(?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, tk.getTenTK());
			stmt.setString(2, tk.getMatKhau());
			stmt.setString(3, tk.getViTri().name());
			stmt.setString(4, tk.getMaNV().getMaNV());
			
			n = stmt.executeUpdate();
		} catch(SQLException e) {
			
			if (e.getErrorCode() == 2627 || e.getErrorCode() == 2601) {
				throw new Exception("Tên tài khoản đã tồn tại!");
			} else {
				throw e;
			}
		}
		
		finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		return n > 0;
	}
	
	public boolean xoaTaiKhoan(String tenTK) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			String sql = "DELETE FROM TaiKhoan WHERE TenTaiKhoan = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, tenTK);
			
			n = stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				
				if(stmt != null) {
					stmt.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return n > 0;
		
	}
	
	
	public boolean suaTaiKhoan(TaiKhoan tk) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		
		try {
			
			String sql = "UPDATE TaiKhoan SET MatKhau = ?, ViTri = ?, MaNV = ? WHERE TenTaiKhoan = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, tk.getMatKhau());
			stmt.setString(2, tk.getViTri().name());
			stmt.setString(3, tk.getMaNV().getMaNV());
			stmt.setString(4, tk.getTenTK());
			
			n = stmt.executeUpdate();
		} catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(SQLException e) {
				
				e.printStackTrace();
			}
		}
		return n > 0;
	}
	public TaiKhoan checkLogin(String user, String pass) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		
		if (con == null) {
	        System.err.println("Lỗi: Chưa kết nối được Database!");
	        return null;
		}
	        
	    try {
	        
	       
	        String sql = "SELECT tk.*, nv.* FROM TaiKhoan tk JOIN NhanVien nv ON tk.MaNV = nv.MaNV WHERE tk.TenTaiKhoan = ? AND tk.MatKhau = ?";
	        
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, user);
	        stmt.setString(2, pass);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	        	
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

				return new TaiKhoan(user, pass, vt, nv);
	        }
	    } catch (SQLException e) {
	        
	    e.printStackTrace();
	    }
	    return null;
	}
}
