package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dao.SanPham_dao;
import entity.SanPham;
import dao.ChiTietPhieuNhap_DAO;
import dao.NhaCungCap_dao;
import dao.PhieuNhap_DAO;
import entity.ChiTietPhieuNhap;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.PhieuNhap;
import entity.ShareData;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.toedter.calendar.JDateChooser;

import custom_gui.CardItemNhapHang_Type1_GUI;
import custom_gui.CardItemNhapHang_Type2_GUI;

public class NhapHang_GUI extends JPanel implements ActionListener {

	private JTextField tFTimKiem;
	private JTextField tfMaPN;
	private JComboBox<NhaCungCap> CbxNCC;
	private JTextField tfTenNV;
	private JPanel pnlDSSP;
	private JPanel pnlNull;
	private JButton btnTaoPhieu;
	private JPanel pnlChiTiet;
	private JDateChooser choseDate;
	private JLabel lblTien;
	private Font originalFont;
	private JButton btnTatCa;
	private JButton btnDoUong;
	private JButton btnVanPhong;
	private JButton btnBanhKeo;
	private JButton btnSua;
	private JButton btnMiGoi;
	private ArrayList<SanPham> dsSP;
	private PhieuNhap_DAO pN_DAO = new PhieuNhap_DAO();
	private SanPham_dao sp_DAO;

	public NhapHang_GUI() {

		setLayout(new BorderLayout());
		Color clrBackgroud = new Color(232, 232, 232);
		Color clrWhite = Color.WHITE;
		// Set bogoc cho Components
		UIManager.put("Component.arc", 15);
		UIManager.put("TextComponent.arc", 15);
		UIManager.put("Button.arc", 15);
		setBackground(clrBackgroud);

		// Them tieu de trang
		JPanel pnlTitleNorth = new JPanel();
		pnlTitleNorth.setLayout(new BoxLayout(pnlTitleNorth, BoxLayout.X_AXIS));
		JLabel lblTitleNorth = new JLabel("Tạo Phiếu Nhập Hàng");
		lblTitleNorth.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTitleNorth.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 0));
		pnlTitleNorth.add(lblTitleNorth);
		pnlTitleNorth.add(Box.createGlue());
		pnlTitleNorth.setBackground(clrBackgroud);

		// Them tim kiem o north cua center
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBackground(clrBackgroud);
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		Box bxNorthOfCenter = Box.createVerticalBox();
		bxNorthOfCenter.setBackground(clrBackgroud);
		// Thanh tim kiem
		Dimension editSizeTimKiem = new Dimension(750, 40);
		Dimension editSizeBacgroundTimKiem = new Dimension(830, 60);
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setLayout(new BoxLayout(pnlTimKiem, BoxLayout.Y_AXIS));
		JPanel pnlBackgroundTimKiem = new JPanel();
		pnlBackgroundTimKiem.setLayout(new BoxLayout(pnlBackgroundTimKiem, BoxLayout.X_AXIS));
		pnlBackgroundTimKiem.setBackground(clrWhite);
		pnlBackgroundTimKiem.setMaximumSize(editSizeBacgroundTimKiem);
		pnlBackgroundTimKiem.setPreferredSize(editSizeBacgroundTimKiem);
		setRoundBorderForTimKiem(pnlBackgroundTimKiem, 15);
		ImageIcon iconTimKiem = new ImageIcon("Img/search.png");
		tFTimKiem = new JTextField();
		tFTimKiem.putClientProperty("JTextField.leadingIcon", setImg(iconTimKiem, 25, 25));
		tFTimKiem.putClientProperty("JTextField.placeholderText", "Tìm kiếm sản phẩm...");
		tFTimKiem.setPreferredSize(editSizeTimKiem);
		tFTimKiem.setMaximumSize(editSizeTimKiem);
		tFTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 16));

		// Thêm lọc theo loại
		ImageIcon filterIcon = new ImageIcon("Img/filter.png");
		JLabel lblLoc = new JLabel();
		lblLoc.setIcon(setImg(filterIcon, 25, 25));

		Box bxLoc = Box.createHorizontalBox();
		btnTatCa = new JButton("Tất cả");
		btnDoUong = new JButton("Đồ uống");
		btnVanPhong = new JButton("Văn phòng phẩm");
		btnBanhKeo = new JButton("Bánh kẹo");
		btnSua = new JButton("Sữa");
		btnMiGoi = new JButton("Mì gói");

		// Hàm style chung
		styleFilterButton(btnTatCa, new Color(100, 149, 237));
		styleFilterButton(btnDoUong, new Color(0, 153, 102));
		styleFilterButton(btnVanPhong, new Color(255, 153, 51));
		styleFilterButton(btnBanhKeo, new Color(255, 102, 102));
		styleFilterButton(btnSua, new Color(153, 102, 255));
		styleFilterButton(btnMiGoi, new Color(255, 204, 0));

		// Add vào box
		bxLoc.add(lblLoc);
		bxLoc.add(Box.createHorizontalStrut(10));
		bxLoc.add(btnTatCa);
		bxLoc.add(Box.createHorizontalGlue());
		bxLoc.add(btnDoUong);
		bxLoc.add(Box.createHorizontalGlue());
		bxLoc.add(btnVanPhong);
		bxLoc.add(Box.createHorizontalGlue());
		bxLoc.add(btnBanhKeo);
		bxLoc.add(Box.createHorizontalGlue());
		bxLoc.add(btnSua);
		bxLoc.add(Box.createHorizontalGlue());
		bxLoc.add(btnMiGoi);

		// Them vao bxNorthOfCenter
		bxNorthOfCenter.add(pnlTimKiem);

		pnlBackgroundTimKiem.add(tFTimKiem);
		pnlTimKiem.add(pnlBackgroundTimKiem);
		pnlTimKiem.add(Box.createVerticalStrut(10));
		pnlTimKiem.add(bxLoc);
		pnlTimKiem.add(Box.createVerticalStrut(10));
		pnlTimKiem.setBackground(clrBackgroud);

		// Danh sach san pham
		JPanel pnlSP = new JPanel();
		pnlSP.setLayout(new BorderLayout());
		pnlSP.setBackground(clrWhite);
		setRoundBorder(pnlSP, 15);
		JLabel lblDSSP = new JLabel("Danh Sách Sản Phẩm");
		lblDSSP.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblDSSP.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		JPanel pnlWrapper = new JPanel(new BorderLayout());
		pnlWrapper.setBackground(clrWhite);
		pnlDSSP = new JPanel();
		pnlDSSP.setLayout(new GridLayout(0, 4, 15, 15));
		pnlDSSP.setBackground(clrWhite);
		pnlDSSP.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		pnlWrapper.add(pnlDSSP, BorderLayout.NORTH);
		JScrollPane scrollSP = new JScrollPane(pnlWrapper);
		scrollSP.getVerticalScrollBar().setUnitIncrement(16);
		scrollSP.setBorder(null);

		pnlSP.add(lblDSSP, BorderLayout.NORTH);
		pnlSP.add(scrollSP, BorderLayout.CENTER);
		// Them vao Center của pnl chính
		pnlCenter.add(bxNorthOfCenter, BorderLayout.NORTH);
		pnlCenter.add(pnlSP, BorderLayout.CENTER);

		// Thong tin PhieuNhap
		JPanel pnlSidebar = new JPanel(new BorderLayout());
		pnlSidebar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
		pnlSidebar.setBackground(clrBackgroud);
		JScrollPane scrollPN = new JScrollPane(pnlSidebar);
		scrollPN.setBorder(null);
		scrollPN.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPN.getVerticalScrollBar().setUnitIncrement(16);
		Box bxPhieuNhap = Box.createVerticalBox();
		// Thong tin chung
		Dimension editTF = new Dimension(450, 40);
		Font fTF = new Font("Segoe UI", Font.BOLD, 14);
		JPanel pnlPhieuNhap = new JPanel();
		pnlPhieuNhap.setBackground(clrWhite);
		pnlPhieuNhap.setLayout(new BoxLayout(pnlPhieuNhap, BoxLayout.Y_AXIS));
		setRoundBorder(pnlPhieuNhap, 15);
		pnlPhieuNhap.setPreferredSize(new Dimension(420, 350));
		pnlPhieuNhap.setMaximumSize(new Dimension(420, 350));
		pnlPhieuNhap.setMinimumSize(new Dimension(420, 3500));
		ImageIcon boxIcon = new ImageIcon("Img/box.png");
		JLabel lblTitlePhieuNhap = new JLabel("Phiếu Nhập");
		lblTitlePhieuNhap.setIcon(setImg(boxIcon, 25, 25));
		lblTitlePhieuNhap.setFont(new Font("Segoe UI", Font.BOLD, 15));
		JLabel lblMaPN = new JLabel("Mã phiếu nhập: ");
		lblMaPN.setFont(fTF);
		tfMaPN = new JTextField(phatSinhMa());
		tfMaPN.setFocusable(false);
		tfMaPN.setPreferredSize(editTF);
		tfMaPN.setMaximumSize(editTF);
		tfMaPN.setFont(new Font("Segoe UI", tfMaPN.getFont().getStyle(), 16));
		JLabel lblTenNCC = new JLabel("Tên nhà cung cấp: ");
		lblTenNCC.setFont(fTF);
		CbxNCC = new JComboBox<NhaCungCap>();
		CbxNCC.setPreferredSize(editTF);
		CbxNCC.setMaximumSize(editTF);
		CbxNCC.setFont(new Font("Segoe UI", CbxNCC.getFont().getStyle(), 16));
		JLabel lblTenNV = new JLabel("Tên nhân viên: ");
		lblTenNV.setFont(fTF);
		tfTenNV = new JTextField(ShareData.taiKhoanDangNhap.getTenNV());
		tfTenNV.putClientProperty("JTextField.placeholderText", "Tên nhân viên...");
		tfTenNV.setFont(new Font("Segoe UI", tfTenNV.getFont().getStyle(), 16));
		tfTenNV.setPreferredSize(editTF);
		tfTenNV.setMaximumSize(editTF);
		tfTenNV.setFocusable(false);
		JLabel lblThoiGianNhap = new JLabel("Ngày nhập: ");
		lblThoiGianNhap.setFont(fTF);

		choseDate = new JDateChooser();
		choseDate.setDateFormatString("dd/MM/yyyy");
		choseDate.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		choseDate.setPreferredSize(editTF);
		choseDate.setMaximumSize(editTF);
		choseDate.setDate(new Date());
		JButton iconDate = choseDate.getCalendarButton();
		iconDate.setPreferredSize(new Dimension(40, 40));
		iconDate.setCursor(new Cursor(Cursor.HAND_CURSOR));
		iconDate.setBorder(BorderFactory.createEmptyBorder());

		// Can trai cac phan tu
		lblTitlePhieuNhap.setAlignmentX(LEFT_ALIGNMENT);
		lblMaPN.setAlignmentX(LEFT_ALIGNMENT);
		tfMaPN.setAlignmentX(LEFT_ALIGNMENT);
		lblTenNCC.setAlignmentX(LEFT_ALIGNMENT);
		CbxNCC.setAlignmentX(LEFT_ALIGNMENT);
		lblTenNV.setAlignmentX(LEFT_ALIGNMENT);
		tfTenNV.setAlignmentX(LEFT_ALIGNMENT);
		lblThoiGianNhap.setAlignmentX(LEFT_ALIGNMENT);
		choseDate.setAlignmentX(LEFT_ALIGNMENT);

		pnlPhieuNhap.add(lblTitlePhieuNhap);
		pnlPhieuNhap.add(Box.createVerticalStrut(10));
		pnlPhieuNhap.add(lblMaPN);
		pnlPhieuNhap.add(tfMaPN);
		pnlPhieuNhap.add(Box.createVerticalStrut(10));
		pnlPhieuNhap.add(lblTenNCC);
		pnlPhieuNhap.add(CbxNCC);
		pnlPhieuNhap.add(Box.createVerticalStrut(10));
		pnlPhieuNhap.add(lblTenNV);
		pnlPhieuNhap.add(tfTenNV);
		pnlPhieuNhap.add(Box.createVerticalStrut(10));
		pnlPhieuNhap.add(lblThoiGianNhap);
		pnlPhieuNhap.add(choseDate);

		// Thong tin chi tiet
		JPanel pnlSanPhamNhap = new JPanel();
		pnlSanPhamNhap.setLayout(new BoxLayout(pnlSanPhamNhap, BoxLayout.Y_AXIS));
		pnlSanPhamNhap.setBackground(clrWhite);
		pnlSanPhamNhap.setPreferredSize(new Dimension(420, 500));
		pnlSanPhamNhap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
		setRoundBorder(pnlSanPhamNhap, 15);
		pnlSanPhamNhap.setFocusable(true);
		JLabel lblTitleSPN = new JLabel("Sản Phẩm Nhập");
		ImageIcon checkListIcon = new ImageIcon("Img/checklist.png");
		lblTitleSPN.setIcon(setImg(checkListIcon, 25, 25));
		lblTitleSPN.setFont(new Font("Segoe UI", Font.BOLD, 15));

		pnlChiTiet = new JPanel();
		pnlChiTiet.setLayout(new BoxLayout(pnlChiTiet, BoxLayout.Y_AXIS));
		pnlChiTiet.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnlChiTiet.setBackground(clrWhite);
		JScrollPane scrollChiTiet = new JScrollPane(pnlChiTiet);
		scrollChiTiet.setBorder(null);
		scrollChiTiet.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollChiTiet.getVerticalScrollBar().setUnitIncrement(16);
		scrollChiTiet.setPreferredSize(new Dimension(Integer.MAX_VALUE, 300));
		scrollChiTiet.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

		// pnl Khong co san pham
		pnlNull = new JPanel();
		pnlNull.setLayout(new BoxLayout(pnlNull, BoxLayout.Y_AXIS));
		pnlNull.setPreferredSize(new Dimension(Integer.MAX_VALUE, 280));
		pnlNull.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
		pnlNull.setBackground(clrWhite);
		ImageIcon IconNull = new ImageIcon("Img/package-box.png");
		JLabel lblNull = new JLabel(setImg(IconNull, 50, 50));
		lblNull.setBorder(BorderFactory.createEmptyBorder(0, 150, 10, 0));
		JLabel lblTextNull = new JLabel("Chưa có sản phẩm");
		lblTextNull.setFont(new Font("Segoe UI", lblTextNull.getFont().getStyle(), 16));
		lblTextNull.setForeground(new Color(180, 180, 180));
		lblTextNull.setBorder(BorderFactory.createEmptyBorder(0, 110, 0, 0));

		pnlNull.add(Box.createVerticalGlue());
		pnlNull.add(lblNull);
		pnlNull.add(Box.createVerticalStrut(10));
		pnlNull.add(lblTextNull);
		pnlNull.add(Box.createVerticalGlue());
		pnlNull.setAlignmentX(Component.CENTER_ALIGNMENT);

		pnlChiTiet.add(pnlNull);

		JSeparator sepLine = new JSeparator(SwingConstants.HORIZONTAL);
		sepLine.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
		sepLine.setForeground(new Color(220, 220, 220));

		// Set tong tien va tao don
		JPanel pnlTongTien = new JPanel();
		pnlTongTien.setLayout(new BoxLayout(pnlTongTien, BoxLayout.X_AXIS));
		pnlTongTien.setOpaque(false);
		JLabel lblTilTongTien = new JLabel("Tổng Tiền: ");
		lblTilTongTien.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTien = new JLabel(formatVND(tongTien()));
		lblTien.setPreferredSize(new Dimension(250, 50));
		lblTien.setMaximumSize(new Dimension(250, 50));
		lblTien.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTien.setForeground(new Color(0, 102, 204));
		lblTien.setHorizontalAlignment(SwingConstants.RIGHT);
		pnlTongTien.add(lblTilTongTien);
		pnlTongTien.add(Box.createHorizontalGlue());
		pnlTongTien.add(lblTien);
		pnlTongTien.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnlTongTien.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		btnTaoPhieu = new JButton("Tạo phiếu nhập");
		btnTaoPhieu.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnTaoPhieu.setMargin(new Insets(10, 115, 10, 117));
		btnTaoPhieu.setBackground(new Color(200, 205, 210));
		btnTaoPhieu.setEnabled(false);
		btnTaoPhieu.setCursor(new Cursor(Cursor.WAIT_CURSOR));

		lblTitleSPN.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollChiTiet.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnlTongTien.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnTaoPhieu.setAlignmentX(Component.LEFT_ALIGNMENT);

		pnlSanPhamNhap.add(lblTitleSPN);
		pnlSanPhamNhap.add(Box.createVerticalStrut(10));
		pnlSanPhamNhap.add(scrollChiTiet);
		pnlSanPhamNhap.add(Box.createVerticalStrut(10));
		pnlSanPhamNhap.add(sepLine);
		pnlSanPhamNhap.add(Box.createVerticalStrut(10));
		pnlSanPhamNhap.add(pnlTongTien);
		pnlSanPhamNhap.add(Box.createVerticalStrut(10));
		pnlSanPhamNhap.add(btnTaoPhieu);

		bxPhieuNhap.add(pnlPhieuNhap);
		bxPhieuNhap.add(Box.createVerticalStrut(20));
		bxPhieuNhap.add(pnlSanPhamNhap);

		pnlSidebar.add(bxPhieuNhap, BorderLayout.CENTER);

		getAllSanPham();
		setOriginalFont();
		loadNCC();
		loadCardItemNhapHang_Type1();
		add(pnlTitleNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(scrollPN, BorderLayout.EAST);

		btnTaoPhieu.addActionListener(this);
		btnTatCa.addActionListener(this);
		btnBanhKeo.addActionListener(this);
		btnDoUong.addActionListener(this);
		btnMiGoi.addActionListener(this);
		btnSua.addActionListener(this);
		btnVanPhong.addActionListener(this);

		KeyAdapter lis = new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				capNhatTrangThaiNutTao();
			}
		};

		tfTenNV.addKeyListener(lis);
		CbxNCC.addActionListener(e -> capNhatTrangThaiNutTao());
		choseDate.addPropertyChangeListener("date", e -> capNhatTrangThaiNutTao());
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

	public void setRoundBorder(JPanel pnl, int arc) {

		pnl.setBorder(new FlatLineBorder(new Insets(20, 20, 20, 20), new Color(240, 240, 240), 1, arc));
	}

	public void setRoundBorderForTimKiem(JPanel pnl, int arc) {

		pnl.setBorder(new FlatLineBorder(new Insets(10, 10, 10, 10), new Color(240, 240, 240), 1, arc));
	}

	public ImageIcon setImg(ImageIcon icon, int width, int height) {

		Image img = icon.getImage();
		Image editImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon editedIcon = new ImageIcon(editImg);
		return editedIcon;
	}

	public void loadCardItemNhapHang_Type1() {
		pnlDSSP.removeAll();
		dsSP = sp_DAO.getTableSanPham();
		for (SanPham sP : dsSP) {

			CardItemNhapHang_Type1_GUI item = new CardItemNhapHang_Type1_GUI(this, sP.getMaSP(), sP.getTenSP(),
					sP.getGiaNhap(), sP.getHinhAnh());
			pnlDSSP.add(item);
		}

		pnlDSSP.revalidate();
		pnlDSSP.repaint();
	}

	public String formatVND(double tien) {

		DecimalFormat fm = new DecimalFormat("#,### đ");
		return fm.format(tien);
	}

	public void loadNCC() {
		CbxNCC.removeAll();
		NhaCungCap_dao ncc_DAO = new NhaCungCap_dao();
		ArrayList<NhaCungCap> dsNCC = ncc_DAO.getTableNhaCungCap();
		NhaCungCap defaultNCC = new NhaCungCap();
		defaultNCC.setTenNCC("--Chọn nhà cung cấp--");
		CbxNCC.removeAllItems();
		
		CbxNCC.addItem(defaultNCC);

		if (dsNCC != null) {

			for (NhaCungCap nCC : dsNCC) {
				CbxNCC.addItem(nCC);
			}
		}
//		CbxNCC.repaint();
	}

	public boolean kiemTraRegex() {

		String maPhieuNhap = tfMaPN.getText();
		String tenNCC = CbxNCC.getSelectedItem().toString();
		String tenNV = tfTenNV.getText();
		JTextField ngayTF = ((JTextField) choseDate.getDateEditor().getUiComponent());
		String thoiGianNhap = ngayTF.getText();

		if (maPhieuNhap.length() == 0) {

			focusTF(tfMaPN, "Mã phiếu nhập không được rỗng!");
			return false;
		} else {

			if (!maPhieuNhap.matches("PN\\d+")) {

				focusTF(tfMaPN, "Mã phiếu nhập phải bắt PN và có ít nhất 1 chứ số!");
				return false;
			}

		}

		if (tenNCC.length() == 0 || tenNCC.equals("--Chọn nhà cung cấp--")) {

			JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp", "Error", JOptionPane.ERROR_MESSAGE);
			CbxNCC.requestFocus();
			return false;
		} else {

			if (!tenNCC.matches("\\p{Lu}\\p{L}+(\\s\\p{L}+)+")) {

				JOptionPane.showMessageDialog(this,
						"Tên nhà cùng cấp tối thiểu 2 từ và cách nhau một khoảng trắng. Viết hoa chữ cái đầu dòng!",
						"Error", JOptionPane.ERROR_MESSAGE);
				CbxNCC.requestFocus();
				return false;
			}
		}

		if (tenNV.length() == 0) {

			focusTF(tfTenNV, "Tên nhân viên không được trống");
			return false;
		} else {
			if (!tenNV.matches("\\p{Lu}\\p{L}+(\\s\\p{Lu}\\p{L}+)+")) {

				focusTF(tfTenNV, "Tên nhân viên tối thiểu 2 từ và cách nhau một khoảng trắng. Viết hoa chữ cái dầu");
				return false;
			}
		}

		if (thoiGianNhap.length() == 0) {

			JOptionPane.showMessageDialog(this, "Thời gian nhập không được trống", "Error", JOptionPane.ERROR_MESSAGE);
			choseDate.requestFocus();
			return false;
		} else {

			if (!thoiGianNhap.matches("\\d{2}/\\d{2}/\\d{4}")) {

				JOptionPane.showMessageDialog(this, "Thời gian nhập phải theo định dạng dd/mm/yyyy!", "Error",
						JOptionPane.ERROR_MESSAGE);
				choseDate.requestFocus();
				return false;
			}
		}

		try {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
			LocalDate ktrDate = LocalDate.parse(thoiGianNhap, dtf);
			LocalDate dayNow = LocalDate.now();
			
			
			if (ktrDate.isAfter(dayNow)) {

				JOptionPane.showMessageDialog(this, "Thời gian nhập không được lớn hơn ngày hiện tại!", "Error",
						JOptionPane.ERROR_MESSAGE);
				choseDate.requestFocus();
				return false;
			}

		} catch (DateTimeParseException e) {

			JOptionPane.showMessageDialog(null, "Ngày nhập không có trên lịch!", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	public void themSanPhamVaoChiTiet(String maSP, String tenSP, double giaNhap, String url) {

		if (pnlChiTiet.getComponentCount() > 0 && pnlChiTiet.getComponent(0) == pnlNull) {
			pnlChiTiet.removeAll();
		}

		Component dsCMP[] = pnlChiTiet.getComponents();
		CardItemNhapHang_Type2_GUI tempItem = null;

		for (Component comp : dsCMP) {

			if (comp instanceof CardItemNhapHang_Type2_GUI) {

				CardItemNhapHang_Type2_GUI item = (CardItemNhapHang_Type2_GUI) comp;
				if (item.getMaSP().equals(maSP)) {
					tempItem = item;
					break;
				}
			}
		}

		if (tempItem != null) {
			tempItem.setSoLuong();
		} else {

			if (pnlChiTiet.getComponentCount() > 0) {

				pnlChiTiet.add(Box.createVerticalStrut(10));
			}

			CardItemNhapHang_Type2_GUI newItem = new CardItemNhapHang_Type2_GUI(this, maSP, tenSP, 1, giaNhap, url);
			pnlChiTiet.add(newItem);
		}

		pnlChiTiet.revalidate();
		pnlChiTiet.repaint();

		setLBLTongTien();
		autoReSize(lblTien, 10, originalFont);

		capNhatTrangThaiNutTao();
	}

	public void xoaSanPhamKhoiChiTiet(String maSP) {

		ArrayList<CardItemNhapHang_Type2_GUI> dsConLai = new ArrayList<>();
		for (Component c : pnlChiTiet.getComponents()) {

			if (c instanceof CardItemNhapHang_Type2_GUI) {

				CardItemNhapHang_Type2_GUI item = (CardItemNhapHang_Type2_GUI) c;

				if (!item.getMaSP().equals(maSP)) {

					dsConLai.add(item);
				}
			}
		}

		pnlChiTiet.removeAll();

		if (dsConLai.isEmpty()) {
			pnlChiTiet.add(pnlNull);
		} else {
			for (int i = 0; i < dsConLai.size(); i++) {

				if (i > 0)
					pnlChiTiet.add(Box.createVerticalStrut(10));
				pnlChiTiet.add(dsConLai.get(i));
			}
		}

		pnlChiTiet.revalidate();
		pnlChiTiet.repaint();

		setLBLTongTien();
		autoReSize(lblTien, 10, originalFont);

		capNhatTrangThaiNutTao();
	}

	public void focusTF(JTextField tf, String str) {

		JOptionPane.showMessageDialog(this, str, "Error", JOptionPane.ERROR_MESSAGE);
		tf.requestFocus();
	}

	public double tongTien() {

		double tongTien = 0;

		Component dsCMP[] = pnlChiTiet.getComponents();

		for (Component cmp : dsCMP) {

			if (cmp instanceof CardItemNhapHang_Type2_GUI) {

				CardItemNhapHang_Type2_GUI item = (CardItemNhapHang_Type2_GUI) cmp;

				tongTien += item.getThanhTien();
			}
		}
		return tongTien;
	}

	public void setLBLTongTien() {

		lblTien.setText(formatVND(tongTien()));
	}

	public void capNhatTrangThaiNutTao() {

		boolean coSanPham = (pnlChiTiet.getComponentCount() > 0 && pnlChiTiet.getComponent(0) != pnlNull);
		boolean dungRegex = (kiemTraDienDuThongTin());

		if (coSanPham && dungRegex) {
			btnTaoPhieu.setEnabled(true);
			btnTaoPhieu.setCursor(new Cursor(Cursor.HAND_CURSOR));
			btnTaoPhieu.setBackground(new Color(0, 102, 204));
			btnTaoPhieu.setForeground(Color.WHITE);
		} else {
			btnTaoPhieu.setEnabled(false);
			btnTaoPhieu.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			btnTaoPhieu.setBackground(new Color(200, 205, 210));
			btnTaoPhieu.setForeground(Color.BLACK);
		}
	}

	public boolean kiemTraDienDuThongTin() {
		String maPN = tfMaPN.getText().trim();
		Object ncc = CbxNCC.getSelectedItem();
		String nv = tfTenNV.getText().trim();
		String date = ((JTextField) choseDate.getDateEditor().getUiComponent()).getText();

		if (maPN.isEmpty() || !maPN.matches("PN\\d+"))
			return false;
		if (ncc == null || ncc.toString().equals("--Chọn nhà cung cấp--"))
			return false;
		if (nv.isEmpty())
			return false;
		if (date.isEmpty())
			return false;

		return true;
	}

	public void setOriginalFont() {

		originalFont = lblTien.getFont();
	}

	public void autoReSize(JLabel lbl, int minSize, Font originalFont) {

		int maxWidth = lbl.getWidth();
		Font fnt = originalFont;
		int size = fnt.getSize();

		FontMetrics fm;

		while (size > minSize) {

			Font font = new Font(fnt.getName(), fnt.getStyle(), size);
			fm = lbl.getFontMetrics(font);

			if (fm.stringWidth(lbl.getText()) <= maxWidth) {

				lbl.setFont(font);
				break;
			}
			size--;
		}
	}

	public void styleFilterButton(JButton btn, Color color) {
		btn.setBackground(color);
		btn.setForeground(Color.WHITE);

		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
		btn.setMargin(new Insets(5, 15, 5, 15));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object o = e.getSource();

		if (o.equals(btnTaoPhieu)) {
			if (kiemTraRegex()) {
				
				String maPN = tfMaPN.getText();
				LocalDate thoiGianNhap = choseDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				NhaCungCap nCC = (NhaCungCap) CbxNCC.getSelectedItem();
				NhanVien nv = ShareData.taiKhoanDangNhap;
				PhieuNhap pN = new PhieuNhap(maPN, thoiGianNhap, nCC, nv, tongTien());
				
				
				SanPham_dao sp_DAO = new SanPham_dao();
				PhieuNhap_DAO pN_DAO = new PhieuNhap_DAO();
				ChiTietPhieuNhap_DAO cTPN_DAO = new ChiTietPhieuNhap_DAO();
				
				try {
					if (pN_DAO.themPhieuNhap(pN)) { 
					    
					    Component dsCMP[] = pnlChiTiet.getComponents();
					    boolean loiChiTiet = false;

					    for (Component cmp : dsCMP) {
					    	
					        if (cmp instanceof CardItemNhapHang_Type2_GUI) {
					            CardItemNhapHang_Type2_GUI item = (CardItemNhapHang_Type2_GUI) cmp;
					            
					            String maSP = item.getMaSP();
					            SanPham sp = sp_DAO.getSanPhamTheoMa(maSP);
					            
					            if (sp != null) {
					                int soLuong = item.getSoLuong();
					                double thanhTien = item.getThanhTien();
					                
					                ChiTietPhieuNhap ct = new ChiTietPhieuNhap(pN, sp, soLuong, thanhTien);
					                
					                if (cTPN_DAO.themChiTietPhieuNhap(ct)) {
					                   
					                    sp_DAO.capNhatSoLuong(maSP, soLuong); 
					                }
					                else {
					                	loiChiTiet = true;
					                }
					            }
					        }
					    }
					    if(!loiChiTiet) {
					    	
					    	resetForm();
					    	JOptionPane.showMessageDialog(this, "Thêm phiếu nhập " + maPN + " thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
					    }
					    else {
					    	
					    	JOptionPane.showMessageDialog(this, "Phiếu nhập đã tạo nhưng có một số chi tiết bị lỗi!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
					    }
					    
					}	
					else {
						JOptionPane.showMessageDialog(this, "Trùng mã phiếu nhập hoặc lỗi kết nối Database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		} else if (o.equals(btnTatCa)) {

			locSanPham("Tat_Ca");

		} else if (o.equals(btnBanhKeo)) {

			locSanPham("Bánh kẹo");

		} else if (o.equals(btnDoUong)) {

			locSanPham("Đồ uống");

		} else if (o.equals(btnMiGoi)) {

			locSanPham("Mì gói");

		} else if (o.equals(btnSua)) {

			locSanPham("Sữa");
			tFTimKiem.setText("");
		} else if (o.equals(btnVanPhong)) {

			locSanPham("Văn phòng phẩm");

		}

	}

	public void locSanPham(String loai) {
		pnlDSSP.removeAll();

		for (SanPham sp : dsSP) {

			if (loai.equals("Tat_Ca") || sp.getLoaiSanPham().toString().equals(loai)) {
				CardItemNhapHang_Type1_GUI newCard = new CardItemNhapHang_Type1_GUI(this, sp.getMaSP(), sp.getTenSP(),
						sp.getGiaNhap(), sp.getHinhAnh());
				pnlDSSP.add(newCard);
			}
		}

		pnlDSSP.revalidate();
		pnlDSSP.repaint();
	}

	public void getAllSanPham() {

		sp_DAO = new SanPham_dao();
		dsSP = sp_DAO.getTableSanPham();

	}

	public boolean soSanhChuoi(String tenSP, String tuKhoa) {

		if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
			return true;
		}

		return tenSP.toLowerCase().contains(tuKhoa.toLowerCase().trim());
	}

	public void thucHienTimKiem() {

		pnlDSSP.removeAll();

		for (SanPham sp : dsSP) {

			if (soSanhChuoi(sp.getTenSP(), tFTimKiem.getText())) {

				CardItemNhapHang_Type1_GUI newCard = new CardItemNhapHang_Type1_GUI(this, sp.getMaSP(), sp.getTenSP(),
						sp.getGiaNhap(), sp.getHinhAnh());
				pnlDSSP.add(newCard);
			}
		}

		pnlDSSP.revalidate();
		pnlDSSP.repaint();
	}
	
	public void resetForm() {
	    // 1. Reset thông tin chung
	    tfMaPN.setText(phatSinhMa()); 
	    choseDate.setDate(new Date());  
	    CbxNCC.setSelectedIndex(0);     
	 
	    pnlChiTiet.removeAll();         
	    pnlChiTiet.add(pnlNull);       

	    // 3. Cập nhật lại giao diện và tổng tiền
	    pnlChiTiet.revalidate();
	    pnlChiTiet.repaint();
	    
	    setLBLTongTien(); 
	    
	    capNhatTrangThaiNutTao();
	}
	
	public String phatSinhMa() {
		
		return pN_DAO.phatSinhMaPN();
	}
}
