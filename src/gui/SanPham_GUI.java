package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import connectDB.ConnectDB;
import dao.NhaCungCap_dao;
import dao.SanPham_dao;
import entity.DonViTinh;
import entity.HanSuDung;
import entity.LoaiSanPham;
import entity.NhaCungCap;
import entity.SanPham;
import entity.TrangThai;

public class SanPham_GUI extends JPanel implements ActionListener, MouseListener {

	private JButton btnThemNCC;
	private JButton btnChonAnh;
	private JTextField txtMaSP;
	private JComboBox<NhaCungCap> cbNhaCC;
	private JTextField txtGiaBan;
	private JTextField txtSoLuong;
	private JComboBox<DonViTinh> cbDonViTinh;
	private JTextField txtTenSP;
	private JComboBox<LoaiSanPham> cbLoaiSP;
	private JComboBox<HanSuDung> cbHanSD;
	private JTextField txtTrangThai;
	private JButton btnTaoMa;
	private JButton btnSua;
	private JButton btnLuu;
	private JButton btnXoa;
	private JButton btnLamMoi;
	private JTextField txtTimTheoMaSP;
	private JTextField txtTimTheoTenSP;
	private JComboBox<String> cbTimTheoLoaiSP;
	private JComboBox<String> cbTimTheoTrangThai;
	private DefaultTableModel model;
	private JTable table;
	private SanPham_dao sp_dao;
	private NhaCungCap_dao ncc_dao;
	private JLabel lblHinhAnh;
	private String duongDanAnh;

	private TableRowSorter<DefaultTableModel> rowSorter;
	private JTextField txtGiaNhap;
	private Main main;

	public SanPham_GUI(Main main) {
		
		this.main = main;
		
		sp_dao = new SanPham_dao();
		ncc_dao = new NhaCungCap_dao();
		setSize(1100, 700);
		

		setLayout(new BorderLayout(10, 10));

		// nua tren
		JPanel pnlTopHalf = new JPanel();
		pnlTopHalf.setLayout(new BoxLayout(pnlTopHalf, BoxLayout.Y_AXIS));
		pnlTopHalf.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// header
		JPanel pnlHeader = new JPanel(new BorderLayout());
		JLabel lblTitle = new JLabel("Sản phẩm", JLabel.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 30));
		lblTitle.setForeground(Color.BLUE);
		pnlHeader.add(lblTitle, BorderLayout.CENTER);

		// nut an NCC
		JPanel pnlHeaderRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnThemNCC = new JButton("Nhà cung cấp");
		pnlHeaderRight.add(btnThemNCC);
		pnlHeader.add(pnlHeaderRight, BorderLayout.EAST);

		JPanel pnlHeaderLeft = new JPanel();
		pnlHeaderLeft.setPreferredSize(btnThemNCC.getPreferredSize());
		pnlHeader.add(pnlHeaderLeft, BorderLayout.WEST);

		pnlTopHalf.add(pnlHeader);
		pnlTopHalf.add(Box.createVerticalStrut(15));

		// thong tin
		JPanel pnlMainInfo = new JPanel();
		pnlMainInfo.setLayout(new BoxLayout(pnlMainInfo, BoxLayout.X_AXIS));

		// thong tin --- hinh anh
		JPanel pnlImageArea = new JPanel();

		pnlImageArea.setLayout(new BoxLayout(pnlImageArea, BoxLayout.Y_AXIS));
		pnlImageArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel pnlImagePlaceholder = new JPanel(new BorderLayout());
		pnlImagePlaceholder.setBackground(Color.LIGHT_GRAY);
		pnlImagePlaceholder.setPreferredSize(new Dimension(150, 200));
		pnlImagePlaceholder.setMaximumSize(new Dimension(150, 200));
		pnlImagePlaceholder.setAlignmentX(Component.CENTER_ALIGNMENT); // cho anh nam giua o bo cuc box layout

		lblHinhAnh = new JLabel();
		lblHinhAnh.setHorizontalAlignment(JLabel.CENTER);
		pnlImagePlaceholder.add(lblHinhAnh, BorderLayout.CENTER);

		btnChonAnh = new JButton("Chọn ảnh");
		btnChonAnh.setAlignmentX(Component.CENTER_ALIGNMENT);

		pnlImageArea.add(pnlImagePlaceholder);
		pnlImageArea.add(Box.createVerticalStrut(10));
		pnlImageArea.add(btnChonAnh);

		// thong tin --- form nhap lieu
		JPanel pnlFormWrapper = new JPanel(new BorderLayout());
		pnlFormWrapper.setBorder(BorderFactory.createTitledBorder("Thông tin:"));

		JPanel pnlFormFields = new JPanel();
		pnlFormFields.setLayout(new BoxLayout(pnlFormFields, BoxLayout.X_AXIS));
		pnlFormFields.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		int labelWidth = 100;
		int lableHeight = 25;
		// col 1
		JPanel pnlCol1 = new JPanel();
		pnlCol1.setLayout(new BoxLayout(pnlCol1, BoxLayout.Y_AXIS));

		// ma san pham
		JPanel pnlMaSP = new JPanel();
		pnlMaSP.setLayout(new BoxLayout(pnlMaSP, BoxLayout.X_AXIS));
		JLabel lblMaSP = new JLabel("Mã sản phẩm:");
		lblMaSP.setPreferredSize(new Dimension(labelWidth, lableHeight));
		lblMaSP.setMinimumSize(new Dimension(labelWidth, lableHeight));
		lblMaSP.setMaximumSize(new Dimension(labelWidth, lableHeight));
		txtMaSP = new JTextField();
		txtMaSP.setMaximumSize(new Dimension(Integer.MAX_VALUE, lableHeight));
		pnlMaSP.add(lblMaSP);
		pnlMaSP.add(txtMaSP);
		pnlCol1.add(pnlMaSP);
		pnlCol1.add(Box.createVerticalStrut(10));

		txtMaSP.setEditable(false);

		// nha cung cap
		JPanel pnlNhaCC = new JPanel();
		pnlNhaCC.setLayout(new BoxLayout(pnlNhaCC, BoxLayout.X_AXIS));
		JLabel lblNhaCC = new JLabel("Nhà cung cấp:");
		lblNhaCC.setPreferredSize(new Dimension(labelWidth, lableHeight));
		lblNhaCC.setMinimumSize(new Dimension(labelWidth, lableHeight));
		lblNhaCC.setMaximumSize(new Dimension(labelWidth, lableHeight));
		cbNhaCC = new JComboBox<NhaCungCap>();
		cbNhaCC.setMaximumSize(new Dimension(Integer.MAX_VALUE, lableHeight));
		pnlNhaCC.add(lblNhaCC);
		pnlNhaCC.add(cbNhaCC);
		pnlCol1.add(pnlNhaCC);
		pnlCol1.add(Box.createVerticalStrut(10));

		JPanel pnlGiaNhap = new JPanel();
		pnlGiaNhap.setLayout(new BoxLayout(pnlGiaNhap, BoxLayout.X_AXIS));
		JLabel lblGiaNhap = new JLabel("Giá nhập:");
		lblGiaNhap.setPreferredSize(new Dimension(labelWidth, lableHeight));
		lblGiaNhap.setMinimumSize(new Dimension(labelWidth, lableHeight));
		lblGiaNhap.setMaximumSize(new Dimension(labelWidth, lableHeight));
		txtGiaNhap = new JTextField();
		txtGiaNhap.setMaximumSize(new Dimension(Integer.MAX_VALUE, lableHeight));
		pnlGiaNhap.add(lblGiaNhap);
		pnlGiaNhap.add(txtGiaNhap);
		pnlCol1.add(pnlGiaNhap);
		pnlCol1.add(Box.createVerticalStrut(10));

		// gia ban
		JPanel pnlGiaBan = new JPanel();
		pnlGiaBan.setLayout(new BoxLayout(pnlGiaBan, BoxLayout.X_AXIS));
		JLabel lblGiaBan = new JLabel("Giá bán:");
		lblGiaBan.setPreferredSize(new Dimension(labelWidth, lableHeight));
		lblGiaBan.setMinimumSize(new Dimension(labelWidth, lableHeight));
		lblGiaBan.setMaximumSize(new Dimension(labelWidth, lableHeight));
		txtGiaBan = new JTextField();
		txtGiaBan.setMaximumSize(new Dimension(Integer.MAX_VALUE, lableHeight));
		pnlGiaBan.add(lblGiaBan);
		pnlGiaBan.add(txtGiaBan);
		pnlCol1.add(pnlGiaBan);
		pnlCol1.add(Box.createVerticalStrut(10));

		// so luong va don vi tinh
		JPanel pnlQtyUnit = new JPanel();
		pnlQtyUnit.setLayout(new BoxLayout(pnlQtyUnit, BoxLayout.X_AXIS));
		JLabel lblSoLuong = new JLabel("Số lượng:");
		lblSoLuong.setPreferredSize(new Dimension(labelWidth, lableHeight));
		lblSoLuong.setMinimumSize(new Dimension(labelWidth, lableHeight));
		lblSoLuong.setMaximumSize(new Dimension(labelWidth, lableHeight));
		txtSoLuong = new JTextField();
		txtSoLuong.setMaximumSize(new Dimension(Integer.MAX_VALUE, lableHeight));
		txtSoLuong.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				tuDongCapNhatTrangThai();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				tuDongCapNhatTrangThai();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				tuDongCapNhatTrangThai();
			}
		});

		JLabel lblDonVi = new JLabel("Đơn vị:");
		cbDonViTinh = new JComboBox<DonViTinh>(DonViTinh.values());
		cbDonViTinh.setMaximumSize(new Dimension(100, lableHeight));

		pnlQtyUnit.add(lblSoLuong);
		pnlQtyUnit.add(txtSoLuong);
		pnlQtyUnit.add(Box.createHorizontalStrut(10));
		pnlQtyUnit.add(lblDonVi);
		pnlQtyUnit.add(Box.createHorizontalStrut(5));
		pnlQtyUnit.add(cbDonViTinh);
		pnlCol1.add(pnlQtyUnit);

		// col 2
		JPanel pnlCol2 = new JPanel();
		pnlCol2.setLayout(new BoxLayout(pnlCol2, BoxLayout.Y_AXIS));

		// ten san pham
		JPanel pnlTenSP = new JPanel();
		pnlTenSP.setLayout(new BoxLayout(pnlTenSP, BoxLayout.X_AXIS));
		JLabel lblTenSP = new JLabel("Tên sản phẩm:");
		lblTenSP.setPreferredSize(new Dimension(labelWidth, lableHeight));
		lblTenSP.setMinimumSize(new Dimension(labelWidth, lableHeight));
		lblTenSP.setMaximumSize(new Dimension(labelWidth, lableHeight));
		txtTenSP = new JTextField();
		txtTenSP.setMaximumSize(new Dimension(Integer.MAX_VALUE, lableHeight));
		pnlTenSP.add(lblTenSP);
		pnlTenSP.add(txtTenSP);
		pnlCol2.add(pnlTenSP);
		pnlCol2.add(Box.createVerticalStrut(10));

		// loai san pham
		JPanel pnlLoaiSP = new JPanel();
		pnlLoaiSP.setLayout(new BoxLayout(pnlLoaiSP, BoxLayout.X_AXIS));
		JLabel lblLoaiSP = new JLabel("Loại sản phẩm:");
		lblLoaiSP.setPreferredSize(new Dimension(labelWidth, lableHeight));
		lblLoaiSP.setMinimumSize(new Dimension(labelWidth, lableHeight));
		lblLoaiSP.setMaximumSize(new Dimension(labelWidth, lableHeight));
		cbLoaiSP = new JComboBox<LoaiSanPham>(LoaiSanPham.values());
		cbLoaiSP.setMaximumSize(new Dimension(Integer.MAX_VALUE, lableHeight));
		pnlLoaiSP.add(lblLoaiSP);
		pnlLoaiSP.add(cbLoaiSP);
		pnlCol2.add(pnlLoaiSP);
		pnlCol2.add(Box.createVerticalStrut(10));

		// han su dung
		JPanel pnlHanSD = new JPanel();
		pnlHanSD.setLayout(new BoxLayout(pnlHanSD, BoxLayout.X_AXIS));
		JLabel lblHanSD = new JLabel("Hạn sử dụng:");
		lblHanSD.setPreferredSize(new Dimension(labelWidth, lableHeight));
		lblHanSD.setMinimumSize(new Dimension(labelWidth, lableHeight));
		lblHanSD.setMaximumSize(new Dimension(labelWidth, lableHeight));
		cbHanSD = new JComboBox<HanSuDung>(HanSuDung.values());
		cbHanSD.setMaximumSize(new Dimension(Integer.MAX_VALUE, lableHeight));
		pnlHanSD.add(lblHanSD);
		pnlHanSD.add(cbHanSD);
		pnlCol2.add(pnlHanSD);
		pnlCol2.add(Box.createVerticalStrut(10));

		// trang thai
		JPanel rowTrangThai = new JPanel();
		rowTrangThai.setLayout(new BoxLayout(rowTrangThai, BoxLayout.X_AXIS));
		JLabel lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setPreferredSize(new Dimension(labelWidth, lableHeight));
		lblTrangThai.setMinimumSize(new Dimension(labelWidth, lableHeight));
		lblTrangThai.setMaximumSize(new Dimension(labelWidth, lableHeight));
		txtTrangThai = new JTextField();
		txtTrangThai.setMaximumSize(new Dimension(Integer.MAX_VALUE, lableHeight));
		rowTrangThai.add(lblTrangThai);
		rowTrangThai.add(txtTrangThai);
		pnlCol2.add(rowTrangThai);

		txtTrangThai.setEditable(false);

		pnlCol1.setAlignmentY(Component.TOP_ALIGNMENT);
		pnlCol2.setAlignmentY(Component.TOP_ALIGNMENT);
		pnlFormFields.add(pnlCol1);
		pnlFormFields.add(Box.createHorizontalStrut(20)); // khoang cach giua col1 col2
		pnlFormFields.add(pnlCol2);
		pnlFormWrapper.add(pnlFormFields, BorderLayout.CENTER);

		// cac nut chuc nang
		JPanel pnlFormButtons = new JPanel();
		pnlFormButtons.setLayout(new BoxLayout(pnlFormButtons, BoxLayout.X_AXIS));
		pnlFormButtons.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		pnlFormButtons.add(Box.createHorizontalGlue());

		int btnWidth = 90;
		int btnHeight = 25;
		btnTaoMa = new JButton("Tạo mã");
		btnTaoMa.setPreferredSize(new Dimension(btnWidth, btnHeight));
		pnlFormButtons.add(btnTaoMa);
		pnlFormButtons.add(Box.createHorizontalStrut(15));

		btnLuu = new JButton("Lưu");
		btnLuu.setPreferredSize(new Dimension(btnWidth, btnHeight));
		pnlFormButtons.add(btnLuu);
		pnlFormButtons.add(Box.createHorizontalStrut(15));

		btnSua = new JButton("Sửa");
		btnSua.setPreferredSize(new Dimension(btnWidth, btnHeight));
		pnlFormButtons.add(btnSua);
		pnlFormButtons.add(Box.createHorizontalStrut(15));

		btnXoa = new JButton("Xoá");
		btnXoa.setPreferredSize(new Dimension(btnWidth, btnHeight));
		pnlFormButtons.add(btnXoa);
		pnlFormButtons.add(Box.createHorizontalStrut(15));

		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setPreferredSize(new Dimension(btnWidth, btnHeight));
		pnlFormButtons.add(btnLamMoi);
		pnlFormButtons.add(Box.createHorizontalGlue());
		pnlFormWrapper.add(pnlFormButtons, BorderLayout.SOUTH);

		pnlMainInfo.add(pnlImageArea);
		pnlMainInfo.add(pnlFormWrapper);

		pnlTopHalf.add(pnlMainInfo);
		pnlTopHalf.add(Box.createVerticalStrut(15));

		// tim kiem
		JPanel pnlSearch = new JPanel();
		pnlSearch.setLayout(new BoxLayout(pnlSearch, BoxLayout.X_AXIS));
		pnlSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm:"));

		pnlSearch.add(new JLabel("Mã sản phẩm:"));
		pnlSearch.add(Box.createHorizontalStrut(5));
		txtTimTheoMaSP = new JTextField();
		pnlSearch.add(txtTimTheoMaSP);
		pnlSearch.add(Box.createHorizontalStrut(15));

		pnlSearch.add(new JLabel("Tên sản phẩm:"));
		pnlSearch.add(Box.createHorizontalStrut(5));
		txtTimTheoTenSP = new JTextField();
		pnlSearch.add(txtTimTheoTenSP);
		pnlSearch.add(Box.createHorizontalStrut(15));

		pnlSearch.add(new JLabel("Loại sản phẩm:"));
		pnlSearch.add(Box.createHorizontalStrut(5));
		cbTimTheoLoaiSP = new JComboBox<String>();
		cbTimTheoLoaiSP.addItem("Tất cả");
		for (LoaiSanPham loai : LoaiSanPham.values()) {
			cbTimTheoLoaiSP.addItem(loai.getMoTa());
		}
		pnlSearch.add(cbTimTheoLoaiSP);
		pnlSearch.add(Box.createHorizontalStrut(15));

		pnlSearch.add(new JLabel("Trạng thái:"));
		pnlSearch.add(Box.createHorizontalStrut(5));
		cbTimTheoTrangThai = new JComboBox<String>();
		cbTimTheoTrangThai.addItem("Tất cả");
		for (TrangThai tt : TrangThai.values()) {
			cbTimTheoTrangThai.addItem(tt.getMoTa());
		}
		pnlSearch.add(cbTimTheoTrangThai);

		pnlTopHalf.add(pnlSearch);
		add(pnlTopHalf, BorderLayout.NORTH);

		// table
		JPanel pnlTable = new JPanel();
		pnlTable.setLayout(new BorderLayout());
		String col[] = { "Hình ảnh", "Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Giá nhập", "Giá bán",
				"Số lượng tồn", "Đơn vị tính", "Nhà cung cấp", "Hạn sử dụng", "Trạng thái" };
		model = new DefaultTableModel(col, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);

		rowSorter = new TableRowSorter<>(model);
		table.setRowSorter(rowSorter);

		
		table.setRowHeight(50);

		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(0).setMinWidth(60);
		table.getColumnModel().getColumn(0).setMaxWidth(60);

		table.getColumnModel().getColumn(1).setPreferredWidth(80); // masp
		table.getColumnModel().getColumn(2).setPreferredWidth(150); // tensp
		table.getColumnModel().getColumn(3).setPreferredWidth(100); // loaisp
		table.getColumnModel().getColumn(4).setPreferredWidth(80); // gianhap
		table.getColumnModel().getColumn(5).setPreferredWidth(80); // giaban
		table.getColumnModel().getColumn(6).setPreferredWidth(80); // soluong
		table.getColumnModel().getColumn(7).setPreferredWidth(50); // donvitinh
		table.getColumnModel().getColumn(8).setPreferredWidth(120); // ncc
		table.getColumnModel().getColumn(9).setPreferredWidth(60); // hsd
		table.getColumnModel().getColumn(10).setPreferredWidth(60); // trangthai

		table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				//ke thua jlable goc cua table
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				label.setText(""); //xoa duong dan de hien thi hinh
				label.setHorizontalAlignment(JLabel.CENTER);

				if (value != null && !value.toString().trim().isEmpty()) {
					String path = value.toString();
					File fileAnh = new File(path);

					if (fileAnh.exists()) {
						ImageIcon icon = new ImageIcon(path);
						Image img = icon.getImage().getScaledInstance(40, 45, Image.SCALE_SMOOTH);
						label.setIcon(new ImageIcon(img));
					} else {
						label.setIcon(null);
						label.setText("Lỗi ảnh");
					}
				} else {
					label.setIcon(null);
				}
				return label;
			}
		});
		
		

		JScrollPane pane = new JScrollPane(table);
		pnlTable.add(pane);
		add(pnlTable, BorderLayout.CENTER);

		DocDuLieuVaoTable();
		DocDuLieuVaoComboBox();

		table.addMouseListener(this);
		btnThemNCC.addActionListener(this);
		btnTaoMa.addActionListener(this);
		btnLuu.addActionListener(this);
		btnChonAnh.addActionListener(this);
		btnSua.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnXoa.addActionListener(this);

		cbTimTheoLoaiSP.addActionListener(this);
		cbTimTheoTrangThai.addActionListener(this);
		txtTimTheoMaSP.getDocument().addDocumentListener(searchListener);
		txtTimTheoTenSP.getDocument().addDocumentListener(searchListener);
		
		khoaChucNang();
	}



	// ham doc du lieu vao bang
	public void DocDuLieuVaoTable() {
		//Danh : tui thêm xóa dữ liệu bản để update mỗi khi thanh toán xog
		xoaTatCaDuLieuTrongBang();
		ArrayList<SanPham> listSP = sp_dao.getTableSanPham();
		for (SanPham sp : listSP) {
			model.addRow(new Object[] { sp.getHinhAnh(), sp.getMaSP(), sp.getTenSP(), sp.getLoaiSanPham(),
					sp.getGiaNhap(), sp.getGiaBan(), sp.getSoLuongTon(), sp.getDonViTinh(), sp.getNhaCungCap(),
					sp.getHanSD(), sp.getTrangThai() });
		}
	}

	private void xoaTatCaDuLieuTrongBang() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged();
	}

	// ham doc du lieu vao combobox
	private void DocDuLieuVaoComboBox() {
		cbNhaCC.removeAllItems();
		ArrayList<NhaCungCap> listNCC = ncc_dao.getTableNhaCungCap();
		for (NhaCungCap ncc : listNCC) {
			cbNhaCC.addItem(ncc);
		}
	}

	// ham hien thi anh
	private void hienThiAnh(String path) {
		if (path == null || path.trim().isEmpty()) {
			lblHinhAnh.setIcon(null);
			return;
		}
		File fileAnh = new File(path);
		if (fileAnh.exists()) {
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
			Image scaledImg = img.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
			lblHinhAnh.setIcon(new ImageIcon(scaledImg));
		} else {
			lblHinhAnh.setIcon(null);
		}
	}

	// ham phat sinh maSP
	private String phatSinhMaSP() {
		ArrayList<SanPham> listSP = sp_dao.getTableSanPham();
		if (listSP.isEmpty()) {
			return "SP1";
		}

		int max = 0;
		for (SanPham sp : listSP) {
			String ma = sp.getMaSP();
			try {

				int so = Integer.parseInt(ma.substring(2));
				if (so > max) {
					max = so;
				}
			} catch (Exception e) {

			}
		}

		return String.format("SP%d", max + 1);
	}
	private void khoaChucNang() {
		if(!main.phanQuyen()) {
		
			btnChonAnh.setEnabled(false);
			btnTaoMa.setEnabled(false);
			btnLuu.setEnabled(false);
			btnSua.setEnabled(false);
			btnLamMoi.setEnabled(false);
			btnXoa.setEnabled(false);
			btnThemNCC.setEnabled(false);
		}
	}

	// validate
	private boolean validData() {
		String ma = txtMaSP.getText().trim();
		String ten = txtTenSP.getText().trim();
		String giaBan = txtGiaBan.getText().trim();
		String soLuong = txtSoLuong.getText().trim();
		String giaNhap = txtGiaNhap.getText().trim();

		if (ma.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Mã sản phẩm không được rỗng. Hãy ấn 'Tạo mã'để phát sinh mã");
			return false;
		}

		if (ten.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống");
			txtTenSP.requestFocus();
			return false;
		}
		if (!ten.matches("^\\p{Lu}.*$")) {
			JOptionPane.showMessageDialog(this, "Tên sản phẩm phải viết hoa chữ cái đầu");
			txtTenSP.requestFocus();
			txtTenSP.selectAll();
			return false;
		}
		if (giaNhap.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Giá nhập không được để trống");
			txtGiaNhap.requestFocus();
			return false;
		}
		if (!giaNhap.matches("^\\d+(\\.\\d+)?$")) {
			JOptionPane.showMessageDialog(this, "Giá nhập phải là số thực dương");
			txtGiaNhap.requestFocus();
			txtGiaNhap.selectAll();
			return false;
		} else if (Double.parseDouble(giaNhap) < 0) {
			JOptionPane.showMessageDialog(this, "Giá nhập không được nhỏ hơn 0");
			txtGiaNhap.requestFocus();
			txtGiaNhap.selectAll();
			return false;
		}
		if (giaBan.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Giá bán không được để trống");
			txtGiaBan.requestFocus();
			return false;
		}
		if (!giaBan.matches("^\\d+(\\.\\d+)?$")) {
			JOptionPane.showMessageDialog(this, "Giá bán phải là số thực dương");
			txtGiaBan.requestFocus();
			txtGiaBan.selectAll();
			return false;
		} else if (Double.parseDouble(giaBan) < 0) {
			JOptionPane.showMessageDialog(this, "Giá bán không được nhỏ hơn 0");
			txtGiaBan.requestFocus();
			txtGiaBan.selectAll();
			return false;
		}

		if (soLuong.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Số lượng không được để trống");
			txtSoLuong.requestFocus();
			return false;
		}
		if (!soLuong.matches("^\\d+$")) {
			JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên dương");
			txtSoLuong.requestFocus();
			txtSoLuong.selectAll();
			return false;
		}

		if (duongDanAnh == null || duongDanAnh.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn hình ảnh cho sản phẩm!");
			return false;
		}

		return true;
	}

	// loc du lieu
	private void locDuLieu() {
		try {
			ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>();
			// loc masp
			String maSP = txtTimTheoMaSP.getText().trim();
			if (!maSP.isEmpty()) {
				filters.add(RowFilter.regexFilter("(?i)" + maSP, 1));
			}

			// loc tensp
			String tenSP = txtTimTheoTenSP.getText().trim();
			if (!tenSP.isEmpty()) {
				filters.add(RowFilter.regexFilter("(?i)" + tenSP, 2));
			}

			// loc loaisp
			if (cbTimTheoLoaiSP.getSelectedItem() != null) {
				String loaiSP = cbTimTheoLoaiSP.getSelectedItem().toString();
				if (!loaiSP.equals("Tất cả")) {
					filters.add(RowFilter.regexFilter("(?i)" + loaiSP, 3));
				}
			}

			// loctrangthai
			if (cbTimTheoTrangThai.getSelectedItem() != null) {
				String trangThai = cbTimTheoTrangThai.getSelectedItem().toString();
				if (!trangThai.equals("Tất cả")) {
					filters.add(RowFilter.regexFilter("(?i)" + trangThai, 10));
				}
			}

			// tat ca dieu kien
			if (filters.isEmpty()) {
				rowSorter.setRowFilter(null);
			} else {
				rowSorter.setRowFilter(RowFilter.andFilter(filters));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void tuDongCapNhatTrangThai() {
		try {
			String text = txtSoLuong.getText().trim();
			if (!text.isEmpty()) {
				int soLuong = Integer.parseInt(text);
				TrangThai tt = (soLuong > 0) ? TrangThai.CON_HANG : TrangThai.HET_HANG;
				txtTrangThai.setText(tt.toString());
			} else {
				txtTrangThai.setText("");
			}
		} catch (NumberFormatException ex) {
			txtTrangThai.setText("");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o.equals(btnThemNCC)) {
			NhaCungCap_GUI ncc_gui = new NhaCungCap_GUI();
//			ncc_gui.setDefaultCloseOperation();
			ncc_gui.addWindowListener(new WindowAdapter() {
		        @Override
		        public void windowClosed(WindowEvent windowEvent) {
		            // load lai du lieu 
		            DocDuLieuVaoComboBox(); 
		        }
		    });
			ncc_gui.setVisible(true);
		} else if (o.equals(btnTaoMa)) {
			txtMaSP.setText("");
			txtTenSP.setText("");
			txtGiaNhap.setText("");
			txtGiaBan.setText("");
			txtSoLuong.setText("");
			txtTrangThai.setText("");
			cbNhaCC.setSelectedIndex(0);
			cbLoaiSP.setSelectedIndex(0);
			cbHanSD.setSelectedIndex(0);
			cbDonViTinh.setSelectedIndex(0);
			duongDanAnh = "";
			lblHinhAnh.setIcon(null);
			txtMaSP.setText(phatSinhMaSP());
			txtMaSP.setEditable(false);

			txtTenSP.requestFocus();
		} else if (o.equals(btnLuu)) {
			if (validData()) {

				try {
					String maSP = txtMaSP.getText().trim();
					String tenSP = txtTenSP.getText().trim();
					LoaiSanPham loaiSP = (LoaiSanPham) cbLoaiSP.getSelectedItem();
					double giaBan = Double.parseDouble(txtGiaBan.getText().trim());
					double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
					int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
					DonViTinh dvt = (DonViTinh) cbDonViTinh.getSelectedItem();
					NhaCungCap ncc = (NhaCungCap) cbNhaCC.getSelectedItem();
					String hinhAnh = (duongDanAnh != null) ? duongDanAnh : "";
					HanSuDung hsd = (HanSuDung) cbHanSD.getSelectedItem();
					TrangThai tt = (soLuong > 0) ? TrangThai.CON_HANG : TrangThai.HET_HANG;

					txtTrangThai.setText(tt.toString());

					SanPham sp = new SanPham(maSP, tenSP, loaiSP, giaBan, giaNhap, soLuong, dvt, ncc, hinhAnh, hsd, tt);

					if (sp_dao.themSanPham(sp)) {
						model.addRow(new Object[] { sp.getHinhAnh(), sp.getMaSP(), sp.getTenSP(), sp.getLoaiSanPham(),
								sp.getGiaNhap(), sp.getGiaBan(), sp.getSoLuongTon(), sp.getDonViTinh(),
								sp.getNhaCungCap(), sp.getHanSD(), sp.getTrangThai() });
						JOptionPane.showMessageDialog(this, "Lưu sản phẩm thành công!");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
					if (ex.getMessage().contains("đã tồn tại")) {
						txtMaSP.requestFocus();
					}
				}

			}
		} else if (o.equals(btnChonAnh)) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Chọn hình ảnh sản phẩm");
			// chi cho phep chon anh
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Hình ảnh (JPG, PNG, GIF)", "jpg", "jpeg",
					"png", "gif");
			fileChooser.setFileFilter(filter);

			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				try {
					File destFolder = new File("img");
					if (!destFolder.exists()) {
						destFolder.mkdir();
					}
					// coppy file vao img
					File destFile = new File(destFolder, selectedFile.getName());
					Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

					duongDanAnh = "img/" + selectedFile.getName();
					hienThiAnh(duongDanAnh);

				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Lỗi khi lưu file ảnh!");
				}
			}

		} else if (o.equals(btnSua)) {
			int viewRow = table.getSelectedRow();
			if (viewRow < 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa trên bảng!");
				return;
			}

			if (validData()) {
				int row = table.convertRowIndexToModel(viewRow);
				try {
					String maSP = txtMaSP.getText().trim();
					String tenSP = txtTenSP.getText().trim();
					LoaiSanPham loaiSP = (LoaiSanPham) cbLoaiSP.getSelectedItem();
					double giaBan = Double.parseDouble(txtGiaBan.getText().trim());
					double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
					int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
					DonViTinh dvt = (DonViTinh) cbDonViTinh.getSelectedItem();
					NhaCungCap ncc = (NhaCungCap) cbNhaCC.getSelectedItem();
					String hinhAnh = (duongDanAnh != null) ? duongDanAnh : "";
					HanSuDung hsd = (HanSuDung) cbHanSD.getSelectedItem();
					TrangThai tt = (soLuong > 0) ? TrangThai.CON_HANG : TrangThai.HET_HANG;

					SanPham sp = new SanPham(maSP, tenSP, loaiSP, giaBan, giaNhap, soLuong, dvt, ncc, hinhAnh, hsd, tt);

					if (sp_dao.suaSanPham(sp)) {
						model.setValueAt(hinhAnh, row, 0);
						model.setValueAt(maSP, row, 1);
						model.setValueAt(tenSP, row, 2);
						model.setValueAt(loaiSP, row, 3);
						model.setValueAt(giaNhap, row, 4);
						model.setValueAt(giaBan, row, 5);
						model.setValueAt(soLuong, row, 6);
						model.setValueAt(dvt, row, 7);
						model.setValueAt(ncc, row, 8);
						model.setValueAt(hsd, row, 9);
						model.setValueAt(tt, row, 10);

						JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!");
					} else {
						JOptionPane.showMessageDialog(this, "Lỗi khi sửa sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} else if (o.equals(btnXoa)) {
			int viewRow = table.getSelectedRow();
			if (viewRow < 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa trên bảng!");
				return;
			}

			int row = table.convertRowIndexToModel(viewRow);
			String maSP = model.getValueAt(row, 1).toString();
			String tenSP = model.getValueAt(row, 2).toString();

			int confirm = JOptionPane.showConfirmDialog(this,
					"Bạn có chắc chắn muốn xóa sản phẩm '" + tenSP + " (" + maSP + ")' không?", "Xác nhận xóa",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {
				try {
					if (sp_dao.xoaSanPham(maSP)) {
						model.removeRow(row);
						JOptionPane.showMessageDialog(this, "Đã xóa sản phẩm thành công!");
						btnLamMoi.doClick();
					} else {
						JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm để xóa!", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi xóa sản phẩm", JOptionPane.ERROR_MESSAGE);
				}
			}

		} else if (o.equals(btnLamMoi)) {

			txtMaSP.setText("");
			txtTenSP.setText("");
			txtGiaNhap.setText("");
			txtGiaBan.setText("");
			txtSoLuong.setText("");
			txtTrangThai.setText("");
			cbNhaCC.setSelectedIndex(0);
			cbLoaiSP.setSelectedIndex(0);
			cbHanSD.setSelectedIndex(0);
			cbDonViTinh.setSelectedIndex(0);

			duongDanAnh = "";
			lblHinhAnh.setIcon(null);

			table.clearSelection();
			txtMaSP.setEditable(false);
		} else if (o.equals(cbTimTheoLoaiSP) || o.equals(cbTimTheoTrangThai)) {
			locDuLieu();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int viewRow = table.getSelectedRow();

		if (viewRow >= 0) {
			int row = table.convertRowIndexToModel(viewRow);
			Object objHinhAnh = model.getValueAt(row, 0);
			duongDanAnh = (objHinhAnh != null) ? objHinhAnh.toString() : "";
			hienThiAnh(duongDanAnh);
			txtMaSP.setText(model.getValueAt(row, 1).toString());
			txtTenSP.setText(model.getValueAt(row, 2).toString());
			cbLoaiSP.setSelectedItem(model.getValueAt(row, 3));
			txtGiaNhap.setText(model.getValueAt(row, 4).toString());
			txtGiaBan.setText(model.getValueAt(row, 5).toString());
			txtSoLuong.setText(model.getValueAt(row, 6).toString());
			cbDonViTinh.setSelectedItem(model.getValueAt(row, 7));
			NhaCungCap nccTable = (NhaCungCap) model.getValueAt(row, 8);
			for (int i = 0; i < cbNhaCC.getItemCount(); i++) {
				NhaCungCap nccCb = cbNhaCC.getItemAt(i);
				if (nccCb.getMaNCC().equals(nccTable.getMaNCC())) {
					cbNhaCC.setSelectedIndex(i);
					break;
				}
			}
			cbHanSD.setSelectedItem(model.getValueAt(row, 9));
			txtTrangThai.setText(model.getValueAt(row, 10).toString());

			txtMaSP.setEditable(false);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	DocumentListener searchListener = new DocumentListener() {
		@Override
		public void insertUpdate(DocumentEvent e) {
			locDuLieu();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			locDuLieu();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			locDuLieu();
		}
	};

}
