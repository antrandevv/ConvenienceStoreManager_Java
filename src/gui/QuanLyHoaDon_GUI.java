package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.ui.FlatLineBorder;
// Import iText PDF
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import common.DesignImg;
import custom_gui.HoaDonCard;
import dao.ChiTietHoaDon_dao;
import dao.HoaDon_dao;
import entity.ChiTietHoaDon;
import entity.HoaDon;

public class QuanLyHoaDon_GUI extends JPanel implements DocumentListener {

    private JTextField txtTimKiem;
    private JPanel pnlListHoaDon;
    private HoaDon_dao hd_dao;
    private ChiTietHoaDon_dao cthd_dao;
    
    private DecimalFormat df = new DecimalFormat("#,###đ");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public QuanLyHoaDon_GUI() {
        DesignImg di = new DesignImg();
        ImageIcon search = new ImageIcon("img/icon/icon_timkiem.png");
        
        hd_dao = new HoaDon_dao();
        cthd_dao = new ChiTietHoaDon_dao();

        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Phần Tiêu đề và Tìm kiếm
        JPanel pnlTop = new JPanel(new BorderLayout(0, 15));
        pnlTop.setOpaque(false);

        JLabel lblTitle = new JLabel("Quản Lý Hóa Đơn");
        // Sử dụng đầy đủ đường dẫn gói để tránh xung đột với iText Font
        lblTitle.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 40, 60));

        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(0, 45));
        txtTimKiem.putClientProperty("JTextField.leadingIcon", di.setImg(search, 15, 15));
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Tìm kiếm theo mã hóa đơn hoặc tên khách hàng...");
        txtTimKiem.setBorder(new FlatLineBorder(new Insets(5, 15, 5, 15), new Color(220, 220, 220), 1, 20));

        pnlTop.add(lblTitle, BorderLayout.NORTH);
        pnlTop.add(txtTimKiem, BorderLayout.CENTER);

        pnlListHoaDon = new JPanel();
        pnlListHoaDon.setLayout(new BoxLayout(pnlListHoaDon, BoxLayout.Y_AXIS));
        pnlListHoaDon.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(pnlListHoaDon);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(pnlTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        txtTimKiem.getDocument().addDocumentListener(this);
        ArrayList<HoaDon> dsMacDinh = hd_dao.getAllHoaDon();
        loadDataToUI(dsMacDinh);
    }

    public void loadDataToUI(ArrayList<HoaDon> dsHoaDon) {
        pnlListHoaDon.removeAll(); 

       
        ArrayList<ChiTietHoaDon> dsChiTiet = cthd_dao.getAllCTHD();

        for (HoaDon hd : dsHoaDon) {
            int tongSoLuongMon = 0;
            for (ChiTietHoaDon ct : dsChiTiet) {
                if (ct.getMaHD() != null && ct.getMaHD().getMaHD().equals(hd.getMaHD())) {
                    tongSoLuongMon += ct.getSoLuong();
                }
            }

            
            String maHD = hd.getMaHD();
            String thoiGian = (hd.getNgayLap() != null) ? hd.getNgayLap().format(dtf) : "N/A";
            String tongTien = df.format(hd.getTongTien());
            String tenNV = (hd.getNV() != null) ? hd.getNV().getTenNV() : "N/A";
            String tenKH = (hd.getKH() != null) ? hd.getKH().getTenKH() : "Khách vãng lai";

            HoaDonCard card = new HoaDonCard(maHD, tongTien, thoiGian, tenNV, tenKH, tongSoLuongMon);
            
            card.addXemActionListener(e -> hienThiChiTietHoaDon(maHD));
            card.addInActionListener(e -> xuLyInHoaDon(maHD));

            pnlListHoaDon.add(card);
            pnlListHoaDon.add(Box.createVerticalStrut(15)); 
        }

        pnlListHoaDon.revalidate();
        pnlListHoaDon.repaint();
    }

    
    private void addTableCell(PdfPTable table, String text, com.itextpdf.text.Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        cell.setPaddingBottom(5);
        table.addCell(cell);
    }

    private void hienThiChiTietHoaDon(String maHD) {
        String htmlContent = generateInvoiceHTML(maHD);
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Chi tiết hóa đơn - " + maHD);
        dialog.setModal(true);
        
        JEditorPane ed = new JEditorPane("text/html", htmlContent);
        ed.setEditable(false);
        ed.setCaretPosition(0);
        
        JScrollPane scrllpane = new JScrollPane(ed);
        scrllpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        
        dialog.add(scrllpane);
        dialog.setSize(450, 600);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    private String generateInvoiceHTML(String maHD) {
    	HoaDon hd = hd_dao.getHoaDonTheoMa(maHD);
        double[] thongSo = hd_dao.getThongSo(maHD);
        ArrayList<ChiTietHoaDon> dsCT = cthd_dao.getChiTietTheoMaHD(maHD);
        
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String ngayLap = hd.getNgayLap() != null ? hd.getNgayLap().format(dtfTime) : "N/A";
        String tenNV = (hd.getNV() != null) ? hd.getNV().getTenNV() : "N/A";
        String tenKH = (hd.getKH() != null) ? hd.getKH().getTenKH() : "Khách vãng lai";

        StringBuilder html = new StringBuilder();
        html.append("<html><body style='width: 320px; font-family: sans-serif; padding: 10px; font-size: 11px;'>");

        // Header
        html.append("<div style='text-align: center;'>");
        html.append("<b style='font-size: 16px;'>CỬA HÀNG TIỆN LỢI 24/7</b><br>");
        html.append("123 Đường ABC, Quận 1, TP.HCM<br>");
        html.append("Hotline: 1900-xxxx<br>");
        html.append("</div>");
        html.append("<div style='color: gray; text-align: center;'>-----------------------------------------------------------------------</div>");
        
        // Thông tin chung
        html.append("<table style='width: 100%; font-size: 11px; margin-bottom: 5px;'>");
        html.append("<tr><td>Mã HĐ:</td><td align='right'><b>").append(hd.getMaHD()).append("</b></td></tr>");
        html.append("<tr><td>Ngày:</td><td align='right'>").append(ngayLap).append("</td></tr>");
        html.append("<tr><td>Thu ngân:</td><td align='right'>").append(tenNV).append("</td></tr>");
        html.append("<tr><td>Khách hàng:</td><td align='right'>").append(tenKH).append("</td></tr>");
        html.append("</table>");
        html.append("<div style='color: gray; text-align: center;'>-----------------------------------------------------------------------</div>");

        // Danh sách món
        for (ChiTietHoaDon ct : dsCT) {
            html.append("<div style='margin-bottom: 8px;'>");
            html.append("<b style='font-size: 12px;'>").append(ct.getSanPham().getTenSP()).append("</b><br>");
            html.append("<span style='color: #666;'>").append(ct.getSoLuong()).append(" x ").append(df.format(ct.getSanPham().getGiaBan())).append("</span><br>");
            html.append("<b style='font-size: 12px;'>").append(df.format(ct.getThanhTien())).append("</b>");
            html.append("</div>");
        }

        html.append("<div style='color: gray; text-align: center;'>-----------------------------------------------------------------------</div>");

        // Tạm tính & VAT
        html.append("<table style='width: 100%; font-size: 12px;'>");
        html.append("<tr><td>Tạm tính:</td><td align='right'>").append(df.format(thongSo[0])).append("</td></tr>");
        html.append("<tr><td>VAT (10%):</td><td align='right'>").append(df.format(thongSo[1])).append("</td></tr>");
        html.append("</table>");

        // Dòng kẻ liền
        html.append("<hr style='border-top: 1px solid black; margin: 8px 0;'>");

        // Tổng kết
        html.append("<table style='width: 100%; font-size: 12px;'>");
        html.append("<tr style='font-size: 13px;'><td>TỔNG CỘNG:</td><td align='right'><b>").append(df.format(hd.getTongTien())).append("</b></td></tr>");
        html.append("</table>");

        html.append("<div style='color: gray; text-align: center;'>-----------------------------------------------------------------------</div>");

        // Footer
        html.append("<div style='text-align: center; margin-top: 15px;'>");
        html.append("<b style='font-size: 13px;'>CẢM ƠN QUÝ KHÁCH!</b><br>");
        html.append("<span style='font-size: 11px;'>Hẹn gặp lại quý khách</span>");
        html.append("</div>");

        html.append("</body></html>");
        return html.toString();
    }
    
    private void xuLyInHoaDon(String maHD) {
        HoaDon hd = hd_dao.getHoaDonTheoMa(maHD);
        double[] thongSo = hd_dao.getThongSo(maHD);
        ArrayList<ChiTietHoaDon> dsCT = cthd_dao.getChiTietTheoMaHD(maHD);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("HoaDon_" + maHD + ".pdf"));
        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        Document document = new Document(new Rectangle(250f, 842f), 15, 15, 15, 15);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileChooser.getSelectedFile()));
            document.open();

            BaseFont bf = BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontTitle = new Font(bf, 13, Font.BOLD);
            Font fontBold = new Font(bf, 10, Font.BOLD);
            Font fontNormal = new Font(bf, 10, Font.NORMAL);
            Font fontSmall = new Font(bf, 9, Font.NORMAL);

            DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String ngayLap = hd.getNgayLap() != null ? hd.getNgayLap().format(dtfTime) : "N/A";
            String tenNV = (hd.getNV() != null) ? hd.getNV().getTenNV() : "N/A";
            String tenKH = (hd.getKH() != null) ? hd.getKH().getTenKH() : "Khách vãng lai";

            // Header
            Paragraph pHeader1 = new Paragraph("CỬA HÀNG TIỆN LỢI 24/7", fontTitle);
            pHeader1.setAlignment(Element.ALIGN_CENTER);
            document.add(pHeader1);

            Paragraph pHeader2 = new Paragraph("123 Đường ABC, Quận 1, TP.HCM\nHotline: 1900-xxxx", fontNormal);
            pHeader2.setAlignment(Element.ALIGN_CENTER);
            document.add(pHeader2);

            document.add(new Paragraph("---------------------------------------------------------", fontNormal));

            
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{1f, 2f});
            
            addTableCell(infoTable, "Mã HĐ:", fontNormal, Element.ALIGN_LEFT);
            addTableCell(infoTable, hd.getMaHD(), fontBold, Element.ALIGN_RIGHT);
            addTableCell(infoTable, "Ngày:", fontNormal, Element.ALIGN_LEFT);
            addTableCell(infoTable, ngayLap, fontNormal, Element.ALIGN_RIGHT);
            addTableCell(infoTable, "Thu ngân:", fontNormal, Element.ALIGN_LEFT);
            addTableCell(infoTable, tenNV, fontNormal, Element.ALIGN_RIGHT);
            addTableCell(infoTable, "Khách hàng:", fontNormal, Element.ALIGN_LEFT);
            addTableCell(infoTable, tenKH, fontNormal, Element.ALIGN_RIGHT);
            
            document.add(infoTable);
            document.add(new Paragraph("---------------------------------------------------------", fontNormal));

            
            for (ChiTietHoaDon ct : dsCT) {
                document.add(new Paragraph(ct.getSanPham().getTenSP(), fontBold));
                document.add(new Paragraph(ct.getSoLuong() + " x " + df.format(ct.getSanPham().getGiaBan()), fontSmall));
                document.add(new Paragraph(df.format(ct.getThanhTien()), fontBold));
                document.add(new Paragraph(" ", new Font(bf, 4, Font.NORMAL)));
            }

            document.add(new Paragraph("---------------------------------------------------------", fontNormal));

         
            PdfPTable sumTable = new PdfPTable(2);
            sumTable.setWidthPercentage(100);
            sumTable.setWidths(new float[]{1f, 1f});
            
            addTableCell(sumTable, "Tạm tính:", fontNormal, Element.ALIGN_LEFT);
            addTableCell(sumTable, df.format(thongSo[0]), fontNormal, Element.ALIGN_RIGHT);
            addTableCell(sumTable, "VAT (10%):", fontNormal, Element.ALIGN_LEFT);
            addTableCell(sumTable, df.format(thongSo[1]), fontNormal, Element.ALIGN_RIGHT);
            
            document.add(sumTable);
            
            Paragraph solidLine = new Paragraph("_____________________________________________", fontNormal);
            solidLine.setAlignment(Element.ALIGN_CENTER);
            document.add(solidLine);
            document.add(new Paragraph(" ", new Font(bf, 5, Font.NORMAL)));

           
            PdfPTable finalTable = new PdfPTable(2);
            finalTable.setWidthPercentage(100);
            finalTable.setWidths(new float[]{1f, 1f});

            addTableCell(finalTable, "TỔNG CỘNG:", fontNormal, Element.ALIGN_LEFT);
            addTableCell(finalTable, df.format(hd.getTongTien()), fontNormal, Element.ALIGN_RIGHT);

            document.add(finalTable);
            document.add(new Paragraph("---------------------------------------------------------", fontNormal));

            // Footer
            Paragraph thanks = new Paragraph("CẢM ƠN QUÝ KHÁCH!", fontBold);
            thanks.setAlignment(Element.ALIGN_CENTER);
            document.add(thanks);

            Paragraph bye = new Paragraph("Hẹn gặp lại quý khách", fontNormal);
            bye.setAlignment(Element.ALIGN_CENTER);
            document.add(bye);

            document.close();
            JOptionPane.showMessageDialog(this, "Xuất PDF thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất PDF: " + e.getMessage());
        }
    }

	@Override
	public void insertUpdate(DocumentEvent e) {
		thucHienTimKiem();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		thucHienTimKiem();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		thucHienTimKiem();
	}
	private void thucHienTimKiem() {
        String keyword = txtTimKiem.getText().trim();
        ArrayList<HoaDon> ketQua = hd_dao.timKiemHoaDon(keyword);
        // Cập nhật lại danh sách hiển thị
        loadDataToUI(ketQua);
    }
}