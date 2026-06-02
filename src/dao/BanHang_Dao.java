package dao;

import java.time.LocalDate;

import javax.swing.table.DefaultTableModel;

import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.SanPham;
import entity.TrangThaiHoaDon;

public class BanHang_Dao {
    private SanPham_dao sp_dao = new SanPham_dao();
    private HoaDon_dao hd_dao = new HoaDon_dao();
    private ChiTietHoaDon_dao cthd_dao = new ChiTietHoaDon_dao();

   
    public double tinhTamTinh(DefaultTableModel model) {
        double tamTinh = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String thanhTienStr = model.getValueAt(i, 3).toString().replace(",", "").replace(".", "");
            tamTinh += Double.parseDouble(thanhTienStr);
        }
        return tamTinh;
    }

    public double tinhThue(double tamTinh) {
        return tamTinh * 0.1;
    }

    
    public String xuLyThanhToan(NhanVien nv, KhachHang kh, double tongTien, DefaultTableModel cartModel) throws Exception {
        String maHD = hd_dao.phatSinhMaHD();
        HoaDon hd = new HoaDon(maHD, LocalDate.now(), nv, kh, TrangThaiHoaDon.DA_THANH_TOAN, tongTien);

        if (hd_dao.themHoaDon(hd)) {
            for (int i = 0; i < cartModel.getRowCount(); i++) {
                String tenSP = cartModel.getValueAt(i, 0).toString();
                int soLuong = Integer.parseInt(cartModel.getValueAt(i, 1).toString());
                double thanhTien = Double.parseDouble(cartModel.getValueAt(i, 3).toString().replace(",", "").replace(".", ""));
                
                // Lấy thông tin SP từ DB hoặc Map
                SanPham sp = sp_dao.getSanPhamTheoTen(tenSP); 
                double thue = thanhTien * 0.1;

                ChiTietHoaDon cthd = new ChiTietHoaDon(hd, sp, soLuong, thanhTien, thue);
                cthd_dao.themChiTietHoaDon(cthd);
                
                // Cập nhật tồn kho
                sp_dao.capNhatSoLuong(sp.getMaSP(), -soLuong);
            }
            return maHD;
        }
        return null;
    }
}
