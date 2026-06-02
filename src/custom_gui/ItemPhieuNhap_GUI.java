package custom_gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.ui.FlatLineBorder;

import entity.NhaCungCap;
import entity.NhanVien;
import entity.PhieuNhap;
import gui.ChiTietPhieuNhap_GUI;
import common.DesignImg;

public class ItemPhieuNhap_GUI extends JPanel implements ActionListener{

	private JLabel lblMaPhieu;
	private JLabel lblNgayNhap;
	private JLabel lblNhaCungCap;
	private JLabel lblNhanVien;
	private JLabel lblTongTien;
	private JButton btnXemChiTiet;
	private DateTimeFormatter fmNgay;
	private ChiTietPhieuNhap_GUI cTPN;
	private PhieuNhap pN;

	public ItemPhieuNhap_GUI(PhieuNhap pn) {

		this.pN = pn;
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		setPreferredSize(new Dimension(1000, 60));
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)));

		fmNgay = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		String ngayStr = pn.getThoiGianNhap().format(fmNgay);

		JPanel pnlNoiDung = new JPanel(new GridLayout(1, 6, 10, 0));
		pnlNoiDung.setOpaque(false);
		pnlNoiDung.setBorder(new EmptyBorder(0, 20, 0, 20));
		lblMaPhieu = createLabelMaVTien(pn.getMaPN(), new Color(13, 110, 253));
		lblNgayNhap = createLabelThongThuong(ngayStr);
		lblNhaCungCap = createLabelThongThuong(pn.getnCC().getTenNCC());
		lblNhanVien = createLabelThongThuong(pn.getnV().getTenNV());
		lblTongTien = createLabelMaVTien(foramtVND(pn.getTongTien()), new Color(220, 20, 60));
		lblTongTien.setHorizontalAlignment(SwingConstants.LEFT);

		btnXemChiTiet = new JButton("Xem");
		styleButtonXem(btnXemChiTiet);

		pnlNoiDung.add(lblMaPhieu);
		pnlNoiDung.add(lblNgayNhap);
		pnlNoiDung.add(lblNhaCungCap);
		pnlNoiDung.add(lblNhanVien);
		pnlNoiDung.add(lblTongTien);

		JPanel pnlWrapperNut = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
		pnlWrapperNut.setOpaque(false);
		pnlWrapperNut.add(btnXemChiTiet);
		pnlNoiDung.add(pnlWrapperNut);
		add(pnlNoiDung, BorderLayout.CENTER);	
		btnXemChiTiet.addActionListener(this);
	}

	private JLabel createLabelThongThuong(String text) {

		JLabel lbl = new JLabel(text);

		lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lbl.setForeground(Color.BLACK);

		lbl.setHorizontalAlignment(SwingConstants.LEFT);

		return lbl;
	}

	private JLabel createLabelMaVTien(String text, Color mauSac) {

		JLabel lbl = new JLabel(text);

		lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lbl.setForeground(mauSac);

		return lbl;
	}

	private void styleButtonXem(JButton btn) {
		
		DesignImg ds = new DesignImg();
		ImageIcon icon = new ImageIcon("img/seen.png");
		btn.setIcon(ds.setImg(icon, 15, 15));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setBackground(new Color(240, 247, 255));
		btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn.setForeground(new Color(13, 110, 253));
		btn.setFocusPainted(false);
		btn.setPreferredSize(new Dimension(85, 36));
		btn.putClientProperty("JButton.arc", 20);
		btnXemChiTiet.setBorder(new FlatLineBorder(
			    new Insets(3, 15, 3, 15),
			    new Color(13, 110, 253), 
			    1,                      
			    20                       
			));
	}

	public JButton getBtnXemChiTiet() {
		return btnXemChiTiet;
	}

	public String foramtVND(double tongTien) {

		DecimalFormat fm = new DecimalFormat("#,### đ");

		return fm.format(tongTien);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object o = e.getSource();
		
		if(o.equals(btnXemChiTiet)) {
			
			cTPN = new ChiTietPhieuNhap_GUI(pN);
			cTPN.setVisible(true);
		}
		
	}
}