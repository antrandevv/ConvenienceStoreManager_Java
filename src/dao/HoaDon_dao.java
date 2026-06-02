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
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.TrangThai;
import entity.TrangThaiHoaDon;

public class HoaDon_dao {

	public ArrayList<HoaDon> getAllHoaDon() {
		ArrayList<HoaDon> dsHD = new ArrayList<HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "Select hd.*, nv.*,kh.* from HoaDon hd Join NhanVien nv on hd.MaNV = nv.MaNV Left Join KhachHang kh "
					+ "On hd.MaKH = kh.MaKH";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {
				String ma = rs.getString("MaHD");
				LocalDate ngay = rs.getDate("NgayLap").toLocalDate();
				NhanVien nv = new NhanVien(rs.getString("MaNV"), rs.getString("TenNV"));
				KhachHang kh = new KhachHang(rs.getString("MaKH"),rs.getString("TenKH"));
				String trangThai = rs.getString("TrangThaiHD");
				TrangThaiHoaDon ttHD = TrangThaiHoaDon.valueOf(trangThai);
				double tongTien = rs.getDouble("TongTien");
				
				HoaDon hd = new HoaDon(ma, ngay, nv, kh, ttHD, tongTien);
				dsHD.add(hd);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsHD;
	}
	
	public boolean themHoaDon(HoaDon hd) throws Exception {
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	    
	        String query = "INSERT INTO HoaDon (MaHD, NgayLap, MaNV, MaKH, TrangThaiHD, TongTien) VALUES (?, ?, ?, ?, ?, ?)";
	        stmt = con.prepareStatement(query);
	        
	        stmt.setString(1, hd.getMaHD());
	        stmt.setDate(2, java.sql.Date.valueOf(hd.getNgayLap()));
	        stmt.setString(3, hd.getNV().getMaNV());
	        
	        // XỬ LÝ KHÁCH VÃNG LAI: Nếu mã KH null thì setNull vào SQL
	        if (hd.getKH() != null && hd.getKH().getMaKH() != null) {
	            stmt.setString(4, hd.getKH().getMaKH());
	        } else {
	            stmt.setNull(4, java.sql.Types.NVARCHAR);
	        }
	        
	        stmt.setString(5, hd.getTrangThai().name());
	        stmt.setDouble(6, hd.getTongTien());
	        
	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        // In lỗi chi tiết ra Console để "bắt bệnh"
	        System.out.println("Lỗi tại HoaDon_dao: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	
	public boolean suaHoaDon(HoaDon hd){
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			String query = "Update HoaDon Set NgayLap = ?, MaNV = ?, MaKH = ?, TrangThaiHD = ?, TongTien = ? Where MaHD = ?";
			stmt = con.prepareStatement(query);
			
			stmt.setDate(1,Date.valueOf(hd.getNgayLap()));
			stmt.setString(2,hd.getNV().getMaNV());
			stmt.setString(3,hd.getKH().getMaKH());
			stmt.setString(4,hd.getTrangThai().name());
			stmt.setDouble(5,hd.getTongTien());
			stmt.setString(6,hd.getMaHD());
			
			n = stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}
	
	public boolean xoaHoaDon(String maHD) throws Exception {
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			String sql = "DELETE FROM HoaDon WHERE MaHD = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maHD);
			
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
	public HoaDon getHoaDonTheoMa(String maHD) {
	    HoaDon hd = null;
	    try {
	        Connection con = ConnectDB.getConnection();
	        String sql = "SELECT hd.*, nv.TenNV, kh.TenKH " +
	                     "FROM HoaDon hd " +
	                     "JOIN NhanVien nv ON hd.MaNV = nv.MaNV " +
	                     "LEFT JOIN KhachHang kh ON hd.MaKH = kh.MaKH " + 
	                     "WHERE hd.MaHD = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maHD);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            String ma = rs.getString("MaHD");
	            LocalDate ngay = rs.getDate("NgayLap").toLocalDate();
	            NhanVien nv = new NhanVien(rs.getString("MaNV"), rs.getString("TenNV"));
	            KhachHang kh = null;
	            if (rs.getString("MaKH") != null) {
	                kh = new KhachHang(rs.getString("MaKH"), rs.getString("TenKH"));
	            }
	            
	            String trangThai = rs.getString("TrangThaiHD");
	            TrangThaiHoaDon ttHD = TrangThaiHoaDon.valueOf(trangThai);
	            double tongTien = rs.getDouble("TongTien");
	            
	            hd = new HoaDon(ma, ngay, nv, kh, ttHD, tongTien);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return hd;
	}
	public String phatSinhMaHD() {
	    String maMoi = "HD001"; // mã mặc định nếu bảng HoaDon đang trống
	    try {
	        Connection con = ConnectDB.getConnection();
	        // cắt bỏ chữ 'HD', ép kiểu phần còn lại thành số nguyên (INT) và lấy giá trị lớn nhất
	        String sql = "SELECT MAX(CAST(REPLACE(MaHD, 'HD', '') AS INT)) FROM HoaDon";
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        
	        if (rs.next()) {
	            int maxNum = rs.getInt(1); 
	            if (maxNum > 0) {
	                
	                maMoi = String.format("HD%03d", maxNum + 1);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return maMoi;
	}
	public double[] getThongSo(String maHD) {
        double tamTinh = 0;
        HoaDon hd = getHoaDonTheoMa(maHD);
        ArrayList<ChiTietHoaDon> dsCT = new ChiTietHoaDon_dao().getChiTietTheoMaHD(maHD);

        for (ChiTietHoaDon ct : dsCT) {
            tamTinh += ct.getThanhTien();
        }

        double vat = tamTinh * 0.1;
        double tongSauVat = tamTinh + vat;
        double giamGia = Math.max(0, Math.round(tongSauVat - hd.getTongTien()));

        return new double[]{tamTinh, vat, giamGia};
    }
	public ArrayList<HoaDon> timKiemHoaDon(String keyword) {
	    ArrayList<HoaDon> ds = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getConnection();
	        
	        String sql = "SELECT h.*, n.TenNV, k.TenKH " +
	                     "FROM HoaDon h " +
	                     "JOIN NhanVien n ON h.MaNV = n.MaNV " +
	                     "LEFT JOIN KhachHang k ON h.MaKH = k.MaKH " +
	                     "WHERE h.MaHD LIKE ? OR k.TenKH LIKE ?";
	        
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, "%" + keyword + "%");
	        stmt.setString(2, "%" + keyword + "%");
	        
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            HoaDon hd = new HoaDon();
	            hd.setMaHD(rs.getString("MaHD"));
	            hd.setNgayLap(rs.getDate("NgayLap").toLocalDate());
	            hd.setTongTien(rs.getDouble("TongTien"));
	            NhanVien nv = new NhanVien();
	            nv.setTenNV(rs.getString("TenNV"));
	            hd.setNV(nv);
	            if (rs.getString("maKH") != null) {
	                KhachHang kh = new KhachHang();
	                kh.setTenKH(rs.getString("TenKH"));
	                hd.setKH(kh);
	            }
	            
	            ds.add(hd);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return ds;
	}
}
