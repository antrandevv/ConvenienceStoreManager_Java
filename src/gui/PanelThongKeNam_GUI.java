package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.toedter.calendar.JYearChooser;

import dao.ThongKeTaiChinhNam_dao;

@SuppressWarnings("serial")
public class PanelThongKeNam_GUI extends JPanel {
    
    private JLabel lblDoanhThuValue, lblChiPhiValue, lblLoiNhuanValue, lblKhachHangValue;
    private ChartPanel chartPanel;
    private ThongKeTaiChinhNam_dao tkDao;
    
    private JYearChooser yearChooser;
    private JTextArea txtDeXuat;
    
    public PanelThongKeNam_GUI() {
        tkDao = new ThongKeTaiChinhNam_dao();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // tieu de top
        JPanel pnlTop = new JPanel(new BorderLayout());
        
        JLabel lblHeader = new JLabel("Thống kê Doanh thu: Theo Năm", JLabel.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        lblHeader.setOpaque(true);
        lblHeader.setBackground(new Color(189, 215, 238));
        lblHeader.setPreferredSize(new Dimension(0, 40));
        pnlTop.add(lblHeader, BorderLayout.NORTH);

        JPanel pnlControl = new JPanel(new BorderLayout(10, 0));
        pnlControl.setBackground(Color.WHITE);
        pnlControl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // bo loc: chon year
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlFilter.setBackground(Color.WHITE);
        
        pnlFilter.add(new JLabel("Chọn Năm:"));
        yearChooser = new JYearChooser();
        pnlFilter.add(yearChooser);
        
        JButton btnThongKe = new JButton("Thống Kê");
        btnThongKe.setFont(new Font("Arial", Font.BOLD, 14));
        btnThongKe.setBackground(new Color(51, 153, 255));
        btnThongKe.setForeground(Color.WHITE);
        btnThongKe.addActionListener(e -> updateThongKeTheoBoChon());
        pnlFilter.add(btnThongKe);
        
        pnlControl.add(pnlFilter, BorderLayout.WEST);

        // o de xuat nam
        txtDeXuat = new JTextArea();
        txtDeXuat.setEditable(false);
        txtDeXuat.setLineWrap(true); 
        txtDeXuat.setWrapStyleWord(true); 
        txtDeXuat.setFont(new Font("Arial", Font.ITALIC, 14));
        txtDeXuat.setForeground(new Color(50, 50, 50));
        
        JScrollPane scrollDeXuat = new JScrollPane(txtDeXuat);
        scrollDeXuat.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Đánh giá tổng quan năm"
        ));
        scrollDeXuat.setPreferredSize(new Dimension(0, 80)); 
        
        pnlControl.add(scrollDeXuat, BorderLayout.CENTER);

        pnlTop.add(pnlControl, BorderLayout.SOUTH);
        add(pnlTop, BorderLayout.NORTH);

        // the so lieu
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        
        lblDoanhThuValue = new JLabel("0 đ", JLabel.CENTER);
        lblDoanhThuValue.setFont(new Font("Arial", Font.BOLD, 20));
        
        lblChiPhiValue = new JLabel("0 đ", JLabel.CENTER);
        lblChiPhiValue.setFont(new Font("Arial", Font.BOLD, 20));
        
        lblLoiNhuanValue = new JLabel("0 đ", JLabel.CENTER);
        lblLoiNhuanValue.setFont(new Font("Arial", Font.BOLD, 20));
        
        lblKhachHangValue = new JLabel("0 khách", JLabel.CENTER);
        lblKhachHangValue.setFont(new Font("Arial", Font.BOLD, 20));
        
        JPanel pnlCards = new JPanel(new GridLayout(1, 4, 15, 15));
        pnlCards.setPreferredSize(new Dimension(0, 120));
        Color nenChanh = new Color(239, 227, 202);
        
        pnlCards.add(createCard("Tổng doanh thu", lblDoanhThuValue, Color.GREEN, nenChanh));
        pnlCards.add(createCard("Tổng chi phí", lblChiPhiValue, Color.ORANGE, nenChanh));
        pnlCards.add(createCard("Lợi nhuận", lblLoiNhuanValue, new Color(0, 128, 0), nenChanh));
        
        pnlCards.add(createCard("Khách hàng", lblKhachHangValue, new Color(0, 128, 0), nenChanh));
        
        
        centerPanel.add(pnlCards, BorderLayout.NORTH);


        // khoi tao bieu do duong cho cac thang
        chartPanel = createEmptyLineChartPanel(); 
        centerPanel.add(chartPanel, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // cap nhat khi khoi tao
        updateThongKeTheoBoChon();
    }

    //tach ham tao card de de quan ly code
	private JPanel createCard(String title, JLabel lblValue, Color titleColor, Color bgColor) {
        JPanel p = new JPanel(new GridLayout(2, 1));
//      p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        p.setBackground(bgColor);
        boGocComponent(p, 15);
        p.setBorder(new FlatLineBorder(
        		new Insets(10, 10, 10, 10), new Color(200, 200, 200), 1, 15                                   
        	));
        
        JLabel lblT = new JLabel(title, JLabel.CENTER);
        lblT.setForeground(titleColor); // mau tieu de
        lblT.setFont(new Font("Arial", Font.BOLD, 16));
        
        p.add(lblT);
        p.add(lblValue); 
        return p;
    }

    private ChartPanel createEmptyLineChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createLineChart(
            "Xu hướng Doanh thu và Chi phí trong năm", 
            "Tháng", "Số tiền (VNĐ)", dataset);
        // --- HIEN THI DINH DANG TRUC Y
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        
        // phan cach 3 so 0 (VD: 1,000,000)
        DecimalFormat df = new DecimalFormat("#,###"); 
        yAxis.setNumberFormatOverride(df);
        // -------------------------------------------------
        return new ChartPanel(chart);
    }
    
    public void updateThongKeTheoBoChon() {
        int year = yearChooser.getYear();
        
        double doanhThu = tkDao.getTongDoanhThu(year);
        double chiPhi = tkDao.getTongChiPhi(year);
        double loiNhuan = tkDao.getLoiNhuan(year);
        int soKhach = tkDao.getSoLuongKhachHang(year);

        DecimalFormat df = new DecimalFormat("#,### đ");
        lblDoanhThuValue.setText(df.format(doanhThu));
        lblChiPhiValue.setText(df.format(chiPhi));
        lblLoiNhuanValue.setText(df.format(loiNhuan));
        lblKhachHangValue.setText(soKhach + " khách");

        lblLoiNhuanValue.setForeground(loiNhuan >= 0 ? new Color(0, 150, 0) : Color.RED);

        // de xuat cho nam
        if (loiNhuan > 0) {
            txtDeXuat.setText("Năm " + year + " ghi nhận mức tăng trưởng dương. Cửa hàng nên trích một phần lợi nhuận để nâng cấp trang thiết bị hoặc mở rộng quy mô kinh doanh vào năm sau.");
        } else if (loiNhuan < 0) {
            txtDeXuat.setText("Năm " + year + " cửa hàng chịu mức lỗ tổng thể. Cần rà soát lại toàn bộ nguồn nhập hàng, loại bỏ các mặt hàng bán chậm và có kế hoạch tái cơ cấu tài chính.");
        } else if (doanhThu == 0) {
            txtDeXuat.setText("Chưa có dữ liệu hệ thống cho năm " + year + ".");
        } else {
            txtDeXuat.setText("Năm " + year + " cửa hàng đạt trạng thái hòa vốn. Cần đẩy mạnh chiến lược marketing để bứt phá doanh thu.");
        }
        txtDeXuat.setCaretPosition(0);

        DefaultCategoryDataset dataset = tkDao.getDatasetThongKeNam(year);
        CategoryPlot plot = (CategoryPlot) chartPanel.getChart().getPlot();
        plot.setDataset(dataset);
    }
    
	  //bo goc cho the
	private void boGocComponent(JComponent comp, int doCong) {
	    comp.putClientProperty("FlatLaf.style", "arc: " + doCong);
	}
}