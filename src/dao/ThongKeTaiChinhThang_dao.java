package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.category.DefaultCategoryDataset;
import connectDB.ConnectDB;

public class ThongKeTaiChinhThang_dao {

    public double getTongDoanhThu(LocalDate start, LocalDate end) {
        double tong = 0;
        String sql = "SELECT SUM(TongTien) FROM HoaDon " +
                     "WHERE TrangThaiHD = N'DA_THANH_TOAN' " +
                     "AND CAST(NgayLap AS DATE) BETWEEN ? AND ?";
        Connection con = ConnectDB.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) tong = rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tong;
    }

    public double getTongChiPhi(LocalDate start, LocalDate end) {
        double tong = 0;
        String sql = "SELECT SUM(TongTien) FROM PhieuNhap " +
                     "WHERE CAST(ThoiGianNhap AS DATE) BETWEEN ? AND ?";
        Connection con = ConnectDB.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) tong = rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tong;
    }

    // tinh loi nhuan theo thang theo cong thuc
    public double getLoiNhuan(LocalDate start, LocalDate end) {
        return getTongDoanhThu(start, end) - getTongChiPhi(start, end);
    }

    public int getSoLuongKhachHang(LocalDate start, LocalDate end) {
        int count = 0;
        String sql = "SELECT COUNT(DISTINCT MaKH) FROM HoaDon " +
                     "WHERE TrangThaiHD = N'DA_THANH_TOAN' " +
                     "AND CAST(NgayLap AS DATE) BETWEEN ? AND ?";
        //connection phai dat ben ngoai try
        Connection con = ConnectDB.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    private Map<LocalDate, Double> getDoanhThuTheoNgay(LocalDate start, LocalDate end) {
        Map<LocalDate, Double> data = new HashMap<>();
        String sql = "SELECT CAST(NgayLap AS DATE), SUM(TongTien) FROM HoaDon " +
                     "WHERE TrangThaiHD = N'DA_THANH_TOAN' " +
                     "AND CAST(NgayLap AS DATE) BETWEEN ? AND ? " +
                     "GROUP BY CAST(NgayLap AS DATE)";
        Connection con = ConnectDB.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data.put(rs.getDate(1).toLocalDate(), rs.getDouble(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private Map<LocalDate, Double> getChiPhiTheoNgay(LocalDate start, LocalDate end) {
        Map<LocalDate, Double> data = new HashMap<>();
        String sql = "SELECT CAST(ThoiGianNhap AS DATE), SUM(TongTien) FROM PhieuNhap " +
                     "WHERE CAST(ThoiGianNhap AS DATE) BETWEEN ? AND ? " +
                     "GROUP BY CAST(ThoiGianNhap AS DATE)";
        Connection con = ConnectDB.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data.put(rs.getDate(1).toLocalDate(), rs.getDouble(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // logic bieu do
    public DefaultCategoryDataset getDatasetThongKeThang(LocalDate start, LocalDate end) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        Map<LocalDate, Double> dataDoanhThu = getDoanhThuTheoNgay(start, end);
        Map<LocalDate, Double> dataChiPhi = getChiPhiTheoNgay(start, end);

        // loop duyet ngay trong thang
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            // lay so ngay (1, 2, 3...) de truc X ro rang
            String dayString = String.valueOf(date.getDayOfMonth()); 
            
            double doanhThu = dataDoanhThu.getOrDefault(date, 0.0);
            double chiPhi = dataChiPhi.getOrDefault(date, 0.0);
            
            dataset.addValue(doanhThu, "Doanh thu", dayString);
            dataset.addValue(chiPhi, "Chi phí", dayString);
        }
        return dataset;
    }
}