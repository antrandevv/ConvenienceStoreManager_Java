package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

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
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import dao.ThongKeTaiChinhThang_dao;

@SuppressWarnings("serial")
public class PanelThongKeThang_GUI extends JPanel {
    
    private JLabel lblDoanhThuValue, lblChiPhiValue, lblLoiNhuanValue, lblKhachHangValue;
    private ChartPanel chartPanel;
    private ThongKeTaiChinhThang_dao tkDao;
    
    private JMonthChooser monthChooser;
    private JYearChooser yearChooser;
    private JTextArea txtDeXuat; // o de xuat
    
    public PanelThongKeThang_GUI() {
        tkDao = new ThongKeTaiChinhThang_dao();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // top: tieu de + thanh cong cu
        JPanel pnlTop = new JPanel(new BorderLayout());
        
        JLabel lblHeader = new JLabel("Thống kê Doanh thu: Theo Tháng", JLabel.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        lblHeader.setOpaque(true);
        lblHeader.setBackground(new Color(189, 215, 238));
        lblHeader.setPreferredSize(new Dimension(0, 40));
        pnlTop.add(lblHeader, BorderLayout.NORTH);

        // panel tuy chon trai, de xuat phai
        JPanel pnlControl = new JPanel(new BorderLayout(10, 0));
        pnlControl.setBackground(Color.WHITE);
        pnlControl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // bo loc ben trai
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlFilter.setBackground(Color.WHITE);
        
        pnlFilter.add(new JLabel("Chọn Tháng:"));
        monthChooser = new JMonthChooser();
        pnlFilter.add(monthChooser);
        
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

        // o de xuat ben phai
        txtDeXuat = new JTextArea();
        txtDeXuat.setEditable(false);
        txtDeXuat.setLineWrap(true); // xuong dong khi het khung
        txtDeXuat.setWrapStyleWord(true); // ngat dong nguyen tu
        txtDeXuat.setFont(new Font("Arial", Font.ITALIC, 14));
        txtDeXuat.setForeground(new Color(50, 50, 50));
        
        JScrollPane scrollDeXuat = new JScrollPane(txtDeXuat);
        scrollDeXuat.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Đề xuất kinh doanh"
        ));
        scrollDeXuat.setPreferredSize(new Dimension(0, 80)); // chieu cao o de xuat
        
        pnlControl.add(scrollDeXuat, BorderLayout.CENTER);

        // add control vao south cua tieu de
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
        
      //gan lable vao card
        JPanel pnlCards = new JPanel(new GridLayout(1, 4, 15, 15));
        pnlCards.setPreferredSize(new Dimension(0, 150));
        
        Color nenChanh = new Color(239, 227, 202);
        
        // doi mau nen
        pnlCards.add(createCard("Tổng doanh thu", lblDoanhThuValue, Color.GREEN, nenChanh));
        pnlCards.add(createCard("Tổng chi phí", lblChiPhiValue, Color.ORANGE, nenChanh));
        pnlCards.add(createCard("Lợi nhuận", lblLoiNhuanValue, new Color(0, 128, 0), nenChanh));
        
        pnlCards.add(createCard("Khách hàng", lblKhachHangValue, new Color(0, 128, 0), nenChanh));
        
        
        centerPanel.add(pnlCards, BorderLayout.NORTH);

        // khoi tao bieu do duong
        chartPanel = createEmptyLineChartPanel(); 
        centerPanel.add(chartPanel, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // goi du lieu khi vua tao tab
        updateThongKeTheoBoChon();
    }

    //tach tung ham de quan ly code card
 // khoi tao the thong tin
 	private JPanel createCard(String title, JLabel lblValue, Color titleColor, Color bgColor) {
         JPanel p = new JPanel(new GridLayout(2, 1));
//       p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
         p.setBackground(bgColor);
         boGocComponent(p, 15);
         p.setBorder(new FlatLineBorder(
        		    new Insets(10, 10, 10, 10), new Color(200, 200, 200), 1, 15));
         
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
            "Xu hướng Doanh thu và Chi phí trong tháng", 
            "Ngày trong tháng", "Số tiền (VNĐ)", dataset);
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
        int month = monthChooser.getMonth() + 1; 
        int year = yearChooser.getYear();
        
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        
        double doanhThu = tkDao.getTongDoanhThu(startOfMonth, endOfMonth);
        double chiPhi = tkDao.getTongChiPhi(startOfMonth, endOfMonth);
        double loiNhuan = tkDao.getLoiNhuan(startOfMonth, endOfMonth);
        int soKhach = tkDao.getSoLuongKhachHang(startOfMonth, endOfMonth);

        DecimalFormat df = new DecimalFormat("#,### đ");
        lblDoanhThuValue.setText(df.format(doanhThu));
        lblChiPhiValue.setText(df.format(chiPhi));
        lblLoiNhuanValue.setText(df.format(loiNhuan));
        lblKhachHangValue.setText(soKhach + " khách");

        lblLoiNhuanValue.setForeground(loiNhuan >= 0 ? new Color(0, 150, 0) : Color.RED);

        // dua ra de xuat tren GUI, phep so sanh la cach hien thi UI
        if (loiNhuan > 0) {
            txtDeXuat.setText("Tháng " + month + " kinh doanh có lãi. Cửa hàng đang hoạt động tốt. Hãy tiếp tục phát huy và có thể cân nhắc các chương trình khuyến mãi nhỏ để giữ chân khách hàng.");
        } else if (loiNhuan < 0) {
            txtDeXuat.setText("Tháng " + month + " đang ghi nhận âm vốn (" + df.format(loiNhuan) + "). Nguyên nhân có thể do đang trong giai đoạn nhập lô hàng mới. Cần theo dõi chặt chẽ và đẩy mạnh bán các mặt hàng tồn kho.");
        } else if (doanhThu == 0 && chiPhi == 0) {
            txtDeXuat.setText("Tháng " + month + " chưa có dữ liệu giao dịch nào được ghi nhận trên hệ thống.");
        } else {
            txtDeXuat.setText("Tháng " + month + " đang hòa vốn. Chú ý tối ưu hóa chi phí vận hành.");
        }
        
        // dua thanh cuon len dau
        txtDeXuat.setCaretPosition(0);

        // cho phep DAO lay dataset, dung de chon thang nam
        DefaultCategoryDataset dataset = tkDao.getDatasetThongKeThang(startOfMonth, endOfMonth);
        CategoryPlot plot = (CategoryPlot) chartPanel.getChart().getPlot();
        plot.setDataset(dataset);
    }
    
  //bo goc cho the
  	private void boGocComponent(JComponent comp, int doCong) {
  	    comp.putClientProperty("FlatLaf.style", "arc: " + doCong);
  	}
}