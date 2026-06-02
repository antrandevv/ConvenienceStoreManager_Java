package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.category.DefaultCategoryDataset;

import connectDB.ConnectDB;

public class ThongKeTaiChinhNam_dao {

    public double getTongDoanhThu(int year) {
        double tong = 0;
        String sql = "SELECT SUM(TongTien) FROM HoaDon " +
                     "WHERE TrangThaiHD = N'DA_THANH_TOAN' AND YEAR(NgayLap) = ?";
        //connection de ben ngoai try
        Connection con = ConnectDB.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) tong = rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tong;
    }

    public double getTongChiPhi(int year) {
        double tong = 0;
        String sql = "SELECT SUM(TongTien) FROM PhieuNhap WHERE YEAR(ThoiGianNhap) = ?";
        Connection con = ConnectDB.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) tong = rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tong;
    }

    //cong thuc tinh loi nhuan
    public double getLoiNhuan(int year) {
        return getTongDoanhThu(year) - getTongChiPhi(year);
    }

    public int getSoLuongKhachHang(int year) {
        int count = 0;
        String sql = "SELECT COUNT(DISTINCT MaKH) FROM HoaDon " +
                     "WHERE TrangThaiHD = N'DA_THANH_TOAN' AND YEAR(NgayLap) = ?";
        Connection con = ConnectDB.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    private Map<Integer, Double> getDoanhThuTheoThang(int year) {
        Map<Integer, Double> data = new HashMap<>();
        String sql = "SELECT MONTH(NgayLap), SUM(TongTien) FROM HoaDon " +
                     "WHERE TrangThaiHD = N'DA_THANH_TOAN' AND YEAR(NgayLap) = ? " +
                     "GROUP BY MONTH(NgayLap)";
        Connection con = ConnectDB.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data.put(rs.getInt(1), rs.getDouble(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private Map<Integer, Double> getChiPhiTheoThang(int year) {
        Map<Integer, Double> data = new HashMap<>();
        String sql = "SELECT MONTH(ThoiGianNhap), SUM(TongTien) FROM PhieuNhap " +
                     "WHERE YEAR(ThoiGianNhap) = ? " +
                     "GROUP BY MONTH(ThoiGianNhap)";
        Connection con = ConnectDB.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data.put(rs.getInt(1), rs.getDouble(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // bieu do
    public DefaultCategoryDataset getDatasetThongKeNam(int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        Map<Integer, Double> dataDoanhThu = getDoanhThuTheoThang(year);
        Map<Integer, Double> dataChiPhi = getChiPhiTheoThang(year);

        // Vòng lặp cố định 12 tháng
        for (int month = 1; month <= 12; month++) {
            String monthString = "Tháng " + month; 
            
            double doanhThu = dataDoanhThu.getOrDefault(month, 0.0);
            double chiPhi = dataChiPhi.getOrDefault(month, 0.0);
            
            dataset.addValue(doanhThu, "Doanh thu", monthString);
            dataset.addValue(chiPhi, "Chi phí", monthString);
        }
        return dataset;
    }
}