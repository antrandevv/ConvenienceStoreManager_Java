package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;

@SuppressWarnings("serial")
public class ThongKeTaiChinh_GUI extends JPanel {

	private PanelThongKeTuan_GUI pnlTuan;
	private PanelThongKeThang_GUI pnlThang;
	private PanelThongKeNam_GUI pnlNam;

	public ThongKeTaiChinh_GUI() {
		setSize(1400,700);
        setLayout(new BorderLayout());
        
        JTabbedPane tabbedPane = new JTabbedPane();
        pnlTuan = new PanelThongKeTuan_GUI();
        
		pnlThang = new PanelThongKeThang_GUI();
		pnlNam = new PanelThongKeNam_GUI();
        
        // danh sach tab con
        tabbedPane.addTab("Theo Tuần", pnlTuan);
        tabbedPane.addTab("Theo Tháng", pnlThang);
        tabbedPane.addTab("Theo Năm", pnlNam);

        // xu ly su kien chuyen tab
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            LocalDate now = LocalDate.now();

            // kiem tra pnlTuan duoc khoi tao
            if (selectedIndex == 0 && pnlTuan != null) { 
                LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                pnlTuan.updateDashboard(startOfWeek, now);
            } 
            else if (selectedIndex == 1 && pnlThang != null) {
				pnlThang.updateThongKeTheoBoChon();
			}
            else if (selectedIndex == 2 && pnlNam != null) {
				pnlNam.updateThongKeTheoBoChon();
			}
        });

        add(tabbedPane, BorderLayout.CENTER);

     // lan chay dau tien
        SwingUtilities.invokeLater(() -> {
            LocalDate now = LocalDate.now();
            LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            
            // kiem tra va update data moi
            if(pnlTuan != null) {
                pnlTuan.updateDashboard(startOfWeek, now);
            }
        });
    }

    public static void main(String[] args) {
    	FlatLightLaf.setup();
    	try {
            connectDB.ConnectDB.getInstance().connect(); // ket noi database
            System.out.println("Đã kết nối Database thành công!");
        } catch (Exception e) {
            System.out.println("Lỗi kết nối Database. Vui lòng kiểm tra lại SQL Server!");
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new ThongKeTaiChinh_GUI().setVisible(true);
        });
    }
}
