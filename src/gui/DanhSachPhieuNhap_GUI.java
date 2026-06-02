package gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.toedter.calendar.JDateChooser;

import dao.PhieuNhap_DAO;
import entity.PhieuNhap;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import common.DesignImg;
import custom_gui.ItemPhieuNhap_GUI;


public class DanhSachPhieuNhap_GUI extends JPanel  implements ActionListener{

	private JTextField tFTimKiem;

	private JDateChooser dateTuNgay;
	private JDateChooser dateDenNgay;
	private JPanel pnlHeaderTable;
	private JPanel pnlDSPN;
	private ArrayList<PhieuNhap> dsPN;
	private PhieuNhap_DAO pN_Dao;
	private JButton btnTaoMoi;
	private Main main;

	public DanhSachPhieuNhap_GUI(Main main) {
		
		this.main = main;
		setLayout(new BorderLayout());
		Color clrBackground = new Color(232, 232, 232);
		setBackground(clrBackground);
		JPanel pnlNorth = new JPanel();
		pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.Y_AXIS));
		pnlNorth.setBackground(clrBackground);
		pnlNorth.setBorder(new EmptyBorder(20, 25, 10, 25));
		JPanel pnlTitle = new JPanel();
		pnlTitle.setLayout(new BoxLayout(pnlTitle, BoxLayout.X_AXIS));
		pnlTitle.setOpaque(false);		JLabel lblHeader = new JLabel("Danh sách phiếu nhập");
		lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 26));
		pnlTitle.add(lblHeader);

		btnTaoMoi = new JButton("Tạo phiếu nhập mới");
		btnTaoMoi.setBackground(new Color(13, 110, 253));
		btnTaoMoi.setForeground(Color.WHITE);
		btnTaoMoi.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnTaoMoi.setMargin(new Insets(10, 20, 10, 20));
		pnlTitle.add(Box.createHorizontalGlue());
		pnlTitle.add(btnTaoMoi);
		pnlNorth.add(pnlTitle);

		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBackground(clrBackground);
		pnlCenter.setBorder(new EmptyBorder(20, 20, 20, 20));

		JPanel pnlSearch = new JPanel();
		pnlSearch.setLayout(new BoxLayout(pnlSearch, BoxLayout.X_AXIS));
		pnlSearch.setBackground(Color.WHITE);
		pnlSearch.setBorder(new FlatLineBorder(new Insets(10, 10, 10, 10), new Color(230, 230, 230), 1, 20));
		ImageIcon searchIcon = new ImageIcon("img/search.png");
		DesignImg disgn = new DesignImg();
		tFTimKiem = new JTextField();
		tFTimKiem.putClientProperty("JTextField.placeholderText", "Tìm mã phiếu nhập...");
		tFTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tFTimKiem.putClientProperty("JTextField.leadingIcon", disgn.setImg(searchIcon, 25, 25));

		JLabel lblTu = new JLabel("Từ ngày:");
		lblTu.setFont(new Font("Segoe UI", Font.BOLD, 14));
		dateTuNgay = new JDateChooser(new Date());
		styleDateChooser(dateTuNgay);
		JLabel lblDen = new JLabel("Đến ngày:");
		lblDen.setFont(new Font("Segoe UI", Font.BOLD, 14));
		dateDenNgay = new JDateChooser(new Date());
		styleDateChooser(dateDenNgay);

		pnlSearch.add(tFTimKiem);
		pnlSearch.add(Box.createHorizontalStrut(30));
		pnlSearch.add(lblTu);
		pnlSearch.add(Box.createHorizontalStrut(10));
		pnlSearch.add(dateTuNgay);
		pnlSearch.add(Box.createHorizontalStrut(30));
		pnlSearch.add(lblDen);
		pnlSearch.add(Box.createHorizontalStrut(10));
		pnlSearch.add(dateDenNgay);

		pnlCenter.add(pnlSearch, BorderLayout.NORTH);

		JPanel pnlTableContainer = new JPanel(new BorderLayout());
		pnlTableContainer.setBackground(Color.WHITE);
		pnlTableContainer.setBorder(new FlatLineBorder(new Insets(10, 10, 10, 10), new Color(230, 230, 230), 1, 20));

		pnlHeaderTable = new JPanel(new GridLayout(1, 6, 10, 0));
		pnlHeaderTable.setBackground(Color.WHITE);
		pnlHeaderTable.setPreferredSize(new Dimension(0, 45));
		pnlHeaderTable.setBorder(new EmptyBorder(0, 20, 0, 20));
		loadTieuDeBang();

		pnlDSPN = new JPanel();
		pnlDSPN.setLayout(new BoxLayout(pnlDSPN, BoxLayout.Y_AXIS));
		pnlDSPN.setBackground(Color.WHITE);
		JScrollPane scroll = new JScrollPane(pnlDSPN);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(16);

		JPanel pnlData = new JPanel(new BorderLayout());
		pnlData.setOpaque(false);
		pnlData.add(pnlHeaderTable, BorderLayout.NORTH);
		pnlData.add(scroll, BorderLayout.CENTER);
		pnlTableContainer.add(pnlData, BorderLayout.CENTER);
		pnlCenter.add(pnlTableContainer, BorderLayout.CENTER);

		loadDuLieu();
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		
		btnTaoMoi.addActionListener(this);
		
		tFTimKiem.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				thucHienTimKiem();

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				thucHienTimKiem();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				thucHienTimKiem();
			}
		});
	}

	public void loadTieuDeBang() {

		String[] headers = { "Mã phiếu", "Ngày nhập", "Nhà cung cấp", "Nhân viên", "Tổng tiền", "Chi tiết" };

		for (String h : headers) {

			JLabel lbl = new JLabel(h);

			lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));

			lbl.setForeground(new Color(137, 104, 205));

			if (h.equals("Chi tiết")) {

				lbl.setHorizontalAlignment(SwingConstants.CENTER);
			}

			pnlHeaderTable.add(lbl);
		}
	}

	public void loadDuLieu() {
		pnlDSPN.removeAll();
		pN_Dao = new PhieuNhap_DAO();

		dsPN = pN_Dao.getAllPhieuNhap();

		for (PhieuNhap pN : dsPN) {

			ItemPhieuNhap_GUI item = new ItemPhieuNhap_GUI(pN);

			pnlDSPN.add(item);

			pnlDSPN.add(Box.createVerticalStrut(10));
		}

		pnlDSPN.revalidate();

		pnlDSPN.repaint();
	}

	private void styleDateChooser(JDateChooser chooser) {
		chooser.setPreferredSize(new Dimension(100, 40));
		chooser.setDateFormatString("dd/MM/yyyy");

		JTextField dateEditor = (JTextField) chooser.getDateEditor().getUiComponent();
		dateEditor.setBackground(Color.WHITE);

		for(ActionListener al : dateEditor.getActionListeners()) {
	        dateEditor.removeActionListener(al);
	    }

		dateEditor.addKeyListener(new java.awt.event.KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	               
	                try {
	                	dateEditor.transferFocus();
	                } catch (Exception ex) {}

	                if (kiemTraRegexNgay(dateTuNgay) && kiemTraRegexNgay(dateDenNgay)) {
	                    getPNTheoDay(dateTuNgay, dateDenNgay);
	                }
	            }
	        }
	    });
		
		JButton btnCalendar = chooser.getCalendarButton();
		btnCalendar.setBorder(BorderFactory.createEmptyBorder());
		btnCalendar.setContentAreaFilled(false);
		btnCalendar.setFocusPainted(false);
		btnCalendar.setBackground(Color.WHITE);
		btnCalendar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		chooser.setBorder(BorderFactory.createEmptyBorder());
		chooser.setBackground(Color.WHITE);
	}

	public boolean kiemTraRegexNgay(JDateChooser date) {

		JTextField ngayTF = ((JTextField) date.getDateEditor().getUiComponent());
		String thoiGianNhap = ngayTF.getText().trim();

		if (thoiGianNhap.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Thời gian nhập không được trống", "Error", JOptionPane.ERROR_MESSAGE);
			ngayTF.requestFocus();
			return false;
		}

		if (!thoiGianNhap.matches("\\d{2}/\\d{2}/\\d{4}")) {
			JOptionPane.showMessageDialog(this, "Thời gian nhập phải theo định dạng dd/mm/yyyy!", "Error",
					JOptionPane.ERROR_MESSAGE);
			ngayTF.requestFocus();
			return false;
		}

		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
			LocalDate ktrDate = LocalDate.parse(thoiGianNhap, dtf);
			LocalDate dayNow = LocalDate.now();

			if (ktrDate.isAfter(dayNow)) {
				JOptionPane.showMessageDialog(this, "Lịch sử nhập không được lớn hơn ngày hiện tại!", "Error",
						JOptionPane.ERROR_MESSAGE);
				ngayTF.requestFocus();
				return false;
			}
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(this, "Ngày nhập không có trên lịch!", "Error", JOptionPane.ERROR_MESSAGE);
			ngayTF.requestFocus();
			return false;
		}

		return true;
	}
	
	public void getPNTheoDay(JDateChooser tuNgay, JDateChooser denNgay) {
		
		pnlDSPN.removeAll();
		
		JTextField tfTuNgay = ((JTextField) tuNgay.getDateEditor().getUiComponent());
		String tuNgayStr = tfTuNgay.getText();
		String  tfDenNgay = ((JTextField) denNgay.getDateEditor().getUiComponent()).getText();
		
		try {
			DateTimeFormatter fmDay = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
			
			LocalDate lcTuNgay = LocalDate.parse(tuNgayStr, fmDay);
			LocalDate lcDenNgay = LocalDate.parse(tfDenNgay, fmDay);
			
			if(lcTuNgay.isAfter(lcDenNgay)) {
				JOptionPane.showMessageDialog(this,"Ngày bắt đầu phải nhỏ hơn ngày hiện tại!");
				tfTuNgay.requestFocus();
				return;
			}
			
			for(PhieuNhap pN : dsPN) {
				
				if((pN.getThoiGianNhap().isEqual(lcTuNgay) || pN.getThoiGianNhap().isAfter(lcTuNgay)) && (pN.getThoiGianNhap().isBefore(lcDenNgay) || pN.getThoiGianNhap().isEqual(lcDenNgay))) {
					
					ItemPhieuNhap_GUI item = new ItemPhieuNhap_GUI(pN);
					
					pnlDSPN.add(item);
				}
			}
			
			pnlDSPN.revalidate();
			pnlDSPN.repaint();
		} catch(DateTimeParseException e) {
			
			JOptionPane.showMessageDialog(this, "Ngày nhập không có trên lịch!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

	}
	public void thucHienTimKiem() {

		pnlDSPN.removeAll();

		for (PhieuNhap pn : dsPN) {

			if (soSanhChuoi(pn.getMaPN(), tFTimKiem.getText())) {

				ItemPhieuNhap_GUI newCard = new ItemPhieuNhap_GUI(pn);
				pnlDSPN.add(newCard);
			}
		}

		pnlDSPN.revalidate();
		pnlDSPN.repaint();
	}
	
	public boolean soSanhChuoi(String tenSP, String tuKhoa) {

		if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
			return true;
		}

		return tenSP.toLowerCase().contains(tuKhoa.toLowerCase().trim());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object o = e.getSource();
		
		if(o.equals(btnTaoMoi)) {
			
			main.setNhapHang();
		}
		
	}
}