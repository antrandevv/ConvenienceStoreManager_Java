package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

//import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import entity.NhanVien;
import connectDB.ConnectDB;
import dao.NhanVien_dao;

import entity.ViTri;
import entity.CaLamViec;


public class NhanVien_GUI extends JPanel implements ActionListener, MouseListener {

	private JButton btnPickAvatar;
	
	private JTextField txtMNV;
	private JTextField txtTenNV;
	private JTextField txtSDTNV;
	private JCheckBox chkPhai;
	private JTextField txtEmail;
	private JTextField txtTuoi;
	private JTextField txtDiaChi;
	private JTextField txtHeSoLuong;
	private JTextField txtLuongCoBan;
	private JTextField txtTongLuong;
	
	private JComboBox<entity.ViTri> cbViTri;
	private JComboBox<entity.CaLamViec> cbCaLam;
	
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLamMoi;
	
	private JTextField txtTimMaNV;
	private JTextField txtTimTenNV;
	private JTextField txtTimSDTNV;
	
	private DefaultTableModel model;
	private JTable table;

	private JButton btnTim;
	
	private NhanVien_dao nv_dao = new NhanVien_dao();
    private TableRowSorter<DefaultTableModel> rowSorter;

	private JComboBox<String> cbTimCaLam;

	private String duongDanAnh;

	private JLabel lblAvatar;

	public NhanVien_GUI() {
		
		setSize(1450, 800);
		setLayout(new BorderLayout());

		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();

		// Tieu de Noth
		JPanel notharea = new JPanel();
		JLabel lblNTitile = new JLabel("QUẢN LÝ NHÂN VIÊN");
		lblNTitile.setFont(new Font("Arial", Font.BOLD, 25));
		lblNTitile.setForeground(Color.blue);
		notharea.add(lblNTitile);
		b1.add(notharea);
		b.add(b1);

		// Phan hinh anh ben trai
		Box bAva = Box.createVerticalBox();
		lblAvatar = new JLabel();
		Dimension size = new Dimension(150, 200);
		lblAvatar.setPreferredSize(size);
		lblAvatar.setMinimumSize(size);
		lblAvatar.setMaximumSize(size);
		lblAvatar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		lblAvatar.setAlignmentX(CENTER_ALIGNMENT);

		btnPickAvatar = new JButton("Chọn ảnh");
		btnPickAvatar.setAlignmentX(CENTER_ALIGNMENT);

		bAva.add(lblAvatar);
		bAva.add(Box.createVerticalStrut(10));
		bAva.add(btnPickAvatar);

		b2.add(Box.createHorizontalStrut(15));
		b2.add(bAva);
		b2.add(Box.createHorizontalStrut(15));
		
		
		// form ben phai
		JPanel form = new JPanel();
		// form, dung border
		form.setLayout(new BorderLayout(0, 20)); 
		form.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên:"));

		//chi tiet trong form
		JPanel pGridFirst = new JPanel(new GridLayout(6, 1, 0, 15));

		// Dòng 1
		JPanel pDong1 = new JPanel(new GridLayout(1, 4, 12, 0));
		pDong1.add(new JLabel("Mã nhân viên:"));
		pDong1.add(txtMNV = new JTextField(15));
		pDong1.add(new JLabel("Tên nhân viên:"));
		pDong1.add(txtTenNV = new JTextField(15));

		// Dòng 2
		JPanel pDong2 = new JPanel(new GridLayout(1, 4, 15, 0));
		pDong2.add(new JLabel("Số điện thoại:"));
		pDong2.add(txtSDTNV = new JTextField(15));
		pDong2.add(new JLabel("Email:"));         
		pDong2.add(txtEmail = new JTextField(15));

		// Dòng 3
		JPanel pDong3 = new JPanel(new GridLayout(1, 4, 15, 0));
		pDong3.add(new JLabel("Vị trí:"));
		cbViTri = new JComboBox<entity.ViTri>(entity.ViTri.values());
		pDong3.add(cbViTri);
		pDong3.add(new JLabel("Ca làm:"));
		cbCaLam = new JComboBox<entity.CaLamViec>(entity.CaLamViec.values());
		pDong3.add(cbCaLam);

		// Dòng 4 tuoi, gioi tinh, dung grid roi chinh lai box
		JPanel pDong4 = new JPanel(new GridLayout(1, 4, 15, 0));
		pDong4.add(new JLabel("Tuổi:"));          
		pDong4.add(txtTuoi = new JTextField(15));   
//		pDong4.add(Box.createHorizontalGlue()); 
		pDong4.add(new JLabel("Giới tính:"));
		JPanel pGioiTinh = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // cum gioi tinh, gom nhom
		pGioiTinh.add(chkPhai = new JCheckBox());
		pGioiTinh.add(new JLabel("Nữ"));
		pDong4.add(pGioiTinh);

		// border layout chia chieu dai va chieu rong
		JPanel pDong5 = new JPanel(new BorderLayout(15, 0));
		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		// chieu rong set cung
		lblDiaChi.setPreferredSize(new Dimension(95, 20)); 
		pDong5.add(lblDiaChi, BorderLayout.WEST);
		pDong5.add(txtDiaChi = new JTextField(), BorderLayout.CENTER);
		
		JPanel pDong6 = new JPanel(new GridLayout(1, 6, 15, 0)); 

		pDong6.add(new JLabel("Hệ số lương:"));
		pDong6.add(txtHeSoLuong = new JTextField(15));

		pDong6.add(new JLabel("Lương cơ bản:"));
		pDong6.add(txtLuongCoBan = new JTextField(15));

		// THÊM ĐOẠN NÀY VÀO:
		pDong6.add(new JLabel("Tổng lương:"));
		pDong6.add(txtTongLuong = new JTextField(15)); // loi null
		txtTongLuong.setEditable(false);               // 
		txtTongLuong.setBackground(Color.LIGHT_GRAY);

		pGridFirst.add(pDong1); 
		pGridFirst.add(pDong2); 
		pGridFirst.add(pDong3); 
		pGridFirst.add(pDong4);
		pGridFirst.add(pDong5);
		pGridFirst.add(pDong6);

		// nut bam la flowlayout
		JPanel pGridSecond = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0)); 
		Dimension btnSize = new Dimension(120, 30); // thu nho nut

		btnThem = new JButton("Thêm"); btnThem.setPreferredSize(btnSize);
		btnSua = new JButton("Sửa"); btnSua.setPreferredSize(btnSize);
		btnXoa = new JButton("Xóa"); btnXoa.setPreferredSize(btnSize);
		btnLamMoi = new JButton("Làm mới"); btnLamMoi.setPreferredSize(btnSize);

		pGridSecond.add(btnThem);
		pGridSecond.add(btnSua);
		pGridSecond.add(btnXoa);
		pGridSecond.add(btnLamMoi);

		// them 2 khu vuc vao form
		form.add(pGridFirst, BorderLayout.CENTER); 
		form.add(pGridSecond, BorderLayout.SOUTH); 

		b2.add(form);
		b.add(b2);

		// Tim kiem
		JPanel pTim = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); //flow layout
		pTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm nhân viên"));
		
		pTim.add(new JLabel("Mã nhân viên:"));
		txtTimMaNV = new JTextField(10);
		pTim.add(txtTimMaNV);
		
		pTim.add(new JLabel("Tên nhân viên:"));
		txtTimTenNV = new JTextField(10);
		pTim.add(txtTimTenNV);
		
		pTim.add(new JLabel("Số điện thoại:"));
		txtTimSDTNV = new JTextField(10);
		pTim.add(txtTimSDTNV);
		
		
		
		pTim.add(new JLabel("Ca làm:"));
		cbTimCaLam = new JComboBox<String>();

		// muc tat ca
		cbTimCaLam.addItem("Tất cả");

		// loop tim theo enum
		for (CaLamViec ca : CaLamViec.values()) {
		    cbTimCaLam.addItem(ca.name());
		}
		pTim.add(cbTimCaLam);
		
		btnTim= new JButton("Tìm");
		pTim.add(btnTim);
		
		b3.add(pTim);
		b.add(b3);

		//Box noth
		this.add(b, BorderLayout.NORTH);

		// Phan center, table
		String[] tititleTable = {"Hình ảnh","Mã NV", "Tên NV", "Giới tính", "Tuổi", "SĐT", "Email", "Địa chỉ", "Ca làm", "Vị trí", "Hệ số", "Lương CB", "Tổng lương"};
		model = new DefaultTableModel(tititleTable, 0) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        // khong cho sua truc tiep
		        return false;
		    }
		};

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane, BorderLayout.CENTER);
		
		//xu ly su kien
		rowSorter = new TableRowSorter<>(model);
        table.setRowSorter(rowSorter);
        
        //Them vao table
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(0).setMinWidth(60);
		table.getColumnModel().getColumn(0).setMaxWidth(60);
		
		//Hien thi hinh anh tren table
		// 1. Tăng chiều cao hàng để nhìn thấy ảnh (Rất quan trọng)
		table.setRowHeight(60); 

		// 2. Thiết lập bộ vẽ (Renderer) cho cột số 0
		table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
		            boolean hasFocus, int row, int column) {
		        
		        // Kế thừa JLabel từ lớp cha
		        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		        label.setText(""); // Xóa đường dẫn text để hiện hình
		        label.setHorizontalAlignment(JLabel.CENTER);

		        if (value != null && !value.toString().trim().isEmpty()) {
		            String path = value.toString();
		            File fileAnh = new File(path);

		            if (fileAnh.exists()) {
		                ImageIcon icon = new ImageIcon(path);
		                // Scale ảnh cho vừa ô (40x55)
		                Image img = icon.getImage().getScaledInstance(40, 55, Image.SCALE_SMOOTH);
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

        // sk click, nut bam
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnTim.addActionListener(this);
        btnPickAvatar.addActionListener(this);      
        table.addMouseListener(this);

        // ket noi csdl
        try {
            connectDB.ConnectDB.getInstance().connect();
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
            DocDuLieuVaoTable();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới Cơ sở dữ liệu!\n" + e.getMessage());
        }
	}

	

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			new NhanVien_GUI().setVisible(true);
		});
	}
	
	private void DocDuLieuVaoTable() {
	    model.setRowCount(0); 
	    ArrayList<NhanVien> listNV = nv_dao.getTableNhanVien();
	    for (NhanVien nv : listNV) {
	        model.addRow(new Object[] { 
	            nv.getHinhAnh(),
	            nv.getMaNV(), 
	            nv.getTenNV(),
	            nv.getGioiTinh() ? "Nữ" : "Nam",
	            nv.getTuoi(),
	            nv.getSoDienThoai(),
	            nv.getEmail(),
	            nv.getDiaChi(),
	            nv.getCaLamViec(),
	            nv.getViTri(),
	            nv.getHeSoLuong(), // Cột 10
	            nv.getLuongCoBan(), // Cột 11
	            String.format("%,.0f VNĐ", nv.getLuong()) //tong luong
	        });
	    }
	}
	
	private String phatSinhMaNV() {
		ArrayList<NhanVien> listNV = nv_dao.getTableNhanVien();
		if (listNV.isEmpty()) {
			return "NV1";
		}
		int max = 0;
		for (NhanVien nv : listNV) {
			String ma = nv.getMaNV();
			try {
				int so = Integer.parseInt(ma.substring(2)); // cat chu NV ra, lay so tinh toan
				if (so > max) {
					max = so;
				}
			} catch (Exception e) {
				// bo qua loi
			}
		}
		return String.format("NV%d", max + 1);
	}
	
	private boolean validData() {
		String ten = txtTenNV.getText().trim();
		String sdt = txtSDTNV.getText().trim();
		String email = txtEmail.getText().trim();
		String tuoiStr = txtTuoi.getText().trim();

		if (ten.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống!");
			txtTenNV.requestFocus();
			return false;
		}
		if (!ten.matches("(?U)^\\p{Lu}[\\p{L}\\d]*(\\s\\p{Lu}[\\p{L}\\d]*)+$")) {
			JOptionPane.showMessageDialog(this, "Tên nhân viên phải viết hoa chữ cái đầu mỗi từ!");
			txtTenNV.requestFocus();
			txtTenNV.selectAll();
			return false;
		}

		if (sdt.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống!");
			txtSDTNV.requestFocus();
			return false;
		}
		if (!sdt.matches("^0\\d{9}$")) {
			JOptionPane.showMessageDialog(this, "Số điện thoại phải bắt đầu bằng 0 và gồm 10 chữ số!");
			txtSDTNV.requestFocus();
			txtSDTNV.selectAll();
			return false;
		}

		if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			JOptionPane.showMessageDialog(this, "Email không hợp lệ!");
			txtEmail.requestFocus();
			txtEmail.selectAll();
			return false;
		}

		if (tuoiStr.isEmpty() || !tuoiStr.matches("^\\d+$")) {
			JOptionPane.showMessageDialog(this, "Tuổi phải là số nguyên!");
			txtTuoi.requestFocus();
			txtTuoi.selectAll();
			return false;
		} else {
			int tuoi = Integer.parseInt(tuoiStr);
			if (tuoi < 18) {
				JOptionPane.showMessageDialog(this, "Nhân viên phải từ đủ 18 tuổi!");
				txtTuoi.requestFocus();
				txtTuoi.selectAll();
				return false;
			}
		}
		
		String hsStr = txtHeSoLuong.getText().trim();
		if (hsStr.isEmpty() || !hsStr.matches("^\\d*\\.?\\d+$")) {
		    JOptionPane.showMessageDialog(this, "Hệ số lương phải là số!");
		    txtHeSoLuong.requestFocus();
		    return false;
		}

		// Kiểm tra lương cơ bản
		String lcbStr = txtLuongCoBan.getText().trim();
		if (lcbStr.isEmpty() || !lcbStr.matches("^\\d+$")) {
		    JOptionPane.showMessageDialog(this, "Lương cơ bản phải là số nguyên!");
		    txtLuongCoBan.requestFocus();
		    return false;
		}

		return true;
	}
	
	private void locDuLieu() {
		try {
			ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>();
			
			String maNV = txtTimMaNV.getText().trim();
			if (!maNV.isEmpty()) {
				filters.add(RowFilter.regexFilter("(?i)" + maNV, 1)); // Cột 1: Mã NV
			}

			String tenNV = txtTimTenNV.getText().trim();
			if (!tenNV.isEmpty()) {
				filters.add(RowFilter.regexFilter("(?i)" + tenNV, 2)); // Cột 2: Tên NV
			}

			String sdtNV = txtTimSDTNV.getText().trim();
			if (!sdtNV.isEmpty()) {
				filters.add(RowFilter.regexFilter("(?i)" + sdtNV, 5)); // Cột 5: SĐT
			}
			
			String caLam = cbTimCaLam.getSelectedItem().toString();
			if (!caLam.equalsIgnoreCase("Tất cả")) {
				filters.add(RowFilter.regexFilter("(?i)" + caLam, 8)); // Cột 8: Ca làm
			}

			if (filters.isEmpty()) {
				rowSorter.setRowFilter(null);
			} else {
				rowSorter.setRowFilter(RowFilter.andFilter(filters));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		//btn lam moi
		if (o.equals(btnLamMoi)) {
			txtMNV.setText(phatSinhMaNV()); 
			txtMNV.setEditable(false);
			txtTenNV.setText("");
			txtSDTNV.setText("");
			txtEmail.setText("");
			txtTuoi.setText(""); 
			txtDiaChi.setText("");
			duongDanAnh = null;
			lblAvatar.setIcon(null);
			chkPhai.setSelected(false);
			cbViTri.setSelectedIndex(0);
			cbCaLam.setSelectedIndex(0);
			
			txtTimMaNV.setText("");
			txtTimTenNV.setText("");
			txtTimSDTNV.setText("");
			cbTimCaLam.setSelectedIndex(0);
			
			if (rowSorter != null) {
			    rowSorter.setRowFilter(null); // bo bo loc
			}
			
			table.clearSelection();
			txtTenNV.requestFocus();
			txtHeSoLuong.setText("");
			txtLuongCoBan.setText("");
		} 
		
		else if (o.equals(btnPickAvatar)) {
		    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
		    
		    // chi cho phep nhan vien nop file anh
		    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Hình ảnh", "jpg", "jpeg", "png"));
		    int result = fileChooser.showOpenDialog(this);

		    if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
		        java.io.File file = fileChooser.getSelectedFile();
		        duongDanAnh = file.getAbsolutePath(); // luu lai duong dan vao database

		        // chinh kich thuoc
		        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(
		            new javax.swing.ImageIcon(duongDanAnh).getImage().getScaledInstance(150, 200, java.awt.Image.SCALE_SMOOTH)
		        );
		        lblAvatar.setIcon(icon); 
		    }
		}
		
		//btn them
		else if (o.equals(btnThem)) {
		    if (validData()) {
		        try {
		            // phat sinh Ma nhan vien
		            String ma = txtMNV.getText().trim().isEmpty() ? phatSinhMaNV() : txtMNV.getText().trim();
		            String ten = txtTenNV.getText().trim();
		            boolean gioiTinh = chkPhai.isSelected();
		            int tuoi = Integer.parseInt(txtTuoi.getText().trim());
		            String sdt = txtSDTNV.getText().trim();
		            String email = txtEmail.getText().trim();
		            String diaChi = txtDiaChi.getText().trim();
		            CaLamViec caLam = (CaLamViec) cbCaLam.getSelectedItem();
		            ViTri viTri = (ViTri) cbViTri.getSelectedItem();

		            // lay anh dung
		            String hinhAnh = duongDanAnh != null ? duongDanAnh : "";
		            double heSo = Double.parseDouble(txtHeSoLuong.getText().trim());
		            double luongCB = Double.parseDouble(txtLuongCoBan.getText().trim());

		            NhanVien nv = new NhanVien(ma, ten, gioiTinh, tuoi, sdt, email, diaChi, caLam, viTri, hinhAnh, heSo, luongCB);


		            if (nv_dao.themNhanVien(nv)) {
		                String chuoiGioiTinh = gioiTinh ? "Nữ" : "Nam";

		                model.addRow(new Object[]{
		                	    nv.getHinhAnh(), nv.getMaNV(), nv.getTenNV(), chuoiGioiTinh, 
		                	    nv.getTuoi(), nv.getSoDienThoai(), nv.getEmail(), nv.getDiaChi(),
		                	    nv.getCaLamViec(), nv.getViTri(), 
		                	    nv.getHeSoLuong(), nv.getLuongCoBan(), // Cột 10, 11
		                	    String.format("%,.0f VNĐ", nv.getLuong()) // Cột 12
		                	});

		                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
		                btnLamMoi.doClick();
		            }

		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
		        }
		    }
		}
		else if (o.equals(btnPickAvatar)) {
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

		} 
		// btn sua
		else if (o.equals(btnSua)) {
		    int viewRow = table.getSelectedRow();
		    if (viewRow < 0) {
		        JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!");
		        return;
		    }

		    if (validData()) {
		        int row = table.convertRowIndexToModel(viewRow);

		        try {
		            String ma = txtMNV.getText().trim();
		            String ten = txtTenNV.getText().trim();
		            boolean gioiTinh = chkPhai.isSelected();
		            int tuoi = Integer.parseInt(txtTuoi.getText().trim());
		            String sdt = txtSDTNV.getText().trim();
		            String email = txtEmail.getText().trim();
		            String diaChi = txtDiaChi.getText().trim();
		            CaLamViec caLam = (CaLamViec) cbCaLam.getSelectedItem();
		            ViTri viTri = (ViTri) cbViTri.getSelectedItem();

		            // neu chon anh thi chon moi, khong thi chon cu
		            String hinhAnh = (duongDanAnh != null) 
		                    ? duongDanAnh 
		                    : (model.getValueAt(row, 0) != null ? model.getValueAt(row, 0).toString() : "");

		            double heSo = Double.parseDouble(txtHeSoLuong.getText().trim());
		            double luongCB = Double.parseDouble(txtLuongCoBan.getText().trim());

		            NhanVien nv = new NhanVien(ma, ten, gioiTinh, tuoi, sdt, email, diaChi, caLam, viTri, hinhAnh, heSo, luongCB);

		            if (nv_dao.suaNhanVien(nv)) {
		                String chuoiGioiTinh = gioiTinh ? "Nữ" : "Nam";

		                model.setValueAt(hinhAnh, row, 0);
		                model.setValueAt(ma, row, 1);
		                model.setValueAt(ten, row, 2);
		                model.setValueAt(chuoiGioiTinh, row, 3);
		                model.setValueAt(tuoi, row, 4);
		                model.setValueAt(sdt, row, 5);
		                model.setValueAt(email, row, 6);
		                model.setValueAt(diaChi, row, 7);
		                model.setValueAt(caLam.name(), row, 8);
		                model.setValueAt(viTri.name(), row, 9);
		                model.setValueAt(heSo, row, 10);
		                model.setValueAt(luongCB, row, 11);
		                model.setValueAt(String.format("%,.0f VNĐ", nv.getLuong()), row, 12);

		                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
		                btnLamMoi.doClick();
		            }

		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(this, "Lỗi cập nhật!");
		        }
		    }
		}
		
		//btn xoa
		else if (o.equals(btnXoa)) {
			int viewRow = table.getSelectedRow();
			if (viewRow < 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
				return;
			}

			int row = table.convertRowIndexToModel(viewRow);
			String maNV = model.getValueAt(row, 1).toString();
			String tenNV = model.getValueAt(row, 2).toString();

			int confirm = JOptionPane.showConfirmDialog(this, 
				"Bạn có chắc muốn xóa nhân viên '" + tenNV + "'?", "Xác nhận", 
				JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				try {
					if (nv_dao.xoaNhanVien(maNV)) {
						model.removeRow(row);
						JOptionPane.showMessageDialog(this, "Xóa thành công!");
						btnLamMoi.doClick();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi xóa", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		// btn tim
		else if (o.equals(btnTim)) {
		    locDuLieu(); 
		}
	}

	private void hienThiAnh(String path) {
		if (path == null || path.trim().isEmpty()) {
			lblAvatar.setIcon(null);
			return;
		}
		File fileAnh = new File(path);
		if (fileAnh.exists()) {
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
			Image scaledImg = img.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
			lblAvatar.setIcon(new ImageIcon(scaledImg));
		} else {
			lblAvatar.setIcon(null);
		}
	}



	@Override
	public void mouseClicked(MouseEvent e) {
	    int viewRow = table.getSelectedRow();
	    if (viewRow >= 0) {
	        int row = table.convertRowIndexToModel(viewRow);

	        // 2. Cập nhật hình ảnh (Cột 0)
	        Object objAnh = model.getValueAt(row, 0);
	        duongDanAnh = (objAnh != null) ? objAnh.toString() : "";
	        hienThiAnh(duongDanAnh);

	        // 3. Đổ dữ liệu cơ bản lên TextField (Cột 1 -> Cột 7)
	        String maNV = model.getValueAt(row, 1).toString();
	        txtMNV.setText(maNV);
	        txtTenNV.setText(model.getValueAt(row, 2).toString());
	        
	        // Giới tính (Cột 3)
	        String gioiTinh = model.getValueAt(row, 3).toString();
	        chkPhai.setSelected(gioiTinh.equalsIgnoreCase("Nữ"));
	        
	        txtTuoi.setText(model.getValueAt(row, 4).toString());
	        txtSDTNV.setText(model.getValueAt(row, 5).toString());
	        txtEmail.setText(model.getValueAt(row, 6).toString());
	        txtDiaChi.setText(model.getValueAt(row, 7).toString());

	        // 4. Đổ dữ liệu Enum lên ComboBox (Cột 8, 9)
	        try {
	            String caStr = model.getValueAt(row, 8).toString();
	            String vtStr = model.getValueAt(row, 9).toString();
	            if(!caStr.isEmpty()) cbCaLam.setSelectedItem(entity.CaLamViec.valueOf(caStr));
	            if(!vtStr.isEmpty()) cbViTri.setSelectedItem(entity.ViTri.valueOf(vtStr));
	        } catch (Exception ex) {
	            // Tránh crash nếu dữ liệu Enum trong table bị trống
	        }

	        // 5. Hiển thị Hệ số và Lương cơ bản (Cột 10, 11)
	        txtHeSoLuong.setText(model.getValueAt(row, 10).toString());
	        txtLuongCoBan.setText(model.getValueAt(row, 11).toString());

	        // 6. GỌI DAO ĐỂ LẤY TỔNG LƯƠNG (GUI không tự tính toán)
	        double tongLuong = nv_dao.layLuongNhanVien(maNV);
	        txtTongLuong.setText(String.format("%,.0f VNĐ", tongLuong));

	        // 7. Khóa Mã nhân viên và Tổng lương (Không cho sửa tay)
	        txtMNV.setEditable(false);
	        txtTongLuong.setEditable(false);
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

	
}